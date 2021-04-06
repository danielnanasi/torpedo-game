import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.BoxLayout;
import javax.swing.Box;

public class Game extends JFrame implements Serializable {
	
	enum Phase {
		STARTING,
		PUTSHIP,
		FIRE,
		FINISHED
	}
	
	public static Phase phase;
	
	static int mapSizeX = 10;
	static int mapSizeY = 10;
	
	JFrame saveFrame;
	
	Board playerBoard;
	Board robotBoard;
	
	JTable playerTable;
	JTable robotTable;
	
	JButton actionButton;
	JLabel actionLabel;
	
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem saveMenuItem;
	JMenuItem exitMenuItem;
	
	JButton saveInsideButton;
	JButton cancelInsideButton;
	
	JLabel robotPoints;
	JLabel playerPoints;
	
	JPanel hudPanel;
	JPanel gamePanel;
	
	JTextField saveName;
	
	public Game() {
		this.setMinimumSize(new Dimension(400,900));
		setResizable(true);
		setTitle("J�t�kban");
		setLocationRelativeTo(null);
	    setVisible(true);
		
		BorderLayout gameLayout = new BorderLayout(20,20);
		this.setLayout(gameLayout);
		
		// j�t�kter�let fel�p�t�se
		playerBoard = new Board(mapSizeX, mapSizeY, true);
		robotBoard = new Board(mapSizeX, mapSizeY, false);
		
		gamePanel = new JPanel();
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
		
		actionLabel = new JLabel("J�t�k Ind�t�sa!");
		actionButton = new JButton("Start!");
		actionButton.addActionListener(new actionButtonActionListener());
		
		playerPoints = new JLabel();
		robotPoints = new JLabel();
		
		gamePanel.add(Box.createRigidArea(new Dimension(0, 20)));
		gamePanel.add(robotBoard);
		gamePanel.add(playerPoints);
		gamePanel.add(robotPoints);
		gamePanel.add(Box.createRigidArea(new Dimension(0, 20)));
		gamePanel.add(actionLabel);
		gamePanel.add(actionButton);
		gamePanel.add(Box.createRigidArea(new Dimension(0, 20)));
		gamePanel.add(playerBoard);
		
		this.add(gamePanel, BorderLayout.CENTER);
		
		// Men�
		menu = new JMenu("Opci�k");
		saveMenuItem = new JMenuItem("Ment�s");
		saveMenuItem.addActionListener(new saveActionListener());
		exitMenuItem = new JMenuItem("Kil�p�s");
		exitMenuItem.addActionListener(new exitActionListener());
		
		menu.add(saveMenuItem);
		menu.add(exitMenuItem);
		menuBar = new JMenuBar();
		menuBar.add(menu);
		
		this.add(menuBar, BorderLayout.NORTH);
		
		// marg�k
		this.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
		this.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST);
		
