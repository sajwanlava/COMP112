// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 - 2017T1, Assignment 6
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.io.*;
import java.net.Socket;
import java.util.*; 

/**
 * Basic IRC Chat Client 
 */

public class ChatClient {
    private String server = "irc.ecs.vuw.ac.nz";  // default IRC server for testing.
    private static final int IRC_PORT = 6667;     // The standard IRC port number.

    private Socket socket;
    private Scanner input;
    private PrintStream output;
    private ArrayList<String> channels = new ArrayList<String>();//names of all the channels
    private String channel;
    
    /**
     * main: construct a new ChatClient
     */
    public static void main(String[] args) {
        new ChatClient();
    }

    /* 
     * Sets up the user interface.
     */
    public ChatClient (){ 
        UI.addButton("Connect", this::connect);
        /*# YOUR CODE HERE */
        UI.addButton("Disconnect", this::closeConnection);
        //UI.addButton("Show Channels", this::listChannels);
        UI.addButton("Join Channel", this::join);

    }

    /**
     * If there is currently an active socket, it should close the
     *  connection and set the socket to null.
     * Creates a socket connected to the server. 
     * Creates a Scanner on the input stream of the socket, 
     *  and a PrintStream on the output stream of the socket.
     * Logs in to the server (calling the loginToServer Message)
     * Once login is successful, starts a separate thread to
     *  listen to the server and process the messages from it.
     */
    public void connect(){
        /*# YOUR CODE HERE */
        try{
            //if there is currently a socket 
            if(socket!=null){
                socket.close(); //close the connection
                socket = null; //set the connection to null
            }
            //creates new socket connected to the server
            socket = new Socket(server, IRC_PORT);
            input = new Scanner(socket.getInputStream());
            output = new PrintStream(socket.getOutputStream(), true);
            if(login()){ //true
                listenToServer();
            }
        }
        catch(IOException e){UI.println("IO failure "+ e);}
    }

    /*
     * Attempt to log in to the Server and return true if successful, false if not.
     *  Ask user for username and real name
     *  Send info to server (NICK command and USER command)
     *  Read lines from server until get a message containing 004 (success) or
     *   a message containing 433 (failure - nickname in use)
     *  (For debugging, at least, print out all lines from the server)
     */
    private boolean login(){
        String username = UI.askToken("Enter your usercode: ");
        String realname = UI.askString("Enter your real name: ");
        /*# YOUR CODE HERE */
        send("NICK " + username);
        send("USER " + username + ": " + realname);
        listenToServer();
        return false; //already listening to server
    }

    /**
     * Send a message to the current server:
     *  - check that the socket and the serverOut are not null
     *  - print the message with a \r\n at the end to serverOut
     *  - flush serverOut (to ensure the message is sent)
     */
    private void send(String msg){
        /*# YOUR CODE HERE */
        if(socket != null && output !=null){
            output.println(msg + "\r\n");
            output.flush();
        }

    }

    public void join(){
        String name = UI.askToken("Please enter the name of the channel");
        send("JOIN" + name);
        output.println("Joined " + name);
        output.println();
        while(input.hasNextLine()){
            String line = input.nextLine();
            UI.println(line); //prints user messages
        }
    }

    /**
     * Method run in the the thread that is listening to the server.
     * Loop as long as there is anything in the serverIn scanner:
     *   Get and process the next line of input from the scanner
     *   Simple version: 
     *    prints the line out for the user
     *    Checks if the line contains "SQUIT",
     *       if so, close the socket, set serverIn and serverOut set the quit the program.
     *      if the line contains "PING", send a PONG message back
     *        (must be identical to the line, but with "PING" replaced by "PONG")
     *   Better version parses the line into source, command, params, finalParam
     *    (where the source and finalParam are optional, and params may be empty)
     *    Then deals with the message appropriately.
     */
    private void listenToServer() {
        /*# YOUR CODE HERE */
        //DIDN'T FINISH
        String line = input.nextLine(); // scan the server 
        if(line.contains("SQUIT")){
            closeConnection();
        }
        else if(line.contains("PING")){
            String pong = line;
            //Scanner scan = ;
            //
            send("PONG" + pong);
        }
        
        UI.println(line);
       Scanner sc = new Scanner(line); // scan the one line 

    }

    /**
     * Close the connection:
     *  - close the socket,
     *  - set the serverIn and serverOut to null
     *  - print a message to the user
     */
    public void closeConnection(){
        /*# YOUR CODE HERE */
        if(socket !=null){
            try{ 
                socket.close();
            }catch(IOException e){UI.println("IOFailure " + e);}
            //make everything null after closiing socket so can't reconnect to server and therefore closes connection
            socket = null;
            input = null;
            output = null;
            UI.println("DISCONNECTED");
        }
    }

}
