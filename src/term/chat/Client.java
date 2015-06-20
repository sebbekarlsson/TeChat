package term.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	String nick;
	
	public Client(String ip, int port, String nick) throws UnknownHostException, IOException{
		socket = new Socket(ip, port);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.nick = nick;
		
		sendToServer("#nick"+nick);
		
		new Thread(new Runnable(){
			public void run(){
				String intext = "";
				try {
					while((intext = reader.readLine()) != null){
						System.out.println(intext);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		new Thread(new Runnable(){
			public void run(){
				String intext = "";
				PrintWriter w = null;
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				try {
					 w = new PrintWriter(socket.getOutputStream(), true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					while((intext = reader.readLine()) != null){
						w.println("#msg"+nick+": "+intext);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	
	public void sendToServer(String msg) throws IOException{
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
		writer.println(msg);
	}
}
