import converters.*;
import exceptions.ConverterException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static options.ArrayOption.*;
import static options.BitsOption.LEFT;
import static options.BitsOption.RIGHT;
import static options.IntOption.BIG;
import static options.IntOption.LITTLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ConverterTest {
	BytesConverter byteConverter = new BytesConverter();
	HexConverter hexConverter = new HexConverter();
	IntConverter intConverter = new IntConverter();
	BitsConverter bitsConverter = new BitsConverter();
	ArrayConverter arrayConverter = new ArrayConverter();

	@Test
	void testBytesToBytes() {
		try {

			String bits = byteConverter.convertFrom("test");
			String actualResult = byteConverter.convertTo(bits);
			String expectedResult = "test";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testHexToBytes() {
		try {
			String bits = hexConverter.convertFrom("74657374");
			String actualResult = byteConverter.convertTo(bits);
			String expectedResult = "test";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testBytesToHex() {
		try {
			String bits = byteConverter.convertFrom("test");
			String actualResult = hexConverter.convertTo(bits, null);
			String expectedResult = "74657374";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testHexWithSpacesToBytes() {
		try {
			String bits = hexConverter.convertFrom("74 65 73 74");
			String actualResult = byteConverter.convertTo(bits);
			String expectedResult = "test";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}


	@Test
	void testIntToHex() {
		try {
			String bits = intConverter.convertFrom("1234567890", null);
			String actualResult = hexConverter.convertTo(bits, null);
			String expectedResult = "499602d2";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testIntWithBigEndianToHex() {
		try {
			String bits = intConverter.convertFrom("1234567890", List.of(BIG));
			String actualResult = hexConverter.convertTo(bits, null);
			String expectedResult = "499602d2";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testIntWithLittleEndianToHex() {
		try {
			String bits = intConverter.convertFrom("1234567890", List.of(LITTLE));
			String actualResult = hexConverter.convertTo(bits, null);
			String expectedResult = "d2029649";

			assertEquals(expectedResult, actualResult);
		} catch (
				ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testHexToInt() {
		try {
			String bits = hexConverter.convertFrom("499602d2");
			String actualResult = intConverter.convertTo(bits, null);
			String expectedResult = "1234567890";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testHexToIntWithBigEndian() {
		try {
			String bits = hexConverter.convertFrom("499602d2");
			String actualResult = intConverter.convertTo(bits, List.of(BIG));
			String expectedResult = "1234567890";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testHexToIntWithLittleEndian() {
		try {
			String bits = hexConverter.convertFrom("d2029649");
			String actualResult = intConverter.convertTo(bits, List.of(LITTLE));
			String expectedResult = "1234567890";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testBitsWithSpacesToBytes() {
		try {
			String bits = bitsConverter.convertFrom("100 1111 0100 1011", null);
			String actualResult = byteConverter.convertTo(bits);
			String expectedResult = "OK";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testBitsWithLeftPadToBytes() {
		try {
			String bits = bitsConverter.convertFrom("100111101001011", List.of(LEFT));
			String actualResult = byteConverter.convertTo(bits);
			String expectedResult = "OK";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testBitsWithRightPadToHex() {
		try {
			String bits = bitsConverter.convertFrom("100111101001011", List.of(RIGHT));
			String actualResult = hexConverter.convertTo(bits, null);
			String expectedResult = "9e96";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testBytesToBits() {
		try {
			String bits = byteConverter.convertFrom("OK", null);
			String actualResult = bitsConverter.convertTo(bits, null);
			String expectedResult = "0100111101001011";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testHexToArray() {
		try {
			String bits = hexConverter.convertFrom("01020304", null);
			String actualResult = arrayConverter.convertTo(bits, null);
			String expectedResult = "{0x1, 0x2, 0x3, 0x4}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testHexToArrayWithDecimal() {
		try {
			String bits = hexConverter.convertFrom("01020304", null);
			String actualResult = arrayConverter.convertTo(bits, List.of(DECIMAL_NUMBER));
			String expectedResult = "{1, 2, 3, 4}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testHexToArrayWithChars() {
		try {
			String bits = hexConverter.convertFrom("01020304", null);
			String actualResult = arrayConverter.convertTo(bits, List.of(CHARACTERS));
			String expectedResult = "{'\\x01', '\\x02', '\\x03', '\\x04'}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testHexToArrayWithBinary() {
		try {
			String bits = hexConverter.convertFrom("01020304", null);
			String actualResult = arrayConverter.convertTo(bits, List.of(ZEROB_PREFIXED_BINARY_NUMBER));
			String expectedResult = "{0b1, 0b10, 0b11, 0b100}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToHex() {
		try {
			String bits = arrayConverter.convertFrom("{0x01, 2, 0b11, '\\x04'}", null);
			String actualResult = hexConverter.convertTo(bits, null);
			String expectedResult = "01020304";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArray() {
		try {
			String bits = arrayConverter.convertFrom("{0x01, 2, 0b11, '\\x04'}", null);
			String actualResult = arrayConverter.convertTo(bits, null);
			String expectedResult = "{0x1, 0x2, 0x3, 0x4}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayWithHex() {
		try {
			String bits = arrayConverter.convertFrom("[0x01, 2, 0b11, '\\x04']", null);
			String actualResult = arrayConverter.convertTo(bits, List.of(ZEROX_PREFIXED_HEX_NUMBER));
			String expectedResult = "{0x1, 0x2, 0x3, 0x4}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayWithDecimal() {
		try {
			String bits = arrayConverter.convertFrom("(0x01, 2, 0b11, '\\x04')", null);
			String actualResult = arrayConverter.convertTo(bits, List.of(DECIMAL_NUMBER));
			String expectedResult = "{1, 2, 3, 4}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayWithChars() {
		try {
			String bits = arrayConverter.convertFrom("{0x01, 2, 0b11, '\\x04'}", null);
			String actualResult = arrayConverter.convertTo(bits, List.of(CHARACTERS));
			String expectedResult = "{'\\x01', '\\x02', '\\x03', '\\x04'}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayWithBinary() {
		try {
			String bits = arrayConverter.convertFrom("[0x01, 2, 0b11, '\\x04']", null);
			String actualResult = arrayConverter.convertTo(bits, List.of(ZEROB_PREFIXED_BINARY_NUMBER));
			String expectedResult = "{0b1, 0b10, 0b11, 0b100}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}
}
