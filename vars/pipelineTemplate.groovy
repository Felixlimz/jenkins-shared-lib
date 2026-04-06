def call(Map config) {

    pipeline {
        agent any

        stages {

            stage('Checkout') {
                steps {
                    git(
                        url: config.gitUrl,
                        branch: config.gitBranch
                    )
                }
            }

            stage('Build') {
                steps {
                    sh config.buildCommand
                }
            }

            stage('Test') {
                steps {
                    sh config.testCommand
                }
            }

            stage('SonarQube') {
                steps {
                    withSonarQubeEnv('SonarQube') {
                        sh config.sonarCommand
                    }
                }
            }

            stage('Deploy') {
                when {
                    expression {
                        return config.deploy
                    }
                }
                steps {
                    sh config.deployCommand
                }
            }
        }
    }
}
