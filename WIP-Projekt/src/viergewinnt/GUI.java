package viergewinnt;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

import com.sun.java.swing.plaf.windows.resources.windows;

import javax.swing.JTextField;

public class GUI {

	public JFrame frame;
	public JTextField textfeld;
	public static JPanel hauptbild;
	public JScrollPane console;
	public JScrollPane spielerbild;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException{
		
		java.net.Socket sock = null;
	    PrintWriter out = null;
	    Scanner in = null;
	    //Scanner sIn = null;
	    BufferedReader eIn = null;
	    boolean listening = true;    
		JLabel label1 = new JLabel();
		
		label1.setText("Hallo Welt!");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.hauptbild.add(label1);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	    try{	
	    	System.out.println("[Client] Verbinde zu Server....");
	        //sock = new java.net.Socket("192.168.2.119",10000);
	    	sock = new java.net.Socket("localhost",10000);
	    	out = new PrintWriter(sock.getOutputStream(), true);
	        //out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
	        in = new Scanner(new BufferedReader(new InputStreamReader(sock.getInputStream())));
	        //sIn = new Scanner(System.in);
	        eIn = new BufferedReader(new InputStreamReader(System.in));
	        
	        label1.setText("Hallo Welt");

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

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.getContentPane().setEnabled(false);
		frame.getContentPane().setLayout(null);
		
		hauptbild = new JPanel();
		hauptbild.setBounds(0, 0, 348, 376);
		frame.getContentPane().add(hauptbild);
		
		console = new JScrollPane();
		console.setBounds(351, 0, 274, 201);
		frame.getContentPane().add(console);
		
		spielerbild = new JScrollPane();
		spielerbild.setBounds(351, 235, 274, 141);
		frame.getContentPane().add(spielerbild);
		
		textfeld = new JTextField();
		textfeld.setBounds(351, 204, 274, 30);
		frame.getContentPane().add(textfeld);
		textfeld.setColumns(10);
		frame.setBounds(100, 100, 641, 414);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
