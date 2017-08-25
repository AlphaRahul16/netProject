### ACS Society - Netforum

#### Summary:

Automated Test Suite. This uses Selenium Testing Tool for automated testing of Project ACS Society
netFORUM is a web-based association management system from Abila.

#### System Requirement:

* JDK 1.7 or above
* Maven 3.1
* Eclipse or IDE of choice in case there is need to update the script. (optional)

#### Script Execution

Open terminal and navigate to the root directory of the scripts. Root directory contains pom.xml file

    mvn clean verify -Dtestngxml=<<TestNG.xml name>> //for complete test-suite
    mvn clean verify -Dtest=<<TestScriptName>> //for single test script

#### Result Files:	
The Test Execution Results will be stored in the following directory once the test has completed

    ./target/test-output/emailable-report.html (for complete test suite)
    ./target/surefire-reports/emailable-report.html (for single test suite)
