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
    
    public listenerThread(Socket inSock){
        sock = inSock;
    
    }
    
    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            
            // only accept 5 lines before closing socket and ending thread
            for(int i = 0; i < 5; i++){
                String received = in.readLine();
                System.out.println(""); // output goes here.
                
            }
        }
        catch(Exception e){
            
            System.out.print(e);
        }
   }
}
    

