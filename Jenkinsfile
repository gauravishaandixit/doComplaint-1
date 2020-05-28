pipeline {
  environment {
    registry = "vatsal199/docomplaintbackend"
    registryCredential = 'docker-hub-credentials'
    dockerImage = ''
    dockerImageLatest = ''
  }
  agent any
  stages {
    stage('Cloning Git Backend') {
      steps {
        git 'https://github.com/vatsal199/doComplaint.git'
      }
    }
    stage('Testing Project'){
         steps {
             sh 'mvn clean test'
         }
    }
    stage('Build Executable Jar'){
        steps {
             sh 'mvn package'
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
    stage('Push Image') {
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
    stage('Deploy On Node') {
      steps{
          step([
             $class:"RundeckNotifier",
             includeRundeckLogs:true,
             jobId: "9f117bd1-3f7e-465d-8c04-441c30f933d1",
             rundeckInstance: "RundeckConf",
             shouldFailTheBuild: true,
             shouldWaitForRundeckJob: true,
             tailLog: true
          ])
      }
    }

  }
}