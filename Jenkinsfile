pipeline {

    agent{
        docker {
            image 'node'
            args '-p 8081:8081'
        }
    }
    environment {
        CI = true
    }
    stages {
        stage('Clonar repositorio') {
            steps {
                git 'https://github.com/CesarRiveraMorales/MingesoPEP3.git'
            }
        }
        stage('Instalar recursos') {
            steps {
                sh 'npm -g install'
                sh 'npm install http-server -g'
            }
        }
        stage('Analisis de codigo estatico'){
            steps {
                sh 'npm run lint'
            }
        }
        stage('Build') {
            steps {
                sh 'npm run serve'
            }
        }
    }
}