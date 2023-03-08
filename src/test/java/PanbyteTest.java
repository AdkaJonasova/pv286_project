import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PanbyteTest {

	Converter converter = new Converter();

	@Test
	void hexToBytes(){
		String bits = converter.hexToBits("74657374");
		String actualResult = converter.bitsToByte(bits);
		String expectedResult = "test";

		assertEquals(expectedResult, actualResult);
	}


	@Test
	void intToHex() {
		String bits = converter.intToBits("1234567890");
		String actualResult = converter.bitsToHex(bits);
		String expectedResult = "499602d2";

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void bytesToHex() {

	}
}