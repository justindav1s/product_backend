#!groovy
//imput vars :

// project : the name of the project hosting the nginx-proxy, eg alfa-dev
// config file type : cloud or onprem

node('maven') {

    echo "Project : ${project}"
    echo "Config File Path : ${config-file-name}"

    stage('Checkout Source') {
        git url: "${git_url}", branch: 'master'
    }

    dir("config/${project}") {

        // Deploy the configMap
        stage('Deploy to ConfigMap') {
            echo "Deploying ConfigMap"
            echo "Project : ${project}"
            echo "config-file : ${app_name}"
            echo "Dev Tag : ${devTag}"

            openshift.withCluster() {
                openshift.withProject(project) {

                    def configmap-name  = sh(returnStdout: true, script: "echo ${config-file-name} | sed 's/\./-/'").trim()

                    sh "oc create configmap proxy-conf-new --from-file=proxy.conf=src/nginx-default-cfg/proxy.conf "
                    

                }
            }
            echo "Deploying ConfigMap : FINISHED"
        }

    }
}


