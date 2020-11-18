# DataSecLab3
Code for Lab 3. Done using Netbeans

To run the server, you need to launch the rmiregistry first. To do so, you need to change the directory to the one that contains the 
.class files. To do so, you need to enter the "cd <YOUR_DIRECTORY>" in the cmd or terminal. 
Then, you need to run the "start rmiregistry" command.

Before starting the server, make sure you have the logins.txt file with filled credentials in it. 
The format of credentials is (Username,Hash_Password)

You should check that the files roles.txt, role_authorization.txt and user_authorization.txt are there too, as they are used for the Access Control Mechanisms. The format is

 - roles: role,user1;user2
 - role_authorization: role,function1;function2
 - user_authorization: user,function1;function2
 
To use the file from console, the credential file should be in the same directory as the .class files (as it is the case on the repository).

The password should be stored in the hashed representation. 

All the user defined in http://www2.imm.dtu.dk/courses/02239/AccessControlLab.html (first category) have as username their first name without capital letter (Alice is "alice", Bob is "bob", etc).
All users have the same password (with the same salt to simplify that lab): "pass". This approach is quite simple but the focus of this file was solely on Access Control.

Users can be added or deleted as explain in the Evaluation section of the report.

After all the prerequisites are satisfied, you can run the server. Once the server is running, you can also start the client.
The client will ask you to log in using the username and a plaintext password. After that, the menu will be displayed.
You can put a number of an option without a dot and press enter to access the server functionality.

You can also access the logs stored in the log.txt file. The same information will be displayed in the console of the server.

	
	*Note: if you need to run the server a second time, you also need to restart the rmiregistry to avoid RMI binding exceptions.
