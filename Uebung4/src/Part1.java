import java.awt.Container;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.json.*;


public class Part1 {
	private JLabel jl;
	
	public Part1()  {
		this.generateGUI();
		try {
			this.getPublicIP();
		}
		catch(IOException e) {
			System.err.println("Error while retriving public IP");
			e.printStackTrace();
		}
	}
	
	private void getPublicIP() throws IOException {
		String url = "https://api.ipify.org?format=json";
		URL ipifi = new URL(url);
		
		Scanner s = new Scanner(ipifi.openStream());
		String result = "";
		while (s.hasNext())
	        result += s.nextLine();
		s.close();
		
		if(result.equals("")) {
			System.err.println("Error while retriving data from URL. Aborting...");
			return; 
		}
		
		JSONObject j = new JSONObject(result);
		String ip = j.getString("ip");
		this.jl.setText(ip);
	}

	private void generateGUI() {
		JFrame f = new JFrame("Uebung 4");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setSize(400, 200);
		f.setLocation( java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 200, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 100);
		
		Container cp = f.getContentPane();
		FlowLayout groupLayout = new FlowLayout(FlowLayout.CENTER);
		cp.setLayout(groupLayout);
		
		this.jl = new JLabel("");
		
		cp.add(jl);
		
		f.pack();
		f.setVisible(true);
	}
	
	

}
