package mychatapp.networking;

import java.util.Scanner;
import javax.swing.JOptionPane;
import chatapp.gui.Login;
import chatapp.gui.MainScreen;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.net.BindException;

public class AuthServer extends Thread {

    private int currentTot;
    public static ServerSocket serversocket;
    Socket client;
    int bytesRead;
    //Connect c = new Connect();
    int port = 8879;
    String hostname = "localhost";
    BufferedReader input;
    PrintWriter output;
    Login x;
    Writer writer = null;
    File texting = new File("C:\\Users\\Yash\\Desktop\\userPass.txt");

    public void initialize() throws IOException {

        try {
            serversocket = new ServerSocket(port);
        } catch (BindException ex) {

        }
        while (true) {

            client = serversocket.accept();
            System.out.println("Server:" + serversocket.getLocalSocketAddress());
            System.out.println(client.getRemoteSocketAddress());
            try {
                logInfo();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public void logInfo() throws Exception {
        //open buffered reader for reading data from client
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String username = input.readLine();
        String password = input.readLine();
        System.out.println("[SERVER] username: " + username);
        System.out.println("[SERVER] password: " + password);
        File file = new File("C:\\Users\\Yash\\Desktop\\userPass.txt");
        Scanner scan = new Scanner(file);
        String line = null;
        String usertxt = "";
        String passtxt = "";
        HashMap<String, String> user = new HashMap<>();
        while (scan.hasNext()) {
            usertxt = scan.nextLine();
            passtxt = scan.nextLine();
            user.put(usertxt, passtxt);
        }
        if (user.get(username).equals(password)) {
            MainScreen one = new MainScreen();
            one.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Wrong Username / Password");
            x.Uname.setText("");
            x.Pswd.setText("");
            x.Uname.requestFocus();
        }
        output.flush();
        output.close();
    }

    @Override
    public void run() {
        try {
            this.initialize();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
