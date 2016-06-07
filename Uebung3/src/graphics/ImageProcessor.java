package graphics;

import java.rmi.*;

public interface ImageProcessor extends Remote {
	public SerializableImage convert(SerializableImage source) throws RemoteException;
}
