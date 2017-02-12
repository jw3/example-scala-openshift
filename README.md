Example for OpenShift
===

Simple example to test out a Scala S2I build

To use it, install S2I: https://github.com/openshift/source-to-image

### Sample invocation:

`s2i build https://github.com/jw3/example-scala-openshift.git jwiii/sbts2i example-scala-openshift`

You can then run the resulting image via:

`docker run <application image>`