		// j�t�k ind�that�s�gnak a jelz�se
		phase = Phase.STARTING;
	}
	
	// friss�ti a pontokat kijelz� labeleket
	void refreshFrame() {
		robotPoints.setText("J�t�kos tal�latai:     "+Integer.toString(robotBoard.getPoints())+"/"+Integer.toString(robotBoard.getMaxPoints()));
		playerPoints.setText("Ellenf�l tal�latai:      "+Integer.toString(playerBoard.getPoints())+"/"+Integer.toString(playerBoard.getMaxPoints()));
		robotBoard.clearSelection();
		playerBoard.clearSelection();
	}
	
	public void startGame() {
		phase = Phase.PUTSHIP;
		actionLabel.setText("Robot haj�k lerak�sa folyamatban...");
		robotBoard.robotPutAllShips();
		actionButton.setText("Lerak!");
		actionLabel.setText("Lerakhat� haj�m�retek: "+playerBoard.getAvailableShips().toString());
		refreshFrame();
	}
	
	public void putShip() {
		boolean successPut = playerBoard.putShip(playerBoard.getSelectedAre());
		if(successPut)
			actionLabel.setText("Haj� lerakva! Lerakhat� haj�m�retek: "+playerBoard.getAvailableShips().toString());
		else
			actionLabel.setText("Hib�s haj�! Lerakhat� haj�m�retek: "+playerBoard.getAvailableShips().toString());
			
		if(playerBoard.allShipsPlaced()) {
			phase = Phase.FIRE;
			actionButton.setText("T�z!");
			actionLabel.setText("L�hetsz egy c�lpontra!");
		}
		refreshFrame();
	}
	
	public void checkEnding() {
		if(playerBoard.getPoints() == playerBoard.getMaxPoints()) {
			actionLabel.setText("Vesztett�l!");
			actionButton.setText("Kil�p�s");
			robotBoard.setShowShips(true);
			phase = Phase.FINISHED;
		}
		if(robotBoard.getPoints() == robotBoard.getMaxPoints()) {
			actionLabel.setText("Nyert�l!");
			actionButton.setText("Kil�p�s");
			robotBoard.setShowShips(true);
			phase = Phase.FINISHED;
		}
	}
	
	public void shot() {
		int sx=0, sy=0, shotNum=0;
		boolean[][] shotArea = robotBoard.getSelectedAre();
 		for(int x = 0; x < mapSizeX; x++) {
			for(int y = 0; y < mapSizeY; y++) {
				if(shotArea[x][y]) {
					shotNum++;
					sx = x;
					sy = y;
				}
			}
		}
 		if(shotNum == 1) {
 			if(robotBoard.shot(sx, sy)) {
 				robotBoard.shot(sx, sy);
 				actionLabel.setText("Tal�lt!");
 				
 			}
 			else {
 				actionLabel.setText("Mell�!"); 				
 			}
 			playerBoard.robotShot();
 			
 		}
 		else {
 			actionLabel.setText("Egy helyre tudsz l�ni!"); 	
 		}
 		checkEnding();
 		refreshFrame();
	}
	
	class actionButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			switch(phase) {
				case STARTING:
					startGame();
					break;
				case PUTSHIP:
					putShip();
					break;
				case FIRE:
					shot();
					break;
				default:
					System.exit(0);
			}
		}
	}
	
    class exitActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			System.exit(0);
		}
    }
    
    class saveActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if(phase == Phase.FIRE) {
				saveFrame = new JFrame();
				saveFrame.setTitle("Ment�s");
				saveFrame.setLocationRelativeTo(null);
				saveFrame.setVisible(true);
				saveFrame.setMinimumSize(new Dimension(250,100));
				
				saveInsideButton = new JButton("Ment�s");
				saveInsideButton.addActionListener(new saveInsideActionListener());
				cancelInsideButton = new JButton("M�gse");
				cancelInsideButton.addActionListener(new cancelInsideActionListener());
				saveName = new JTextField(20);
				
				JPanel savePanel = new JPanel();
				
				savePanel.add(saveName);
				savePanel.add(saveInsideButton);
				savePanel.add(cancelInsideButton);
				saveFrame.add(savePanel);
				
			}
			else {
				actionLabel.setText("Az �sszes haj� lerak�sa ut�n lehet j�t�k�ll�st menteni.");
			}
		}
    }
    class saveInsideActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			try {
				String filename = saveName.getText().length() > 0 ? saveName.getText() : "unnamed";
				Path saveDir = new File(Paths.get("").toAbsolutePath().toString()+File.separatorChar+"savedGames").toPath();
				if(!Files.isDirectory(saveDir))
						Files.createDirectories(saveDir);
				File saveFile = new File(saveDir.toAbsolutePath().toString()+File.separatorChar+filename);
				if(Files.isRegularFile(saveFile.toPath()))
					Files.delete(saveFile.toPath());
				ObjectOutputStream sr = new ObjectOutputStream(new FileOutputStream(saveFile));
				
				boolean [][] saveObject = compressSaveObject();			
				sr.writeObject(saveObject);
				sr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			saveFrame.dispose();
		}
    }
    class cancelInsideActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			saveFrame.dispose();
		}
    }
    
    // j�t�k bet�lt�se a beolvasott objektumb�l
    public boolean[][] compressSaveObject() {
    	boolean [][] saveObject = new boolean[mapSizeX][mapSizeY*4];
    	
		for (int y=0; y < mapSizeY; y++) {
			for (int x=0; x < mapSizeX; x++) {
				for(int i =0; i < 4; i++) {
					switch (i) {
					case 0:
						saveObject[x][y*i] = playerBoard.getShot(x, y);
						break;
					case 1:
						saveObject[x][y*i] = playerBoard.getShip(x, y);
						break;
					case 2:
						saveObject[x][y*i] = robotBoard.getShot(x, y);
						break;
					case 3:
						saveObject[x][y*i] = robotBoard.getShip(x, y);
						break;
					}
				}
			}
		}
		return saveObject;
    }
    
    // j�t�k bet�lt�se a beolvasott objektumb�l
    public void loadGame(boolean[][] savedObject) {
    	
		for (int y=0; y < mapSizeY; y++) {
			for (int x=0; x < mapSizeX; x++) {
				for(int i =0; i < 4; i++) {
					System.out.println(y + " " +x + " " +i);
					switch (i) {
					case 0:
						playerBoard.setShot(x, y, savedObject[x][y*i]);
						break;
					case 1:
						playerBoard.setShip(x, y, savedObject[x][y*i]);
						break;
					case 2:
						robotBoard.setShot(x, y, savedObject[x][y*i]);
						break;
					case 3:
						robotBoard.setShip(x, y, savedObject[x][y*i]);
						break;
					}
				}
			}
		}
		
		phase = Phase.FIRE;
		actionButton.setText("T�z");
		actionLabel.setText("J�t�k bet�ltve!");
		refreshFrame();
    	
    }
    
}
