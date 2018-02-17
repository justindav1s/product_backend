pipeline {
  agent {
      label 'maven'
  }

  stages {

    stage('Maven Build') {
      steps{
        echo "Doing the Maven build"
        sh "mvn -U -B -s settings.xml install deploy"
      }
    }

    stage('Sonar Analysis') {
      steps {
        echo "Doing the Sonar Analysis"
        sh "mvn -B sonar:sonar \\\n" +
                "  -Dsonar.host.url=https://sonarqube-cicd.apps.ocp.datr.eu \\\n" +
                "  -Dsonar.login=3a520d0add2d09c66a38a9837e73962c613b802d"
      }
    }

    stage('Openshift Build') {
      steps{
        echo "Doing the Openshift Build"
          script {
              openshift.withCluster() {
                  openshift.withProject('cicd') {
                      echo "Hello from project ${openshift.project()} in cluster ${openshift.cluster()}"
                  }
              }
          }
      }
    }

  }
}