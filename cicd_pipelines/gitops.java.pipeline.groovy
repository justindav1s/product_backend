#!groovy

node('maven') {

    stage('Checkout Source') {
        git url: "${git_url}", branch: 'master'
    }

    dir("src/${app_name}") {

        def mvn          = "mvn -U -B -q -s ../settings.xml -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true"
        def dev_project  = "${org}-dev"
        def prod_project = "${org}-prod"
        def app_url_dev  = "http://${app_name}.${dev_project}.svc:8080"
        def groupId      = getGroupIdFromPom("pom.xml")
        def artifactId   = getArtifactIdFromPom("pom.xml")
        def version      = getVersionFromPom("pom.xml")
        def packaging    = getPackagingFromPom("pom.xml")
        def sonar_url    = "http://sonarqube.cicd.svc:9000"
        def nexus_url    = "http://nexus.cicd.svc:8081/repository/maven-snapshots"
        def registry     = "docker-registry.default.svc:5000"
        def commitId  = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()


        stage('Build jar') {
            echo "Building version : ${version}"
            sh "${mvn} clean package -Dspring.profiles.active=dev -DskipTests"
        }

        // Using Maven run the unit tests
        stage('Unit/Integration Tests') {
            echo "Running Unit Tests"
            sh "${mvn} test -Dmaven.wagon.http.ssl.insecure=true -Dspring.profiles.active=dev"
            archive "target/**/*"
            junit 'target/surefire-reports/*.xml'
        }

        //Build the OpenShift Image in OpenShift and tag it.
        stage('Build and Tag OpenShift Image') {
            echo "Building OpenShift container image ${app_name}:${devTag}"
            echo "Project : ${dev_project}"
            echo "App : ${app_name}"
            echo "Group ID : ${groupId}"
            echo "Artifact ID : ${artifactId}"
            echo "Version : ${version}"
            echo "Packaging : ${packaging}"
            sh "${mvn} clean"
            sh "${mvn} dependency:copy -DstripVersion=true -Dartifact=${groupId}:${artifactId}:${version}:${packaging} -DoutputDirectory=."
            sh "cp \$(find . -type f -name \"${artifactId}-*.${packaging}\")  ${artifactId}.${packaging}"
            sh "pwd; ls -ltr"

            openshift.withCluster() {
                openshift.withProject("${dev_project}") {

                    echo "Building ...."
                    def nb = openshift.startBuild("${app_name}", "--from-file=${artifactId}.${packaging}")
                    nb.logs('-f')

                    echo "Tagging ...."
                    openshift.tag("${app_name}:latest", "${app_name}:${devTag}")
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
                    //remove any triggers
                    openshift.set("triggers", "dc/${app_name}", "--remove-all");

                    //update deployment config with new image
                    openshift.set("image", "dc/${app_name}", "${app_name}=${registry}/${dev_project}/${app_name}:${devTag}")

                    //update app config
                    openshift.delete("configmap", "${app_name}-config", "--ignore-not-found=true")
                    openshift.create("configmap", "${app_name}-config", "--from-file=${config_file}")

                    //trigger a rollout of the new image
                    def rm = openshift.selector("dc", [app:app_name]).rollout().latest()
                    //wait for rollout to start
                    timeout(5) {
                        openshift.selector("dc", [app:app_name]).related('pods').untilEach(1) {
                            return (it.object().status.phase == "Running")
                        }
                    }
                    //rollout has started

                    //wait for deployment to finish and for new pods to become active
                    def latestDeploymentVersion = openshift.selector('dc',[app:app_name]).object().status.latestVersion
                    def rc = openshift.selector("rc", "${app_name}-${latestDeploymentVersion}")
                    rc.untilEach(1) {
                        def rcMap = it.object()
                        return (rcMap.status.replicas.equals(rcMap.status.readyReplicas))
                    }
                    //deployment finished
                }
            }
            echo "Deploying container image to Development Project : FINISHED"

        }

        dir("build-metadata") {

            stage('manage version data') {
                manageVersionData(commitId, git_url)
            }

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

def manageVersionData(commitId, git_url) {
    def github = "github.com"
    def github_user = "justindav1s"
    def github_repo = "manifest-test"
    def trackingrepo = "https://${github}/${github_user}/${github_repo}.git"
    git url: "${trackingrepo}", branch: 'master', credentialsId: 'cab006f5-c6cf-43bc-8c1e-50a4430d44c6'
    def workspace = pwd()
    def versionFileName = "version"
    versionFileName = workspace+"/"+versionFileName
    def versiondata = sh(returnStdout: true, script: "cat ${versionFileName} | head -1")
    println "Existing version data : "+versiondata
    def versionnumber = versiondata.tokenize(':')[0]
    def gitcommitid = versiondata.tokenize(':')[1]
    int newVersion = versionnumber.toInteger()
    newVersion = newVersion + 1
    def newVersionString = newVersion+":"+commitId
    println "New version data :  : "+newVersionString
    sh(returnStdout: true, script: "echo ${newVersionString} > ${versionFileName}")
    def newversiondata = sh(returnStdout: true, script: "cat ${versionFileName} | head -1")
    withCredentials([usernamePassword(credentialsId: 'cab006f5-c6cf-43bc-8c1e-50a4430d44c6', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        sh ("git config user.email \"jenkins@dev.com\"")
        sh ("git config user.name \"${GIT_USERNAME}\"")
        sh ("git add version")
        sh ("git commit -m \"updating version data to ${newVersionString}\"")
        sh ("git push https://${GIT_USERNAME}:${GIT_PASSWORD}@${github}/${github_user}/${github_repo}.git master")
        //sh ("git push origin master")
    }
}