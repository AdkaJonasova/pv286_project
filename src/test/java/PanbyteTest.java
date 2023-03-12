import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PanbyteTest {

	Converter converter = new Converter();

	@Test
	void testBytesToBytes(){
		String bits = converter.bytesToBits("test");
		String actualResult = converter.bitsToBytes(bits);
		String expectedResult = "test";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToBytes(){
		String bits = converter.hexToBits("74657374");
		String actualResult = converter.bitsToBytes(bits);
		String expectedResult = "test";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testBytesToHex() {
		String bits = converter.bytesToBits("test");
		String actualResult = converter.bitsToHex(bits);
		String expectedResult = "74657374";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexWithSpacesToBytes(){
		String bits = converter.hexToBits("74 65 73 74");
		String actualResult = converter.bitsToBytes(bits);
		String expectedResult = "test";

		assertEquals(expectedResult, actualResult);
	}


	@Test
	void testIntToHex() {
		String bits = converter.intToBits("1234567890", true);
		String actualResult = converter.bitsToHex(bits);
		String expectedResult = "499602d2";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testIntWithBigEndianToHex() {
		String bits = converter.intToBits("1234567890", true);
		String actualResult = converter.bitsToHex(bits);
		String expectedResult = "499602d2";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testIntWithLittleEndianToHex() {
		String bits = converter.intToBits("1234567890", false);
		String actualResult = converter.bitsToHex(bits);
		String expectedResult = "d2029649";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToInt() {
		String bits = converter.hexToBits("499602d2");
		String actualResult = converter.bitsToInt(bits, true);
		String expectedResult = "1234567890";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToIntToBigEndian() {
		String bits = converter.hexToBits("499602d2");
		String actualResult = converter.bitsToInt(bits, true);
		String expectedResult = "1234567890";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testHexToIntToLittleEndian() {
		String bits = converter.hexToBits("d2029649");
		String actualResult = converter.bitsToInt(bits, false);
		String expectedResult = "1234567890";

		assertEquals(expectedResult, actualResult);
	}

}