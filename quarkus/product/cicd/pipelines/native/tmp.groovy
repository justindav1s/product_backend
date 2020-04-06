node('maven-quarkus’) {

    def mvn = "/opt/apache-maven-3.6.3/bin/mvn -U -B -q -s settings.xml"

    stage('Checkout Source') {
        git url: "${git_url}", branch: 'master'
    }

    dir("quarkus_app”) {
        stage ('Maven native build'){
            echo 'Building and test native binary'
            sh "${mvn} package -Pnative"   
        }
    }
}