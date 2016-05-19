/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Serveur;



import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;

/**
 *
 * @author John
 */
public class ServerObj extends UnicastRemoteObject implements IServer
{
	static Registry reg = null;
	public ServerObj() throws RemoteException {
	}

	Database d = new Database();
	ArrayList<User> Users = d.getUserlist();

	List<IClient> clients = new ArrayList<IClient>();
	List<String> clientsOnline = new ArrayList<String>();

	public boolean RegisterToServer(IClient client, String Name, char[] password) throws RemoteException
	{

		boolean resultToReturn = false;
		try
		{
			if(clientsOnline.indexOf(Name)>-1)
			{
				// utilisateur déjà en ligne 
				System.out.println("deja connecte");
				client.ServerNotification("User Already Online","");
			}
			else
			{
				//On regarde si l'utilisateur exsite déjà, si ce n'est pas le cas il est créé
				if(d.addUser(Name, new String(password))){
					//Si il existe on vérifie que le mot de passe est le bon
					if(d.isIdentifierCorrect(Name,new String(password))){

					}
					else{
						try{
							System.out.println("Mauvais mdp");
							client.ServerNotification("Wrong Password","");
							return false;
						}
						catch(Exception e)
						{

						}
					}
				}
				//L'utilisateur peut être rajouté aux personnes en ligne
				client.ServerNotification("OK","");
				System.out.print(Name);
				System.out.println(" identification ok");
				clients.add(client); 
				clientsOnline.add(Name); 
				resultToReturn = true;
				for(int i=0; i < clients.size(); i++)
				{
					try
					{
						int id=d.getUserid(clientsOnline.get(i));
						ArrayList<Contact> contacts=d.getContactsOfUser(id);
						List<String> ContactsOnline= new ArrayList<String>();



						if(contacts!=null){

							ContactsOnline.clear();
							for(Contact c : contacts){
								String Contactname=c.getName();
								if(clientsOnline.indexOf(Contactname)>-1)
									ContactsOnline.add(Contactname);

							}

						}
						clients.get(i).UpdateUserList(ContactsOnline);


					}
					catch(Exception e)
					{
						e.printStackTrace();
						clients.remove(i);
						clientsOnline.remove(i);
					}
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}        

		return resultToReturn;
	}

	@Override
	public void addContact(String UserName, String ContactName) throws RemoteException{

		try{
			int i = clientsOnline.indexOf(UserName);
			int id=d.getUserid(clientsOnline.get(i));
			User u=d.getUser(UserName);		
			  
			if(UserName==ContactName){
				clients.get(i).addContactResult(4);
			}
			else if(u.getContact(ContactName)!=null){

				clients.get(i).addContactResult(2);

			}
			else if(d.getUser(ContactName)!=null){

				try{
					d.addContact(id, ContactName);
				}
				catch(Exception e){
					e.printStackTrace();
				}

				refreshContactList();
				clients.get(i).addContactResult(0);

			}
			else{
				clients.get(i).addContactResult(1);
			}
		}
		catch(RemoteException e){
			e.printStackTrace();
		}


	}

	@Override
	public void removeContact(String UserName, String ContactName) throws RemoteException{
		try{
			int i =clientsOnline.indexOf(UserName);
			int id=d.getUserid(clientsOnline.get(i))-1;

			d.removeContact(id, ContactName);		
			refreshContactList();		
			clients.get(i).ServerNotification("Contact Removed",ContactName);

		}
		catch(RemoteException e){
			e.printStackTrace();
			int i =clientsOnline.indexOf(UserName);
			clients.get(i).ServerNotification("Contact Removing fail",ContactName);
		}
	}


	@Override
	public void MsgToServer(String msg, String FromUser, String ToName) throws RemoteException
	{
		for(int i=0; i< clientsOnline.size(); i++)                
		{
			if(clientsOnline.get(i).equals(ToName))
			{    

				try
				{
					clients.get(i).MsgArrived(msg, FromUser);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					clients.remove(i);
					clientsOnline.remove(i);
					int idSender=clientsOnline.indexOf(FromUser);
					clients.get(idSender).ServerNotification("Disconnected Contact",ToName);					
				}                                        
			}
			else if(clientsOnline.get(i).equals(FromUser))
			{

				try
				{
					clients.get(i).MsgArrived(msg, "Moi");
				}
				catch(Exception e)
				{

					clients.remove(i);
					clientsOnline.remove(i);


				}
			}


			refreshContactList();

		}
	}

	public void refreshContactList(){
		for(int i=0; i< clientsOnline.size(); i++)  {
			try
			{
				int id=d.getUserid(clientsOnline.get(i));
				ArrayList<Contact> contacts=d.getContactsOfUser(id);
				List<String> ContactsOnline= new ArrayList<String>();



				if(contacts!=null){

					ContactsOnline.clear();
					for(Contact c : contacts){
						String Contactname=c.getName();
						if(clientsOnline.indexOf(Contactname)>-1)
							ContactsOnline.add(Contactname);

					}

				}
				clients.get(i).UpdateUserList(ContactsOnline);


			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}  
		}
	}


}
