//http://javacamp.org/moreclasses/rmi/rmi4.html
package common;

import java.rmi.*;


public interface ImageProcessor extends Remote {
	public SerializableImage convert(SerializableImage source) throws RemoteException;
}
