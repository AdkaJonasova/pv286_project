import static org.junit.jupiter.api.Assertions.*;

import converters.*;
import org.junit.jupiter.api.Test;

class ConverterTest {

	BytesConverter byteConverter = new BytesConverter();
	HexConverter hexConverter = new HexConverter();
	IntConverter intConverter = new IntConverter();
	BitsConverter bitsConverter = new BitsConverter();
	ArrayConverter arrayConverter = new ArrayConverter();

	@Test
	void testBytesToBytes(){
		String bits = byteConverter.convertFrom("test");
		String actualResult = byteConverter.convertTo(bits);
		String expectedResult = "test";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToBytes(){
		String bits = hexConverter.convertFrom("74657374");
		String actualResult = byteConverter.convertTo(bits);
		String expectedResult = "test";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testBytesToHex() {
		String bits = byteConverter.convertFrom("test");
		String actualResult = hexConverter.convertTo(bits);
		String expectedResult = "74657374";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexWithSpacesToBytes(){
		String bits = hexConverter.convertFrom("74 65 73 74");
		String actualResult = byteConverter.convertTo(bits);
		String expectedResult = "test";

		assertEquals(expectedResult, actualResult);
	}


	@Test
	void testIntToHex() {
		String bits = intConverter.convertFrom("1234567890", null);
		String actualResult = hexConverter.convertTo(bits);
		String expectedResult = "499602d2";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testIntWithBigEndianToHex() {
		String bits = intConverter.convertFrom("1234567890", "big");
		String actualResult = hexConverter.convertTo(bits);
		String expectedResult = "499602d2";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testIntWithLittleEndianToHex() {
		String bits = intConverter.convertFrom("1234567890", "little");
		String actualResult = hexConverter.convertTo(bits);
		String expectedResult = "d2029649";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToInt() {
		String bits = hexConverter.convertFrom("499602d2");
		String actualResult = intConverter.convertTo(bits, null);
		String expectedResult = "1234567890";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToIntWithBigEndian() {
		String bits = hexConverter.convertFrom("499602d2");
		String actualResult = intConverter.convertTo(bits, "big");
		String expectedResult = "1234567890";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToIntWithLittleEndian() {
		String bits = hexConverter.convertFrom("d2029649");
		String actualResult = intConverter.convertTo(bits, "little");
		String expectedResult = "1234567890";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testBitsWithSpacesToBytes() {
		String bits = bitsConverter.convertFrom("100 1111 0100 1011", null);
		String actualResult = byteConverter.convertTo(bits);
		String expectedResult = "OK";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testBitsWithLeftPadToBytes() {
		String bits = bitsConverter.convertFrom("100111101001011", "left");
		String actualResult = byteConverter.convertTo(bits);
		String expectedResult = "OK";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testBitsWithRightPadToHex() {
		String bits = bitsConverter.convertFrom("100111101001011", "right");
		String actualResult = hexConverter.convertTo(bits);
		String expectedResult = "9e96";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testBytesToBits() {
		String bits = byteConverter.convertFrom("OK", null);
		String actualResult = bitsConverter.convertTo(bits, null);
		String expectedResult = "0100111101001011";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToArray() {
		String bits = hexConverter.convertFrom("01020304", null);
		String actualResult = arrayConverter.convertTo(bits, null);
		String expectedResult = "{0x1, 0x2, 0x3, 0x4}";

		assertEquals(expectedResult, actualResult);
	}
}
