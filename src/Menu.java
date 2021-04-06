import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JPanel;
import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;


public class Menu extends JFrame {

	JFrame loadFrame;
	File loadFile;
	
	// Fõmenü elemei
	static JPanel menuPanel;
	static JLabel welcomeMessage, credits;
	static JButton newGameButton, loadGameButton, exitButton;
	
	// load ablak
	JPanel loadPanel;
	JButton loadButton, cancelButton;
	JComboBox fileList;
	
	Path saveDir;
	
	public Menu(){
		this.setMinimumSize(new Dimension(300,200));
		setTitle("Menü");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	    
	    GridLayout menuLayout = new GridLayout();
	    menuLayout.setRows(5);
	    menuLayout.setColumns(1);
	    
	    menuPanel = new JPanel();
	    menuPanel.setLayout(menuLayout);
		
		// Fõmenü gombjai
		newGameButton = new JButton("Új játék");
		loadGameButton = new JButton("Játék betöltése");
		exitButton = new JButton("Kilépés");
		
		newGameButton.addActionListener(new newGameActionListener());
		loadGameButton.addActionListener(new loadGameActionListener());
		exitButton.addActionListener(new exitActionListener());
		
		// Fõmenü üzenetei
		welcomeMessage = new JLabel("Torpedó játék!");
		credits = new JLabel("Készítette: Nánási Dániel, C1NRWP. 2020 õsz");

		welcomeMessage.setHorizontalAlignment(JLabel.CENTER);
		credits.setHorizontalAlignment(JLabel.CENTER);
		
		
		// Fõmenü felépítése
		menuPanel.add(welcomeMessage);
		menuPanel.add(newGameButton);
		menuPanel.add(loadGameButton);
		menuPanel.add(exitButton);
		menuPanel.add(credits);
		
		this.add(menuPanel);
	}
    class newGameActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			new Game();
		}
    }
    class loadGameActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			saveDir = null;
			
			loadFrame = new JFrame();
			loadFrame.setTitle("Betöltés");
			loadFrame.setLocationRelativeTo(null);
			loadFrame.setVisible(true);
			loadFrame.setMinimumSize(new Dimension(250,100));
			loadFrame.setLayout(new FlowLayout());
			
			loadPanel = new JPanel(new FlowLayout());
			loadButton = new JButton("Betöltés");
			cancelButton = new JButton("Mégse");
			loadButton.addActionListener(new loadInsideActionListener());
			loadButton.addActionListener(new cancelInsideActionListener());
			
			try {
				saveDir = new File(Paths.get("").toAbsolutePath().toString()+File.separatorChar+"savedGames").toPath();
				if(!Files.isDirectory(saveDir)) {
						Files.createDirectories(saveDir);
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
			String [] fileNames = saveDir.toFile().list();
			fileList = new JComboBox(fileNames);
			loadPanel.add(fileList);
			loadPanel.add(loadButton);
			loadPanel.add(cancelButton);
			loadFrame.add(loadPanel);
		}
			
	}
	class loadInsideActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			String filename = (String)fileList.getSelectedItem();
			System.out.print(filename);
			try {
				File saveDir = new File(Paths.get("").toAbsolutePath().toString()+File.separatorChar+"savedGames");
				File loadThis = new File(saveDir.getAbsolutePath().toString()+File.separatorChar+filename);
				ObjectInputStream sr = new ObjectInputStream(new FileInputStream(loadThis));
				
				boolean [][] savedObject;
				savedObject= (boolean[][])sr.readObject();
				
				Game loadedGame = new Game();
				loadedGame.loadGame(savedObject);

				sr.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			loadFrame.dispose();
		}
	}
    class exitActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			System.exit(0);
		}
    }
    
    class cancelInsideActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			loadFrame.dispose();
		}
    }
    
	public static void main(String[] args) {
		new Menu();
	}
}

