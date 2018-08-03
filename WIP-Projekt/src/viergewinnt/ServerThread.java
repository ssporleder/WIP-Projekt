package viergewinnt;

import java.net.*;

import spielpaket.*;

import java.io.*;
import java.lang.*;

public class ServerThread extends Thread {
  private Socket socket = null;
  private SpielProtokoll protocol;
  public String name;
  boolean exit = true;
  boolean contact = false;
  PlayerList playlist; 

  
  public  ServerThread(Socket socket, PlayerList playlist) {
    super("ServerThread");
    this.socket = socket;
    this.playlist = playlist;
    
    System.out.println("Connection accepted  "
	+" for " + socket.getInetAddress()
	+ ":"+ socket.getPort());
    	
    protocol = new SpielProtokoll();
  
  }
}