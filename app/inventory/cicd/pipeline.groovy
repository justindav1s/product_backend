#!groovy

node('maven') {

    stage('Checkout Source') {
        git url: "${git_url}"
    }

    stage('Changing workspace location') {
        dir path: "app/inventory"
    }

    stage('Build war') {
        echo "Building version"
        sh "mvn -U -B -q -s ../settings.xml clean package -DskipTests"
    }
}