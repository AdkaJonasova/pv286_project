# Panbyte - PV286 #

## Team members ##
- Michal Badin (485517)
- Dominik Dubovský (485020)
- Andrea Jonásová (485243)

## How to build and run our application ##
- Make sure that you have set java application path in system environment variable named "Path"
```
./gradlew build jar
cd appBuilds (from pv_286_project)
java -jar ./panbyte.jar
```
- In case you have a problem with the execution of the command ```./gradlew build jar```
  - ```-bash: ./gradlew: Permission Denied``` run ```chmod +x gradlew```
  - ```No such file or directory``` run ```gradle wrapper```
## Analysis tools we used ##
1. __Code quality__: we created our own checkstyle.xml file
2. __Static analysis__: SonarLint, SpotBugs plugins 
3. __Dynamic analysis & testing__: we wrote tests for important components as well as overall functionality, 
code coverage tool, debugging, free trial version of YourKit tool 
4. __Fuzzing__: Jazzer

## Overall functionality ##
Our application supports all arguments from assignment. We established several rules some of which are not specified in 
the assignment:
 - ```--from-options=OPTIONS``` can be only directly after ```-f FROM``` or ```--from=FROM``` argument (same goes for 
```--to-options=OPTIONS```).
 - Order of other arguments is not important.
 - If there are multiple ```--from-options=OPTIONS``` or ```--to-options=OPTIONS``` arguments, the last option is stored.
 - Repetition of any other argument is considered as invalid argument.
 - If arguments contain ```-h``` or ```--help``` argument, no other arguments are allowed.

### Limitations of our application ###
 - Since arrays can have quite complicated syntax and there are many special characters in them, 
we decided to disable custom delimiters ```-d DELIMITER in program args```. For example if user chose ``` ( ``` as a 
delimiter, after splitting input with this delimiter, structure of the array could have been ruined.
 - Our application does not support converting integers bigger than 1B to bytes. Application will not crash or print 
error, but it will convert integer byte after byte. This means that non UTF-8 characters will not be converted as 1 
character, but rather as a sequence of multiple UTF-8 characters.
 - When using default delimiter (new line), it is important that you use correct new line characters based on your 
platform. This is especially important when using reading from standard input where you have to specify these characters 
manually.
 - When converting from array we support for bytes both representations ```'a'``` and ```'\x00'```, but to array with ```--to-options=a``` we convert values to representation starting with ```\x```.



