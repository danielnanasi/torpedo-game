import java.util.Arrays;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class Board extends Map implements MouseListener {

	static int rectSize = 30;
	private boolean showShips;
	private boolean isEnemy;

	boolean[][] selectedArea;
	
	public Board(int xSize, int ySize, boolean isEnemy) {
		super(xSize, ySize);
		addMouseListener(this);
		Board.xSize = xSize;
		Board.ySize = ySize;
		setShowShips(isEnemy);
		setEnemy(isEnemy);
		
		this.setSize(new Dimension(xSize*rectSize, ySize*rectSize));
		
		
		selectedArea = new boolean[xSize][ySize];
		for(boolean[] array : selectedArea) Arrays.fill(array, false);
		
		
	}
	
	public void setShowShips(boolean b) {
		showShips = b;
	}
	public void setEnemy(boolean b) {
		isEnemy = b;
	}
	
	// kijelölés törlése és hajók/lövések újrarajzolása
	public void clearSelection() {
		
		for(boolean[] array : selectedArea) Arrays.fill(array, false);
		
		Graphics gp = getGraphics();
		gp.clearRect(0, 0, xSize*rectSize, ySize*rectSize);
		
		for(int x = 0; x < xSize; ++x) {
			for(int y = 0; y < ySize; ++y) {
				
				// hajó rajzolása
				if(ships[x][y] && showShips) {
					gp.setColor(Color.YELLOW);
					gp.fillRect(x*rectSize+1, y*rectSize+1, rectSize-1, rectSize-1);
				}
				
				// lövések rajzolása
				if(shots[x][y]) {
					gp.setColor(Color.black);
					gp.drawLine(x*rectSize, y*rectSize, x*rectSize+rectSize, y*rectSize+rectSize);
					gp.drawLine(x*rectSize, y*rectSize+rectSize, x*rectSize+rectSize, y*rectSize);
				}
			}			
		}
		drawMap(gp);
	}
	
	// négyzetrács rajzolása
	public void drawMap(Graphics gp) {
		gp.setColor(Color.black);
		for(int x = 0; x <= xSize; x++)
			gp.drawLine(x*rectSize, 0, x*rectSize, ySize*rectSize);
		for(int y = 0; y <= xSize; y++)
			gp.drawLine(0, y*rectSize, ySize*rectSize, y*rectSize);

	}
	
	public void paint(Graphics gp) {
		drawMap(gp);
	}

	public boolean[][] getSelectedAre(){
		return selectedArea;
	}
	
	// négyzet kijelölése
	public void select(int x, int y) {
		Graphics gp = getGraphics();
		selectedArea[x][y] = true;
		if(isEnemy) gp.setColor(Color.cyan);
		else gp.setColor(Color.red);
		gp.fillRect(x*rectSize+1, y*rectSize+1, rectSize-1, rectSize-1);
	}
	
	// kijelölés törlése
	public void deleteSelection() {
		for(boolean[] array : selectedArea) Arrays.fill(array, false);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX()/rectSize;
		int y = e.getY()/rectSize;
		
		// pályán belüli klikket figyelünk
		if(0 <= x && x < xSize && 0 <= y && y < ySize)
			select(x, y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	

}
