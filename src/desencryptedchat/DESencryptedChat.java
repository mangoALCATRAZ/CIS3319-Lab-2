/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desencryptedchat;

import java.net.*;
import java.util.*;
/**
 *
 * @author woah dude
 */
public class DESencryptedChat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        
        Socket sock = null;
        Scanner scan = new Scanner(System.in);
        String input;
        int port = 5000;
        
        
        while(sock == null){ // host or join menu. stuck here until a valid socket is produced.
            System.out.println("\n\nHost of join?\n\n1. Host\n2. Join");
            input = scan.nextLine();
            
            if(input.equals("1") || input.equals("1.")){
                sock = hostMethod(port);
            }
            else if(input.equals("2") || input.equals("2.")){
                sock = joinMethod(port);
            }
            else{
                System.out.println("\nInvalid, please try again.");
            }
        }
        
        
        
        
        
    }
    
    public static Socket hostMethod(int inPort) throws Exception{
        Socket ret = new Socket();
        
        // under construction
        
        try{
            ServerSocket serverSocket = new ServerSocket(inPort);
            System.out.println("\n\nAwaiting connection...");
            ret = serverSocket.accept();
            
            System.out.println("\n" + ret.getInetAddress().toString() + " connected!");
        }
        catch(Exception e){
            System.out.println(e);
            throw e;
        
        }
        return ret;
    }
    
    public static Socket joinMethod(int inPort) throws Exception{
        Socket ret = new Socket();
        Scanner in = new Scanner(System.in);
        String ip;
        
        try{
            System.out.println("\n\nPlease enter ip: ");
            ip = in.nextLine();
            
            ret = new Socket(ip, inPort);
        }
        catch(Exception e){
            System.out.println(e);
            throw e;
        }
        
        return ret;
        
        
        
        
        // under construction
        
       
    }
    
    
}
