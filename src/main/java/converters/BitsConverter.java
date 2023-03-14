package converters;

import utils.BitsOption;
import utils.Separator;

import static utils.BitsOption.LEFT;
import static utils.Separator.EMPTY;
import static utils.Separator.SPACE;

public class BitsConverter implements IConverter{
	@Override
	public String convertTo(String bitStr, String option) {
		return bitStr;
	}

	@Override
	public String convertFrom(String str, String option) {
		option = Separator.isEmpty(option) ? "left" : option;
		BitsOption padSide = BitsOption.fromString(option);

		str = str.replace(SPACE.getText(), EMPTY.getText());
		str = addMissingZerosToBitString(str, padSide);
		return str;
	}

	private static String addMissingZerosToBitString(String bitStr, BitsOption padSide) {
		int numOfMissingBits = bitStr.length() % 8;

		if (numOfMissingBits != 0){
			int padding = 8 - (numOfMissingBits);
			StringBuilder sb = new StringBuilder();
			if (LEFT.equals(padSide)){
				sb.append("0".repeat(padding));
				sb.append(bitStr);
				return sb.toString();
			}
			sb.append(bitStr);
			sb.append("0".repeat(padding));
			return sb.toString();
		}
		return bitStr;
	}
}
