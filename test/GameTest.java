import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class GameTest {

	Game g = new Game();
	
	// J�t�k ment�se �s bet�lt�se
	@Test
	void saveAndLoadTest() {
		
		// random lerakatjuk a haj�kat a 2 p�ly�ra
		g.robotBoard.robotPutAllShips();
		g.playerBoard.robotPutAllShips();
		
		// l�v�nk 10-10 random helyre a p�ly�n
		for(int i = 0; i < 10; i++) {
			g.robotBoard.robotShot();
			g.playerBoard.robotShot();
		}
		
		// j�t�kment�s k�sz�t�se
		boolean[][] savedObject1 = g.compressSaveObject();
		
		// bet�lt�s
		g.loadGame(savedObject1);
		
		// �j ment�s k�sz�t�se
		boolean[][] savedObject2 = g.compressSaveObject();
		
		Assert.assertArrayEquals(savedObject1, savedObject2);
	}

}
