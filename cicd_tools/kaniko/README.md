# Kaniko : Building container Images from within a Container

## Initial Source

https://medium.com/developers-writing/reducing-build-time-on-openshift-using-kaniko-909d4bf0a874

Before the Kaniko pod can run there are two things that must happen.

   *A Persistent Volume must be created an populated with a docker config file with a servicesccount token that has privileges to pudh to the openshift registry
