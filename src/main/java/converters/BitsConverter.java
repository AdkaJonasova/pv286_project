package converters;

import exceptions.ConverterException;
import options.BitsOption;

import java.util.List;

import static options.BitsOption.LEFT;
import static options.BitsOption.SHORT;

public class BitsConverter extends Converter<BitsOption> {
	@Override
	public String convertTo(String bitStr, List<BitsOption> options) throws ConverterException {
		if(!validateInput(bitStr, "^[0-1 ]+$")){
			throw new ConverterException(String.format("Invalid input format: %s", bitStr));
		}

		BitsOption padSide = options == null || options.isEmpty() || options.get(0) == null ? LEFT : options.get(options.size() -1 );

		if (padSide.equals(SHORT)){
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
	public String convertFrom(String input, List<BitsOption> options) throws ConverterException {
		if(!validateInput(input, "^[0-1 ]+$")){
			throw new ConverterException(String.format("Invalid input format: %s", input));
		}

		BitsOption padSide = options == null || options.isEmpty() || options.get(0) == null ? LEFT : options.get(options.size() -1 );

		input = input.replace(" ", "");
		return addMissingZerosToBitString(input, padSide);
	}
}
