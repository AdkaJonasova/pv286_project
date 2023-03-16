package converters;

import options.BitsOption;

import static options.BitsOption.LEFT;
import static utils.Separator.EMPTY;
import static utils.Separator.SPACE;

public class BitsConverter extends Converter<BitsOption> {
	@Override
	public String convertTo(String bitStr, BitsOption option) {
		return addMissingZerosToBitString(bitStr, LEFT);
	}

	@Override
	public String convertFrom(String input, BitsOption option) {
		BitsOption padSide = option == null ? LEFT : option;

		input = input.replace(SPACE.getText(), EMPTY.getText());
		return addMissingZerosToBitString(input, padSide);
	}
}
