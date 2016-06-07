package server;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.ImageProcessor;
import common.SerializableImage;

public class ImageProcessorImpl extends UnicastRemoteObject implements ImageProcessor {

	private static final long serialVersionUID = 2972039445284885112L;

	public ImageProcessorImpl() throws RemoteException {
		super();
	}

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
