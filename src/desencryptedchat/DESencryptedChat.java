/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desencryptedchat;

import java.net.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author woah dude
 */
public class DESencryptedChat {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
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
            
            switch (input) {
                case "1":
                case "1.":
                    sock = hostMethod(port);
                    break;
                case "2":
                case "2.":
                    sock = joinMethod(port);
                    break;
                default:
                    System.out.println("\nInvalid, please try again.");
                    break;
            }
        }
        scan.close();
        
        senderThread(sock);
        
        System.out.println("\nClosing chat program...");
        
        
    }
    
    public static Socket hostMethod(int inPort) throws IOException{
        Socket ret = null;
        
     
        
        try{
            ServerSocket serverSocket = new ServerSocket(inPort);
            System.out.println("\n\nAwaiting connection...");
            ret = serverSocket.accept();
            
            System.out.println("\n" + ret.getInetAddress().toString() + " connected!");
        }
        catch(IOException e){
            System.out.println("\n" + e);
            throw e;
        
        }
        return ret;
    }
    
    public static Socket joinMethod(int inPort) throws IOException{
        Socket ret = null;
        Scanner in = new Scanner(System.in);
        String ip;
        
        try{
            System.out.println("\n\nPlease enter ip: ");
            ip = in.nextLine();
            
            ret = new Socket(ip, inPort);
        }
        catch(IOException e){
            System.out.println("\n" + e);
            throw e;
        }
        
        in.close();
        return ret;
        
        
        
        
       
        
       
    }
    
    public static void senderThread(Socket sock) throws IOException{
        // fork here
        listenerThread lThread = new listenerThread(sock);
        lThread.start(); // starts listening thread
        
        boolean stopFlag = false;
        
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
        
        System.out.println("\nLocal sender ready.");
        while(!stopFlag){
            try{
                String userInput = stdIn.readLine();
                if(userInput.toLowerCase().equals("stop")){
                    stopFlag = true;
                    
                    
                }
                
                System.out.print("\nMe: " + userInput);
                
                // ENCRYPTION GOES HERE. SEND OUT ENCRYPTED CIPHERTEXT INSTEAD
                // OF userInput
                
                out.println(userInput);
            }   
            
                
            catch(IOException e){
                System.out.println("\n" + e);
                lThread.end();
                sock.close();
                throw e;
            }
        }
        
        lThread.end();
        sock.close();
        
        System.out.println("\nSender-aspect done running.");
        
    }
    
    
}
