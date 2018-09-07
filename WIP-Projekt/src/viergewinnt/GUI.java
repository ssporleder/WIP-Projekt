package viergewinnt;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

import com.sun.java.swing.plaf.windows.resources.windows;

import javax.swing.JTextField;

public class GUI {

	public JFrame frame;
	public JTextField textfeld;
	public JPanel hauptbild;
	public JScrollPane console;
	public JScrollPane spielerbild;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
