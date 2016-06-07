//This class would be present either in the server and in the client
package common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class SerializableImage implements Serializable {
	
	/**
	 * This is because of Serializable
	 */
	private static final long serialVersionUID = 1L;
	private byte[] serImg;

	public void setImage(BufferedImage in) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	ImageIO.write(in, "png", baos);
	    	this.serImg = baos.toByteArray();
		}
		catch(IOException ex) {
			System.err.println("Error while serializing image. Perhaps not a PNG?");
		}
		
	}
	
	public BufferedImage getImage() {
		try {
		   return ImageIO.read(new ByteArrayInputStream(this.serImg));
		}
		catch(IOException e) {
			System.err.println("Error while turning image back to BufferedImage");
			return null;
		}
	}

}