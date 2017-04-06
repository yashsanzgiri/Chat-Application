package mychatapp.networking;

import java.io.*;
import java.net.*;
import chatapp.gui.Login;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Client extends Thread {

    //private final String FILENAME = null;
    //Connect c = new Connect();
    int port = 8879;
    String hostname = "127.0.0.2";
    Socket socket;
    WritableGUI gui;
    BufferedReader read;
    PrintWriter output;
    Login l;
    
    public Client(Login l) {
        this.l = l;
    }
    
    

    public void startClient() throws UnknownHostException, IOException {
        //Create socket connection
        socket = new Socket(hostname, port);

        //create printwriter for sending login to server
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        
        String username = l.un;

        output.println(username);

        String password = l.pd;
        output.println(password);
        
        /*MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            String hpass = password;
            md.update(hpass.getBytes("UTF-16"));
            byte[] digest = md.digest();
        
        output.println(new String(digest));
        System.out.println(digest);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }*/
            
        
        output.flush();

        //read = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //read response from server
        //String response = read.readLine();
        //System.out.println("This is the response: " + response);
    
    }

    public void fileInfo() {

    }
    
    @Override
    public void run() {
        Client client = this;
        try {
            client.startClient();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    
    }
    
    
    
}
