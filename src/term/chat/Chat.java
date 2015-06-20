package term.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Date;

public class Chat {
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		String mode = askUser("server/client:");
	
		if(mode.equalsIgnoreCase("server")){
			int port = Integer.parseInt(askUser("Port:"));
			new Server(port);
			
			System.out.println("Server started at: "+new Date());
			
		}else if(mode.equalsIgnoreCase("client")){
			String ip = askUser("IP:");
			int port = Integer.parseInt(askUser("Port:"));
			String nick = askUser("Nickname:");
			new Client(ip, port, nick);
		}
		
	}
	
	private static String askUser(String question) throws IOException{
		System.out.print(question);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String intext = "";
		while((intext = reader.readLine()) != null){
			
			return intext;
			
		}
		
		return null;
	}
}
