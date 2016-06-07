package server;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

import graphics.ImageProcessor;
import graphics.SerializableImage;

public class ImageProcessorImpl implements ImageProcessor {

	@Override
	public SerializableImage convert(SerializableImage source) throws RemoteException {
		BufferedImage img = source.getImage();
		BufferedImage newimg = source.getImage();
		for(int x = 0; x < img.getWidth(); x++) {
			for(int y = 0; y < img.getHeight(); y++) {
				Color oldColor = new Color(img.getRGB(x, y));
				float red = 2*oldColor.getRed();
				float green = 2*oldColor.getGreen();
				float blue = (float) 0.5*oldColor.getBlue();
				Color newColor = new Color(red, green, blue);
				img.setRGB(x, y, newColor.getRGB());
			}
		}
		
		SerializableImage i = new SerializableImage();
		i.setImage(newimg);
		return i;
	}

	
}
