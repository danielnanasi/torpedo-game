import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class GameTest {

	Game g = new Game();
	
	// Játék mentése és betöltése
	@Test
	void saveAndLoadTest() {
		
		// random lerakatjuk a hajókat a 2 pályára
		g.robotBoard.robotPutAllShips();
		g.playerBoard.robotPutAllShips();
		
		// lövünk 10-10 random helyre a pályán
		for(int i = 0; i < 10; i++) {
			g.robotBoard.robotShot();
			g.playerBoard.robotShot();
		}
		
		// játékmentés készítése
		boolean[][] savedObject1 = g.compressSaveObject();
		
		// betöltés
		g.loadGame(savedObject1);
		
		// új mentés készítése
		boolean[][] savedObject2 = g.compressSaveObject();
		
		Assert.assertArrayEquals(savedObject1, savedObject2);
	}

}
