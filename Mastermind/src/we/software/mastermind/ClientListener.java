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
	//private static final java.awt.Color Color.AQUA = null;
	Client client;
	Socket socket;
	MainMenu mainMenu;
	GameGui gameGui;
	ChatGui chatGui;
	
	public ClientListener(Client client, Socket socket, MainMenu mainMenu, ChatGui chatGui) {
		this.client = client;
		this.socket = socket;
		this.mainMenu = mainMenu;
		this.chatGui = chatGui;
	}
	
	public void setChatGui(ChatGui chatGui){
		this.chatGui = chatGui;
	}
	public void setGameGui(GameGui gameGui) {
		this.gameGui = gameGui;
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
						} else if(message.equals("taken")){
							mainMenu.getUsername();
						}
					} else if (inmessage.startsWith("request")) {
						if (message.equals("wannaplay")) {
							if (!client.isInGame()) {
								// asks for user permission to start the game
								// if user accepts then:
								/*client.acceptGameRequest(transmitter);
								client.setEnemy(transmitter);
								client.setInGame(true);*/
								// if he rejects the client.RejectGameRequest();
							} else {
								client.rejectGameRequest(transmitter);
							}
						} else if (message.equals("ok")) {
							// accepted and game starts
						} else if (message.equals("not")) {
							// rejected
						}
					} else if (inmessage.startsWith("play")) {
						// do the playThing and saves the progress
					} else if (inmessage.startsWith("message")) {
						chatGui.appendToPane(transmitter+": "+message+"\n", java.awt.Color.CYAN);
						// "+message);
						// shows message to user

					} else if (inmessage.startsWith("score")) {
						// do the scoreThing and saves progress
					} else if (inmessage.startsWith("fscore")) {
						// Shows the final score to user
					}else if (inmessage.startsWith("hscore")) {
						if(client.isInGame()){
							
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