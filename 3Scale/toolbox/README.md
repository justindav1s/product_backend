## Register Tenant with toolbox

```
3scale remote add 3scale "https://cc296872c91149e83aee0ab67350beff8f744903d7387037775e02b09adcd870@3scale-admin.apps.ocp4.datr.eu"
```

## List Services

```
3scale service list 3scale
```

## New Service

3scale import openapi \
    -d 3scale \
    product_openapi3.yaml \
    --override-private-base-url=http://product-amazin-quarkus.apps.ocp4.datr.eu \
    -t product-catalog default \
    --default-credentials-userkey=monkey123

