node {

	    def app

    	stage('Clone Repository') {
			echo "Poolig git gepository..."
			git "https://github.com/dhruvin32/doComplaint.git"
    	}

    	stage('Clean & Validate') {
			echo "Cleaning privious targets..."
			sh " mvn clean validate"
    	}

    	stage('Compile') {
			echo "Compiling source code..."
			sh " mvn compile"
    	}

    	stage('Test') {
			echo "Performing unit testing..."
			sh " mvn test"
    	}

    	stage('Package') {
			echo "Packaging the project and generating jars..."
			sh " mvn package"
    	}

    	stage('verify') {
        	echo "Performing integration testing..."
        	sh " mvn verify"
        }

    	stage('Build Image') {
			echo "Generating docker image..."
        	app = docker.build("dhruvin32/docomplaintbackend")
    	}

    	stage('Push Image') {
			echo "Uploading docker image to docker hub..."
			docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
		    		app.push("${env.BUILD_NUMBER}")
		    		app.push("latest")
			}
    	}

}