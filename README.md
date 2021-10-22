# SoccerMaster
## Description
This simple java program reads a listing of game
results for a soccer league as a file and returns the top teams at
the end of each matchday.

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

(2) Build and run using javac/java commands in command line. 
* To compile, once you git clone the repo, cd into soccermaster/src/main/java
```
cd soccermaster/src/main/java
javac com/xztie/soccermaster/SoccerMaster.java
```
you should see the generated SoccerMaster.class file.
* To run, 
```
java com.xztie.soccermaster.SoccerMaster [PATH_TO_INPUT_FILE]
```
For example
```
java com.xztie.soccermaster.SoccerMaster ../../test/resources/sample-input1.txt
```
It will print top 3 teams' points for each matchday. 
## Test and Validation
The code provides these validations:
* It expects exactly one argument as input parameter, i.e., 0 or more than 1 inputs are invalid inputs. 
* It validates the game results in input file (see `SoccerMasterTest.java` and files in src/test/resources):
  * each line must be in the format of "team1 goals, team2 goals", see `invalidGameResultTest` function, `sample-input2.txt` and `expected-output2.txt`.
  * the input file must exist, see `FileNotFoundTest` function.
  * each matchday must have exactly same set of teams, i.e.,
    * there shouldn't be new teams appearing on matchday > 1, see `invalidGameResultNewTeamTest` function, `sample-input3.txt` and `expected-output3.txt`.
    * there shouldn't be result missing for some team on any matchday, see `gameResultMissingForTeamTest` function, `sample-input4.txt` and `expected-output4.txt` 
