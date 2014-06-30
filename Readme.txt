===How to execute the application===

1. using ant
if you have ant installed on your system invoke the target 'run' (which is the default target) and follow the instructions to post, read, follow, and wall

> ant run

2. without using ant
set the class path then go to the base folder of the down loaded code base and invoke the below command from the command prompt
java com.test.socialnw.SocialNWStarter 
or
java -cp <classpath> SocialNWStarter //if you haven't set the class path

then follow the instruction displayed on the console to post, read, follw and display wall

The test directory contains junit test cases, using ant,  unit tests can be invoke by the ant target junit

>ant junit

The application will also accept user with first, second and last names

example
post:
john miller -> what a nice weather
Nacy J Nadth -> What a funny post

Nancy J Smith follows John Miller

Nancy J Smith wall

You can also see  the functional test report with all test cases in the file TestReport.xlsx

If you need any more clarification contact me @ sunish.jose@ymail.com