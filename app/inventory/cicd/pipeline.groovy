#!groovy

node('maven') {

    stage('Checkout Source 2') {
        git url: "${git_url}"
    }

    stage('Changing workspace location') {
        dir path: "app/inventory"
        sh "ls -ltr"
    }

    stage('Build war') {
        echo "Building version"
        sh "mvn -U -B -q -s ../settings.xml clean package -DskipTests"
    }
}