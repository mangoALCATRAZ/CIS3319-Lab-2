/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desencryptedchat;

import java.net.*;
import java.io.*;
/**
 *
 * @author woah dude
 */
public class listenerThread extends Thread{
    private Socket sock;
    private volatile boolean endFlag;
    
    public listenerThread(Socket inSock){
        sock = inSock;
        endFlag = false;
    }
    
    @Override
    public void run() {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            
            System.out.println("\nListener Thread Ready.");
            
            while(!endFlag){
                String received = in.readLine(); //incoming ciphertext
                
                // DECRYPTION GOES HERE, PRINT OUT RESULTING PLAINTEXT INSTEAD OF received
                
                
                String[] ReversedRoundKeyArray = KeyGenerator.keyGenerator(KeyGenerator.key);
                ReversedRoundKeyArray = KeyGenerator.roundKeyArrayReversal(ReversedRoundKeyArray);
                String pt = EncryptDecrypt.Decrypt(received, ReversedRoundKeyArray);
                String printOut = ChatHelper.binaryStringToText(pt);
                
                
                System.out.println(sock.getInetAddress().toString() + ": " + printOut); // output goes here.
                
                
            }
            
            sock.close();
            System.out.println("Server-aspect done running.");

            
        }
        catch(IOException e){
            
            System.out.print("\nListener thread: " + e);
            
            
        }
   }
    
    // called by other thread
    public void end(){
        endFlag = true;
    }
}
    

