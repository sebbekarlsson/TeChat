package term.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectedClient {
	public Socket socket;
	public Server server;
	public String nick;
	
	public ConnectedClient(Socket socket, Server server){
		this.socket = socket;
		this.server = server;
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String intext = "";
					while((intext = reader.readLine())!= null){
						System.out.println("From client: "+intext);
						if(intext.startsWith("#nick")){
							String msg = intext.split("#nick")[1];
							nick = msg;
							server.broadcast(nick+" joined.");
						}
						else if(intext.startsWith("#msg")){
							String msg = intext.split("#msg")[1];
							server.broadcast(msg);
						}
					}
					server.broadcast(nick+" left.");
					server.clients.remove(this);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
		}).start();
	}
	
	public void sendMessage(String msg) throws IOException{
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
		writer.println(msg);
	}
	
	
	
}
