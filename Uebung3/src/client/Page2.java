package client;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import graphics.SerializableImage;

public class Page2 extends JFrame {
	
	private static final long serialVersionUID = 8006298895302238219L;

	public Page2(SerializableImage s) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Modified image");
		JLabel lab = new JLabel();
		lab.setIcon(new ImageIcon(s.getImage()));
		this.getContentPane().add(lab);
		this.setVisible(true);
	}
	
}
