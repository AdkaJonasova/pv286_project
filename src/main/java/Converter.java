public class Converter {

    // To bits

    public String bytesToBits(String bytesStr){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bytesStr.length(); i++) {
            StringBuilder binaryBuilder = new StringBuilder();
            int charValue = bytesStr.charAt(i);
            while (charValue > 0) {
                binaryBuilder.append(charValue % 2);
                charValue /= 2;
            }
            while (binaryBuilder.length() < 8) {
                binaryBuilder.append(0);
            }
            result.append(binaryBuilder.reverse());
        }
        return result.toString();
    }

    public String hexToBits(String hexStr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < hexStr.length(); i++) {
            String binaryString = String.format("%4s", Integer.toBinaryString(Character.digit(hexStr.charAt(i), 16)))
                    .replace(' ', '0');
            builder.append(binaryString);
        }
        return builder.toString();
    }

    public String intToBits(String intStr){
        int value = Integer.parseInt(intStr);
        return Integer.toBinaryString(value);
    }

    // To concrete type
    public String bitsToHex(String bitStr) {
        StringBuilder builder = new StringBuilder();

        StringBuilder bitStrBuilder = new StringBuilder(bitStr);
        while (bitStrBuilder.length() % 4 != 0) {
            bitStrBuilder.insert(0, "0");
        }
        bitStr = bitStrBuilder.toString();

        for (int i = 0; i < bitStr.length(); i += 4) {
            String nibble = bitStr.substring(i, i + 4);
            int value = Integer.parseInt(nibble, 2);
            builder.append(Integer.toHexString(value));
        }

        return builder.toString();
    }

    public String bitsToBytes(String bitStr){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bitStr.length(); i += 8) {
            String byteString = bitStr.substring(i, Math.min(i + 8, bitStr.length()));
            int byteValue = Integer.parseInt(byteString, 2);

            builder.append((char) byteValue);
        }
        return builder.toString();
    }

}
