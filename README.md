# Use the same examples and verify through different seams

Examples that describes the behaviour of a system should be 
valid however you use the system. It is not expected to be different if you 
use its rest api or verify the model.

This leads us to want to use the same example and verify that 
the system works through different seams and with different clients 
or devices.

## What is a seam?

A seam is one place where you can connect to the system under test. 
This can be a class inside the application, it can be an external endpoint 
exposed as a rest api, or it can be a web user interface.

The different seams require different connection mechanisms. They are 
called adapters in this example.

There is one adapter for connecting to the model called `ModelAdapter`. 
There is another adapter for connecting through a public rest api called `RestAdapter`.

The adapters must be specified when running the build. The tests will default 
to the model adapter if no adapter is specified.

## Specifying the seam

Defining the seam to connect through is done by specifying a runtime property 
in the JVM.

Specifying runtime properties for the JVM is done 
using `-Dproperty-name=property-value`.

There are only two valid seams in this example:

* `-Dseam=model`
* `-Dseam=rest`

## Building

Building using different seams are done like this:

`./gradlew clean build -Dseam=model`

`./gradlew build -Dseam=rest` 

This will allow fast feedback when verifying that the model works as expected. 

It will also allow you to verify the delivery mechanism through a rest api. 
Starting the application and connecting to it using a network and http is 
much slower compared to an in memory solution. 
Unfortunately, the system is useless if the delivery mechanism doesn't work. 
Both ways of verifying the system are needed.

There is a shell script that will run both steps in sequence, `build.sh`.

This example uses Cucumber-JVM. Cucumber-JVM requires Java 8.
