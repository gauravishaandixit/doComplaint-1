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
}