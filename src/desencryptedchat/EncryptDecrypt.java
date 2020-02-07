/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tilley
 */
package desencryptedchat;
public class EncryptDecrypt {
    
    public static int INITIALMESSAGELENGTH = 64;
    public static int ROUNDKEYLENGTH = 48;
    public static int NUMBEROFROUNDS = 16;
    public static int BPERGROUP = 6;
    public static int BGROUPS = 8;
    
    private static String message;
    
    private static int[][] s0 = {
        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    };

    private static int[][] s1 = {
        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    };

    private static int[][] s2 = {
        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    };

    private static int[][] s3 = {
        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    };

    private static int[][] s4 = {
        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    };

    private static int[][] s5 = {
        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    };

    private static int[][] s6 = {
        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    };

    private static int[][] s7 = {
        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    };

    private static int[][][] sboxes = {s0, s1, s2, s3, s4, s5, s6, s7};

    private static int[] E
            = {
                32, 1, 2, 3, 4, 5,
                4, 5, 6, 7, 8, 9,
                8, 9, 10, 11, 12, 13,
                12, 13, 14, 15, 16, 17,
                16, 17, 18, 19, 20, 21,
                20, 21, 22, 23, 24, 25,
                24, 25, 26, 27, 28, 29,
                28, 29, 30, 31, 32, 1
            };

    private static int[] P
            = {
                16, 7, 20, 21,
                29, 12, 28, 17,
                1, 15, 23, 26,
                5, 18, 31, 10,
                2, 8, 24, 14,
                32, 27, 3, 9,
                19, 13, 30, 6,
                22, 11, 4, 25
            };

    private static int[] IP
            = {
                58, 50, 42, 34, 26, 18, 10, 2,
                60, 52, 44, 36, 28, 20, 12, 4,
                62, 54, 46, 38, 30, 22, 14, 6,
                64, 56, 48, 40, 32, 24, 16, 8,
                57, 49, 41, 33, 25, 17, 9, 1,
                59, 51, 43, 35, 27, 19, 11, 3,
                61, 53, 45, 37, 29, 21, 13, 5,
                63, 55, 47, 39, 31, 23, 15, 7
            };

    private static int[] IPinv
            = {
                40, 8, 48, 16, 56, 24, 64, 32,
                39, 7, 47, 15, 55, 23, 63, 31,
                38, 6, 46, 14, 54, 22, 62, 30,
                37, 5, 45, 13, 53, 21, 61, 29,
                36, 4, 44, 12, 52, 20, 60, 28,
                35, 3, 43, 11, 51, 19, 59, 27,
                34, 2, 42, 10, 50, 18, 58, 26,
                33, 1, 41, 9, 49, 17, 57, 25
            };
    
    
    public EncryptDecrypt(String inMessage){
        
        message = ChatHelper.textToBinaryString(inMessage);
    }
    
    // takes 64 bit msg, puts it through IP table, outputs 64 bit
    public static String permutationIP(String msg){
        String[] initialMsg = msg.split("");
        String[] IPMsgArr = new String[INITIALMESSAGELENGTH];
        for (int i = 0 ; i < INITIALMESSAGELENGTH ; i++){
            IPMsgArr[i] = initialMsg[IP[i] - 1];
//            
        }
        String  IPMsg = String.join("", IPMsgArr);

        return IPMsg;
    }
    // uses roundFunction to get all the way to the 16th round
    public static String multiRoundFunction(
            String lHalfANDRHalf, String[] RoundKeyArray){
        
        String bothHalves = lHalfANDRHalf;
        int middleOfString = lHalfANDRHalf.length() / 2;
        
        for (int round = 0; round < NUMBEROFROUNDS; round++){
            
            String[] Halves = {bothHalves.substring(0, middleOfString),
                bothHalves.substring(middleOfString)};
        
            String lHalf = Halves[0];
            String rHalf = Halves[1];
            
            bothHalves = roundFunction(lHalf,rHalf,RoundKeyArray[round]);
        }
        // at this point, we are on round 16 and bothHalves is L16R16
        String[] Halves = {bothHalves.substring(0, middleOfString),
                bothHalves.substring(middleOfString)};
        
        String lHalf = Halves[0];
        String rHalf = Halves[1];
        
        String preCypherText = rHalf + lHalf;
//        System.out.println("cypher text before IPi: " + preCypherText);
        
        return preCypherText;
                
        
        
    }
    
