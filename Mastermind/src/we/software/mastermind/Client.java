package we.software.mastermind;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import we.software.gui.ChatGui;
import we.software.gui.GameGui;
import we.software.gui.MainMenu;

public class Client extends Player{
	public String username;
	public Player enemy;
	public ClientListener cListener;
	private Socket socket;
	public boolean inGame;
	private String server = "83.212.99.117";
	private int PORT = 12498;
	public boolean codeMaker;
	static int rounds;
	ChatGui chatGui;
	
	public Client() {
		super();
		enemy = null;
		username = "";
		inGame = false;
	}
	

	public boolean isCodeMaker() {
		return codeMaker;
	}


	public void setCodeMaker(boolean codeMaker) {
		this.codeMaker = codeMaker;
	}


	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public void setEnemy(Player enemy) {
		this.enemy = enemy;
	}
	
	public Player getEnemy() {
		return enemy;
	}
	//Starts the client ServerThread 
	public void startListening(ChatGui chatGui) throws UnknownHostException, IOException {
		socket = new Socket(server, PORT);
		cListener = new ClientListener(this, socket, chatGui);
		cListener.start();
	}

	// add a new player with username and password
	public void addMe(String name, String password) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("add:" + name + ": %" + password);
		bw.newLine();
		bw.flush();

	}
	
	// check credentials of user in order to log in
	public void logMeIn(String name, String password) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("login:" + name + ": %" + password);
		bw.newLine();
		bw.flush();

	}

	// send a game request
	public void sendGameRequest(String someone) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("request:" + username + ":" + someone + "%wannaplay");
		bw.newLine();
		bw.flush();
	}

	// accepts a game request
	public void acceptGameRequest(String someone) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("request:" + username + ":" + someone + "%ok");
		bw.newLine();
		bw.flush();
		// game starts
	}

	// rejects a game request
	public void rejectGameRequest(String someone) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("request:" + username + ":" + someone + "%not");
		bw.newLine();
		bw.flush();
	}

	// it will change soon
	public void sendGamePin(int position,int color) throws IOException {
		super.getCode().set(position, color);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("play:" + username + ":" + enemy.getName() + "%" + position+" "+color);
		bw.newLine();
		bw.flush();
	}

	
	// it will change soon
	public void sendGameRoundScore() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("score:" + username + ":" + enemy.getName() + "%" + "");
		bw.newLine();
		bw.flush();
	}

	// it will change soon
	public void sendFinalScore() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("fscore:" + username + ":" + enemy.getName() + "%" + "");
		bw.newLine();
		bw.flush();
		
	}
	
	public void sendHighScore(int highscore) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("hscore:" + username + ":server%" + highscore);
		bw.newLine();
		bw.flush();
	}

	// sends this message when the game closes to inform server
	public void sendCloseMessage() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("close:" + username + ":" + "server" + "%close");
		bw.newLine();
		bw.flush();
		
	}

	// sends a chat message
	public void sendMessage(String s) throws IOException{
		if(s.contains(":")){
		String[] t = s.split(":");
		String someone = t[0];
		String mes = t[1];
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bw.write("message:" + username + ":" + someone + "%" + mes);
			bw.newLine();
			bw.flush();
		
		
		//return true;
		}
		else{
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bw.write("allmessage:" + username + ": %" + s);
			bw.newLine();
			bw.flush();
			//return false;
			
		}
		
		
		}
	

}
