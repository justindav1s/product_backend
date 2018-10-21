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

//        stage('Build jar') {
//            echo "Building version : ${version}"
//            sh "mvn -U -B -q -s ../settings.xml clean package -Dspring.profiles.active=dev -DskipTests"
//        }
//
//        // Using Maven run the unit tests
//        stage('Unit/Integration Tests') {
//            echo "Running Unit Tests"
//            sh "mvn -U -B -q -s ../settings.xml test -Dspring.profiles.active=dev"
//            archive "target/**/*"
//            junit 'target/surefire-reports/*.xml'
//        }
//
//        stage('Coverage') {
//            echo "Running Coverage"
//            sh "mvn -U -B -q -s ../settings.xml clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dspring.profiles.active=dev"
//        }
//
//        // Using Maven call SonarQube for Code Analysis
//        stage('Code Analysis') {
//            echo "Running Code Analysis"
//            sh "mvn -U -B -q -s ../settings.xml sonar:sonar -Dspring.profiles.active=dev -Dsonar.host.url=${sonar_url}"
//        }
//
//        // Publish the built war file to Nexus
//        stage('Publish to Nexus') {
//            echo "Publish to Nexus"
//            sh "mvn -U -B -q -s ../settings.xml deploy -DskipTests"
//        }

        //Build the OpenShift Image in OpenShift and tag it.
        stage('Build and Tag OpenShift Image') {
            echo "Building OpenShift container image tasks:${devTag}"
            echo "Project : ${dev_project}"
            echo "App : ${app_name}"
            echo "Group ID : ${groupId}"
            echo "Artifact ID : ${artifactId}"
            echo "Version : ${version}"
            echo "Packaging : ${packaging}"
            sh "mvn -U -B -q -s ../settings.xml clean"
            sh "mvn -q -s ../settings.xml dependency:copy -DstripVersion=true -Dartifact=${groupId}:${artifactId}:${version}:${packaging} -DoutputDirectory=."
            sh "cp \$(find . -type f -name \"${artifactId}-*.${packaging}\")  ${artifactId}.${packaging}"
            sh "pwd; ls -ltr"
            //sh "oc start-build ${app_name} --follow --from-file=${artifactId}.${packaging} -n ${dev_project}"
            //openshiftVerifyBuild apiURL: '', authToken: '', bldCfg: app_name, checkForTriggeredDeployments: 'true', namespace: dev_project, verbose: 'false', waitTime: ''
            //openshiftTag alias: 'false', apiURL: '', authToken: '', destStream: app_name, destTag: devTag, destinationAuthToken: '', destinationNamespace: dev_project, namespace: dev_project, srcStream: app_name, srcTag: 'latest', verbose: 'false'
            // The selector returned from newBuild will select all objects created by the operation
            openshift.withCluster() {
                openshift.withProject("${dev_project}") {
                    //openshift.verbose()


                    def nb = openshift.startBuild("${app_name}", "--follow", "--from-file=${artifactId}.${packaging}")

//                    echo "nb : ${nb}"
//                    // Print out information about the objects created by newBuild
//                   echo "newBuild created: ${nb.count()} objects : ${nb.names()}"
//
//                    // Filter non-BuildConfig objects and create selector which will find builds related to the BuildConfig
//                    def builds = nb.narrow("bc").related("${app_name}")
//
//                    // Raw watch which only terminates when the closure body returns true
//                    builds.watch {
//                        // 'it' is bound to the builds selector.
//                        // Continue to watch until at least one build is detected
//                        if (it.count() == 0) {
//                            return false
//                        }
//                        // Print out the build's name and terminate the watch
//                        echo "Detected new builds created by buildconfig: ${it.names()}"
//                        return true
//                    }
//
//                    echo "Waiting for builds to complete..."
//
//                    // Like a watch, but only terminate when at least one selected object meets condition
//                    builds.untilEach {
//                        return it.object().status.phase == "Complete"
//                    }
//
//                    // Print a list of the builds which have been created
//                    echo "Build logs for ${builds.names()}:"
//
//                    // Find the bc again, and ask for its logs
//                    def result = nb.narrow("bc").logs()
//
//                    // Each high-level operation exposes stout/stderr/status of oc actions that composed
//                    echo "Result of logs operation:"
//                    echo "  status: ${result.status}"
//                    echo "  stderr: ${result.err}"
//                    echo "  number of actions to fulfill: ${result.actions.size()}"
//                    echo "  first action executed: ${result.actions[0].cmd}"

                }
            }

        }

        // Deploy the built image to the Development Environment.
        stage('Deploy to Dev') {
            echo "Deploying container image to Development Project"
            echo "Project : ${dev_project}"
            echo "App : ${app_name}"
            echo "Dev Tag : ${devTag}"

            openshift.withCluster() {
                openshift.withProject(dev_project) {
                    echo "****SET IMAGE start"
                    //openshift.verbose()
                    //sh "oc set image dc/${app_name} ${app_name}=docker-registry.default.svc:5000/${dev_project}/${app_name}:${devTag} -n ${dev_project}"
                    openshift.set("image", "dc/${app_name}", "${app_name}=docker-registry.default.svc:5000/${dev_project}/${app_name}:${devTag}")
                    echo "****SET IMAGE end"
                    //openshift.verbose(false)

                    echo "****ROLLOUT start"
                    openshift.verbose()
                    //openshiftDeploy apiURL: '', authToken: '', depCfg: app_name, namespace: dev_project, verbose: 'false', waitTime: '180', waitUnit: 'sec'
                    def rm = openshift.selector("dc", [app:app_name]).rollout()
                    rm.latest()
                    echo "****ROLLOUT waiting ......"
                    timeout(5) {
                        openshift.selector("dc", [app:app_name]).related('pods').untilEach(1) {
                            return (it.object().status.phase == "Running")
                        }
                    }
                    openshift.verbose(false)
                    echo "****ROLLOUT end"
                }
            }

            //def ret = sh(script: "oc delete configmap ${app_name}-config --ignore-not-found=true -n ${dev_project}", returnStdout: true)
            //ret = sh(script: "oc create configmap ${app_name}-config --from-file=${config_file} -n ${dev_project}", returnStdout: true)







            //openshiftVerifyDeployment apiURL: '', authToken: '', depCfg: app_name, namespace: dev_project, replicaCount: '1', verbose: 'false', verifyReplicaCount: 'true', waitTime: '180', waitUnit: 'sec'
//            openshift.withCluster() {
//                openshift.withProject("${dev_project}") {
//                    openshift.verbose(false)
//                    def latestDeploymentVersion = openshift.selector('dc', "${app_name}").object().status.latestVersion
//                    def rc = openshift.selector('rc', "${app_name}-${latestDeploymentVersion}")
//                    rc.untilEach(1) {
//                        def rcMap = it.object()
//                        return (rcMap.status.replicas.equals(rcMap.status.readyReplicas))
//                    }
//                }
//            }

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