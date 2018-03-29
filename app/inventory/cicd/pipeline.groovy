#!groovy

node('maven') {

    stage('Checkout Source') {
        git url: "${git_url}"
    }

    dir('app/inventory') {

        def dev_project  = "${org}-dev"
        def prod_project = "${org}-prod"
        def app_url_dev  = "http://${app_name}.${dev_project}.svc:8080"
        def groupId      = getGroupIdFromPom("pom.xml")
        def artifactId   = getArtifactIdFromPom("pom.xml")
        def version      = getVersionFromPom("pom.xml")
        def packaging    = getPackagingFromPom("pom.xml")
        def sonar_url    = "http://sonarqube.cicd.svc:9000"
        def nexus_url    = "http://nexus.cicd.svc:8081/repository/maven-snapshots"

        stage('Build jar') {
            echo "Building version : ${version}"
            sh "mvn -U -B -q -s ../settings.xml clean package -DskipTests"
        }

        // Using Maven run the unit tests
        stage('Unit Tests') {
            echo "Running Unit Tests"
            sh "mvn -U -B -q -s ../settings.xml test"
        }

        // Using Maven call SonarQube for Code Analysis
        stage('Code Analysis') {
            echo "Running Code Analysis"
            sh "mvn -U -B -q -s ../settings.xml sonar:sonar -Dsonar.host.url=${sonar_url}"
        }

        // Publish the built war file to Nexus
        stage('Publish to Nexus') {
            echo "Publish to Nexus"
            sh "mvn -U -B -s ../settings.xml deploy -DskipTests"
        }

        //Build the OpenShift Image in OpenShift and tag it.
        stage('Build and Tag OpenShift Image') {
            echo "Building OpenShift container image tasks:${devTag}"
            echo "Project : ${dev_project}"
            echo "App : ${app_name}"
            echo "Group ID : ${groupId}"
            echo "Artifact ID : ${artifactId}"
            echo "Version : ${version}"
            echo "Packaging : ${packaging}"

            sh "mvn -q -s settings.xml dependency:copy -DstripVersion=true -Dartifact=${groupId}:${artifactId}:${version}:${packaging} -DoutputDirectory=."
            sh "cp \$(find . -type f -name \"${artifactId}-*.${packaging}\")  ${artifactId}.${packaging}"
            sh "pwd; ls -ltr"
            sh "oc start-build ${app_name} --follow --from-file=${artifactId}.${packaging} -n ${dev_project}"
            openshiftVerifyBuild apiURL: '', authToken: '', bldCfg: app_name, checkForTriggeredDeployments: 'true', namespace: dev_project, verbose: 'false', waitTime: ''
            openshiftTag alias: 'false', apiURL: '', authToken: '', destStream: app_name, destTag: devTag, destinationAuthToken: '', destinationNamespace: dev_project, namespace: dev_project, srcStream: app_name, srcTag: 'latest', verbose: 'false'
        }

        // Deploy the built image to the Development Environment.
        stage('Deploy to Dev') {
            echo "Deploying container image to Development Project"
            echo "Project : ${dev_project}"
            echo "App : ${app_name}"
            echo "Dev Tag : ${devTag}"
            sh "oc set image dc/${app_name} ${app_name}=${dev_project}/${app_name}:${devTag} -n ${dev_project}"
            def ret = sh(script: "oc delete configmap ${app_name}-config --ignore-not-found=true -n ${dev_project}", returnStdout: true)
            ret = sh(script: "oc create configmap ${app_name}-config --from-file=${config_file} -n ${dev_project}", returnStdout: true)

            openshiftDeploy apiURL: '', authToken: '', depCfg: app_name, namespace: dev_project, verbose: 'false', waitTime: '180', waitUnit: 'sec'
            openshiftVerifyDeployment apiURL: '', authToken: '', depCfg: app_name, namespace: dev_project, replicaCount: '1', verbose: 'false', verifyReplicaCount: 'true', waitTime: '180', waitUnit: 'sec'
        }
    }
}

// Convenience Functions to read variables from the pom.xml
// Do not change anything below this line.
// --------------------------------------------------------
def getVersionFromPom(pom) {
    def matcher = readFile(pom) =~ '<version>(.+)</version>'
    matcher ? matcher[0][1] : null
}
def getGroupIdFromPom(pom) {
    def matcher = readFile(pom) =~ '<groupId>(.+)</groupId>'
    matcher ? matcher[0][1] : null
}
def getArtifactIdFromPom(pom) {
    def matcher = readFile(pom) =~ '<artifactId>(.+)</artifactId>'
    matcher ? matcher[0][1] : null
}
def getPackagingFromPom(pom) {
    def matcher = readFile(pom) =~ '<packaging>(.+)</packaging>'
    matcher ? matcher[0][1] : null
}