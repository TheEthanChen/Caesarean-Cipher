/* Name: Ethan Chen
 * PennKey: etc
 * Recitation: 217
 * 
 * Execution: java Caesar
 * 
 * Allows you to encode/decode a string in Caesar cipher and crack a Caesar
 * encrypted text through brute force
 */

public class Caesar {
    
    public static void main(String[] args) {
        
        //stop program if not all inputs entered
        if (args.length != 3) {
            throw new RuntimeException("Please enter three arguments.");
        }
        
        //variables for the entered data
        String function = args[0];
        String filename = args[1];
        String shifter = args[2];
        
        In inStream = new In(filename);
        String fileText = inStream.readAll();
        
        //decide which function to run and then runs it
        if (function.equals("encrypt")) {
            
            int key = (int) (shifter.charAt(0)) - 'A';
            System.out.println(encrypt(fileText, key));
        }
        else if (function.equals("decrypt")) {
            
            int key = (int) (shifter.charAt(0)) - 'A';
            System.out.println(decrypt(fileText, key));
        }
        else if (function.equals("crack")) {
            
            String filename2 = args[2];
            System.out.println(crack(filename, filename2));
        }
        else {
            //if did not enter function name correctly as first argument
            System.out.println("Please type in encrypt/decrypt/crack as the" + 
                               " first argument");
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
        
        //encode message
        for (int i = 0; i < str.length(); i++) {
            messagenum[i] = (int) upperCased.charAt(i) - 'A';
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
        char[] totalString = new char[symbols.length];
        
        //Set converted characters into string
        for (int i = 0; i < symbols.length; i++) {
            totalString[i] = (char) (symbols[i] + 'A');
        }
        
        return new String(totalString);
    }
    
    /*
     * Description: shifts the integers in the symbol by a user defined offset
     * Input: integer encoding of the message and int offset to encode
     * Output: shifted integer encoding of the message
     * 
     */
    public static int shift(int symbol, int offset) {
        //shift symbols only if letter
        if (symbol >= 0 && symbol <= 25) {
            //create variable to hold shifted symbols, shift to correct spot
            int shiftedSymbol = symbol + offset;
            shiftedSymbol = ((shiftedSymbol % 26) + 26) % 26;
            return shiftedSymbol;
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
        return shift(symbol, -offset);
    }
    
    /* Description: Encrytps a string message to code
     * Input: string message to be encrypted with int key to encrypt message
     * Output: encrypted string message
     */
    public static String encrypt(String message, int key) {
        //initialize the message into an int array
        int[] intMess = stringToSymbolArray(message);
        
        //shift the int array by the encrypting key
        for (int i = 0; i < message.length(); i++) {
            intMess[i] = shift(intMess[i], key);
        }
        
        //convert shifted int array into the encoded string message
        return symbolArrayToString(intMess);      
    }
    
    /* Description: Decrypts an encrypted cipher to the string message
     * Input: string cipher to be decrypted with int key to decrypt message
     * Output: decrypted string message
     */
    public static String decrypt(String cipher, int key) {
        return encrypt(cipher, -key);
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
        double totalLetters = 0;
        double[] freqOfLetters = new double[26];
        
        //find the gross amount letters appear in int array
        for (int k = 0; k < symbol.length; k++) {
            if (symbol[k] >= 0 && symbol[k] <= 25) {
                freqOfLetters[symbol[k]]++;
                totalLetters++;
            }
        }
        
        //find and insert letter frequencies into double array
        for (int j = 0; j < 26; j++) {
            freqOfLetters[j] /= totalLetters;
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
        In inStream = new In(encodedText);
        String fileText = inStream.readAll();
        
        //variables to store letters info
        double lowestScore = Double.MAX_VALUE;
        int key = 0;
        
        //find the lowest score frequency
        for (int i = 0; i < 26; i++) {
            //variables to store info at various steps to fit onto line
            String decryptedTxt = decrypt(fileText, i);
            double[] txtLetterFreq = 
                findFrequencies(stringToSymbolArray(decryptedTxt));
            
            double keyScore =
                scoreFrequencies(txtLetterFreq, 
                                 getDictionaryFrequencies(englishText));
            
            if (keyScore < lowestScore) {
                key = i;
                lowestScore = keyScore;
            }
        }
        
        //return the decoded string
        return decrypt(fileText, key);
    }
}