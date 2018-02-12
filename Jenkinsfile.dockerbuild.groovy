#!/usr/bin/env groovy


properties(
        [
                //pipelineTriggers([cron('H/30 * * * *')]),
        ]
)

node('maven') {
    stage ('build for base docker image') {
        openshiftBuild(namespace: '${PROJECT}',
                buildConfig: '${APPLICATION_TYPE}-${ENVIRONMENT_NAME}-docker-build',
                showBuildLogs: 'true',
                waitTime: '3000000')
    }
}