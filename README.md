# pv286_project
- Michal Badin
- Dominik Dubovský 
- Andrea Jonásová


# How to build and run JAR
- Be sure that you have set java application path in system environment variable named "Path"
```
./gradlew build jar
cd appBuilds (from pv_286_project)
java -jar ./panbyte.jar
```

#Analysis tools we used
- Code quality: we created our own checkstyle.xml 
- Static analysis: SonarLint, SpotBugs
- Dynamic analysis & testing: we wrote tests for important components as well as overall functionality, code coverage tool
- Fuzzing:
