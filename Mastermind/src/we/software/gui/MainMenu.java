package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import we.software.mastermind.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by bill on 3/28/17.
 * This class is the frame that hosts the menu of the game.
 */
public class MainMenu extends JFrame {

	private MenuButton howToPlay, play, options;            //Main menu buttons for functionality
	private GameMode gameModePanel;                         //The panel that appears for the user to select a game-mode
	private Options optionsPanel;                           //The panel that appears for the user to select game options
	private HowToPlay howToPlayPanel;
    private final int WIDTH = 1280;
	private final int HEIGHT = WIDTH / 16 * 9;
	private int posY = 230;                                 //The starting vertical position of the menu buttons
	private ButtonListener b = new ButtonListener();
	public static boolean musicOn = true;                  //Variable that controls the music (on/off)
	public static boolean soundfxOn = false;                //Variable that controls the sound effects (on/off)
	private AudioLoad menuMusic;                            //The audio file of the music tha plays in the menu
	private String username = null;
	private JButton minimizeButton;
	private Client client = null;
	private GameGui previous = null;
	private boolean modeSelected = false;
	private MenuButton selected = null;
	private ChatGui chatGui;
	private LogIn loginPanel;
	private boolean ready = false;
	private int gameMode = 0;


	public MainMenu() {




		menuMusic = new AudioLoad("MainMenu.wav");
		gameModePanel = new GameMode();
		optionsPanel = new Options();
		howToPlayPanel = new HowToPlay();
		loginPanel = new LogIn();
		chatGui = new ChatGui();
		chatGui.setBoundsForMainMenu();
		chatGui.setMainMenu(this);

		howToPlay = addMenuButton("howtoplayv2.png");
		play = addMenuButton("playv2.png");
		options = addMenuButton("optionsv2.png");

		PreloadImages.preloadImages();

		initFrame();

	}

