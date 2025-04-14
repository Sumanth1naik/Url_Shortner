import java.math.BigInteger;

public class Base62Encoder {
    // Create a string with the [0-9] [A-Z] [a-z] and their index will be used to encode the values
    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;

    // Convert an integer to a Base62 encoded string
    // num will be the number to encode ex:123, 111
    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            // Append the base value of the BASE62_CHARS by finding the quotient of each digit left to right
            sb.append(BASE62_CHARS.charAt((int) (num % BASE)));
            // Remove the last digit
            num /= BASE;
        }
        // Reverse the sb to get the correct order
        return sb.reverse().toString();
    }

    // This function is used to convert the URL string to byte array value
    public static long byteArrayValue(String inputString) {
        BigInteger bigInt = null;
        byte[] byteArray = null;
        long byteValue = 0;
        // Convert the input string into the UTF-8 byte array
        byteArray = inputString.getBytes();
        // Step 2: Convert Byte Array to BigInteger
        bigInt = new BigInteger(1, byteArray);
        byteValue = bigInt.longValue();
        return byteValue;
    }

    // This is the controller function for Base62 logic
    public static String stringEncoder(String url) {
        // Convert string to byte array value
        long byteValue = byteArrayValue(url);
        // Convert the byte array value to the encoded string
        String encoded_String = encode(byteValue);
        return encoded_String;
    }
}
