import static org.junit.jupiter.api.Assertions.*;

import converters.BytesConverter;
import converters.HexConverter;
import converters.IConverter;
import converters.IntConverter;
import org.junit.jupiter.api.Test;

class PanbyteTest {

	IConverter byteConverter = new BytesConverter();
	IConverter hexConverter = new HexConverter();
	IConverter intConverter = new IntConverter();

	@Test
	void testBytesToBytes(){
		String bits = byteConverter.convertFrom("test", false);
		String actualResult = byteConverter.convertTo(bits, false);
		String expectedResult = "test";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToBytes(){
		String bits = hexConverter.convertFrom("74657374", false);
		String actualResult = byteConverter.convertTo(bits, false);
		String expectedResult = "test";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testBytesToHex() {
		String bits = byteConverter.convertFrom("test", false);
		String actualResult = hexConverter.convertTo(bits, false);
		String expectedResult = "74657374";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexWithSpacesToBytes(){
		String bits = hexConverter.convertFrom("74 65 73 74", false);
		String actualResult = byteConverter.convertTo(bits, false);
		String expectedResult = "test";

		assertEquals(expectedResult, actualResult);
	}


	@Test
	void testIntToHex() {
		String bits = intConverter.convertFrom("1234567890", true);
		String actualResult = hexConverter.convertTo(bits, false);
		String expectedResult = "499602d2";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testIntWithBigEndianToHex() {
		String bits = intConverter.convertFrom("1234567890", true);
		String actualResult = hexConverter.convertTo(bits, false);
		String expectedResult = "499602d2";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testIntWithLittleEndianToHex() {
		String bits = intConverter.convertFrom("1234567890", false);
		String actualResult = hexConverter.convertTo(bits, false);
		String expectedResult = "d2029649";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToInt() {
		String bits = hexConverter.convertFrom("499602d2", false);
		String actualResult = intConverter.convertTo(bits, true);
		String expectedResult = "1234567890";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToIntToBigEndian() {
		String bits = hexConverter.convertFrom("499602d2", false);
		String actualResult = intConverter.convertTo(bits, true);
		String expectedResult = "1234567890";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToIntToLittleEndian() {
		String bits = hexConverter.convertFrom("d2029649", false);
		String actualResult = intConverter.convertTo(bits, false);
		String expectedResult = "1234567890";

		assertEquals(expectedResult, actualResult);
	}

}