    /**
     * Initializes the frame. Moved from the constructor for cleaner code.
     */
	private void initFrame() {

		JButton exitButton = new MenuButton("exitv2.png", 1240, 5, 0, 0);
		minimizeButton = new MenuButton("minimize.png", 1200, 5, 0, 0);
		exitButton.addActionListener(b);
		minimizeButton.addActionListener(b);

		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		try {
			setIconImage(ImageIO.read(LoadAssets.load("master.png")));
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(LoadAssets.load("Backgroundv2.png")))));
		} catch (IOException exc) {
			exc.printStackTrace();
		}

		while(!modeSelected){

		    modeSelected = true;
		}

        add(exitButton);
        add(minimizeButton);
        add(loginPanel);

        setTitle("Mastermind WE - Pre Alpha 0.0.1");
		setUndecorated(true);
		setVisible(true);
		if(musicOn) menuMusic.playMenuClip();

		//getUsername();

	}

	public String getUsername(){

	    return "Default";
    }

	private void addMenu(){

	    add(howToPlay);
        add(play);
        add(options);
        add(gameModePanel);
        add(howToPlayPanel);
        add(optionsPanel);
        add(chatGui);
        revalidate();
    }



	public void setFrameVisible(GameGui previous) {
	    this.previous = previous;
	    previous.dispose();
		setVisible(true);
		if(musicOn) menuMusic.playMenuClip();
		if(!(selected == null)) selected.setUnselected();
		selected = null;
		chatGui.setBoundsForMainMenu();
		add(chatGui);
		revalidate();
	}

    /**
     *
     * Initializes and returns a menu button with the correct coordinates.
	 * This is used so we can initialize many buttons at the same time
	 * and put them in the correct position, without having to manually input
	 * the coordinates.
     */
	private MenuButton addMenuButton(String path) {

		posY += 120;
		int posX = 25;
		MenuButton button = new MenuButton(path, posX, posY, 0, 0);
		button.addActionListener(b);
		return button;
	}

	class LogIn extends JPanel {

	    private BufferedImage background, signupBackground, loginBackground, offlineBackground, serverOfflineBackground, modeSelect;
	    private BufferedImage currentBackground;
	    private MenuButton login, signup, playOffline, online, offline, noAccount, backArrow;
	    private JTextField username, password;
	    private JLabel wrongUser, wrongPassword, userExists;
	    private ImageIcon wrongUserIcon, wrongPasswordIcon, userExistsIcon;
	    private int mode = 0;
        private MenuButton previous1 = null;
        private MenuButton previous2 = null;

	    public LogIn(){


	        loadImages();
            setBounds(0, 0, background.getWidth(), background.getHeight());
	        setOpaque(false);

	        setUpTextFields();
	        setupButtons();

	        setLayout(null);

	        add(username);
	        add(password);
	        add(login);
	        add(signup);
	        add(playOffline);
	        add(noAccount);
	        add(backArrow);
	        add(online);
	        add(offline);

	        setBackground(0);
	    }

        private void loadImages(){

	        try{
	            background = ImageIO.read(LoadAssets.load("modePanelv2.png"));
	            modeSelect = ImageIO.read(LoadAssets.load("Layers/selectMode.png"));
	            signupBackground = ImageIO.read(LoadAssets.load("Layers/signUp.png"));
	            loginBackground = ImageIO.read(LoadAssets.load("Layers/login.png"));
	            offlineBackground = ImageIO.read(LoadAssets.load("Layers/selectUsername.png"));
	            serverOfflineBackground = ImageIO.read(LoadAssets.load("Layers/serverOffline.png"));
            }catch(IOException e){
	            e.printStackTrace();
            }

            wrongPasswordIcon = new ImageIcon(LoadAssets.load("Layers/wrongUsername.png"));
            wrongUserIcon = new ImageIcon(LoadAssets.load("Layers/wrongPass.png"));
            userExistsIcon = new ImageIcon(LoadAssets.load("Layers/userExists.png"));

        }

	    @Override
        protected void paintComponent(Graphics g){

            g.drawImage(background, 0, 0, null);
            g.drawImage(currentBackground, 487+35, 244+25, null);
        }

        private void setupButtons(){

            login = new MenuButton("login.png", 487+81 , 244+315, 0, 0);
            login.setVisible(false);
            login.addActionListener(b);

            signup = new MenuButton("signUp.png", 487+87, 244+295, 0, 0);
            signup.setVisible(false);
            signup.addActionListener(b);

            playOffline = new MenuButton("playOffline.png", 487+87, 244+296, 0, 0);
            playOffline.setVisible(false);
            playOffline.addActionListener(b);

            noAccount = new MenuButton("noAccount.png", 487+52, 244+274, 0, 0);
            noAccount.setVisible(false);
            noAccount.addActionListener(b);

            backArrow = new MenuButton("backArrow.png", 487+32, 244+28, 0, 0);
            backArrow.setVisible(false);
            backArrow.addActionListener(b);

            online = new MenuButton("online.png", 487+94, 244+106, 0, 0);
            online.setVisible(false);
            online.addActionListener(b);

            offline = new MenuButton("offline.png", 487+85, 244+177, 0, 0);
            offline.setVisible(false);
            offline.addActionListener(b);
        }

        private void setUpTextFields(){

            username = new JTextField();
            username.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            username.setOpaque(false);

            password = new JTextField(10);
            password.setOpaque(false);
            password.setBorder(javax.swing.BorderFactory.createEmptyBorder());

            Font f1 = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
            username.setFont(f1);
            username.setForeground(Color.white);

            password.setFont(f1);
            password.setForeground(Color.white);

            username.setVisible(false);
            password.setVisible(false);


        }

        private void hidePrevious(){
            if(!(previous1 == null)) previous1.setVisible(false);
            if(!(previous2 == null)) previous2.setVisible(false);
            repaint();
        }

        public void setBackground(int bg){

            switch(bg){
                case 0:
                    currentBackground = modeSelect;
                    username.setVisible(false);
                    password.setVisible(false);

                    hidePrevious();
                    previous1 = online;
                    previous2 = offline;

                    online.setVisible(true);
                    offline.setVisible(true);
                    backArrow.setVisible(false);
                    break;
                case 1:
                    currentBackground = loginBackground;
                    username.setBounds(487+55, 244+116, 196, 33);
                    password.setBounds(487+55, 244+211, 196, 33);

                    hidePrevious();
                    login.setVisible(true);
                    previous1 = login;
                    previous2 = noAccount;

                    backArrow.setVisible(true);
                    username.setVisible(true);
                    password.setVisible(true);
                    noAccount.setVisible(true);
                    break;
                case 2:
                    currentBackground = signupBackground;
                    username.setBounds(487+55, 244+120, 196, 33);
                    password.setBounds(487+55, 244+228, 196, 33);

                    hidePrevious();
                    previous1 = signup;

                    username.setVisible(true);
                    password.setVisible(true);
                    signup.setVisible(true);
                    break;
                case 3:
                    currentBackground = offlineBackground;
                    username.setBounds(487+55, 244+160, 196, 33);

                    hidePrevious();
                    previous1 = playOffline;

                    username.setVisible(true);
                    password.setVisible(false);
                    backArrow.setVisible(true);
                    playOffline.setVisible(true);
                    break;
                case 4:
                    currentBackground = serverOfflineBackground;

                    hidePrevious();

                    username.setVisible(false);
                    password.setVisible(false);
                    backArrow.setVisible(true);
                    break;
            }
        }
    }

	/**
     * This class handles the properties of the game mode panel.
     */
	class GameMode extends JPanel {

		private BufferedImage background;
		private MenuButton pVsAi, pVsP;
		private boolean flag = false;
		private boolean flagOptions = true;

		private GameMode() {

			pVsAi = new MenuButton("pvai.png", 800, 44, 0, 0);
			pVsAi.addActionListener(b);

			pVsP = new MenuButton("pvp.png", 506, 44, 0, 0);
			pVsP.addActionListener(b);

			readImages();
			initPanel();

		}

		private void readImages() {

			try {
				background = ImageIO.read(LoadAssets.load("playPanel.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		private void initPanel() {
			setLayout(null);
			add(pVsAi);
			if(!(gameMode == 0)) add(pVsP);

			setBounds(193, 300, background.getWidth(null), background.getHeight(null));
			setOpaque(false);
			setVisible(flag);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, null);
		}

		private void setPanelVisible() {
			if (!flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}

		private void setPanelInvisible() {
			if (flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}

		private void panelRestart() {
			pVsP.setVisible(true);
			pVsAi.setVisible(true);
		}
	}

    /**
     * This class handles the properties of the options panel.
     */
	class Options extends JPanel {

		private Image background;                                                 //The background image of the panel
		private MenuButton musicButton, soundFXButton;
		private JLabel title;
		private boolean flag = false;
		private boolean flagOptions = true;

		public Options() {

			readImages();
			initPanel();
		}

		private void readImages() {

			try {
				background = ImageIO.read(LoadAssets.load("gameoptions.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		private void initPanel() {

            musicButton = new MenuButton("switch.png", 254, 45, 0, 0);
            musicButton.addActionListener(b);

            soundFXButton = new MenuButton("switch.png", 254, 85, 0, 0);
            soundFXButton.addActionListener(b);


			setLayout(null);
			add(musicButton);
			add(soundFXButton);

			setBounds(322, 538, background.getWidth(null), background.getHeight(null));
			setOpaque(false);
			setVisible(flag);
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
	}

    /**
     * The panel that appears after the users selects the "How to playOffline" button.
     * Explains the rules of the game and how to playOffline it.
     */
	class HowToPlay extends JPanel{

        private Image background;                                       //The background image of the panel
        private boolean flag = false;

        public HowToPlay(){

            try{
                background = ImageIO.read(LoadAssets.load("howToPlayv2.png"));
            }catch (IOException e){
                e.printStackTrace();
            }
            initPanel();
        }

        private void initPanel(){

            setLayout(null);

            setBounds(554, 36, background.getWidth(null), background.getHeight(null));
            setOpaque(false);
            setVisible(flag);
        }

        @Override
        protected void paintComponent(Graphics g){

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
    }

    /**
	 * Handles the action after a button has been pressed.
	 */
	class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == howToPlay) {

				if (soundfxOn)
					howToPlay.playSound();

                if(!(selected == null)) selected.setUnselected();
				howToPlay.staySelected();
				selected = howToPlay;
				optionsPanel.setPanelInvisible();
				gameModePanel.setPanelInvisible();
				howToPlayPanel.setPanelVisible();
			}
			else if (e.getSource() == play) {

				optionsPanel.setPanelInvisible();
				howToPlayPanel.setPanelInvisible();
				gameModePanel.setPanelVisible();
                if(!(selected == null)) selected.setUnselected();
				play.staySelected();
				selected = play;

				if (soundfxOn)
					play.playSound();
			}
			else if (e.getSource() == options) {

				gameModePanel.setPanelInvisible();
				howToPlayPanel.setPanelInvisible();
				optionsPanel.setPanelVisible();
				if(!(selected == null)) selected.setUnselected();
				options.staySelected();
				selected = options;

				if (!gameModePanel.flagOptions) {
					gameModePanel.panelRestart();
				}

				if (soundfxOn)
					options.playSound();

			}

			else if (e.getSource() == gameModePanel.pVsP) {

				gameModePanel.pVsP.setVisible(false);
				gameModePanel.pVsAi.setVisible(false);

				gameModePanel.flagOptions = false;
				if (soundfxOn)
					gameModePanel.pVsP.playSound();

				GameGui gameGui = new GameGui(MainMenu.this, 1, chatGui);
				setVisible(false);

				//gameModePanel.panelRestart();
				gameModePanel.setPanelInvisible();
				if (musicOn)
					menuMusic.closeClip();
			}
			else if (e.getSource() == gameModePanel.pVsAi) {

				gameModePanel.pVsP.setVisible(false);
				gameModePanel.pVsAi.setVisible(false);

				gameModePanel.flagOptions = false;
				if (soundfxOn)
					gameModePanel.pVsAi.playSound();

				GameGui gameGui = new GameGui(MainMenu.this, 0, chatGui);
				setVisible(false);

				//gameModePanel.panelRestart();
				gameModePanel.setPanelInvisible();
				if (musicOn)
					menuMusic.closeClip();
			}
			else if (e.getSource() == optionsPanel.musicButton) {

				if (musicOn) {
					musicOn = false;
					menuMusic.closeClip();
					optionsPanel.musicButton.staySelected();
				} else {
					musicOn = true;
					optionsPanel.musicButton.setUnselected();
					menuMusic.playMenuClip();
				}
			}
			else if (e.getSource() == optionsPanel.soundFXButton) {

				if (soundfxOn) {
                    soundfxOn = false;
                    optionsPanel.soundFXButton.staySelected();
                }
				else {
                    soundfxOn = true;
                    optionsPanel.soundFXButton.setUnselected();
                }
			}

			else if (e.getSource() == loginPanel.backArrow){

			    loginPanel.setBackground(0);
            }

			else if (e.getSource() == loginPanel.offline){

			    loginPanel.setBackground(3);
            }

            else if (e.getSource() == loginPanel.online){

			    /*loginPanel.setVisible(false);
			    addMenu();*/
			    gameMode = 1;
			    loginPanel.setBackground(1);
            }

            else if (e.getSource() == loginPanel.noAccount){

                loginPanel.setBackground(2);
            }

            else if (e.getSource() == loginPanel.playOffline){

                username = loginPanel.username.getText();
                /*while(loginPanel.username.getText().equals("") || loginPanel.username.getText().equals(" ")){
                    username = loginPanel.username.getText();
                }*/
                loginPanel.setVisible(false);
                addMenu();
                gameMode = 0;
            }

            else if (e.getSource() == loginPanel.login){

                try {
                    client = new Client();
                    client.logMeIn(loginPanel.username.getText(), loginPanel.password.getText());
                    loginPanel.setVisible(false);
                    addMenu();

                } catch (Exception ex) {
                    loginPanel.setBackground(4);
                }

                client.getcListener().setMainMenu(MainMenu.this);
                client.getcListener().setChatGui(chatGui);
                chatGui.setClient(client);
            }

            else if (e.getSource() == minimizeButton){
			    setState(Frame.ICONIFIED);
            }
			else {

				int exit = JOptionPane.showConfirmDialog(MainMenu.this, "Are you sure you want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION);
				if (exit == 0)
					System.exit(0);
				// setState(Frame.ICONIFIED);
			}
		}
	}
}
