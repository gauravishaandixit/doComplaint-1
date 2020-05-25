pipeline {
  environment {
    registry = "dhruvin32/docomplaintbackend"
    registryCredential = 'docker-hub-credentials'
    dockerImage = ''
    dockerImageLatest = ''
  }
  agent any
  stages {
    stage('Cloning Git Backend') {
      steps {
        git 'https://github.com/dhruvin32/doComplaint.git'
      }
    }
    stage('Build Executable Jar'){
        steps {
             sh 'mvn clean test package'
        }
    }

    stage('Building image') {
       steps{
         script {
           dockerImage = docker.build registry + ":$BUILD_NUMBER"
           dockerImageLatest = docker.build registry + ":latest"
         }
       }
    }
    stage('Deploy Image') {
      steps{
        script {
          docker.withRegistry( '', registryCredential ) {
            dockerImage.push()
            dockerImageLatest.push()
          }
        }
      }
    }
    stage('Remove Unused docker image') {
      steps{
        sh "docker rmi $registry:$BUILD_NUMBER"
      }
    }
  }
}