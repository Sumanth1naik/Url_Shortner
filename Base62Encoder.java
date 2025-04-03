import java.math.BigInteger;

public class Base62Encoder {
    //create a string with the [0-9] [A-Z] [a-z] and their index will be used to encode the values
    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;

    // Convert an integer to a Base62 encoded string
    //num will be the number to encode ex:123, 111
    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            //append the base value of the BASE62_CHARS by fining the quetiont of the each digit left to right
            float val = num%BASE;
            sb.append(BASE62_CHARS.charAt((int) (num % BASE)));
            //remove the last digit
            num /= BASE;
        }
        //reverse the sb to get the correct order
        return sb.reverse().toString();
    }

    // // Convert a Base62 encoded string back to an integer
    // //str is the encoded value of the num
    // public static long decode(String str) {
    //     long num = 0;
    //     for (int i = 0; i < str.length(); i++) {
    //         num = num * BASE + BASE62_CHARS.indexOf(str.charAt(i));
    //     }
    //     return num;
    // }

    public static long byteArrayValue(String inputString){
        BigInteger bigInt = null;
        byte [] byteArray = null;
        long byteValue = 0;
        //Convert the input string into the utf8 byte array
        byteArray = inputString.getBytes();
        //convert array to String
         // Step 2: Convert Byte Array to BigInteger
        bigInt = new BigInteger(1, byteArray);
        byteValue = bigInt.longValue();
        return byteValue;
    }

    public static void main(String[] args) {
        String number = "https://www.tutorialspoint.com/java/math/biginteger_longvalue.htm";
        long byteValue = byteArrayValue(number);
        String encoded = encode(byteValue);
        // long decoded = decode(encoded);

        System.out.println("Original Number: " + number);
        System.out.println("Base62 Encoded: https://short.ly/" + encoded);
        // System.out.println("Decoded Back: " + decoded);
    }
}
