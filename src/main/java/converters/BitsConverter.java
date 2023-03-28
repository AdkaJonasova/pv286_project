package converters;

import exceptions.ConverterException;
import options.BitsOption;
import options.IOption;

import java.util.List;

import static options.BitsOption.LEFT;
import static options.BitsOption.SHORT;

public class BitsConverter extends Converter {
	@Override
	public String convertTo(String bitStr, List<IOption> options) throws ConverterException {
		validateInput(bitStr, "^[01 ]+$");

		BitsOption padSide = options == null || options.isEmpty() || options.get(0) == null ? LEFT : (BitsOption) options.get(options.size() - 1);

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
	public String convertFrom(String input, List<IOption> options) throws ConverterException {
		validateInput(input, "^[01 ]+$");

		BitsOption padSide = options == null || options.isEmpty() || options.get(0) == null ? LEFT : (BitsOption) options.get(options.size() - 1);

		input = input.replace(" ", "");
		return addMissingZerosToBitString(input, padSide);
	}
}
