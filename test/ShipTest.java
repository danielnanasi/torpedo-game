import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class ShipTest {

	@Test
	public void testLength() {
		 Ship s = new Ship(3);
		 int result = s.getLenght();
		 Assert.assertEquals(s, result);
		 }
}
