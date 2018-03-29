#!groovy

node('maven') {

    stage('Checkout Source 2') {
        git url: "${git_url}"
    }

    stage('Build war') {
        echo "Building version"
        dir('app/inventory') {
            sh "mvn -U -B -q -s ../settings.xml clean package -DskipTests"
        }
    }
}