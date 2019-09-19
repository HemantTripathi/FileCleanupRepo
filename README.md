# Repo1

Business requirement:
Many a times case arises where Log files have logged so much data that user can not view through any specific tool(e.g. notepad).
That becomes quite tedious for anyone to read or trace the data or any issue.

>Added file would check if file size is more than 2MB->rename->create a new file with same name with 0KB
*Files:
1)FileCleanup:main logic
2)Configuration.properties : properties file where you have to specify folder location and backup folder name
3)FileCleanup.jar : using above code runnable jar is created
4)execute.bat : Batch file to execute the FileCleanup.jar
Note: copy FileCleanup.jar and execute.bat at same folder location
