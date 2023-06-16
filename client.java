package chatpackage;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class client implements ActionListener{
	
	static JFrame f =new JFrame("CHATING APPLICATION");
	static JPanel a1;
	JTextField text;
	static Box vertical = Box.createVerticalBox();
	static DataOutputStream dout;
	
	client(){
		
		f.setLayout(null);
		
		//nav
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(10,94,94));
		p1.setBounds(0,0,600,70);   // x,y,wid,height
		f.add(p1);
		
		JLabel name = new JLabel("CLIENT");
		name.setBounds(100,10,100,50);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("SAN_SERIF",Font.BOLD , 22));
		p1.add(name);
		
		a1 =new JPanel();
		a1.setBounds(5,75,580,400);
		f.add(a1);
		
		text = new JTextField();
		text.setBounds(5,410,450,40);
		text.setFont(new Font("SANS_SERIF", Font.PLAIN ,16));
		f.add(text);
		
		JButton send = new JButton("SEND");
		send.setBounds(460,410,110,40);
		send.setBackground(Color.BLACK);
		send.setForeground(Color.WHITE);
		send.setFont(new Font("SANS_SERIF", Font.PLAIN ,16));
		send.addActionListener(this); 
		f.add(send);
		
		
		
		f.setSize(600,500);     //w,h
		f.setLocation(500,300);   //side,up
		f.getContentPane().setBackground(Color.WHITE);
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		try {
			String out = text.getText();
			
			JPanel p2 = formatLabel(out);
			
			a1.setLayout(new BorderLayout());
			
			JPanel right = new JPanel(new BorderLayout());
			right.add(p2 ,BorderLayout.LINE_END);
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(15));
			
			a1.add(vertical,BorderLayout.PAGE_START);
			
			dout.writeUTF(out);
			
			text.setText("");
			
			f.repaint();
			f.validate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static JPanel formatLabel(String out) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output = new JLabel(out);
		output.setFont(new Font("SANS_SERIF", Font.PLAIN ,22));
		output.setBackground(Color.GRAY);
		output.setForeground(Color.WHITE);
		output.setOpaque(true);
		
		panel.add(output);
		
		return panel;

	}
	
	
	public static void main(String[] args) {
		
		new client();
		try {
			    Socket s = new Socket("127.0.0.1",4525);
			
				DataInputStream din = new DataInputStream(s.getInputStream());
				dout =new DataOutputStream(s.getOutputStream());
				
				while(true) {
					a1.setLayout(new BorderLayout());
					String msg = din.readUTF();
					JPanel panel = formatLabel(msg);
					
					JPanel left = new JPanel(new BorderLayout());
					left.add(panel, BorderLayout.LINE_START);
					vertical.add(left);
					
					vertical.add(Box.createVerticalStrut(15));
					a1.add(vertical,BorderLayout.PAGE_START);
					f.validate();
				}	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}

