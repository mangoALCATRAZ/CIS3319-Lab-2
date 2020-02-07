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
            
            // only accept 5 lines before closing socket and ending thread
            while(!endFlag){
                String received = in.readLine();
                
                // DECRYPTION GOES HERE, PRINT OUT RESULTING PLAINTEXT INSTEAD OF received
                System.out.print("\n" + sock.getInetAddress().toString() + ": " + received); // output goes here.
                
                
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
    

