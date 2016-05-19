package Serveur;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author John
 */
public class ClientObj  extends UnicastRemoteObject  implements IClient
{       
	public ClientObj() throws RemoteException
	{

	}

	public void MsgArrived(String msg, String FromUser) throws RemoteException
	{
		String selected=(String) MainForm.jList.getSelectedValue();
		MainForm._MsgArrived(msg, FromUser);
		MainForm.jList.setSelectedValue(selected,true);
	}

	public void UpdateUserList(List<String> ClientsName) throws RemoteException
	{
		int selected=MainForm.jList.getSelectedIndex();
		MainForm._UpdateUserList(ClientsName);
		MainForm.jList.setSelectedIndex(selected);
	}

	public void  ServerNotification(String message1,String message2) throws RemoteException{

		if(message1.equals("Wrong Password")){
			MainForm.setLoginFormLabel("Mot de passe érroné");
		}
		if(message1.equals("User Already Online")){
			MainForm.setLoginFormLabel("Utilisateur déjà connecté");
		}
		if(message1.equals("OK")){
			MainForm.disposeLoginframe();
		}
			
		if(message1.equals("No Contact Selectioned")){			
			showAlert("Veuillez sélectionner un contact");
		}
		
		if(message1.equals("Disconnected Contact")){			
			showAlert(message2 + "s'est déconnecté");
		}
		
		if(message1.equals("Contact Removed")){			
			showAlert("Le contact " + message2 + " a été supprimé");
		}
		
		if(message1.equals("Contact Removing fail")){			
			showAlert("Une erreur est survenue lors de la suppression du contact");
		}
		
		
		
	}
	
	public void  addContactResult(int status) throws RemoteException{
		if(status==0){
			MainForm.setAddContactLabel("Contact ajouté !", status);
		}
		else if(status==1){
			MainForm.setAddContactLabel("Cet utilisateur n'existe pas", status);
		}
		else if(status==2){
			MainForm.setAddContactLabel("Vous possédez déjà ce contact", status);
		}
		else{
			MainForm.setAddContactLabel("Vous ne pouvez vous rajouter", status);
		}
		
	}
	

	public static void showAlert(final String message){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				JOptionPane.showMessageDialog(null,message,"Message à caractère informatif",JOptionPane.WARNING_MESSAGE);
			}
		});
	}


}
