
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient
{
	private BufferedReader userInput;
	private PrintWriter output;
	private BufferedReader chatInput;
	private Socket socket;
	
	
	public ChatClient()
	{
		this.userInput=null;
		this.output=null;
		this.chatInput=null;
		this.socket=null;
		
	}
	public ChatClient(String address,int port)
	{
		try 
		{
			this.socket=new Socket(address,port);
			this.userInput=new BufferedReader(new InputStreamReader(System.in));
			this.chatInput=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.output=new PrintWriter(this.socket.getOutputStream(),true);
		}
		catch(Exception e)
		{
			System.out.println("Error");
		}
	}
	public int send() throws IOException
	{
	
		int sending=0;
		String toBeSent=this.userInput.readLine();
		if(toBeSent.equals("exit"))
			return 1;
		output.println(toBeSent);
		return sending;
		
	}
	public String recieve() throws IOException
	{
		String recieved=this.chatInput.readLine();
		return recieved;
	}
	
	
	public static void main(String []args) throws IOException
	{
		ChatClient client=new ChatClient(args[0],Integer.parseInt(args[1]));
		while(client.send()==0)
		{
			String x=client.recieve();
			System.out.print(x);
		}
	}
}
