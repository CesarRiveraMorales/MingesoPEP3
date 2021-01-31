pipeline {
  agent {
    docker {
      image 'node:12'
      args '--network mingesopep3_mynet'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'cd ./frontend; npm -g install'
      }
    }

  }
}