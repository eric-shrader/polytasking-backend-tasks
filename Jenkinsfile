pipeline {
    agent any
    stages {
        stage('Build') {
            agent {
                any {
                    image 'maven:3.9.4-eclipse-temurin-17-alpine'
                    reuseNode true
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn clean package'
            }
        }
        stage('test') {
            agent {
                any {
                    image 'maven:3.9.4-eclipse-temurin-17-alpine'
                    reuseNode true
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn test'
            }
        }
    }
}