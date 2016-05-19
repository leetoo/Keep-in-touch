package Serveur;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IClient extends Remote{    
    public void UpdateUserList(List<String> ClientsName) throws RemoteException;
    public void  MsgArrived(String msg, String FromUser) throws RemoteException;  
    public void  ServerNotification(String message1,String message2) throws RemoteException;
    public void   addContactResult(int status) throws RemoteException;
}
