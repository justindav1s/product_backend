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
                "  -Dsonar.login=a4238b69e9c491ca39a810e87e9e43327e1841e4"
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