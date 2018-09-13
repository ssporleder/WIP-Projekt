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
import javax.swing.JTextPane;

public class GUI {
	
	public static JPanel hauptbild;
	public static JFrame frame;
	public static JTextField textfeld;
	public static JScrollPane console;
	public static JScrollPane spielerbild;

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
		GUI window = new GUI();
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	    try{	
	    	label1.setText("[Client] Verbinde zu Server....");
	    	setJPanel(label1);
	        //sock = new java.net.Socket("192.168.2.119",10000);
	    	sock = new java.net.Socket("localhost",10000);
	    	out = new PrintWriter(sock.getOutputStream(), true);
	        //out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
	        in = new Scanner(new BufferedReader(new InputStreamReader(sock.getInputStream())));
	        //sIn = new Scanner(System.in);
	        eIn = new BufferedReader(new InputStreamReader(System.in));
	        
	        //label1.setText("Hallo Welt!!!");

	    }catch(Exception e){
	        label1.setText("[Client] Error connecting to server.");
	        setJPanel(label1);
	        System.exit(1);
	    }
	    label1.setText("[Client] Verbunden.");
	    setJPanel(label1);

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
	    			label1.setText(line);
	    			setJPanel(label1);
	    		//System.out.println(line);    	
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
		
		frame = new JFrame("Viergewinnt");
		frame.getContentPane().setEnabled(false);
		frame.getContentPane().setLayout(null);
		
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
		
		hauptbild = new JPanel();
		hauptbild.setBounds(0, 0, 351, 376);
		frame.getContentPane().add(hauptbild);
		frame.setBounds(100, 100, 641, 414);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
		private static void setJPanel(JLabel label){
			hauptbild.add(label);
			hauptbild.setVisible(true);
		}
	}


