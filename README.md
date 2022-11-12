# Tasker
A central application for the modern workspace - empowering you to collaborate and track your coworkers and employees despite the barriers of remote work. 
Tasker enables you to clock into your workplace with secure facial recognition and receive and delegate tasks on our platform with your coworkers.

## How to Use 
Visit https://tasker-nushhack.herokuapp.com

#### Logging in
1. Click register to register for an account. 
![image](https://user-images.githubusercontent.com/85171457/201458142-87c9132f-7885-46e9-a238-8b8ffa0ba88d.png)
2. Click scan and move your head slowly to register your face in our database.
3. After the AI has trained, click register to complete the process.
4. You can now login by clicking login and entering your username. Our facial recognition software will recognise your identity and you will be able to login.
5. When facial recognition does not work - i.e. in low light conditions or when your face is obscured, you can simply enter your password to enter anyway. In the future this will be secured by OAuth to maintain 2FA throughout the app.
6. At this point, you have clocked in. Your work hours are tracked by your employer from your login to your logout.
#### Your tasks 
![image](https://user-images.githubusercontent.com/85171457/201458208-db2e267c-2824-4281-b496-e145cd85c2cd.png)
1. Your tasks assigned to you by your coworkers or managers prior to your login can be seen at the homepage. 
2. To complete a task and delete it, click the radio button on the left.
3. To create a new task, click the button on the bottom right. You can set this task to be assigned to yourself by leaving it blank or to a coworker by entering their username.



## Development process
This web app was developed with a standard JavaScript frontend supported by a Kotlin backend using the KTor framework. PostgreSQL was used for database needs.

#### Work distribution 
Sai implemented the majority of the front-end, including the facial recognition and Raghav implemented a majority of the back-end, including the database and security. 

