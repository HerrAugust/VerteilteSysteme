package client;

import graphics.ImageProcessor;
import graphics.SerializableImage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Client implements ActionListener {
	
	private JFrame frame;
	private JLabel path, loadingGIF;
	private String IPServer;
	private int portServer;
	private BufferedImage myimg;
	
	public Client(String[] args) {
		this.IPServer = args[0];
		this.portServer = Integer.parseInt(args[1]);
		this.createGUI();
	}
	
	private void createGUI() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Client");
		frame.setSize(300,300); // default size is 0,0
		frame.setLocation((int)d.getWidth() / 2 - 150, (int)d.getHeight() / 2 - 100); // default is 0,0 (top left corner)
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		frame.getContentPane().setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		
		JLabel lab = new JLabel("Image:");
		JButton imgsrc = new JButton("Choose");
		imgsrc.addActionListener(this);
		imgsrc.setActionCommand("choose picture");
		
		path = new JLabel("Path");
		path.setVisible(false);
		
		JButton send = new JButton("Send to server");
		send.setActionCommand("send");
		send.addActionListener(this);
		
		URL gifurl = Client.class.getResource("./loading.gif");
		Icon icon = new ImageIcon(gifurl);
		loadingGIF = new JLabel(icon);
		
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
				.addComponent(lab)
				.addGroup(groupLayout.createParallelGroup()
						.addComponent(imgsrc)
						.addComponent(path)
						.addComponent(send)
						.addComponent(loadingGIF)));
		
		groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup()
						.addComponent(lab)
						.addComponent(imgsrc))
				.addComponent(path)
				.addComponent(send)
				.addComponent(loadingGIF));
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "choose picture":
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Image to edit");
				fileChooser.setVisible(true);
				int userSelection = fileChooser.showSaveDialog(frame);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File file = fileChooser.getSelectedFile();
				    try {
				    	this.myimg = ImageIO.read(file);
				    }
				    catch(IOException ex) {
				    	System.err.println("Error: you did not select an image?");
				    	this.frame.dispose();
				    }
				    path.setVisible(true);
				    path.setToolTipText(file.getAbsolutePath());
				    path.setText(file.getName());
				}
				break;
			case "send":
				//IMPORTANT PART
				//Communicate image (serialized) to server (through stub) and wait synchronously for result
				ImageProcessor server = null;
				SerializableImage editedImage = null;
				try {
					server = (ImageProcessor) Naming.lookup("rmi://" + this.IPServer + ":" + this.portServer + "/ImageProcessor");
					SerializableImage source = new SerializableImage();
					source.setImage(this.myimg);
					editedImage = server.convert(source);
				}
				catch(RemoteException ex) {
					System.err.println("Error while communicating with server. Aborting");
					this.frame.dispose();
				}
				catch(MalformedURLException ex) {
					System.err.println("URL for Java RMI not correct. Aborting");
					this.frame.dispose();
				}
				catch(NotBoundException ex) {
					System.err.println("Java server not bound. Please try again. Aborting");
					this.frame.dispose();
				}
				//END IMPORTANT PART
				
				//Show it on screen
				new Page2(editedImage);
				break;
			default: break;
		}
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length != 2) {
			System.err.println("Error, expected (IP server, Port server)");
			return;
		}
		
		new Client(args);

	}

}
