/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mychatapp.networking;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.IOUtils;
import java.lang.Object;
import java.security.Key;
import java.util.Calendar;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import sun.misc.BASE64Decoder;

public class MessageListener extends Thread {

    ServerSocket server;
    int port = 8877;
    WritableGUI gui;

    public MessageListener(WritableGUI gui, int port) {
        this.port = port;
        this.gui = gui;
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public MessageListener() {

        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
                        
       private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
    
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
}
                        
       public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    
    
    @Override
    public void run() {
        Socket clientSocket;
        try {
            while ((clientSocket = server.accept()) != null) {
                InputStream is = clientSocket.getInputStream();
//                InputStream is = new ByteArrayInputStream(clientSocket.getBytes());
                DataInputStream dis = new DataInputStream(is);
//              String result = getStringFromInputStream(is);
               // String en = is.toString();
                try {
//                BufferedReader br = new BufferedReader(new InputStreamReader(is));
               byte[] tt = new byte[2048];
                int size = dis.read(tt);
                
                String s = new String(Arrays.copyOfRange(tt, 0, size));
                 String decry = decrypt(s);
                String line = decry /*br.readLine();*/;
                
                String[] split = line.split(":");
                int flag1 = 0, flag2 =0;
                 MessageDigest md;
                try {
                    md = MessageDigest.getInstance("SHA-256");
                
            String ts = split[2];
           /* Date date = new java.util.Date();
            Calendar cal = Calendar.getInstance();
            Timestamp ts_recv = Timestamp.valueOf(ts);
            Timestamp ts_curr = new Timestamp(cal.getTime().getTime());
            long diff = ts_curr.getTime() - ts_recv.getTime();*/
            
            String text = split[0];
            md.update(text.getBytes("UTF-16"));
            byte[] digest = md.digest();
            String hash = new String(digest);
           // String hash1 = digest.toString();
            if((hash.equals(split[1])))
                    {
                        flag1 =1; // checking if hashes are equal
                    } 
            /*if (diff == 1000)
                    {
                         flag2=1; // checking difference in timestamps
                    }*/
                
                if (line != null && flag1 == 1) 
                {
                    gui.write("Friend: " + split[0]);
                   // gui.write("Readme " + s);
                   //  gui.write(split[1]);
                   //   gui.write(hash);
                    //    gui.write(split[2]);
                }
                else if(flag1 ==0)
                {
                    gui.write("System: OOPS! Someone sneaked in!");
                }
                else if (line == null)
                {
                    gui.write("System: Please write something");
                }
                
                
                }
                
            catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
                    gui.write("System: OOPS! Something's wrong 1!");
                }
            
                } catch (Exception ex) {
                    Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
                    gui.write("System: OOPS! Something's wrong 2!");
                }
                
                
                

            }
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
            gui.write("System: OOPS! Something's wrong 3!");
        }
    }
   /* public void sendData(){
        gui.write("Enter username and password");
        String username ;
        String password  ;
        double Random = Math.random()/10;
        Server 
    }*/
}
