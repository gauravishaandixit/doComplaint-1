pipeline {
        environment {
            registry = "dhruvin32/docomplaintbackend"
            registryCredential = 'docker-hub-credentials'
            dockerImage = ''
             //dockerImageLatest = ''
        }

        agent any
        stages{
    	    stage('Clone Repository') {
			        steps{
			        echo "Poolig git gepository..."
			        git "https://github.com/dhruvin32/doComplaint.git"
			        }
    	        }

    	stage('Clean & Validate') {
    	    steps{
			echo "Cleaning privious targets..."
			sh " mvn clean validate"
			}
    	}

    	stage('Test') {
    	    steps{
			echo "Performing unit testing..."
			sh " mvn test"
			}
    	}

    	stage('Package') {
    	    steps{
			echo "Packaging the project and generating jars..."
			sh " mvn package"
			}
    	}

    	stage('verify') {
        	steps{
        	echo "Performing integration testing..."
        	sh " mvn verify"
        	}
        }

        stage('Building image') {
               steps{
                 script {
                   dockerImage = docker.build registry + ":$BUILD_NUMBER"
                   //dockerImageLatest = docker.build registry + ":latest"
                 }
               }
            }

        stage('Deploy Image') {
           steps{
             script {
               docker.withRegistry( '', registryCredential ) {
                 dockerImage.push()
                 //dockerImageLatest.push()
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