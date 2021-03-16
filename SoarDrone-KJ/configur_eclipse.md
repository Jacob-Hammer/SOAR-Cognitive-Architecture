# Configuring eclipse, javafx, and Soar Markup Language (sml) java package. 


Buckle up and keep your hands and feet inside the vehicle, its going to be a long and bumpy ride.... 

1a. This tutorial assumes you have already installed the Soar debugger on your workstation. If you have not, you can find the downloads here: https://soar.eecs.umich.edu/Downloads. Remember the location where you installed this. 


1. From the top menu, select 'Help', scroll down and select to 'Eclipse Marketplace...'. 
![image1] [SoarDrone-KJ/images/eclipse_setup1.png]


2. In the search bar type, fx. The first to pop-up will be e(fx)clipse. Select install. Hit confirm, accept the terms, and click finish. 

3. While this is installing, go to this link https://gluonhq.com/products/javafx/ and download: the appropriate version of eclipse for your workstation. (For mac, I used: JavaFX Mac OS X SDK). Remember the location where you installed this. 



4. If you are trying to open a pre-existing project, click on 'Import Projects from File System or Archive' under the 'File' drop down menu at the top of the screen. 

5. After opening the project, click on the drop-down arrow next to the green play button and select 'Run Configurations'. 
![image1] [SoarDrone-KJ/images/eclise55.png]

6. Click on java application, and press the 'new configuration' icon. 
![image1] [SoarDrone-KJ/images/eclipse6a.png]

7. Name the configuration, specify the name of the project it is supposed to run, in the Main Class type box type: application.Main, and click the boxes for include system libraries, and include inherited mains. 
![image1] [SoarDrone-KJ/images/eclipse7a.png]

8. Now click on: (x)=Arguements. And add in the following to your VM arguements:

		--module-path "/PATH/TO/javafx-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml -Djava.library.path=/PATH/TO/SOAR_Debugger/bin/ -cp /PATH/TO/SOAR_Debugger/bin/java/sml.jar:.

		The module-path is the path to where you saved the javafx you just downloaded. The add-modules is the path to the Soar Debugger you downloaded, and the bath to the sml.jar file that sits inside bin/java/ in the soar debugger download. 

9. Right below vm arguments. Be sure to **uncheck** the box: Use the XstartOnFirstThread arugment when launching SWT. 
![image1] [SoarDrone-KJ/images/eclipse9a.png]

10. If you are still getting errors when running the program, you will need to make sure the build path's are configured properly with the jar files. Control click on the project. 
![image1] [SoarDrone-KJ/images/eclipse10a.png]

11. From the menu, select 'Build Path' and from the next dropdown menu select 'Configure Build Path...'

12. Under libraries. You will need to add the jar files for javafx and the sml jar file. The java fx jar files, will be found in /Path/To/javafx-sdk-11.0.2/lib. (Make sure to add all of them). The sml.jar file is found in the Soar downloads files at: /PATH/TO/SOAR_Debugger/bin/java/sml.jar.  
![image1] [SoarDrone-KJ/images/eclipse5a.png]


If the run configuration you set up stops working for you, check the build paths. Make sure all the jar files are pointing to the right location. They can sometimes get changed if you are doing a git pull. 











