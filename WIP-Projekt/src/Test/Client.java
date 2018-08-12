package Test;


import java.io.*;
import java.util.*;

public class Client {

public static void main(String[] args) throws IOException
{
	java.net.Socket sock = null;
    PrintWriter out = null;
    Scanner in = null;
    //Scanner sIn = null;
    BufferedReader eIn = null;
    boolean listening = true;

    try{
    	System.out.println("[Client] Verbinde zu Server....");
        sock = new java.net.Socket("192.168.2.119",10000);
        out = new PrintWriter(sock.getOutputStream(), true);
        //out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
        in = new Scanner(new BufferedReader(new InputStreamReader(sock.getInputStream())));
        //sIn = new Scanner(System.in);
        eIn = new BufferedReader(new InputStreamReader(System.in));
        
    }catch(Exception e){
        System.out.println("[Client] Error connecting to server.");
        System.exit(1);
    }

    System.out.println("[Client] Verbunden.");

    String temp = "";
    
    //while((temp = sIn.nextLine()) != null)
    while(listening)
    {
    	
    	//System.out.println(out.println(temp));
    	
    
    	//out.println(temp);
    	String line = "";
    	while(!line.equals("#")){
    		
    		line = in.nextLine();
    		if(!line.equals("#")){
    		System.out.println(line);    	
    		}
    		
    	} 
    	
    	    	
    	//System.out.println("[Client] Sende Nachricht....");
    	temp = eIn.readLine();
    	System.out.println(temp);
    	out.println(temp);
    	out.flush();

        if(temp.equals("end")) break;
    }

    try{
        sock.close();
        in.close();
        out.close();
        eIn.close();
    }catch(IOException ioe){}

}

}