package converters;

import options.BitsOption;

import java.util.List;

import static options.BitsOption.LEFT;

public class BitsConverter extends Converter<BitsOption> {
	@Override
	public String convertTo(String bitStr, List<BitsOption> options) {
		return addMissingZerosToBitString(bitStr, LEFT);
	}

	@Override
	public String convertFrom(String input, List<BitsOption> options) {
		BitsOption padSide = options == null || options.isEmpty() || options.get(0) == null ? LEFT : options.get(options.size() -1 );

		input = input.replace(" ", "");
		return addMissingZerosToBitString(input, padSide);
	}
}
