package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import we.software.mastermind.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by bill on 3/28/17.
 * This class is the frame that hosts the menu of the game.
 */
public class MainMenu extends JFrame {

	private MenuButton howToPlay, play, options;            //Main menu buttons for functionality
	private GameMode gameModePanel;                         //The panel that appears for the user to select a game-mode
	private Options optionsPanel;                           //The panel that appears for the user to select game options
	private final int WIDTH = 1024;
	private final int HEIGHT = WIDTH / 12 * 9;
	private int posY = 230;                                 //The starting vertical position of the menu buttons
	private ButtonListener b = new ButtonListener();
	public static boolean musicOn = false;                  //Variable that controls the music (on/off)
	public static boolean soundfxOn = false;                //Variable that controls the sound effects (on/off)
	private AudioLoad menuMusic;                            //The audio file of the music tha plays in the menu
	private String username = null;
	private Client player = null;


	public MainMenu() {
		
		/*player = new Client();
		
		try {
			player.startListening(this);
		} catch (IOException e1) {
			System.out.println("Tsekare Server...");
		}*/

		menuMusic = new AudioLoad("MainMenu.wav");
		gameModePanel = new GameMode();
		optionsPanel = new Options();

		howToPlay = addMenuButton("howtoplay.png");
		play = addMenuButton("play.png");
		options = addMenuButton("options.png");

		initFrame();

	}

