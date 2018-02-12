#!/usr/bin/env groovy


properties(
        [
                //pipelineTriggers([cron('H/30 * * * *')]),
        ]
)

node('maven') {
    stage ('build for base docker image') {
        openshiftBuild(namespace: '${NAMESPACE}',
                buildConfig: '${NAMESPACE}-docker-build',
                showBuildLogs: 'true',
                waitTime: '3000000')
    }
}