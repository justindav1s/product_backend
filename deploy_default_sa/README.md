#Deployment scripts

A selection of resources to set up : 
   * Jenkins Pipelines
   * OCP constructs
   * Istio components
   
##Making a template from what's there :

oc export all -l app=inventory --as-template="invenory-prd-template" > invenory-prd-template.yml   