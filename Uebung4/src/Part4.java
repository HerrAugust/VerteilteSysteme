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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;


public class Part4 {

	public Part4() {
		this.generateGUI();
		this.start();
	}
	
	private void generateGUI() {
		JFrame f = new JFrame("Uebung 4");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setMinimumSize(new Dimension(200, 200));
		f.setLocation( java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 200, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 100);
		
		Container cp = f.getContentPane();
		FlowLayout groupLayout = new FlowLayout(FlowLayout.CENTER);
		cp.setLayout(groupLayout);
		
		JLabel label = new JLabel("Please use console");
		
		cp.add(label);
		
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
				System.err.println("Errors while processing website. Cannot get IP. Aborting...");
				return;
			}
			
			double[] ll_1 = null;
			try {
				ll_1 = this.FreeGeoIP(ip_1);
			} catch (IOException e1) {
				e1.printStackTrace();
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
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("Not a correct input. Restarting...");
			sc.close();
			this.start();
			break;
		}
		
		//Now I have latitude and longitude, than I can use OpenStreetMap
		this.OpenStreetMap(latitude, longitude);
		sc.close();
	}
	
	private double[] FreeGeoIP(String ip) throws MalformedURLException, UnsupportedEncodingException, IOException {
		Scanner sc = new Scanner(new URL(" https://freegeoip.net/json/" + URLEncoder.encode(ip, "UTF-8")).openStream());
		String rsp = "";
		while(sc.hasNextLine()) {
			rsp += sc.nextLine();
		}
		sc.close();
		
		JSONObject obj = new JSONObject(rsp);
		double latitude  = obj.getDouble("latitude");
		double longitude = obj.getDouble("longitude");
		
		return new double[] { latitude, longitude };
	}
	
	private String DNS(String website) {
		String completeURL, result = "";
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
		return result;
	}
	
	private void OpenStreetMap(double latitude, double longitude) {
		String completeSite = "http://staticmap.openstreetmap.de/staticmap.php?center=" + latitude + "," + longitude + "&zoom=13&size=512x512&maptype=osmarenderer";
		
	}
	
}
