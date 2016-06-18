package server;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.ImageProcessor;
import common.SerializableImage;

// Note: if ImageProcessorImp extended UnicastRemoteObject, the following would arise http://www.coderanch.com/t/210349/java/java/object-exported-exception-RMI
public class ImageProcessorImpl extends UnicastRemoteObject implements ImageProcessor, Serializable {
	
	/**
	 * Because of Serializable
	 */
	private static final long serialVersionUID = 6203748007440698415L;

	public ImageProcessorImpl() throws RemoteException {
		super();
	}

	@Override
	public SerializableImage convert(SerializableImage source) throws RemoteException {
		BufferedImage img = source.getImage();
		BufferedImage newimg = new BufferedImage(source.getImage().getWidth(), source.getImage().getHeight(), BufferedImage.TYPE_INT_ARGB);
		System.out.println(img.getWidth());
		for(int x = 0; x < img.getWidth(); x++) {
			for(int y = 0; y < img.getHeight(); y++) {
				Color oldColor = new Color(img.getRGB(x, y));
				int red = 2 * oldColor.getRed();
				if(red > 255)
					red = 255;
				int green = 2 * oldColor.getGreen();
				if(green > 255)
					green = 255;
				int blue = (int) 0.5 * oldColor.getBlue();
				if(blue > 255)
					blue = 255;
				try {
					Color newColor = new Color(red, green, blue);
					newimg.setRGB(x, y, newColor.getRGB());
				}
				catch(IllegalArgumentException ex) {
					// http://stackoverflow.com/questions/16497390/illegalargumentexception-color-parameter-outside-of-expected-range-red-green-b
					System.out.println("Could not modify this image because colors go out of range. x = " + x + "; y = " + y);
					ex.printStackTrace();
					return source;
				}
			}
		}
		
		SerializableImage i = new SerializableImage();
		i.setImage(newimg);
		return i;
	}

	
}
