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

}
