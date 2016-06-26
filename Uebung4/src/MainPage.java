import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;


public class MainPage implements ActionListener {

	public MainPage() {
		//Without the following you get most of the times a HTTP 403 error code from dig.json.org. So I set it here for good
		System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 10.0; rv:47.0) Gecko/20100101 Firefox/47.0"); 
		this.generateGUI();
	}
	
	private void generateGUI() {
		JFrame f = new JFrame("Uebung 4");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setSize(400, 200);
		f.setLocation( java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 200, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 100);
		
		Container cp = f.getContentPane();
		FlowLayout groupLayout = new FlowLayout(FlowLayout.CENTER);
		cp.setLayout(groupLayout);
		
		JButton p1 = new JButton("Part 1");
		p1.setActionCommand(p1.getText());
		p1.addActionListener(this);
		JButton p2 = new JButton("Part 2");
		p2.setActionCommand(p2.getText());
		p2.addActionListener(this);
		JButton p3 = new JButton("Part 3");
		p3.setActionCommand(p3.getText());
		p3.addActionListener(this);
		JButton p4 = new JButton("Part 4");
		p4.setActionCommand(p4.getText());
		p4.addActionListener(this);
		
		cp.add(p1);
		cp.add(p2);
		cp.add(p3);
		cp.add(p4);
		
		f.pack();
		f.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "Part 1":
			new Part1();
			break;
		case "Part 2":
			new Part2();
			break;
		case "Part 3":
			new Part3();
			break;
		case "Part 4":
			new Part4();
			break;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainPage();
	}

}
