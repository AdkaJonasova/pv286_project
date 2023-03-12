package converters;

/**
 * @author Michal Badin
 */
public class IntConverter implements IConverter {
	@Override
	public String convertTo(String bitStr, boolean isBigEndian) {
		if (!isBigEndian){
			StringBuilder builder = new StringBuilder();
			bitStr = addMissingZerosToBitString(bitStr);

			for (int i = bitStr.length() - 1; i >= 0; i -= 8) {
				int startIndex = Math.max(i - 7, 0);
				String chunk = bitStr.substring(startIndex, i + 1);
				builder.append(chunk);
			}
			bitStr = builder.toString();
		}

		long unsignedInt = Long.parseUnsignedLong(bitStr, 2);
		String intstr = Long.toUnsignedString(unsignedInt);
		return intstr;
	}

	@Override
	public String convertFrom(String str, boolean isBigEndian) {
		long unsignedInt = Long.parseUnsignedLong(str);
		String bitStr = Long.toBinaryString(unsignedInt);
		StringBuilder result = new StringBuilder();

		bitStr = addMissingZerosToBitString(bitStr);

		if (!isBigEndian){
			for (int i = bitStr.length() - 1; i >= 0; i -= 8) {
				int startIndex = Math.max(i - 7, 0);
				String chunk = bitStr.substring(startIndex, i + 1);
				result.append(chunk);
			}
			return result.toString();
		}
		return bitStr;
	}

	private static String addMissingZerosToBitString(String bitStr) {
		if (bitStr.length() % 8 != 0){
			int padding = 8 - (bitStr.length() % 8);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < padding; i++) {
				sb.append("0");
			}
			sb.append(bitStr);
			bitStr = sb.toString();
		}
		return bitStr;
	}

}
