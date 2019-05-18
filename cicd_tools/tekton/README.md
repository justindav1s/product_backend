https://github.com/tektoncd/pipeline

https://github.com/tektoncd/pipeline/blob/master/docs/install.md

oc apply --filename https://storage.googleapis.com/tekton-releases/latest/release.yaml

oc apply --filename https://storage.googleapis.com/tekton-releases/previous/v0.3.1/release.yaml

oc new-project tekton-test

oc apply -f helloword_task.yaml

oc apply -f helloworld_taskrun.yaml