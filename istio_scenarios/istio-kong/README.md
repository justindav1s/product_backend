## Adventures with Flux, Helm, Istio and Kong


### Minikube

With VMware driver

https://minikube.sigs.k8s.io/docs/drivers/vmware/

```
minikube delete

brew install docker-machine-driver-vmware

minikube start --driver=vmware --memory=32768 --cpus=8

minikube addons enable ingress

minikube dashboard

kubectl create namespace hello
kubectl create deployment web --image=gcr.io/google-samples/hello-app:1.0 -n hello
kubectl expose deployment web --type=NodePort --port=8080 -n hello
kubectl get service web -n hello
minikube service web --url -n hello
http://192.168.28.136:31161

so hello.192.168.28.136.nip.io is our host
kubectl apply -f hello-ingress.yaml -n hello

```

### Helm Operator & Flux1

https://docs.fluxcd.io/projects/helm-operator/en/stable/get-started/quickstart/

https://github.com/fluxcd/helm-operator-get-started

```

kubectl apply -f https://raw.githubusercontent.com/fluxcd/helm-operator/1.2.0/deploy/crds.yaml
kubectl create ns flux
helm repo add fluxcd https://charts.fluxcd.io

helm upgrade -i flux fluxcd/flux \
   --set git.url=git@gitlab.com:justindav1s/inventory \
   --namespace flux

get the output ssh public key 
kubectl -n flux logs deployment/flux | grep identity.pub | cut -d '"' -f2
upload to gitlab

helm upgrade -i helm-operator fluxcd/helm-operator \
   --set helm.versions=v3 \
   --set git.ssh.secretName=flux-git-deploy \
   --namespace flux    

```

### Istio
```
istioctl install --set values.global.logging.level=debug --set meshConfig.outboundTrafficPolicy.mode=REGISTRY_ONLY

oc create ns bookinfo

kubectl apply -f samples/bookinfo/platform/kube/bookinfo.yaml -n bookinfo

kubectl label namespace bookinfo istio-injection=enabled

kubectl exec "$(kubectl -n bookinfo get pod -l app=ratings -o jsonpath='{.items[0].metadata.name}')" -n bookinfo -c ratings -- curl -sS productpage:9080/productpage | grep -o "<title>.*</title>"
<title>Simple Bookstore App</title


istioctl install --set values.global.logging.level=debug
kubectl apply -f istio/samples/addons/kiali.yaml
istioctl dashboard kiali

kubectl apply -f istio/samples/addons/prometheus.yaml

kubectl create ns bookinfo
kubectl label namespace bookinfo istio-injection=enabled
kubectl apply -f istio/samples/bookinfo/platform/kube/bookinfo.yaml -n bookinfo
kubectl apply -f istio/samples/bookinfo/networking/bookinfo-gateway.yaml -n bookinfo
kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.status.loadBalancer.ingress[0].ip}'
kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].port}'

NodePort
export INGRESS_HOST=$(minikube ip)
export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT
echo $GATEWAY_URL

minikube ip
kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}' 

loadBalancer (tunnel)
export INGRESS_HOST=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.status.loadBalancer.ingress[0].ip}')
export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].port}')
export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT
echo $GATEWAY_URL
curl -s "http://${GATEWAY_URL}/productpage"
http://192.168.28.136:32195/productpage

kubectl create ns shop
kubectl label namespace shop istio-injection=enabled
helm install inventory inventory -n shop
export INGRESS_HOST=$(minikube ip)
export INGRESS_PORT=$(kubectl -n shop get service inventory -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT
curl -v "http://${GATEWAY_URL}/api/products/all"

helm install inventory inventory -n shop

istio configmap
set accessLogFile

kind: ConfigMap
apiVersion: v1
metadata:
  name: istio
  namespace: istio-system
  labels:
    install.operator.istio.io/owning-resource: unknown
    install.operator.istio.io/owning-resource-namespace: istio-system
    istio.io/rev: default
    operator.istio.io/component: Pilot
    operator.istio.io/managed: Reconcile
    operator.istio.io/version: 1.9.0
    release: istio
data:
  mesh: |-
    accessLogFile: /dev/stdout
    defaultConfig:
      discoveryAddress: istiod.istio-system.svc:15012
      proxyMetadata: {}
      tracing:
        zipkin:
          address: zipkin.istio-system:9411
    enablePrometheusMerge: true
    rootNamespace: istio-system
    accessLogFile: /dev/stdout
	outboundTrafficPolicy:
		mode: REGISTRY_ONLY
    trustDomain: cluster.local
  meshNetworks: 'networks: {}'

https://www.programmersought.com/article/96831571283/
https://karlstoney.com/2019/05/31/istio-503s-ucs-and-tcp-fun-times/index.html
https://medium.com/expedia-group-tech/all-about-istio-proxy-5xx-issues-e0221b29e692#:~:text=Inbound%20Call%20Error%20example,the%20pod%20has%20been%20terminated.
```

### Kong
```
Install
kubectl create namespace kong-istio
kubectl label namespace kong-istio istio-injection=enabled
helm install -n kong-istio example-kong kong/kong --set ingressController.installCRDs=false

Uninstall
get the release name to uninstall
helm list
helm uninstall <release-name>
helm uninstall kong-1613673890

https://discuss.konghq.com/t/kong-istio-strict-mtls-503-errors/7887
https://discuss.konghq.com/t/kong-ingress-controller-is-unable-to-route-request-to-istio-virtual-services/4457/3
https://discuss.konghq.com/t/kong-ingress-controller-istio-service-mesh-doesnt-support-global-mtls/5971/10

helm install -n shop shop-kong kong/kong --set ingressController.installCRDs=false

helm install inventory ./inventory --dry-run --debug --set platform=k8s -n shop
helm install inventory ./inventory --dry-run --debug --set platform=ocp -n shop

```
### Flux2

https://toolkit.fluxcd.io/get-started/


```
oc new-project flux-system

oc adm policy add-scc-to-user privileged -z source-controller

flux bootstrap gitlab \
  --owner=$GITLAB_USER \
  --repository=flux2 \
  --branch=master \
  --path=./clusters/ocp \
  --personal


find out why namespace is stuck terminating

https://www.openshift.com/blog/the-hidden-dangers-of-terminating-namespaces

oc  api-resources --verbs=list --namespaced -o name | xargs -n 1 oc get --show-kind --ignore-not-found -n flux-system


oc get kustomization flux-system -o json > kustomization.json

remove finalizers

then

curl -k -H "Content-Type: application/json" -H "Authorization: Bearer $(oc whoami -t)" -X PUT --data-binary @kustomization.json $(oc whoami --show-server)/apis/kustomize.toolkit.fluxcd.io/v1beta1/namespaces/flux-system/kustomizations/flux-system

oc get namespace flux-system -o json > flux-system-ns.json

remove finalizers

then 

curl -k -H "Content-Type: application/json" -H "Authorization: Bearer $(oc whoami -t)" -X PUT --data-binary @flux-system-ns.json  $(oc whoami --show-server)/api/v1/namespaces/flux-system/finalize


https://toolkit.fluxcd.io/guides/helmreleases/


apiVersion: source.toolkit.fluxcd.io/v1beta1
kind: HelmRepository
metadata:
  name: jd-repo
  namespace: flux-system
spec:
  interval: 1m
  url: https://gitlab.com/justinndavis/inventory/-/raw/master/flux/charts/
  
```