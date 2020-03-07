kn service create hello --image gcr.io/knative-samples/helloworld-go --env TARGET=Knative

kn service delete inventory

kn service create inventory-v1 \
    --image quay.io/justindav1s/inventory:2 \
    --env TARGET=Knative \
    --env SPRING_PROFILES_ACTIVE=v1

    