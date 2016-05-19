package Serveur;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String args[])
    {
        
        try {
			LocateRegistry.createRegistry(2005);
			ServerObj serveur= new ServerObj(); 
			Naming.rebind("//:2005/transformeur",serveur);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
