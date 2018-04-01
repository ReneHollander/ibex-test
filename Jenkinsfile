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
            sshagent(credentials: ['deployer-production']) {
                stage('Deploy to production') {
                    sh "ssh -o StrictHostKeyChecking=no deployer@production01.server.ibex.renehollander.at \"mkdir -p ~/deployment\""
                    sh "scp -o StrictHostKeyChecking=no deployment/deploy-production.sh deployer@production01.server.ibex.renehollander.at:~/deployment/deploy-production.sh"
                    sh "ssh -o StrictHostKeyChecking=no deployer@production01.server.ibex.renehollander.at \"chmod +x ~/deployment/deploy-production.sh\""

                    echo 'Pre-deploy phase'
                    sh "ssh -o StrictHostKeyChecking=no deployer@production01.server.ibex.renehollander.at \"~/deployment/deploy-production.sh pre-deploy ${BUILD_NUMBER}\""
                    sh "scp -o StrictHostKeyChecking=no deployment/base.yml deployer@production01.server.ibex.renehollander.at:~/deployment/base.yml"
                    sh "scp -o StrictHostKeyChecking=no deployment/production.yml deployer@production01.server.ibex.renehollander.at:~/deployment/production.yml"

                    echo 'Deploy phase'
                    sh "ssh -o StrictHostKeyChecking=no deployer@production01.server.ibex.renehollander.at \"~/deployment/deploy-production.sh deploy ${BUILD_NUMBER}\""

                    echo 'Post-deploy phase'
                    sh "ssh -o StrictHostKeyChecking=no deployer@production01.server.ibex.renehollander.at \"~/deployment/deploy-production.sh post-deploy ${BUILD_NUMBER}\""
                }
            }
        }

        if (env.BRANCH_NAME.equals("staging")) {
            stage('Deploy to staging') {
                echo 'Deployed to staging'
            }
        }
    }
}
