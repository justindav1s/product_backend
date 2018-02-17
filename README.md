# product_backend

mvn org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
    -DrepoUrl=http://nexus-cicd2.apps.172.16.173.128.nip.io/repository/maven-public/ \
    -Dartifact=org.jnd:product.backend:1.0.0-SNAPSHOT \
    -Dpackaging=war 
    
 goes to ~/.m2/........ 
 
 http://nexus-cicd2.apps.172.16.173.128.nip.io/org/jnd/product.backend/1.0.0-SNAPSHOT/product.backend.war  
 
 curl -u admin:admin123 -X GET 'http://nexus-cicd2.apps.172.16.173.128.nip.io/service/rest/beta/search/assets?group=org.jnd&name=product.backend&version=1.0.0-SNAPSHOT&maven.extension=war&maven.classifier'


curl https://justin-admin:04aa8e41ad277bda62e6b584e918a037@jenkins-cicd.apps.ocp.datr.eu/job/test/build?token=12jnd34

curl https://justin-admin:2aec819fed47c83e80a2ed50b7cb69e0@jenkins-cicd.apps.ocp.datr.eu/job/product_backend/build?token=product

