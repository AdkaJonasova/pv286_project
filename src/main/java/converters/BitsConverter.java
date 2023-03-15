package converters;

import options.BitsOption;
import options.IOption;
import options.IntOption;
import utils.Separator;

import static options.BitsOption.LEFT;
import static options.IntOption.BIG;
import static utils.Separator.EMPTY;
import static utils.Separator.SPACE;

public class BitsConverter extends Converter {
	@Override
	public String convertTo(String bitStr, IOption option) {
		return addMissingZerosToBitString(bitStr, LEFT);
	}

	@Override
	public String convertFrom(String str, IOption option) {
		BitsOption padSide = option == null ? LEFT : (BitsOption) option;

		str = str.replace(SPACE.getText(), EMPTY.getText());
		return addMissingZerosToBitString(str, padSide);
	}
}
