# home-vision-demo
Demo for HomeVision Interview


Running the Code on Linux or macOS

Before You Start
What you need on your computer:
A program called Java: Think of this as the "engine" that runs our code.
A tool called Maven: This is like our "toolbox" for the code.
A tool called Git: It helps get the code from the online storage to your computer.

Step-by-step Instructions:

1. Open the "Terminal" on your computer. You can find it using the search functionality.

2. Type in the following (and press Enter):

    bash
    
    git clone https://github.com/Gsanchez92/home-vision-demo.git

3. You'll see some lines of text. Once it stops, type:

     bash
     cd home-vision-demo/

     And press Enter again.

4. Prepare and Run the Code:

    In the same Terminal window, type:

    mvn clean install

    press Enter 
    This will organize and set things up. It might take a few minutes.

5. Once that's done, type:

    java -jar target/demo-0.0.1-SNAPSHOT.jar
    and press Enter. The program should now start!

6. To test the functionality of the service:

    a. Ensure that the server is running.

    b. Open any web browser and enter the following URL in the address bar:

    http://localhost:8080/houses/fetchAndSave

    c. Press enter. If everything is set up correctly, you should receive a response from the app.

    d. Additionally, the downloaded pictures will be saved inside the root directory of the project (or specify the exact folder name/path if it's different).
