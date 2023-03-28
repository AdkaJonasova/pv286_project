package converters;

import exceptions.ConverterException;
import options.BitsOption;
import options.IOption;

import static options.BitsOption.LEFT;
import static options.BitsOption.SHORT;

public class BitsConverter extends Converter {
	@Override
	public String convertTo(String bitStr, IOption[] options) throws ConverterException {
		validateInput(bitStr, "^[01 ]+$");

		BitsOption padSide = options == null || options.length == 0 || options[0] == null ? LEFT : (BitsOption) options[0];

		if (padSide.equals(SHORT)) {
			int index = bitStr.indexOf("1");
			if (index == -1) {
				return bitStr;
			} else {
				return bitStr.substring(index);
			}
		}

		return addMissingZerosToBitString(bitStr, padSide);
	}

	@Override
	public String convertFrom(String input, IOption[] options) throws ConverterException {
		validateInput(input, "^[01 ]+$");

		BitsOption padSide = options == null || options.length == 0 || options[0] == null ? LEFT : (BitsOption) options[0];

		input = input.replace(" ", "");
		return addMissingZerosToBitString(input, padSide);
	}
}
