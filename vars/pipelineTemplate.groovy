def call(Map config) {

    pipeline {
        agent any

        stages {

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

            if (config.deploy) {
                stage('Deploy') {
                    steps {
                        sh config.deployCommand
                    }
                }
            }
        }
    }
}
