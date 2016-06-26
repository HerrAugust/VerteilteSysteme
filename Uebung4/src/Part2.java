import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;


public class Part2 implements ActionListener {

	private JTextField tf;
	private JLabel label;
	
	public Part2() {
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
		
		tf = new JTextField(30);
		JButton p1 = new JButton("Obtain IP address");
		p1.setActionCommand(p1.getText());
		p1.addActionListener(this);
		label = new JLabel();
		
		cp.add(tf);
		cp.add(p1);
		cp.add(label);
		
		f.pack();
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Only button click is possible
		this.label.setText("");
		String website = this.tf.getText();
		String result = "";
		String completeURL;
		try {
			completeURL = "http://dig.jsondns.org/IN/" + URLEncoder.encode(website, "UTF-8") + "/A";
		
			Scanner sc = new Scanner(new URL(completeURL).openStream());
			while(sc.hasNextLine()) {
				result += sc.nextLine();
			}
			System.out.println(result);
			sc.close();
		}
		catch (UnsupportedEncodingException e2) {
			System.err.println(e2.getMessage());
			return;
		}
		catch (IOException e1) {
			System.err.println("Errors while get input, aborting...");
			System.err.println(e1.getMessage());
			return;
		}
		
		JSONObject jsonobj = new JSONObject(result);
		JSONArray addresses = jsonobj.getJSONArray("answer");
		for(int i = 0; i < addresses.length(); i++) {
			result = addresses.getJSONObject(i).getString("rdata");
			System.out.println(website + " IP Address: " + result);
			this.label.setText(label.getText() + " " + System.lineSeparator() + result);
		}
		
	}

}
