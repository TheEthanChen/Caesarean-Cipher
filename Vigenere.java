/* Name: Ethan Chen
 * PennKey: etc
 * Recitation: 217
 * 
 * Execution: java Vigenere
 * 
 * Allows you to encode/decode a string in Vigenere cipher
 */

public class Vigenere {
    
    public static void main(String[] args) {
        
        String function = args[0];
        String filename = args[1];
        String shifter = args[2];
        if (args[0] == null || args[1] == null || args[2] == null) {
            throw new RuntimeException("Please enter three arguements");
        }
        
        In inStream = new In(filename);
        String fileText = inStream.readAll();
        
        if (function.equals("encrypt")) {
            System.out.println(encrypt(fileText, shifter));
        }
        else if (function.equals("decrypt")) {
            System.out.println(decrypt(fileText, shifter));
        }
        else {
            System.out.println("Please type in encrypt/decrypt as the" + 
                               "first arguement");
        }
    }
    
    /*
     * Description: converts a string to a symbol array,
     * where each element of the array is an integer
     * encoding of the corresponding element of the string.
     * Input:  the message text to be converted
     * Output: integer encoding of the message
     */
    public static int[] stringToSymbolArray(String str) {
        
        //create new array of strings
        int[] messagenum = new int [str.length()];
        
        //raise string to Caps for easier shifting
        String upperCased = str.toUpperCase();
        
        //place str length in variable to reduce computations
        int stringLength = str.length();
        
        //encode message
        for (int i = 0; i < stringLength; i++) {
            
            messagenum[i] = (int) upperCased.charAt(i) - 'A';
        }
        
        return messagenum;  
    }
    
    /*
     * Description: converts the string key to an int array corresponding to
     * each element of the string. The length of the array is the length of 
     * the string message to be encoded.
     * Input:  the message text to be converted and the string key
     * Output: integer key to encode the message
     */
    public static int[] stringToSymbolArrayKey(String message, 
                                               String key) {
        
        //create new array of strings
        int[] keynum = new int [message.length()];
        
        //raise string to Caps for easier shifting
        String upperCased = key.toUpperCase();
        
        //place message length in variable to reduce computations
        int messageLength = message.length();
        int k = 0;
        
        //encode message
        for (int i = 0; i < messageLength; i++) {
            keynum[i] = (int) upperCased.charAt(k) - 'A';
            k += 1;
            if (k >= key.length() && i < messageLength) {
                k = 0;
            }    
        }
        
        return keynum;  
    }
    
    /*
     * Description: converts an array of symbols to a string, 
     * where each element of the 
     * array is an integer encoding of the corresponding element of the string.
     * Input:  integer encoding of the message
     * Output: the message text
     */
    public static String symbolArrayToString(int[] symbols) {
        
        //Empty String to contain characters from integer encoding
        String totalString = "";
        
        //Set converted characters into string
        for (int i = 0; i < symbols.length; i++) {
            
            char symbolChar = (char) (symbols[i] + 'A');
            totalString += symbolChar;
        }
        
        return totalString;
    }
    
    /*
     * Description: shifts the integers in the symbol by a user defined offset
     * Input: integer encoding of the message and int offset to encode
     * Output: shifted integer encoding of the message
     * 
     */
    
    public static int shift(int symbol, int offset) {
        
        //create variable to hold shifted symbols
        int shiftedSymbol = symbol + offset;
        
        //shift symbols only if letter
        if (symbol >= 0 && symbol <= 25) {
            
            if (shiftedSymbol > 25) {
                
                //if shift extends past alphabet, loop back through
                shiftedSymbol -= 26;
                return shiftedSymbol;
            }
            else if (shiftedSymbol < 0) {
                
                //if shift extends behind alphabet, loop back through
                shiftedSymbol += 26;
                return shiftedSymbol;
            }
            else {
                return shiftedSymbol;
            }
        }
        else {
            //return symbol if it is not a letter
            return symbol;
        }
    }
    
    /* Description: shifts the integers in the shifted symbol by the offset to 
     * return the unshifted original integer encoding of the message
     * Input: shifted integer encoding of the message with int offset to uncode
     * Output: unshifted integer encoding of the message 
     */
    public static int unshift(int symbol, int offset) {
        
        //variable to contain unshifted symbol
        int unshiftedSymbol = symbol - offset;
        
        //unshift symbol only if a letter
        if (symbol >= 0 && symbol <= 25) {
            
            //if shifts extends past alphabet, loop back around
            if (unshiftedSymbol > 25) {
                
                unshiftedSymbol -= 26;
                return unshiftedSymbol;
            }
            else if (unshiftedSymbol < 0) {
                
                //if shift extends behing alphabet, loop back around
                unshiftedSymbol += 26;
                return unshiftedSymbol;
            }
            else {
                return unshiftedSymbol;
            }
        }
        else {
            //return symbol if not letter
            return symbol;
        }
    }
    
    /* Description: Encrytps a string message to code
     * Input: string message to be encrypted with string key to encrypt message
     * Output: encrypted string message
     */
    public static String encrypt(String message, String key) {
        
        //initialize the message into an int array
        int[] intMess = new int [message.length()];
        intMess = stringToSymbolArray(message);
        
        //initialize key into int array
        int[] intKey = new int [message.length()];
        intKey = stringToSymbolArrayKey(message, key);
        
        //shift the int array by the encrypting key
        for (int i = 0; i < message.length(); i++) {
            
            int shiftedMess = shift(intMess[i], intKey[i]);
            intMess[i] = shiftedMess;
        }
        
        //convert shifted int array into the encoded string message
        return symbolArrayToString(intMess);      
    }
    
    /* Description: Decrypts an encrypted cipher to the string message
     * Input: string cipher to be decrypted with string key to decrypt message
     * Output: decrypted string message
     */
    public static String decrypt(String cipher, String key) {
        
        //initialize the message into an int array
        int[] intMess = new int [cipher.length()];
        intMess = stringToSymbolArray(cipher);
        
        int[] intKey = new int [cipher.length()];
        intKey = stringToSymbolArrayKey(cipher, key);
        
        //shift the int array by the encrypting key
        for (int i = 0; i < cipher.length(); i++) {
            
            int unshiftedMess = unshift(intMess[i], intKey[i]);
            intMess[i] = unshiftedMess;
        }
        
        //convert shifted int array into the encoded string message
        return symbolArrayToString(intMess);      
    }
}