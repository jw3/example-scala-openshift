Example for OpenShift
===

Simple example to test out a Scala S2I build

To use it, install S2I: https://github.com/openshift/source-to-image


### Sample invocation:

`s2i build https://github.com/jw3/example-scala-openshift.git jwiii/sbts2i example-scala-openshift`

You can then run the resulting image via:

`docker run <application image>`


### Configuration

- `LOG_LEVEL`: String; log level, one of `TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`, `ALL` or `OFF` (default `INFO`)
- `ACTOR_LOG_LEVEL`: String; log level, one of `OFF`, `ERROR`, `WARNING`, `INFO`, `DEBUG` (default `INFO`)

#### Client
- `EXAMPLE_SERVER_HOST`: String; Hostname to bind to
- `EXAMPLE_SERVER_PORT`: Integer; Port to listen on
- `EXAMPLE_SERVER_PATH`: String; URI Path for the request

#### Server
- `EXAMPLE_HTTP_HOST`: String; Hostname to bind to
- `EXAMPLE_HTTP_PORT`: Integer; Port to listen on

### OpenShift

You can access a free for limited use OpenShift account at: https://www.openshift.com/devpreview/register.html

Create a new OpenShift project an log in using the [oc command line tools](https://github.com/openshift/origin/releases)

`oc new-app jwiii/sbts2i:latest~https://github.com/jw3/example-scala-openshift.git --name='examples2i'`
