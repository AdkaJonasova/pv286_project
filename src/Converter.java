public class Converter {

    public String HexToBytes(String hexStr) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < hexStr.length() - 1; i+=2) {
            var hexPart = hexStr.substring(i, i + 2);
            var intFromHex = Integer.parseInt(hexPart, 16);
            builder.append((char) intFromHex);
        }

        return builder.toString();
    }

    public String BytesToHex(String bytesStr) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < bytesStr.length(); i++) {
            var asciiVal = (int) bytesStr.charAt(i);
            var hexAscii = Integer.toHexString(asciiVal);
            builder.append(hexAscii);
        }

        return builder.toString();
    }
}
