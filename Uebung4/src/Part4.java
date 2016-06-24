import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class Part4 implements ActionListener {

	public Part4() {
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
