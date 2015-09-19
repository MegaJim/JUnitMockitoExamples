# JUnitMockitoExamples

1. Download GitHub for Windows from https://desktop.github.com/ (note, if you can't install due to admin rights, you can try https://github.com/git-for-windows/git/releases) and download portable git 7z exe, extract and open shell in /bin and clone the repo by typing git clone https://github.com/MegaJim/JUnitMockitoExamples.git
If you can't get any of these to work the Git plugin for eclipse should also work using JGit
2. Download maven: https://maven.apache.org/download.cgi
3. Download JDK 1.7+ if not already installed
4. Install all software and ensure following environment variables are set:
JAVA_HOME
MAVEN_HOME
5. Add JAVA_HOME\bin and MAVEN_HOME\bin to the beginning of your Path variable, e.g.
%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%Path%
by typing environment into the start ment and choose "edit environment variables for your account" and creating variables as per above
6. Sign up for a (free!) GitHub account
7. Clone the https://github.com/MegaJim/JUnitMockitoExamples.git repository using GitHub for windows application
8. Put a file called settings.xml in userhome\\.m2 with the following contents (if you want the local repo to go to D: drive)
```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          http://maven.apache.org/xsd/settings-1.0.0.xsd">
      <localRepository>D:/m2/repository</localRepository>
</settings>
```
9. Go to the repository directory and run mvn eclipse:eclipse in the same folder as the pom.xml to generate .project and .classpath based on the pom.xml for the project - this will also download all project dependencies listed in the pom and install them to your local maven repository at user\\.m2\repository
10. Import project into eclipse - all your dependencies are automatically included from the M2 repository
11. Set the M2_REPO environment variable in eclipse if there are build path errors
12. Right-click the project and select Run As -> JUnit Test
13. Start reading through the tests beginning with Example1JUnitOverviewTest

You can run mvn eclipse:eclipse at any time to refresh the project definition for eclipse if you update the pom.  You can use mvn eclipse:clean to delete the files if they are not refreshing.  Go back to Eclipse and F5 the project to see changes.
