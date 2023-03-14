import static org.junit.jupiter.api.Assertions.*;

import converters.BytesConverter;
import converters.HexConverter;
import converters.IntConverter;
import org.junit.jupiter.api.Test;

class ConverterTest {

	BytesConverter byteConverter = new BytesConverter();
	HexConverter hexConverter = new HexConverter();
	IntConverter intConverter = new IntConverter();

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
	void testHexToIntToBigEndian() {
		String bits = hexConverter.convertFrom("499602d2");
		String actualResult = intConverter.convertTo(bits, "big");
		String expectedResult = "1234567890";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToIntToLittleEndian() {
		String bits = hexConverter.convertFrom("d2029649");
		String actualResult = intConverter.convertTo(bits, "little");
		String expectedResult = "1234567890";

		assertEquals(expectedResult, actualResult);
	}
}