    // takes Lhalf, Rhalf, and roundKey as input, 
    // outputs concatenated new L and R half
    public static String roundFunction(String lHalf, String rHalf, String roundKey){
        String lHalfNew = rHalf;
        String rHalfNew = rightHalfFunction(lHalf, rHalf, roundKey);
//        System.out.println("new Right Half: " + rHalfNew);
        
        return lHalfNew + rHalfNew;
    }
    
    
    // determines the right half of function for each round
    public static String rightHalfFunction(String lHalf, String rHalf, String roundKey){
        String fExpansion = rightHalfToE(rHalf); // 32 bit to 48 bit
        String fXOR = XOR(roundKey,fExpansion); // xors round key and expanded rhalf
        
        String[] fArr = fXOR.split("");
        String[] B = new String[BGROUPS];
        String b;
        int bitTracker = 0; // determines what group number we are on
        
        for (int bitgroup = 0; bitgroup < BGROUPS; bitgroup++){ //8 bit group
            b = "";
            for (int bitnum = 0; bitnum < BPERGROUP; bitnum++){ // 6 bits per group
                b = b + fArr[bitTracker];
                bitTracker++;
            }
            B[bitgroup] = b;
        }
        String fSB = "";
        
        for (int bitgroup = 0; bitgroup < BGROUPS; bitgroup++){ // gets correct val from sbox
            
            b = B[bitgroup];
            String[] bArr = b.split("");
            
            String srow = bArr[0] + bArr[BPERGROUP - 1]; // splits bits into rows
            String scol = bArr[1] + bArr[2] + bArr[3] + bArr[4]; // and columns
            
            int srowval = Integer.parseInt(srow, 2);
            int scolval = Integer.parseInt(scol, 2);
            
            int[][] sbox = sboxes[bitgroup];
     
            String sval = sboxParser(sbox,srowval,scolval);
            fSB = fSB + sval; // gets the 4 bit val from the sbox
            
//            System.out.println(fSB);
            
        }
        String f = sbToP(fSB); // puts the 32 bit sb through P permutation
        String newRHalf = XOR(lHalf, f);
        
        return newRHalf;
        
    }
    
    // sends right half through E table
    public static String rightHalfToE(String rHalf){
        String[] initialRHalf = rHalf.split("");
        String[] rHalfArr = new String[ROUNDKEYLENGTH];
        for (int i = 0 ; i < ROUNDKEYLENGTH ; i++){
            rHalfArr[i] = initialRHalf[E[i] - 1];
//            
        }
        String  rHalfNew = String.join("", rHalfArr);

        return rHalfNew;
    }
    
    // xors 2 bit strings
    public static String XOR(String str1, String str2){
        StringBuffer xor = new StringBuffer();

      for (int i = 0; i < str1.length(); i++) {
         xor.append(str1.charAt(i) ^ str2.charAt(i));
      }
      return xor.toString();
    }
    
    // gets correct value from indicated sbox, returns string
    public static String sboxParser(int[][] sbox, int row, int col){
        int sboxval = sbox[row][col];
        String sval = Integer.toString(sboxval, 2); // converts to binary string
        while(sval.length() != 4){
            sval = "0" + sval;
        }
//        System.out.println(sval.length());
        return sval;
        
    }
    // passes SB through P table
    public static String sbToP(String SB){
        String[] initialSB = SB.split("");
        String[] PSBArr = new String[INITIALMESSAGELENGTH / 2 ];
        for (int i = 0 ; i < INITIALMESSAGELENGTH / 2 ; i++){
            PSBArr[i] = initialSB[P[i] - 1];
//            
        }
        String  permutedSB = String.join("", PSBArr);

        return permutedSB;
    }
    
    public static String permutationIPinv(String preCT){
        String[] initialCT = preCT.split("");
        String[] cypherTextArr = new String[INITIALMESSAGELENGTH];
        for (int i = 0 ; i < INITIALMESSAGELENGTH ; i++){
            cypherTextArr[i] = initialCT[IPinv[i] - 1];
//            
        }
        String  cypherText = String.join("", cypherTextArr);

        return cypherText;
    }
    
    public static String Encrypt(String initialMessage, String[] RoundKeyArray){
        
        String IPMessage = permutationIP(initialMessage);
        String preCypherText = multiRoundFunction(IPMessage, RoundKeyArray);
        String CypherText = permutationIPinv(preCypherText);
//        System.out.println("cypher text1: " + CypherText);

        return CypherText;
    }
    
    public static String Decrypt(String cypherText, String[] REVRoundKeyArray){
        
        String IPMessage = permutationIP(cypherText);
        String prePlainText = multiRoundFunction(IPMessage, REVRoundKeyArray);
        String PlainText = permutationIPinv(prePlainText);
//        System.out.println("Plaintext: " + PlainText);
        
        return PlainText;
    }
    public static String getInitialMessage(){
        return message;
    }
    
  /*  public static void main(String[] args){
      
        System.out.println("Initial message: " + message);
        
        String[] RoundKeyArray = KeyGenerator.keyGenerator(KeyGenerator.key);
        
//        System.out.println("round key 1: " + RoundKeyArray[0]);
//        System.out.println("round key 16: " + RoundKeyArray[15]);
                
        String ct = Encrypt(message, RoundKeyArray);
        System.out.println("CypherText: " + ct);
        
        String[] ReversedRoundKeyArray = KeyGenerator.keyGenerator(KeyGenerator.key);
        
        ReversedRoundKeyArray = KeyGenerator.roundKeyArrayReversal(ReversedRoundKeyArray);
        
//        System.out.println("reversed round key 1: " + RoundKeyArray[15]);
//        System.out.println("reversed round key 16: " + RoundKeyArray[0]);
        
        String pt = Decrypt(ct, ReversedRoundKeyArray);
        System.out.println("CypherText decrypted: " + pt);
        
    }
 */
}
