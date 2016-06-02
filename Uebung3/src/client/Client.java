package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.ImageFilter;
import java.io.File;
import java.net.URL;

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
	
	public Client() {
		this.createGUI();
	}
	
	private void createGUI() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				    path.setVisible(true);
				    path.setToolTipText(file.getAbsolutePath());
				    path.setText(file.getName());
				}
				break;
			default: break;
		}
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Client();

	}

}