    /**
     * Initializes the frame. Moved from the constructor for cleaner code.
     */
	private void initFrame() {

		JButton exitButton = new MenuButton("exit.png", 1001, 5, 0);
		exitButton.addActionListener(b);

		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		try {
			setIconImage(ImageIO.read(LoadAssets.load("master.png")));
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(LoadAssets.load("Background.png")))));
		} catch (IOException exc) {
			exc.printStackTrace();
		}


		add(howToPlay);
		add(play);
		add(options);
		add(gameModePanel);
		add(optionsPanel);
		add(exitButton);

		setUndecorated(true);
		setVisible(true);
		if(musicOn) menuMusic.playMenuClip();

		//getUsername();

	}


	public void setFrameVisible() {
		setVisible(true);
		if(musicOn) menuMusic.playMenuClip();
	}

    /**
     * Prompts the user to enter a username and a password to enter the system.
     */
	public void getUsername() {

		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter Your username: ");
		JTextField txt = new JTextField(10);

		boolean isOk = true;
		int selectedOption = 0;

		panel.add(lbl);
		panel.add(txt);
		// Checking Server connectivity remove lines 127-131 to make it
		// like it was
		// H allagh pou egine einai proxeirorammeni alla leitourgikh
		// Zitaei apo ton xrhsth na dwsei onoma kai stin sunexeia tsekarei an
		// ekeinh th stigmh einai kapoios me auto to onoma sundedemenos ston
		// server an nai tote tou zitaei na
		// ksanavalei onoma an oxi sunexizei kanonika
		//gia na xrisimopoieisetai ton server tha anevasw ta stoixeia tou server pou prepei
		//na alaxthoun sto arxeio Client stis seires 16 'Server' kai 17 'PORT'
		//parakalw na min ginoun upload sto github gia logous asfaleias
		//o server einai panw sxedon 24/7


		while ((selectedOption != 1 && txt.getText().equals(""))) {
				selectedOption = JOptionPane.showOptionDialog(null, panel, "Login", JOptionPane.NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				username = txt.getText();

		}
		/*try {
			player.addMe(username);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

    /**
     *
     * Initializes and returns a menu button with the correct coordinates.
     */
	private MenuButton addMenuButton(String path) {

		posY += 70;
		int posX = 130;
		MenuButton button = new MenuButton(path, posX, posY, 51);
		button.addActionListener(b);
		return button;
	}

	/**
     * This class handles the properties of the game mode panel.
     */
	class GameMode extends JPanel {

		private Image background;
		private ImageIcon exitIcon, exitIconHover, titleImage, pvaiTitle, pvpTitle;
		private MenuButton pVsAi, pVsP;
		private JButton exit;
		private JLabel title;
		private boolean flag = false;
		private boolean flagOptions = true;

		private GameMode() {

			title = new JLabel();

			titleImage = new ImageIcon(LoadAssets.load("Buttons/selectgamemode.png"));
			pvaiTitle = new ImageIcon(LoadAssets.load("Buttons/titlepvai.png"));
			pvpTitle = new ImageIcon(LoadAssets.load("Buttons/titlepvp.png"));

			title.setIcon(titleImage);
			title.setBounds(60, 11, titleImage.getIconWidth(), titleImage.getIconHeight());

			pVsAi = new MenuButton("pvai.png", 39, 46, 0);
			pVsAi.addActionListener(b);

			pVsP = new MenuButton("pvp.png", 39, 98, 0);
			pVsP.addActionListener(b);

			readImages();
			addExit();
			initPanel();

		}

		private void readImages() {

			try {
				background = ImageIO.read(LoadAssets.load("gameoptions.png"));
				exitIcon = new ImageIcon(ImageIO.read(LoadAssets.load("Buttons/exit.png")));
				exitIconHover = new ImageIcon(ImageIO.read(LoadAssets.load("Buttons/sexit.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		private void initPanel() {
			setLayout(null);
			add(exit);
			add(title);
			add(pVsAi);
			add(pVsP);

			setBounds(420, 280, background.getWidth(null), background.getHeight(null));
			setOpaque(false);
			setVisible(flag);
		}

		private void addExit() {
			exit = new JButton();
			exit.setIcon(exitIcon);
			exit.setBounds(323, 13, exitIcon.getIconWidth(), exitIcon.getIconHeight());
			exit.addActionListener(b);
			exit.setOpaque(false);
			exit.setContentAreaFilled(false);
			exit.setBorderPainted(false);
			exit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					exit.setBounds(316, 7, exitIconHover.getIconWidth(), exitIconHover.getIconHeight());
					exit.setIcon(exitIconHover);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					exit.setBounds(323, 13, exitIcon.getIconWidth(), exitIcon.getIconHeight());
					exit.setIcon(exitIcon);
				}
			});

		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, null);
		}

		public void setPanelVisible() {
			if (!flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}

		public void setPanelInvisible() {
			if (flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}

		public void panelRestart() {
			pVsP.setVisible(true);
			pVsAi.setVisible(true);
			title.setIcon(titleImage);
			title.setBounds(60, 11, titleImage.getIconWidth(), titleImage.getIconHeight());
		}
	}

    /**
     * This class handles the properties of the options panel.
     */
	class Options extends JPanel {

		private Image background;                                       //The background image of the panel
		private ImageIcon exitIcon, exitIconHover,
				titleImage, musicTitle, soundFXTitle, optionsSquare;    //The images for the buttons
		private JLabel music;
		private JLabel soundFX;
		private MenuButton musicButton;
		private MenuButton soundFXButton;
		private JButton exit;
		private JLabel title;
		private boolean flag = false;
		private boolean flagOptions = true;

		public Options() {

			readImages();
			addExit();
			initPanel();
		}

		private void readImages() {

			try {
				background = ImageIO.read(LoadAssets.load("gameoptions.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			exitIcon = new ImageIcon(LoadAssets.load("Buttons/exit.png"));
			exitIconHover = new ImageIcon(LoadAssets.load("Buttons/sexit.png"));
			optionsSquare = new ImageIcon(LoadAssets.load("Buttons/redsquare.png"));
		}

		private void initPanel() {

            title = new JLabel();
            music = new JLabel();
            soundFX = new JLabel();

            titleImage = new ImageIcon(LoadAssets.load("Buttons/titleoptions.png"));
            musicTitle = new ImageIcon(LoadAssets.load("Buttons/titlemusic.png"));
            soundFXTitle = new ImageIcon(LoadAssets.load("Buttons/titlesoundfx.png"));

            title.setIcon(titleImage);
            title.setBounds(130, 11, titleImage.getIconWidth(), titleImage.getIconHeight());

            music.setIcon(musicTitle);
            music.setBounds(44, 60, musicTitle.getIconWidth(), musicTitle.getIconHeight());

            soundFX.setIcon(soundFXTitle);
            soundFX.setBounds(44, 110, soundFXTitle.getIconWidth(), soundFXTitle.getIconHeight());

            musicButton = new MenuButton("redsquare.png", 250, 60, 0);
            musicButton.addActionListener(b);
            soundFXButton = new MenuButton("redsquare.png", 250, 110, 0);
            soundFXButton.addActionListener(b);

			setLayout(null);
			add(exit);
			add(title);
			add(music);
			add(soundFX);
			add(musicButton);
			add(soundFXButton);

			setBounds(420, 350, background.getWidth(null), background.getHeight(null));
			setOpaque(false);
			setVisible(flag);
		}

		private void addExit() {
			exit = new JButton();
			exit.setIcon(exitIcon);
			exit.setBounds(323, 13, exitIcon.getIconWidth(), exitIcon.getIconHeight());
			exit.addActionListener(b);
			exit.setOpaque(false);
			exit.setContentAreaFilled(false);
			exit.setBorderPainted(false);
			exit.addMouseListener(new MouseAdapter() {

				public void mouseEntered(MouseEvent e) {
					exit.setBounds(316, 7, exitIconHover.getIconWidth(), exitIconHover.getIconHeight());
					exit.setIcon(exitIconHover);
				}

				public void mouseExited(MouseEvent e) {
					exit.setBounds(323, 13, exitIcon.getIconWidth(), exitIcon.getIconHeight());
					exit.setIcon(exitIcon);
				}
			});
		}

		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);
			g.drawImage(background, 0, 0, null);
		}

		/**
         * Sets the panel visible after checking it is invisible.
         */
		public void setPanelVisible() {
			if (!flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}

		/**
         * Sets the panel invisible after checking it is visible.
         */
		public void setPanelInvisible() {
			if (flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}

		/**
         * Restarts the panel to its initial state.
         */
		public void panelRestart() {
			title.setIcon(titleImage);
			title.setBounds(60, 11, titleImage.getIconWidth(), titleImage.getIconHeight());
		}
	}

	/**
	 * Handles the action after a button has been pressed.
	 */
	class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == howToPlay) {

				if (soundfxOn)
					howToPlay.playSound();
			}
			else if (e.getSource() == play) {

				optionsPanel.setPanelInvisible();
				gameModePanel.setPanelVisible();

				if (!optionsPanel.flagOptions) {
					optionsPanel.panelRestart();
				}

				if (soundfxOn)
					play.playSound();
			}
			else if (e.getSource() == options) {

				gameModePanel.setPanelInvisible();
				optionsPanel.setPanelVisible();

				if (!gameModePanel.flagOptions) {
					gameModePanel.panelRestart();
				}

				if (soundfxOn)
					options.playSound();

			}
			else if (e.getSource() == gameModePanel.exit) {

				gameModePanel.setPanelInvisible();

				if (!gameModePanel.flagOptions) {
					gameModePanel.panelRestart();
				}

				if (soundfxOn)
					options.playSound();

			}
			else if (e.getSource() == gameModePanel.pVsP) {

				gameModePanel.pVsP.setVisible(false);
				gameModePanel.pVsAi.setVisible(false);

				gameModePanel.title.setBounds(30, -2, gameModePanel.pvpTitle.getIconWidth(),
						gameModePanel.pvpTitle.getIconHeight());
				gameModePanel.title.setIcon(gameModePanel.pvpTitle);

				gameModePanel.flagOptions = false;
				if (soundfxOn)
					gameModePanel.pVsP.playSound();
			}
			else if (e.getSource() == gameModePanel.pVsAi) {

				gameModePanel.pVsP.setVisible(false);
				gameModePanel.pVsAi.setVisible(false);

				gameModePanel.title.setIcon(gameModePanel.pvaiTitle);
				gameModePanel.title.setBounds(30, -2, gameModePanel.pvaiTitle.getIconWidth(),
						gameModePanel.pvaiTitle.getIconHeight());

				gameModePanel.flagOptions = false;
				if (soundfxOn)
					gameModePanel.pVsAi.playSound();
				GameGui gameGui = new GameGui(MainMenu.this);
				setVisible(false);

				gameModePanel.panelRestart();
				gameModePanel.setPanelInvisible();
				if (musicOn)
					menuMusic.closeClip();
			}
			else if (e.getSource() == optionsPanel.exit) {

				optionsPanel.setPanelInvisible();

				if (!optionsPanel.flagOptions) {
					optionsPanel.panelRestart();
				}

				if(soundfxOn) options.playSound();
			}
			else if (e.getSource() == optionsPanel.musicButton) {

				optionsPanel.musicButton.setIcon("Buttons/hredsquare.png");
				if (musicOn) {
					musicOn = false;
					menuMusic.closeClip();
				} else {
					musicOn = true;
					menuMusic.playMenuClip();
				}
			}
			else if (e.getSource() == optionsPanel.soundFXButton) {

				optionsPanel.soundFXButton.setIcon("Buttons/hredsquare.png");
				if (soundfxOn)
					soundfxOn = false;
				else
					soundfxOn = true;
			}
			else {

				int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION);
				if (exit == 0)
					System.exit(0);
				// setState(Frame.ICONIFIED);
			}
		}
	}
}
