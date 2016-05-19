package Serveur;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote // Interface permet la création d'un supertype.
{   
    public boolean RegisterToServer(IClient client, String Name, char[] password) throws  RemoteException;
    public void MsgToServer(String msg, String FromUser, String ToName) throws RemoteException;
    public void addContact(String UserName, String ContactName) throws RemoteException;
    public void removeContact(String UserName, String ContactName) throws RemoteException;
   
}

