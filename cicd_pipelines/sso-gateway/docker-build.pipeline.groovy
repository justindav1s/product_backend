#!groovy

node('nodejs') {

  stage('initialise') {
    def dev_project = "${org}-dev"
    sh "echo Dev Project : ${dev_project}"
  }


  stage('Checkout Source') {
    git url: "${git_url}", branch: 'master'
  }


  dir("deploy_istio_mtls_authn_authz/sso-gatekeeper") {
    //deploy_istio_mtls_authn_authz/sso-gatekeeper/gatekeeper-template.yml
    stage('cleanup') {

      openshift.withCluster() {
        openshift.withProject("${dev_project}") {

          sh "oc delete imagestream ${app_name}"
          sh "oc delete buildconfig ${app_name}-docker-build"
          sh "oc delete deploymentconfig ${app_name}"
          sh "oc delete serviceaccounts ${app_name}"
          sh "oc delete service ${app_name}"
          sh "oc delete route ${app_name}"

        }
      }
    }

    stage('build') {

      openshift.withCluster() {
        openshift.withProject("${dev_project}") {

          sh "oc new-app -f gatekeeper-template.yml \
                -p APPLICATION_NAME=${app_name} \
                -p SOURCE_REPOSITORY_URL=${git_url} \
                -p SOURCE_REPOSITORY_REF=master \
                -p DOCKERFILE_PATH=" src//${app_name}"
        }
      }
    }
  }
}

//      oc new-app -f gatekeeper-template.yml \
//    -p APPLICATION_NAME=${APP} \
//    -p SOURCE_REPOSITORY_URL=https://github.com/justindav1s/openshift-sso.git \
//      -p SOURCE_REPOSITORY_REF=master \
//    -p DOCKERFILE_PATH="gatekeeper" \
//
//          echo "Building ...."
//          def nb = openshift.startBuild("${app_name}-docker-build", "--from-file=${artifactId}-${commitId}.${packaging}")
//          nb.logs('-f')
//
//          echo "Tagging ...."
//          openshift.tag("${app_name}:latest", "${app_name}:${devTag}")
//          openshift.tag("${app_name}:latest", "${app_name}:${commitId}")
//        }
//      }




//    }
//
//
//
//  }
//  dir("src/${app_name}") {
//
//
//
//    //Build the OpenShift Image in OpenShift and tag it.
//    stage('Dev : Build and Tag OpenShift Image') {
//      echo "Building OpenShift container image ${app_name}:${devTag}"
//      echo "Project : ${dev_project}"
//      echo "App : ${app_name}"
//
//      sh "oc start-build ${app_name} --follow --from-dir=. -n ${dev_project}"
//      openshiftVerifyBuild apiURL: '', authToken: '', bldCfg: app_name, checkForTriggeredDeployments: 'false', namespace: dev_project, verbose: 'false', waitTime: ''
//      openshiftTag alias: 'false', apiURL: '', authToken: '', destStream: app_name, destTag: devTag, destinationAuthToken: '', destinationNamespace: dev_project, namespace: dev_project, srcStream: app_name, srcTag: 'latest', verbose: 'false'
//    }
//
//    // Deploy the built image to the Development Environment.
//    stage('Dev :  Deploy') {
//      echo "Deploying container image to Development Project"
//      echo "Project : ${dev_project}"
//      echo "App : ${app_name}"
//      echo "Dev Tag : ${devTag}"
//
//      sh "oc set image dc/${app_name} ${app_name}=docker-registry.default.svc:5000/${dev_project}/${app_name}:${devTag} -n ${dev_project}"
//
//      openshiftDeploy apiURL: '', authToken: '', depCfg: app_name, namespace: dev_project, verbose: 'false', waitTime: '180', waitUnit: 'sec'
//      openshiftVerifyDeployment apiURL: '', authToken: '', depCfg: app_name, namespace: dev_project, replicaCount: '1', verbose: 'false', verifyReplicaCount: 'true', waitTime: '180', waitUnit: 'sec'
//    }
//  }
//}
