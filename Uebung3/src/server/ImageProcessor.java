package server;

import graphics.*;
import java.rmi.*;

public interface ImageProcessor extends Remote {
	public int convert(SerializableImage source, Alteration typeOfEdit) throws RemoteException;
}
