#!groovy

node('docker') {
    def frontend
    def backend

    stage('Checkout') {
        checkout scm
    }

    docker.withRegistry('https://registry.ibex.renehollander.at/', 'ibex-registry-credentials') {
        stage('Build Frontend') {
            frontend = docker.build("ibex/frontend:$BRANCH_NAME", "--pull -f ./deployment/frontend/Dockerfile .")
        }

        stage('Build Backend') {
            backend = docker.build("ibex/backend:$BRANCH_NAME", "--pull -f ./deployment/backend/Dockerfile .")
        }

        if (env.BRANCH_NAME.equals("production") || env.BRANCH_NAME.equals("staging")) {
            stage('Push Images') {
                frontend.push()
                backend.push()
            }
        }

        if (env.BRANCH_NAME.equals("production")) {
            stage('Deploy to production') {
                echo 'Deployed to production'
            }
        }

        if (env.BRANCH_NAME.equals("staging")) {
            stage('Deploy to staging') {
                echo 'Deployed to staging'
            }
        }
    }
}
