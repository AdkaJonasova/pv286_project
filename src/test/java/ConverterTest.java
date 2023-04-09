import converters.*;
import exceptions.ConverterException;
import options.IOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static options.ArrayOption.*;
import static options.BitsOption.LEFT;
import static options.BitsOption.RIGHT;
import static options.IntOption.BIG;
import static options.IntOption.LITTLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class ConverterTest {
	BytesConverter byteConverter = new BytesConverter();
	HexConverter hexConverter = new HexConverter();
	IntConverter intConverter = new IntConverter();
	BitsConverter bitsConverter = new BitsConverter();
	ArrayConverter arrayConverter = new ArrayConverter();

	@ParameterizedTest
	@ValueSource(strings = {"test", "12t", "-12"})
	void testFromIntInvalid(String input) {
		assertThrows(ConverterException.class, () -> intConverter.convertFrom(input, null));
	}

	@ParameterizedTest
	@ValueSource(strings = {"12G", "test", "35FH"})
	void testFromHexInvalid(String input) {
		assertThrows(ConverterException.class, () -> hexConverter.convertFrom(input, null));
	}

	@ParameterizedTest
	@ValueSource(strings = {"012", "test"})
	void testFromBitsInvalid(String input) {
		assertThrows(ConverterException.class, () -> bitsConverter.convertFrom(input, null));
	}

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
			String bits = intConverter.convertFrom("1234567890", new IOption[]{BIG});
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
			String bits = intConverter.convertFrom("1234567890", new IOption[]{LITTLE});
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
			String actualResult = intConverter.convertTo(bits, new IOption[]{BIG});
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
			String actualResult = intConverter.convertTo(bits, new IOption[]{LITTLE});
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
			String bits = bitsConverter.convertFrom("100111101001011", new IOption[]{LEFT});
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
			String bits = bitsConverter.convertFrom("100111101001011", new IOption[]{RIGHT});
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
			String actualResult = arrayConverter.convertTo(bits, new IOption[]{DECIMAL_NUMBER});
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
			String actualResult = arrayConverter.convertTo(bits, new IOption[]{CHARACTERS});
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
			String actualResult = arrayConverter.convertTo(bits, new IOption[]{ZEROB_PREFIXED_BINARY_NUMBER});
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
	void testArrayToInt() {
		try {
			String bits = arrayConverter.convertFrom("(85, 75, 45 ,55)", null);
			String actualResult = intConverter.convertTo(bits, null);
			String expectedResult = "1430990135";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testIntToArray() {
		try {
			String bits = intConverter.convertFrom("85754555", null);
			String actualResult = arrayConverter.convertTo(bits, null);
			String expectedResult = "{0x5, 0x1c, 0x82, 0xbb}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToBytes() {
		try {
			String bits = arrayConverter.convertFrom("{85, 75, 95}", null);
			String actualResult = byteConverter.convertTo(bits, null);
			String expectedResult = "UK_";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testBytesToArray() {
		try {
			String bits = byteConverter.convertFrom("UK_", null);
			String actualResult = arrayConverter.convertTo(bits, null);
			String expectedResult = "{0x55, 0x4b, 0x5f}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArray() {
		try {
			String actualResult = arrayConverter.convertFromArrayToArray("{0x01, 2, 0b11, '\\x04'}", null);
			String expectedResult = "{0x1, 0x2, 0x3, 0x4}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayWithHex() {
		try {
			String actualResult = arrayConverter.convertFromArrayToArray("[0x01, 2, 0b11, '\\x04']", new IOption[]{ZEROX_PREFIXED_HEX_NUMBER});
			String expectedResult = "{0x1, 0x2, 0x3, 0x4}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayWithDecimal() {
		try {
			String actualResult = arrayConverter.convertFromArrayToArray("(0x01, 2, 0b11, '\\x04')", new IOption[]{DECIMAL_NUMBER});
			String expectedResult = "{1, 2, 3, 4}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayWithChars() {
		try {
			String actualResult = arrayConverter.convertFromArrayToArray("{0x01, 2, 0b11, '\\x04'}", new IOption[]{CHARACTERS});
			String expectedResult = "{'\\x01', '\\x02', '\\x03', '\\x04'}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayWithBinary() {
		try {
			String actualResult = arrayConverter.convertFromArrayToArray("[0x01, 2, 0b11, '\\x04']", new IOption[]{ZEROB_PREFIXED_BINARY_NUMBER});
			String expectedResult = "{0b1, 0b10, 0b11, 0b100}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayWithRegularBracket() {
		try {
			String actualResult = arrayConverter.convertFromArrayToArray("(0x01, 2, 0b11, '\\x04')", new IOption[]{REGULAR_BRACKETS});
			String expectedResult = "(0x1, 0x2, 0x3, 0x4)";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayWithDecimalAndSquareBracket() {
		try {
			String actualResult = arrayConverter.convertFromArrayToArray("[0x01, 2, 0b11, '\\x04']", new IOption[]{DECIMAL_NUMBER, SQUARE_BRACKETS});
			String expectedResult = "[1, 2, 3, 4]";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayEmpty() {
		try {
			String actualResult = arrayConverter.convertFromArrayToArray("()", null);
			String expectedResult = "{}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayNestedEmptyWithSquareBracket() {
		try {
			String actualResult = arrayConverter.convertFromArrayToArray("([],{})", new IOption[] {SQUARE_BRACKETS});
			String expectedResult = "[[], []]";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayNestedWithInts() {
		try {
			String actualResult = arrayConverter.convertFromArrayToArray("[[1, 2], [3, 4], [5, 6]]", null);
			String expectedResult = "{{0x1, 0x2}, {0x3, 0x4}, {0x5, 0x6}}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayNestedWithIntsWithDecimal() {
		try {
			String actualResult = arrayConverter.convertFromArrayToArray("[[1, 2], [3, 4], [5, 6]]", new IOption[] {DECIMAL_NUMBER});
			String expectedResult = "{{1, 2}, {3, 4}, {5, 6}}";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testArrayToArrayNestedWithHexAndIntsWithDecimalAndSquareBrackets() {
		try {
			String actualResult = arrayConverter.convertFromArrayToArray("{{0x01, (2), [3, 0b100, 0x05], '\\x06'}}", new IOption[] {DECIMAL_NUMBER, SQUARE_BRACKETS});
			String expectedResult = "[[1, [2], [3, 4, 5], 6]]";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testByteToVeryLongInt(){
		try {
			String bits = byteConverter.convertFrom("hahahahahahahahahhahahahahahahahahhahahahahahahahahhahaha" +
					"hahahahahahhahahahahahahahahhahahahahahahahahhahahahahahahahahhahahahahahahahahhahaha", null);
			String actualResult = intConverter.convertTo(bits, null);
			String expectedResult = "38058746259573735706638784505536541680220395367276312960468310427870180575747260" +
					"978749031380703736745090995454611661395016199587892556002253176894044034955562780052515218582446" +
					"742442895272795493953889919883268519247689344580540826993570923722824991222759198641564507752729" +
					"5534398547357009919981075021542512677674519077324171214437549040822369";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testVeryLongIntToByte(){
		try {
			String bits = intConverter.convertFrom("3805874625957373570663878450553654168022039536727631296046" +
					"83104278701805757472609787490313807037367450909954546116613950161995878925560022531768940440349" +
					"55562780052515218582446742442895272795493953889919883268519247689344580540826993570923722824991" +
					"2227591986415645077527295534398547357009919981075021542512677674519077324171214437549040822369",
					null);
			String actualResult = byteConverter.convertTo(bits, null);
			String expectedResult = "hahahahahahahahahhahahahahahahahahhahahahahahahahahhahahahahahahahahhahahahahah" +
					"ahahahhahahahahahahahahhahahahahahahahahhahahahahahahahahhahaha";

			assertEquals(expectedResult, actualResult);
		} catch (ConverterException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	//region Invalid arrays tests
	@ParameterizedTest
	@ValueSource(strings = {"[{1, 2}, {", "]{1, 2}]", "[{1, 2}, {3, 4}}", "[{1, 2}, {3, 4}]("})
	void testInvalidArrayIncorrectBrackets(String input) {
		assertThrows(ConverterException.class, () -> arrayConverter.convertFromArrayToArray(input, null));
		assertThrows(ConverterException.class, () -> arrayConverter.convertFrom(input, null));
	}

	@Test
	void testInvalidArrayWithoutBrackets() {
		String invalidArray = "1, 2";
		assertThrows(ConverterException.class, () -> arrayConverter.convertFromArrayToArray(invalidArray, null));
		assertThrows(ConverterException.class, () -> arrayConverter.convertFrom(invalidArray, null));
	}
	//endregion
}
