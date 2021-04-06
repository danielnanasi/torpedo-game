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
	
	// Nem tudunk lerakni haj�t a p�lya sz�l�re
	@Test
	public void putShipOnEdge() {
		Assert.assertFalse(m.putShip(ship4));
	}
	
	// Nem �tk�zhetnek haj�k
	@Test
	public void collideShip() {
		m.putShip(ship1);
		Assert.assertFalse(m.putShip(ship3));
	}
	
	// Nem lehetnek 1-es k�rzetben egym�shoz
	@Test
	public void closeShip() {
		m.putShip(ship1);
		Assert.assertFalse(m.putShip(ship2));
	}
	
	// Egy testb�l kell �llnia a haj�nak
	@Test
	public void moreNodeShip() {
		Assert.assertFalse(m.putShip(ship5));
	}
	
	// Hib�s haj�m�ret
	@Test
	public void wrongSize() {
		Assert.assertFalse(m.putShip(ship1));
	}
	
	// 4 haj�t lerakunk szab�lyosan, megn�zz�k, hogy siker�l-e
	@Test
	public void goodCenario() {
		m.putShip(ship2);
		m.putShip(ship3);
		m.putShip(ship6);
		m.putShip(ship7);
		
		Assert.assertTrue(m.allShipsPlaced());
	}
	
	// l�v�nk mindenhova, megn�zz�k, hogy nyer�nk-e
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
	
	// Robot lerakja-e az �sszes haj�t j�l
	@Test
	public void robotPutShips() {
		m.robotPutAllShips();
		Assert.assertTrue(m.allShipsPlaced());
	}

}
