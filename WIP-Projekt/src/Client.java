

import java.io.*;
import java.util.*;

public class Client {


public static void main(String[] args)
{
	java.net.Socket sock = null;
    PrintStream out = null;
    Scanner in = null;
    Scanner sIn = null;
    Scanner eIn = null;
    boolean listening = true;

    try{
        sock = new java.net.Socket("localhost",10000);
        out = new PrintStream(sock.getOutputStream());
        in = new Scanner(sock.getInputStream());
        sIn = new Scanner(System.in);
        eIn = new Scanner(System.in);
        
    }catch(Exception e){
        System.out.println("Error connecting to server.");
        System.exit(1);
    }

    System.out.println("Connection successful.");

    String temp = "";

    //while((temp = sIn.nextLine()) != null)
    while(listening)
    {
    	
    	//System.out.println(out.println(temp));
    	
    
    	//out.println(temp);
    	//
    	if(in.hasNextLine())System.out.println(in.nextLine());
    	//while(in.hasNextLine())
    			//{
    			//System.out.println(in.nextLine());
    			//};
    			//while(in.hasNextLine())System.out.println(in.nextLine());
    			out.flush();
    			String eingabe = eIn.next();
    			out.println(eingabe);
    			out.flush();

    			
    	
    	
        //out.flush();
        if(temp.equals("end")) break;
    }

    try{
        sock.close();
        in.close();
        out.close();
    }catch(IOException ioe){}

}

}