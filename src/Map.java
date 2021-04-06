import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Map extends JPanel {
	static int xSize;
	static int ySize;
	protected boolean[][] ships;
	protected boolean[][] shots;
	
	ArrayList<Ship> availableShips;
	
	int robotMemoryX, robotMemoryY, robotMemoryDelta;
	boolean robotMemoryHorisontal;
	enum robotMemoryPhase{
		SEARCHING,
		FINDING_DIRECTION,
		FIRE_NEGATIVE_DIRECTION,
		FIRE_POSITIVE_DIRECTION
	}
	robotMemoryPhase robotMemoryPhase;
	
	public Map(int xSize, int ySize)  {
		Map.xSize = xSize;
		Map.ySize = ySize;
		
		robotMemoryPhase = robotMemoryPhase.SEARCHING;
		
		// Haj� �s l�v�s t�rk�p inicializ�l�sa
		ships = new boolean[xSize][ySize];
		shots = new boolean[xSize][ySize];
		for(boolean[] array : ships) Arrays.fill(array, false);
		for(boolean[] array : shots) Arrays.fill(array, false);
		
		availableShips = new ArrayList<Ship>();
		availableShips.add(new Ship(2));
		availableShips.add(new Ship(3));
		availableShips.add(new Ship(4));
		availableShips.add(new Ship(5));
	}
	
	public boolean allShipsPlaced() {
		return availableShips.isEmpty();
	}
	
	public int getFirstShipLength() {
		return availableShips.isEmpty() ? 0 : availableShips.get(0).getLenght();
	}
	
	public void setShots(boolean[][] newShots) {
		shots = newShots;
	}
	
	public void setShips(boolean[][] newShips) {
		ships = newShips;
	}
	
	public boolean[][] getShots(){
		return shots;
	}
	
	public boolean getShot(int x, int y){
		return shots[x][y];
	}
	public boolean getShip(int x, int y){
		return ships[x][y];
	}
	public void setShip(int x, int y, boolean b){
		ships[x][y] =b;
	}
	public void setShot(int x, int y, boolean b){
		ships[x][y] =b;
	}
	
	public boolean[][] getShips(){
		return ships;
	}
	
	public ArrayList<Ship> getAvailableShips(){
		return availableShips;
	}
	
	public boolean isEnded() {
		for(int y = 0; y < ySize; ++y)
			for(int x = 0; x < xSize; ++x)
				if(shots[x][y] ^ ships[x][y])
					return false;
		return true;
	}
	
	// l� �s megmondja, hogy tal�lt-e
	public boolean shot(int x, int y){
		shots[x][y] = true;
		return ships[x][y];
	}
	
	// �sszesz�moljuk az ellenf�l pontj�t
	public int getPoints() {
		int points = 0;
		for(int y = 0; y < ySize; ++y)
			for(int x = 0; x < xSize; ++x)
				if(shots[x][y] && ships[x][y])
					points++;
		return points;
	}
	
	// �sszesz�moljuk az �sszes megszerezhet� pontot
	public int getMaxPoints() {
		int maxPoints = 0;
		for(int y = 0; y < ySize; ++y)
			for(int x = 0; x < xSize; ++x)
				if(ships[x][y])
					maxPoints++;
		return maxPoints;
	}
	
	// megmondja, hogy lerakhat�-e a haj� �s lerakja
	public boolean putShip(boolean[][] newShipMap) {
		
		boolean vertical=false;
		boolean horisontal=false;
		int newShipLength=0;
		boolean shipSizeAvalaible=false;
		int x=0,y=0;
		
		// megn�zz�k a 4 sz�l�n sz�l�n, ahova nem lehet rakni
		for(y = 0, x = 0; x < xSize; ++x) if(newShipMap[x][y]) return false;
		for(y = ySize-1, x = 0; x < xSize; ++x) if(newShipMap[x][y]) return false;
		for(y = 0, x = 0; y < ySize; ++y) if(newShipMap[x][y]) return false;
		for(y = 0, x = xSize-1; y < ySize; ++y) if(newShipMap[x][y]) return false;
		
		// megkeress�k a haj� egyik pontj�t
		int firstX=1, firstY=1;
		outerloop:
		for(y = 0; y < ySize; y++) {
			for(x = 0; x < xSize; x++) {
				if(newShipMap[x][y]) {
					firstX=x; firstY=y;
					break outerloop;
				}
			}
		}
		
		// vertik�lis haj�test felt�tel
		if(newShipMap[firstX-1][firstY] || newShipMap[firstX+1][firstY])
			vertical=true;
		
		// horizont�lis haj�test felt�tel
		if(newShipMap[firstX][firstY-1] || newShipMap[firstX][firstY+1])
			horisontal=true;
		
		
		// megn�zz�k mekkora az �j haj�
		for(y = 0; y < ySize; ++y)
			for(x = 0; x < xSize; ++x)
				if(newShipMap[x][y]) 
					newShipLength++;
		
		// megn�zz�k, hogy t�nyleg csak abban a sorban van haj�test �s az �sszef�gg�
		if(horisontal) {
			for(y = 0; y < ySize; ++y)
				for(x = 0; x < xSize; ++x)
					if(newShipMap[x][y] && x!=firstX)
						return false;
			for(int dy = firstY; dy < firstY+newShipLength; dy++)
				if(!newShipMap[firstX][dy]) return false; 
		}
		
		// megn�zz�k, hogy t�nyleg csak abban az oszlopban van haj�test �s az �sszef�gg�
		if(vertical) {
			for(y = 0; y < ySize; ++y)
				for(x = 0; x < xSize; ++x)
					if(newShipMap[x][y] && y!=firstY)
						return false;
			for(int dx = firstX; dx < firstX+newShipLength; dx++)
				if(!newShipMap[dx][firstY]) return false; 
		}
		
		// vagy veritk�lis vagy horizont�lisak lehetnek egym�shoz k�pest a haj�elemek
		if(!(vertical ^ horisontal))
			return false;		
		
		// megn�zz�k, hogy nincs-e az �j haj� k�r�l/hely�n r�gi haj�
		for(y = 0; y < ySize; ++y)
			for(x = 0; x < xSize; ++x)
				if(newShipMap[x][y])
					for(int dx = x-1; dx<=x+1; dx++)
						for(int dy = y-1; dy<=y+1; dy++)
							if(ships[dx][dy]) 
								return false;
		
		// megn�zz�k, hogy van ilyen haj�nk
		for (Ship ship : availableShips) {
			if (ship.getLenght() == newShipLength) {
				shipSizeAvalaible=true;
				availableShips.remove(ship);
				break;
			}			
		}
		
		if(!shipSizeAvalaible) return false;
		
		// Ha eljut id�ig a fgv, akkor teljes�lt minden felt�tel, lerakjuk a haj�t
		for(y = 0; y < ySize; y++)
			for(x = 0; x < xSize; x++)
				if(newShipMap[x][y]) 
					ships[x][y]=true;
		return true;
	}
	
	// debuggol�shoz
	public void printMap(boolean[][] map) {
		for(int y = 0; y < ySize; y++) {
			for(int x = 0; x < xSize; x++) {
				int b = map[x][y] ? 1 : 0;
				System.out.print((b)+" ");
			}
			System.out.println();			
		}
	}
	
	// robot lerakja az �sszes haj�j�t
	public void robotPutAllShips() {
		Random rand = new Random();
		while(!allShipsPlaced()) {
			int shipLength = getFirstShipLength();
			boolean[][] tryPutShip = new boolean[xSize][ySize]; 
			int rX = rand.nextInt(xSize);
			int rY = rand.nextInt(ySize);
			int dR = rand.nextInt(4);
			switch(dR) {
				case 0: 
					for(int x = rX; x > rX-shipLength; x--)
						if(x > 0 && x < xSize) tryPutShip[x][rY] = true;
					break;
				case 1:
					for(int x = rX; x < rX+shipLength; x++)
						if(x > 0 && x < xSize) tryPutShip[x][rY] = true;
					break;
				case 2:
					for(int y = rY; y > rY-shipLength; y--)
						if(y > 0 && y < ySize) tryPutShip[rX][y] = true;
					break;
				default: 
					for(int y = rY; y < rY+shipLength; y++)
						if(y > 0 && y < ySize) tryPutShip[rX][y] = true;
					break;
			}
			putShip(tryPutShip);
		}
	}
	
	// robot l� egyet, haszn�lva a mem�ri�j�t
	public void robotShot() {
		boolean point;
		switch (robotMemoryPhase) {
		case SEARCHING:
			Random rand = new Random();
			int rX = 0;
			int rY = 0;
			while(!(rX > 0 && rY > 0 && rX < xSize-1 && rY < ySize-1 && !shots[rX][rY])) {
				rX  = rand.nextInt(xSize);
				rY  = rand.nextInt(ySize);
			}
			point = shot(rX, rY);
			if(point) {
				robotMemoryX=rX;
				robotMemoryY=rY;
				robotMemoryDelta=0;
				robotMemoryPhase = robotMemoryPhase.FINDING_DIRECTION;
			}
			break;
		case FINDING_DIRECTION:
			switch (robotMemoryDelta) {
			case 0:
				point = shot(robotMemoryX+1, robotMemoryY);
				if(point) robotMemoryHorisontal=false;
				break;
			case 1:
				point = shot(robotMemoryX-1, robotMemoryY);
				if(point) robotMemoryHorisontal=false;
				break;
			case 2:
				point = shot(robotMemoryX, robotMemoryY+1);
				if(point) robotMemoryHorisontal=true;
				break;
			default:
				point = shot(robotMemoryX, robotMemoryY-1);
				if(point) robotMemoryHorisontal=true;
				break;
			}
			if(point) {
				robotMemoryPhase = robotMemoryPhase.FIRE_NEGATIVE_DIRECTION;
				robotMemoryDelta = 0;
			}
			else robotMemoryDelta++;
			break;
		case FIRE_NEGATIVE_DIRECTION:
			robotMemoryDelta++;
			point = robotMemoryHorisontal ? shot(robotMemoryX, robotMemoryY-robotMemoryDelta) : shot(robotMemoryX-robotMemoryDelta, robotMemoryY);
			if(!point) {
				robotMemoryPhase = robotMemoryPhase.FIRE_POSITIVE_DIRECTION;
				robotMemoryDelta = 0;
			}
			break;
		case FIRE_POSITIVE_DIRECTION:
			robotMemoryDelta++;
			point = robotMemoryHorisontal ? shot(robotMemoryX, robotMemoryY+robotMemoryDelta) : shot(robotMemoryX+robotMemoryDelta, robotMemoryY);
			if(!point)
				robotMemoryPhase = robotMemoryPhase.SEARCHING;
			break;
		}
			
	}

}
