package server;

import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.AccessControlException;

import common.ImageProcessor;


public class Server {

	static ImageProcessor proc;
	
	public Server(String[] args) throws RemoteException {
		System.out.println("Server PID: " + ManagementFactory.getRuntimeMXBean().getName());
		if(args.length != 1) {
			System.err.println("Server: error, port required as argument");
			System.exit(1);
		}
		int port = 0;
		try {
			port = Integer.parseInt(args[0]);
			if(port < 10000 || port > 20000) {
				System.err.println("Server: error, port required as argument in range 10000-20000");
				System.exit(1);
			}
		}
		catch(NumberFormatException e) 
		{
			System.err.println("Server: error while formatting port");
			System.exit(1);
		}
		
        try {
        	if(System.getSecurityManager() == null)
        		System.setSecurityManager(new RMISecurityManager());
        	proc = new ImageProcessorImpl();
        	Naming.rebind("rmi://localhost:" + port + "/ImageProcessor", proc); // When client will ask for ImageProcessor, the server will know this way to whom to redirect the call (ie, returns the stub to client) 
		} catch (RemoteException e) {
			System.err.println("Errors while setting up server");
			e.printStackTrace();
			System.exit(1);
		}
        catch(AccessControlException ex){
        	System.err.println("Error about security. You did not indicate a valid permissions file as argument!");
        	return;
        } catch (MalformedURLException e) {
        	System.err.println("Malformed URL");
			e.printStackTrace();
			return;
		}
        
        System.out.println("Server ready on port: " + port);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new Server(args);
		}
		catch(RemoteException ex) {
			System.err.println("Remote exception");
			return;
		}
	}

}
