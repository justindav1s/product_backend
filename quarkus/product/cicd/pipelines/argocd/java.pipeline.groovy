#!groovy
//import groovy.transform.Field

node('maven') {

    def mvn          = "/opt/apache-maven-3.6.3/bin/mvn -U -B -q -s settings.xml"
    def dev_project  = "${org}-quarkus"
    def app_url_dev  = "http://${app_name}.${dev_project}.svc:8080"
    def sonar_url    = "http://sonarqube.cicd.svc:9000"
    def nexus_url    = "http://nexus.cicd.svc:8081/repository/maven-snapshots"
    def registry     = "quay.io/justindav1s"
    def groupId, version, packaging = null
    def artifactId = null
    def github_repo  = sh (returnStdout: true, script: "echo $git_url | awk '{split(\$0,a,\"[/.]\"); print a[6]}' | tr -d '\n'") 
    sh "git config --global user.email \"justinndavis@gmail.com\""
    sh "git config --global user.name \"Justin Davis\""
    sh "git config --global push.default matching"

    stage('Repo Clone') {
        git url: "${git_url}", branch: 'master'
    }

    // stage('Check Maven Version') {           
    //         sh "${mvn} -version"
    // }

    // def commitId  = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
    // def commitmsg  = sh(returnStdout: true, script: "git log --format=%B -n 1 ${commitId}").trim()

    dir("${src_root}/${app_name}") {

        // groupId      = getGroupIdFromPom("pom.xml")
        // artifactId   = getArtifactIdFromPom("pom.xml")
        // version      = getVersionFromPom("pom.xml")
        // packaging    = getPackagingFromPom("pom.xml")

        stage('Init') {
            sh "env"
            sh "ls -ltr"
        }

        // stage('Build jar') {
        //     echo "Building version : ${version}"
        //     sh "${mvn} clean package"
        // }

        // // Using Maven run the unit tests
        // stage('Unit/Integration Tests') {
        //     echo "Running Unit Tests"
        //     sh "${mvn} test"
        //     archive "target/**/*"
        //     junit 'target/surefire-reports/*.xml'
        // }

        // stage('Coverage') {
        //     echo "Running Coverage"
        //     sh "${mvn} clean install org.jacoco:jacoco-maven-plugin:prepare-agent"
        // }

        // // Using Maven call SonarQube for Code Analysis
        // stage('Code Analysis') {
        //     echo "Running Code Analysis"
        //     sh "${mvn} sonar:sonar -Dspring.profiles.active=dev -Dsonar.host.url=${sonar_url}"
        // }

        // Publish the built war file to Nexus
        // stage('Publish to Nexus') {
        //     echo "Publish to Nexus"
        //     sh "${mvn} deploy -DskipTests -Dquarkus.package.uber-jar=true"
        // }

        // //Build the OpenShift Image in OpenShift and tag it.
        // stage('Build and Tag OpenShift Image') {
        //     echo "Building OpenShift container image ${app_name}:${devTag}"
        //     echo "Project : ${dev_project}"
        //     echo "App : ${app_name}"
        //     echo "Group ID : ${groupId}"
        //     echo "Artifact ID : ${artifactId}"
        //     echo "Version : ${version}"
        //     echo "Packaging : ${packaging}"

        //     sh "cp target/${artifactId}-*runner.${packaging} ."
        //     sh "cp ${artifactId}-*runner.${packaging} ${artifactId}-${commitId}.${packaging}"
        //     sh "pwd && ls -ltr"

        //     openshift.withCluster() {
        //         openshift.withProject("${dev_project}") {

        //             echo "Building ...."
        //             def bc = openshift.selector( "bc/${app_name}" ).object()
        //             bc.spec.output.to.name="${registry}/${app_name}:${commitId}"
        //             openshift.apply(bc)

        //             def nb = openshift.startBuild("${app_name}", "--from-file=${artifactId}-${commitId}.${packaging}")
        //             nb.logs('-f')

        //         }
        //     }

        // }

        // Deploy the built image to the Development Environment.
        stage('Update Repo') {
            sh "sed -i \'\' \'s/val1/val2/\' argocd/plain-yaml/configmap.yaml"
            sh "cat argocd/plain-yaml/configmap.yaml"
            echo "github repo : ${github_repo}"

            withCredentials([usernamePassword(credentialsId: 'GIT', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                sh "git status"
                sh "git add .."
                sh "git commit -m \"updated by Jenkins\" || true"
                def origin = "https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/${GIT_USERNAME}/${github_repo}.git"
                sh "git push ${origin} master || true"
            }
        }

        stage('Argocd Sync') {
            echo "ArgoCD Parameters"
            echo "ARGOCD_USER : ${ARGOCD_USER}"
            echo "ARGOCD_PASSWORD : ${ARGOCD_PASSWORD}"
            echo "ARGOCD_SERVER : ${ARGOCD_SERVER}"
            sh "ls -ltr /usr/local/bin/argocd"
            sh "argocd --insecure login --username ${ARGOCD_USER} --password ${ARGOCD_PASSWORD} ${ARGOCD_SERVER}"
            sh "argocd --insecure app list"             
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


//def manageVersionData(commitId, commitmsg, groupId, artifactId, project) {
//
//    withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
//        def github_repo = "manifest-test"
//        def trackingrepo = "https://github.com/${GIT_USERNAME}/${github_repo}.git"
//        echo "1"
//        git url: "${trackingrepo}", branch: 'master', credentialsId: 'github'
//        echo "2"
//        def versionFileName = "version"
//        versionFileName = groupId+"."+artifactId+"."+project+"."+versionFileName
//        echo "3"
//        @Field def timeStamp = Calendar.getInstance().getTime().format('ddMMyy-HH:mm:ss',TimeZone.getTimeZone('GMT'))
//        echo "4"
//        def newVersionString = "{ \\\"build\\\": \\\"${env.BUILD_NUMBER}\\\", \\\"timestamp\\\": \\\"${timeStamp}\\\", \\\"commitId\\\": \\\"${commitId}\\\", \\\"commitMsg\\\": \\\"${commitmsg}\\\"}"
//        sh(returnStdout: true, script: "echo ${newVersionString} >> ${versionFileName}")
//        echo "5"
//        sh (returnStdout: true, script: "git config user.email \"jenkins@${GIT_USERNAME}.dev\"; git config user.name \"${GIT_USERNAME}\"")
//        sh (returnStdout: true, script: "git add ${versionFileName}")
//        sh (returnStdout: true, script: "git commit -m \"version data update for ${artifactId} to ${env.BUILD_NUMBER}:${commitId}\" || true")
//        sh (returnStdout: true, script: "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/${GIT_USERNAME}/${github_repo}.git master || true")
//    }
//}