import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.JSONObject;


public class Part3 implements ActionListener {

	private JTextField txt_ip;
	private JLabel label;
	
	public Part3() {
		this.generateGUI();
	}
	
	private void generateGUI() {
		JFrame f = new JFrame("Uebung 4");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setMinimumSize(new Dimension(400, 200));
		f.setLocation( java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 200, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 100);
		
		Container cp = f.getContentPane();
		FlowLayout groupLayout = new FlowLayout(FlowLayout.CENTER);
		cp.setLayout(groupLayout);
		
		txt_ip = new JTextField(30);
		JButton p1 = new JButton("Extract lat&long");
		p1.setActionCommand(p1.getText());
		p1.addActionListener(this);
		label = new JLabel();
		
		cp.add(txt_ip);
		cp.add(p1);
		cp.add(label);
		
		f.pack();
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// There is only one button...
		this.label.setText("");
		try {
			String ip = this.txt_ip.getText();
			String completeSite = "http://freegeoip.net/json/" + URLEncoder.encode(ip, "UTF-8");
			String result = "";
			
			Scanner sc = new Scanner(new URL(completeSite).openStream());
			while(sc.hasNextLine()) {
				result += sc.nextLine();
			}
			sc.close();
			
			JSONObject obj = new JSONObject(result);
			this.label.setText("Latitude: " + obj.getDouble("latitude") + " Longitude: " + obj.getDouble("longitude"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
