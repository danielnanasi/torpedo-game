import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapTest {
	
	private boolean[][] ship1, ship2, ship3, ship4, ship5, ship6, ship7;
	Map m;
	
	@Before
	public void defineShips() {
		
		boolean o = false;
		boolean x = true;
		
		m = new Map(10, 10);
		ship1 = new boolean[][] {{o,o,o,o,o,o,o,o,o,o},
								 {o,x,o,o,o,o,o,o,o,o},
								 {o,x,o,o,o,o,o,o,o,o},
								 {o,x,o,o,o,o,o,o,o,o},
								 {o,x,o,o,o,o,o,o,o,o},
								 {o,x,o,o,o,o,o,o,o,o},
								 {o,x,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o}};
	
		ship2 = new boolean[][] {{o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,x,x,x,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o}};
	
		ship3 = new boolean[][] {{o,o,o,o,o,o,o,o,o,o},
								 {o,x,x,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o}};
	
		ship4 = new boolean[][] {{o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,x,o,o,o,o,o,o,o,o},
								 {o,x,o,o,o,o,o,o,o,o}};

		ship5 = new boolean[][] {{o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,x,x,x,o,x,x,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o}};
	
		ship6 = new boolean[][] {{o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,x,x,x,x,x,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o}};
	
		ship7 = new boolean[][] {{o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,x,x,x,x,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o},
								 {o,o,o,o,o,o,o,o,o,o}};
	}
	
	// Nem tudunk lerakni hajót a pálya szélére
	@Test
	public void putShipOnEdge() {
		Assert.assertFalse(m.putShip(ship4));
	}
	
	// Nem ütközhetnek hajók
	@Test
	public void collideShip() {
		m.putShip(ship1);
		Assert.assertFalse(m.putShip(ship3));
	}
	
	// Nem lehetnek 1-es körzetben egymáshoz
	@Test
	public void closeShip() {
		m.putShip(ship1);
		Assert.assertFalse(m.putShip(ship2));
	}
	
	// Egy testbõl kell állnia a hajónak
	@Test
	public void moreNodeShip() {
		Assert.assertFalse(m.putShip(ship5));
	}
	
	// Hibás hajóméret
	@Test
	public void wrongSize() {
		Assert.assertFalse(m.putShip(ship1));
	}
	
	// 4 hajót lerakunk szabályosan, megnézzük, hogy sikerül-e
	@Test
	public void goodCenario() {
		m.putShip(ship2);
		m.putShip(ship3);
		m.putShip(ship6);
		m.putShip(ship7);
		
		Assert.assertTrue(m.allShipsPlaced());
	}
	
	// lövünk mindenhova, megnézzük, hogy nyerünk-e
	@Test
	public void winCenario () {
		m.putShip(ship2);
		m.putShip(ship3);
		m.putShip(ship6);
		m.putShip(ship7);
		
		for(int x = 0; x < 10; x++)
			for(int y = 0; y < 10; y++)
				m.shot(x,y);
		
		Assert.assertTrue(m.isEnded());
	}
	
	// Robot lerakja-e az összes hajót jól
	@Test
	public void robotPutShips() {
		m.robotPutAllShips();
		Assert.assertTrue(m.allShipsPlaced());
	}

}
