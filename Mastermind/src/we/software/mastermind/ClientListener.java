package we.software.mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javafx.scene.paint.Color;
import we.software.gui.ChatGui;
import we.software.gui.GameGui;
import we.software.gui.MainMenu;

public class ClientListener extends Thread {
	// private static final java.awt.Color Color.AQUA = null;
	private Client client;
	private Socket socket;
	private MainMenu mainMenu;
	private GameGui gameGui;
	private ChatGui chatGui;
	private Game game;

	public ClientListener(Client client, Socket socket, ChatGui chatGui) {
		this.client = client;
		this.socket = socket;
		this.chatGui = chatGui;
	}

	public void setGameGui(GameGui gameGui) {
		this.gameGui = gameGui;
	}
	
	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void run() {

		String transmitter;
		String reciever;
		String inmessage;
		String message = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((inmessage = bufferedReader.readLine()) != null) {
				try {
					String[] info = inmessage.split("%", 2);
					transmitter = info[0].split(":")[1];
					reciever = info[0].split(":")[2];
					message = info[1];
					if (inmessage.startsWith("add")) {
						if (message.equals("ok")) {
							client.setUsername(reciever);
						} else if (message.equals("taken")) {
							mainMenu.getUsername();
						}
					} else if (inmessage.startsWith("request")) {
						if (message.equals("wannaplay")) {
							if (!client.isInGame()) {
								// asks for user permission to start the game
								// if user accepts then:
								/*
								 * client.acceptGameRequest(transmitter);
								 * client.setEnemy(transmitter);
								 * client.setInGame(true);
								 */
								// if he rejects the client.RejectGameRequest();
							} else {
								client.rejectGameRequest(transmitter);
							}
						} else if (message.equals("ok")) {
							
							client.setEnemy(new Player());
							client.enemy.setName(transmitter);
							this.game.p2 = client.getEnemy();
							// accepted and game starts
						} else if (message.equals("not")) {
							// rejected
						}
					} else if (inmessage.startsWith("playpin")) {
						int pos = Integer.parseInt(message.split(" ")[0]);
						int color = Integer.parseInt(message.split(" ")[1]);
						switch (pos) {
						case 0: {
							gameGui.selectionBtn1.setColored(color-1);
							gameGui.selectionBtn1.setUnselected();
							break;
						}
						case 1: {
							gameGui.selectionBtn2.setColored(color-1);
							gameGui.selectionBtn2.setUnselected();
							break;
						}
						case 2: {
							gameGui.selectionBtn3.setColored(color-1);
							gameGui.selectionBtn3.setUnselected();
							break;
						}
						case 3: {
							gameGui.selectionBtn3.setColored(color-1);
							gameGui.selectionBtn3.setUnselected();
							break;
						}
						}

					} else if (inmessage.startsWith("playcheck")) {
						if (client.isCodeMaker()) {
							for (int i = 0; i < game.p2.getCode().size(); i++) {
								gameGui.turnHistory.addToRounds(game.p2.getCode().get(i));

							}
							gameGui.selectionBtn1.setUncolored();
							gameGui.selectionBtn2.setUncolored();
							gameGui.selectionBtn3.setUncolored();
							gameGui.selectionBtn4.setUncolored();
							gameGui.turnHistory.repaint();
							gameGui.numbersPanel.changeRound();
							game.p2.restoreGuessToDefault();
						}
					} else if (inmessage.startsWith("message")) {
						if (transmitter.equals("Server")) {
							chatGui.appendToPane("From " + transmitter + ": " + message + "\n", 3);
						} else if (transmitter.equals("liveServer")) {
							chatGui.appendToPane("From Server: " + message + "\n", 4);
						} else {
							chatGui.appendToPane("From " + transmitter + ": " + message + "\n", 2);
							// "+message);
							// shows message to user
						}
					} else if (inmessage.startsWith("score")) {
						// do the scoreThing and saves progress
					} else if (inmessage.startsWith("fscore")) {
						// Shows the final score to user
					} else if (inmessage.startsWith("hscore")) {
						if (client.isInGame()) {

						}

					}

				} catch (ArrayIndexOutOfBoundsException aioe) {
					System.out.println("Sit on it .!. aioe");
				}
			} // end of while
		} catch (IOException e) {
			System.out.println("Check me...");
		}

	}// end of method run

}
