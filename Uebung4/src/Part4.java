import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;


public class Part4 implements ActionListener {

	private JLabel png;
	private JFrame f;
	private JButton p1;
	
	public Part4() {
		this.generateGUI();
	}
	
	private void generateGUI() {
		f = new JFrame("Uebung 4");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setMinimumSize(new Dimension(700, 700));
		f.setLocation(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 350, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 350);
		
		Container cp = f.getContentPane();
		FlowLayout groupLayout = new FlowLayout(FlowLayout.CENTER);
		cp.setLayout(groupLayout);
		
		this.png = new JLabel("Please use console");
		p1 = new JButton("OK");
		p1.addActionListener(this);
		
		cp.add(this.png);
		cp.add(p1);
		
		f.pack();
		f.setVisible(true);
	}
	
	private void start() {
		System.out.println("Welcome");
		System.out.println("Please choose one of the following options:");
		System.out.println("1. Input: Latitude & Longitude");
		System.out.println("2. Input: Website");
		System.out.println("3. IP address");
		System.out.println("[1,2,3]: ");
		
		Scanner sc = new Scanner(System.in);
		String res = "";
		double latitude = 0.0, longitude = 0.0;
		if(sc.hasNextLine())
			res = sc.nextLine();
		switch(res) {
		case "1":
			System.out.println("");
			System.out.println("Please enter Latitude and Longitude: ");
			
			if(sc.hasNextDouble())
				latitude = sc.nextDouble();
			if(sc.hasNextDouble())
				longitude = sc.nextDouble();
			break;
		case "2":
			System.out.println("");
			System.out.println("Please enter a website: ");
			
			String website = "";
			if(sc.hasNextLine())
				website = sc.nextLine();
			sc.close();
			
			String ip_1 = this.DNS(website);
			if(ip_1 == null) {
				return;
			}
			
			double[] ll_1 = null;
			try {
				ll_1 = this.FreeGeoIP(ip_1);
			} catch (IOException e1) {
				System.err.println("Error retriving the latitude and longitude from FreeGeoIP, aborting...");
				e1.printStackTrace();
				return;
			}
			latitude = ll_1[0];
			longitude = ll_1[1];
			break;
		case "3":
			System.out.println("");
			System.out.println("Please enter an IP: ");
			
			String ip_2 = "";
			if(sc.hasNextLine())
				ip_2 = sc.nextLine();
			sc.close();
			try {
				//get lat & long
				double[] ll_2 = this.FreeGeoIP(ip_2);
				latitude = ll_2[0];
				longitude = ll_2[1];
			} catch (IOException e) {
				System.err.println("Errors while retriving latitude and longitude. Aborting...");
				e.printStackTrace();
				return;
			}
			break;
		default:
			System.out.println("Not a correct input. Please restart");
			sc.close();
			return;
		}
		
		//Now I have latitude and longitude, than I can use OpenStreetMap
		this.OpenStreetMap(latitude, longitude);
		sc.close();
		
	}
	
	private double[] FreeGeoIP(String ip) throws MalformedURLException, UnsupportedEncodingException, IOException {
		Scanner sc = new Scanner(new URL(" http://freegeoip.net/json/" + URLEncoder.encode(ip, "UTF-8")).openStream());
		String rsp = "";
		while(sc.hasNextLine()) {
			rsp += sc.nextLine();
		}
		sc.close();
		
		JSONObject obj = new JSONObject(rsp);
		double latitude  = obj.getDouble("latitude");
		double longitude = obj.getDouble("longitude");
		
		System.out.println("FreeGeoIP, got latitude and longitude: (" + latitude + "," + longitude + ")");
		return new double[] { latitude, longitude };
	}
	
	private String DNS(String website) {
		if(website.contains("/")) {
			System.out.println("For dig.jsondns.org, website must not contain /. Aborting...");
			return null;
		}
		
		String completeURL, result = "";
		try {
			completeURL = "http://dig.jsondns.org/IN/" + URLEncoder.encode(website, "UTF-8") + "/A";
		
			Scanner sc = new Scanner(new URL(completeURL).openStream());
			while(sc.hasNextLine()) {
				result += sc.nextLine();
			}
			sc.close();
		}
		catch (UnsupportedEncodingException e2) {
			System.err.println(e2.getMessage());
			return null;
		}
		catch (IOException e1) {
			System.err.println("Errors while get input, aborting...");
			System.err.println(e1.getMessage());
			return null;
		}
		
		JSONObject jsonobj = new JSONObject(result);
		JSONArray addresses = jsonobj.getJSONArray("answer");
		result = addresses.getJSONObject(0).getString("rdata");
		System.out.println("dig.jsondns.org, got IP: " + result);
		return result;
	}
	
	private void OpenStreetMap(double latitude, double longitude) {
		String completeSite = "http://staticmap.openstreetmap.de/staticmap.php?center=" + latitude + "," + longitude + "&zoom=13&size=512x512&maptype=osmarenderer";
		JFrame f = new JFrame("Map for " + latitude + ", " + longitude);
		f.setSize(400, 400);
		
		System.out.println("Processing request. Address: " + completeSite);
		
		try {
			this.png.setIcon(new ImageIcon(new URL(completeSite)));
			f.repaint();
			System.out.print("Process finished. Look at GUI");
		} catch (MalformedURLException e) {
			System.err.println("Cannot download map, aborting...");
			this.png.setText("Cannot download map, please retry");
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//OK button clicked
		p1.setVisible(false);
		this.png.setText("");
		this.start();
	}
	
}
