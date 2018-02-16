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

    stages {
      stage('Sonar Analysis') {
        steps {
          echo "Doing the Sonar Analysis"
          sh "mvn sonar:sonar \\\n" +
                  "  -Dsonar.host.url=https://sonarqube-cicd.apps.ocp.datr.eu \\\n" +
                  "  -Dsonar.login=ce60f9d40cba6f25ab731fbc2384e1f691a11c48"
        }
      }
    }
//    stage('Openshift Build') {
//      steps{
//        echo "Doing the Openshift Build"
//        script {
//            openshift.withCluster() {
//                def models = openshift.create( "templates/docker-bc.yml" )
//            }
//        }
//      }
//    }

  }
}