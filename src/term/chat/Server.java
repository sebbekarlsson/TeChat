package term.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	InputStream in;
	OutputStream out;
	ServerSocket socket;
	public ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>();

	public Server(int port) throws IOException{
		socket = new ServerSocket(port);
		Server server = this;

		new Thread(new Runnable(){
			public void run(){
				try {
					while(true){
						Socket s = socket.accept();
						ConnectedClient client = new ConnectedClient(s, server);
						clients.add(client);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void broadcast(String msg) throws IOException{
		for(int i = 0; i < clients.size(); i++){
			ConnectedClient client = clients.get(i);
			PrintWriter writer = new PrintWriter(client.socket.getOutputStream(), true);
			writer.println(msg);
		}
	}
}
