/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desencryptedchat;

/**
 *
 * @author Tilley
 */
public class ChatHelper {
    public static String textToBinaryString(String message) {
        byte[] bytes = message.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            
        }
        String bString = binary.toString();
//        if (bString.length() > 64){
//            System.out.println("greater than 64");
//        }
//        while (bString.length() < 64){
//            bString = "0" + bString;
//        }
        return bString;
        
    }
    
    public static String binaryStringToText(String binaryString){
        String str = "";
        char nextChar;

        for (int i = 0; i <= binaryString.length() - 8; i += 8) 
        {
            nextChar = (char) Integer.parseInt(binaryString.substring(i, i + 8), 2);
            str += nextChar;
        }
        System.out.println(str);
        return str;
    }
    
   
    
        
    
    
}
