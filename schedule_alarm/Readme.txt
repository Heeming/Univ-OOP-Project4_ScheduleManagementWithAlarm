Since the curl Post statement is written for Windows, the program must be executed in Windows.
Otherwise, email does not work properly.
Because curl is used, curl must be installed.
How to install: https://curl.haxx.se/download.html

Since the program was written in Eclipse, I will describe the execution environment based on Eclipse.

¡ÙThe main class is in Alarmmain.¡Ù

Download the Java Developer Toolkit.
The project is the build.gradle project.
Click the properties of the project, enter the project setting, and add JRE to Add Library.
Click Installed JREs to open the JRE management window.
Click Add to add the Standard VM.

Click Directory to select the installed JRE directory
Click Finish to register the JRE directory

Afterwards, JDK can be linked by selecting the registered JRE

Run Gradle's build script (build.gradle)

Then, create the schedule_management database with mysql

Then, after entering your mysql ID and password in the auth\mysql.auth file,
Just compile the project.