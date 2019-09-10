/* Name: Ethan Chen
 * PennKey: etc
 * Recitation: 217
 * 
 * Execution: java Caesar
 * 
 * Allows you to encode/decode a string in Caesar cipher and crack a Caesar
 * encrypted text through brute force
 */

public class Caesar2 {
    public static void main(String[] args) {
        //stringToSymbolArray("Consul");
        //symbolArrayToString([2, 14, 13, 18, 20, 11]);
        shift(5, 26);
        unshift(5, 5);
        int[] n = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
        
        String function = args[0];
        String filename = args[1];
        String shifter = args[2];
        
        In inStream = new In(filename);
        String fileText = inStream.readAll();
        //scoreFrequencies(findFrequencies(stringToSymbolArray(args[0])),getDictionaryFrequencies(args[1]));
        
        if (function.equals("encrypt")) {
            int key = ((int)(shifter.charAt(0)) - 'A');
            System.out.println(encrypt(fileText, key));
        }
        else if (function.equals("decrypt")) {
            int key = ((int)(shifter.charAt(0)) - 'A');
            System.out.println(decrypt(fileText, key));
        }
        else if (function.equals("crack")) {
            String filename2 = args[2];
            System.out.println(crack(filename, filename2));
        }
        else {
            System.out.println("Please type in encrypt/decrypt/crack as the" + 
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
            messagenum[i] = (int)upperCased.charAt(i) - 'A';
            //System.out.println(messagenum[i]);
        }
        return messagenum;
        
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
            char symbolChar = (char)(symbols[i] + 'A');
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
                //System.out.println(shiftedSymbol);
                return shiftedSymbol;
            }
            else if (shiftedSymbol < 0) {
                //if shift extends behind alphabet, loop back through
                shiftedSymbol += 26;
                return shiftedSymbol;
            }
            else {
                //System.out.println(shiftedSymbol);
                return shiftedSymbol;
            }
        }
        else {
            //return symbol if it is not a letter
            //System.out.println(symbol);
            //return symbol;
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
                //System.out.println(unshiftedSymbol);
                return unshiftedSymbol;
            }
            else if (unshiftedSymbol < 0) {
                //if shift extends behing alphabet, loop back around
                unshiftedSymbol += 26;
                return unshiftedSymbol;
            }
            else {
                //System.out.println(unshiftedSymbol);
                return unshiftedSymbol;
            }
        }
        else {
            //return symbol if not letter
            //System.out.println(symbol);
            return symbol;
        }
    }
    
    /* Description: Encrytps a string message to code
     * Input: string message to be encrypted with int key to encrypt message
     * Output: encrypted string message
     */
    
    public static String encrypt(String message, int key) {
        
        //initialize the message into an int array
        int[] intMess = new int [message.length()];
        intMess = stringToSymbolArray(message);
        
        //shift the int array by the encrypting key
        for (int i = 0; i < message.length(); i++) {
            int shiftedMess = shift(intMess[i], key);
            intMess[i] = shiftedMess;
            //System.out.print(intMess[i]);
        }
        //convert shifted int array into the encoded string message
        return symbolArrayToString(intMess);      
    }
    
    /* Description: Decrypts an encrypted cipher to the string message
     * Input: string cipher to be decrypted with int key to decrypt message
     * Output: decrypted string message
     */
    
    public static String decrypt(String cipher, int key) {
        
        //initialize the message into an int array
        int[] intMess = new int [cipher.length()];
        intMess = stringToSymbolArray(cipher);
        
        //shift the int array by the encrypting key
        for (int i = 0; i < cipher.length(); i++) {
            int unshiftedMess = unshift(intMess[i], key);
            intMess[i] = unshiftedMess;
        }
        //convert shifted int array into the encoded string message
        return symbolArrayToString(intMess);      
    }
    
    /* Description: analyzes frequencies of letters in english
     * Input: name of string file to analyze
     * Output: double array with letter frequencies
     */
    
    public static double[] getDictionaryFrequencies(String filename) {
        
        //read file
        In inStream = new In(filename);
        
        //create a double array to contain letter frequencies
        double[] letterFreq = new double[26];
        
        //place letter frequencies from file into double array
        for (int i = 0; i < 26; i++) {
            letterFreq[i] = Double.parseDouble(inStream.readLine());
        }
        return letterFreq;
    }
    
    /* Description: analyzes frequencies of english letters in ciphertext
     * Input: symbol int array to analyze
     * Output: double array with letter frequencies
     */
    
    public static double[] findFrequencies(int[] symbol) {
        
        //initialize variables to hold letter info
        double letterFreq = 0;
        double totalLetters = 0;
        double[] freqOfLetters = new double[26];
        
        //find the gross amount letters appear in int array
        for (int i = 0; i < 26; i++) {
            for (int k = 0; k < symbol.length; k++) {
                if (i == symbol[k]) {
                    letterFreq += 1;
                    totalLetters += 1;
                    freqOfLetters[i] = letterFreq;
                }
                //reset letter frequency count for next letter
                letterFreq = 0;
            }
        }
        
        //find and insert letter frequencies into double array
        for (int j = 0; j < 26; j++) {
            freqOfLetters[j] = freqOfLetters[j]/totalLetters;
        }
        return freqOfLetters;
    }
    
    /* Description: Finds algamated difference of letters frequencies in 
     * analyzed cipher text against english letter frequency. The lower scores 
     * indicate higher probability of successfully decoded text.
     * Input: double array with frequency of letters coded text and 
     * double array with frequency of letters in english language
     * Output: double with score of compared letter frequencies
     */
    
    public static double scoreFrequencies(double[] freqs, 
                                          double[] englishFreqs) {
        double messFreqScore = 0;
        for (int i = 0; i < 26; i++) {
            messFreqScore += Math.abs(freqs[i] - englishFreqs[i]);
        }
        return messFreqScore;
    }
    
    /* Description: Analyzes coded text and returns uncoded text 
     * Input: string with encoded text and string with english letters
     * Output: string of uncoded text
     */
    
    public static String crack(String encodedText, String englishText) {
        //read file
        In inStream = new In (encodedText);
        String fileText = inStream.readAll();
        
        //variables to store letters info
        double lowestScore = 100;
        double[] letterScoreFreq = new double[26];
        int key = 0;
        
        //find the lowest score frequency
        for (int i = 0; i < 26; i++) {
            letterScoreFreq[i] = scoreFrequencies(findFrequencies(stringToSymbolArray(decrypt(fileText, i))), getDictionaryFrequencies(englishText));
            if (letterScoreFreq[i] < lowestScore) {
                lowestScore = letterScoreFreq[i];
            }
            System.out.println(letterScoreFreq[i]);
        }
        System.out.println(lowestScore);
        //find the key to decode the string
        for (int j = 0; j < 26; j++) {
            if (lowestScore == letterScoreFreq[j]) {
                key = j;
                System.out.println(key);
            }
        }
        //return the decoded string
        return decrypt (fileText, key);
    }
}



