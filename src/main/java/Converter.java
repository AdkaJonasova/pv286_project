import org.junit.platform.commons.util.StringUtils;

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
        hexStr = hexStr.replace(Separator.SPACE, Separator.EMPTY);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < hexStr.length(); i++) {
            String binaryString = String.format("%4s", Integer.toBinaryString(Character.digit(hexStr.charAt(i), 16)))
                    .replace(' ', '0');
            builder.append(binaryString);
        }
        return builder.toString();
    }

    public String intToBits(String intStr, boolean isBigEndian){
        long unsignedInt = Long.parseUnsignedLong(intStr);
        String bitsStr = Long.toBinaryString(unsignedInt);
        StringBuilder result = new StringBuilder();

        if (bitsStr.length() % 8 != 0){
            int padding = 8 - (bitsStr.length() % 8);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < padding; i++) {
                sb.append("0");
            }
            sb.append(bitsStr);
            bitsStr = sb.toString();
        }

        if (!isBigEndian){
            for (int i = bitsStr.length() - 1; i >= 0; i -= 8) {
                int startIndex = Math.max(i - 7, 0);
                String chunk = bitsStr.substring(startIndex, i + 1);
                result.append(chunk);
            }
            return result.toString();
        }
        return bitsStr;
    }

    // To concrete type
    public String bitsToBytes(String bitStr){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bitStr.length(); i += 8) {
            String byteString = bitStr.substring(i, Math.min(i + 8, bitStr.length()));
            int byteValue = Integer.parseInt(byteString, 2);

            builder.append((char) byteValue);
        }
        return builder.toString();
    }

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

    public String bitsToInt(String bitStr){
        long unsignedInt = Long.parseUnsignedLong(bitStr, 2);
        String intstr = Long.toUnsignedString(unsignedInt);
        return intstr;
    }

}
