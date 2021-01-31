pipeline {

    agent{
        docker {
            image 'node:12' 
            args '-p 8081:8081 --network mingesopep3_mynet'
        }
    }
    stages {
        stage('Instalar recursos') {
            steps {
                sh 'cd ./frontend/;npm -g install'
                sh 'cd ./frontend/;npm install http-server -g'
                sh 'cd ./frontend/;npm install -g @vue/cli'
            }
        }
        stage('Analisis de codigo estatico'){
            steps {
                sh 'cd ./frontend/;npm run lint'
            }
        }
        stage('Build') {
            steps {
                sh 'cd ./frontend/;npm run serve'
            }
        }
    }
}