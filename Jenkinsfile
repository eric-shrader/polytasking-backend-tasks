pipeline {
    agent any

    environment {
		DOCKERHUB_CREDENTIALS=credentials('Docker_Hub')
	}

    stages {
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'mvn clean package -Dmaven.test.skip=true'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                sh "mvn test"
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                sh 'docker build -t ericshrader/polytasking_task_service .'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                echo 'Pushing to Docker Hub...'
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                sh 'docker push ericshrader/polytasking_task_service'
            }
        }
    }

    post {
		always {
			sh 'docker logout'
		}
	}
}