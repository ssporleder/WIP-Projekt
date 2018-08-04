

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
        sock = new java.net.Socket("localhost",10000);
        out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
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
    	//
    	if(in.hasNextLine()) {
    		System.out.println(in.nextLine());
    	} 
    	
    	//System.out.println("[Client] Sende Nachricht....");
    	temp = eIn.readLine();
    	//System.out.println(temp);
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