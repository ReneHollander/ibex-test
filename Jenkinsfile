#!groovy
import java.time.Instant;


node('docker') {
    def frontend
    def backend

    stage('Checkout') {
        checkout scm
        def timestamp = Instant.now()
        env.IBEX_BUILD_LONG_HASH = sh(returnStdout: true, script: "git rev-parse HEAD").trim()
        env.IBEX_BUILD_SHORT_HASH = sh(returnStdout: true, script: "git rev-parse --short HEAD").trim()
        env.IBEX_BUILD_BRANCH = env.BRANCH_NAME
        env.IBEX_BUILD_JOB_ID = env.BUILD_NUMBER
        env.IBEX_BUILD_VERSION_TAG = "$env.IBEX_BUILD_SHORT_HASH-$env.IBEX_BUILD_JOB_ID"
        env.IBEX_BUILD_TIME = timestamp.toString()
        env.IBEX_BUILD_TIME_MILLIS = timestamp.toEpochMilli()
    }

    docker.withRegistry('https://registry.ibex.renehollander.at/', 'ibex-registry-credentials') {
        def dockerBuildArgs = "--pull --build-arg IBEX_BUILD_LONG_HASH=$IBEX_BUILD_LONG_HASH --build-arg IBEX_BUILD_SHORT_HASH=$IBEX_BUILD_SHORT_HASH --build-arg IBEX_BUILD_BRANCH=$IBEX_BUILD_BRANCH --build-arg IBEX_BUILD_JOB_ID=$IBEX_BUILD_JOB_ID --build-arg IBEX_BUILD_VERSION_TAG=$IBEX_BUILD_VERSION_TAG --build-arg IBEX_BUILD_TIME=$IBEX_BUILD_TIME --build-arg IBEX_BUILD_TIME_MILLIS=$IBEX_BUILD_TIME_MILLIS"

        stage('Build Frontend') {
            frontend = docker.build("ibex/frontend:$IBEX_BUILD_BRANCH", "$dockerBuildArgs -f ./deployment/frontend/Dockerfile .")
        }

        stage('Build Backend') {
            backend = docker.build("ibex/backend:$IBEX_BUILD_BRANCH", "$dockerBuildArgs -f ./deployment/backend/Dockerfile .")
        }

        if (env.IBEX_BUILD_BRANCH.equals("production") || env.IBEX_BUILD_BRANCH.equals("staging")) {
            stage('Push Images') {
                frontend.push()
                backend.push()
            }
        }

        if (env.IBEX_BUILD_BRANCH.equals("production")) {
            sshagent(credentials: ['deployer-production']) {
                stage('Deploy to production') {
                    sh "ssh -o StrictHostKeyChecking=no deployer@production01.server.ibex.renehollander.at \"mkdir -p ~/deployment\""
                    sh "scp -o StrictHostKeyChecking=no deployment/deploy-production.sh deployer@production01.server.ibex.renehollander.at:~/deployment/deploy-production.sh"
                    sh "ssh -o StrictHostKeyChecking=no deployer@production01.server.ibex.renehollander.at \"chmod +x ~/deployment/deploy-production.sh\""

                    echo 'Pre-deploy phase'
                    sh "ssh -o StrictHostKeyChecking=no deployer@production01.server.ibex.renehollander.at \"~/deployment/deploy-production.sh pre-deploy ${IBEX_BUILD_JOB_ID}\""
                    sh "scp -o StrictHostKeyChecking=no deployment/base.yml deployer@production01.server.ibex.renehollander.at:~/deployment/base.yml"
                    sh "scp -o StrictHostKeyChecking=no deployment/production.yml deployer@production01.server.ibex.renehollander.at:~/deployment/production.yml"

                    echo 'Deploy phase'
                    sh "ssh -o StrictHostKeyChecking=no deployer@production01.server.ibex.renehollander.at \"~/deployment/deploy-production.sh deploy ${IBEX_BUILD_JOB_ID}\""

                    echo 'Post-deploy phase'
                    sh "ssh -o StrictHostKeyChecking=no deployer@production01.server.ibex.renehollander.at \"~/deployment/deploy-production.sh post-deploy ${IBEX_BUILD_JOB_ID}\""
                }
            }
        }

        if (env.IBEX_BUILD_BRANCH.equals("staging")) {
            stage('Deploy to staging') {
                echo 'Deployed to staging'
            }
        }
    }
}
