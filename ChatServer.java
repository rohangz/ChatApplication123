import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer 
{
	private Socket clientSocket1;
	private Socket clientSocket2;
	private ServerSocket clientServer1;
	private ServerSocket clientServer2;
	private BufferedReader reader1;
	private BufferedReader reader2;
	private PrintWriter printMe1;
	private PrintWriter printMe2;
	private String data;
	private int turn;
	
	
	public ChatServer()
	{
		
	}
	public ChatServer(int port1,int port2)
	{
		try 
		{
			this.turn=0;
			
			this.data=new String("");
			
			this.clientServer1=new ServerSocket(port1);
			this.clientSocket1=this.clientServer1.accept();
			
			this.clientServer2=new ServerSocket(port2);
			this.clientSocket2=this.clientServer2.accept();
			
			this.reader1=new BufferedReader(new InputStreamReader(this.clientSocket1.getInputStream()));
			this.reader2=new BufferedReader(new InputStreamReader(this.clientSocket2.getInputStream()));
			
			this.printMe1=new PrintWriter(this.clientSocket1.getOutputStream(),true);
			this.printMe2=new PrintWriter(this.clientSocket2.getOutputStream(),true);
			
			
			
			
		} 
		catch (Exception e)
		{

		}
		
		
	}
	public void recieve() throws IOException
	{
		String getLine=new String("");
		if(this.turn==0)
		{
			getLine=this.reader1.readLine();
			this.printMe2.println(getLine);
			this.turn=1;
		}
		else
		{
			getLine=this.reader2.readLine();
			this.printMe1.println(getLine);
			this.turn=0;
		}
	}
	
	public String getReaderInput1() throws IOException
	{
		String getLine=this.reader1.readLine();
		return getLine;
	}
	public String getReaderInput2() throws IOException
	{
		String getLine=this.reader2.readLine();
		return getLine;
	}
	public void outputData1(String data)
	{
		this.printMe1.println(data);
	}
	public void outputData2(String data)
	{
		this.printMe2.println(data);
	}
	public static void main(String []args) throws IOException
	{
		ChatServer cs=new ChatServer(5000,4000);
		Thread t1=new Thread(()-> 
		{
			while(true)
			{
				try 
				{
					String data=cs.getReaderInput1();
					cs.outputData2(data);
				} 
				catch (Exception e)
				{
				
				}	
			}
		});
		Thread t2=new Thread(()->
		{
			while(true)
			{
				try 
				{
					String data=cs.getReaderInput2();
					cs.outputData1(data);
				}
				catch (Exception e) 
				{
				
				}
				
			}
		});
		t1.start();
		t2.start();
	}
}