package converters;

import utils.BitsOption;
import utils.Separator;

import static utils.Separator.EMPTY;
import static utils.Separator.SPACE;

public class BitsConverter extends Converter {
	@Override
	public String convertTo(String bitStr, String option) {
		return bitStr;
	}

	@Override
	public String convertFrom(String str, String option) {
		option = Separator.isEmpty(option) ? "left" : option;
		BitsOption padSide = BitsOption.fromString(option);

		str = str.replace(SPACE.getText(), EMPTY.getText());
		str = this.addMissingZerosToBitString(str, padSide);
		return str;
	}
}
