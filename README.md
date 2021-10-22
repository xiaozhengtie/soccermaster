# SoccerMaster
## Description

## Prerequisites
Java JDK version 8 or higher. To check, run
```
>java -version
openjdk version "1.8.0_292"
```
I used java 11 to compile, build and run.

## Code
See the `SoccerMaster` class in src/main/java/, and test code and files in src/test/java/.

## Build and run
(1) I used IntelliJ as IDE and gradle as build tool on my macbook. 
* Install [gradle](https://gradle.org/install/). You can use sdkman to install it
```
>sdk install gradle
```
* After you install gradle, you can build and run unit test:
```
./gradlew build
``` 
* You can also open the project in your IDE and run the `main` method in the `SoccerMaster` class.
You will have to specify an input file as the argument of main. You can use the sample-input files in
src/test/resources/.
(2) Command line 

to compile it from raw javac/java command:
```
cd soccermaster/src/main/java
javac com/xztie/soccermaster/SoccerMaster.java
```
you should see SoccerMaster.class
```
java com.xztie.soccermaster.SoccerMaster 

```
## Test