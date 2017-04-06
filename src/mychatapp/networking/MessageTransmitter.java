/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mychatapp.networking;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.*;

public class MessageTransmitter extends Thread {

    String hostname, message;
    int port;
    WritableGUI gui;

    public MessageTransmitter() {
    }

    public MessageTransmitter(String message, String hostname, int port, WritableGUI gui) {
        this.message = message;
        this.hostname = hostname;
        this.port = port;
        this.gui = gui;
    }
    
     private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };

public static String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    /*
        public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
*/
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
}

    

    @Override
    public void run() {

        try {
            Socket s = new Socket(hostname, port);
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            String text = message;
            md.update(text.getBytes("UTF-16"));
            byte[] digest = md.digest();
            
            Date date = new java.util.Date();
            Calendar cal = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(cal.getTime().getTime()); 
            String text1 = message + ":" + new String(digest) + ":" + timestamp;
            try {
                String encryptdata =encrypt(text1);
            s.getOutputStream().write(encryptdata.getBytes());
            gui.write("Me: " + message);
            
            } catch (Exception ex) {
                Logger.getLogger(MessageTransmitter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//            s.close();
        } catch (IOException | NoSuchAlgorithmException ex) {
            Logger.getLogger(MessageTransmitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
