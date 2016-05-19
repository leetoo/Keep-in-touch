package Serveur;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Database {

	public ArrayList<User> Users = new ArrayList<User>();

	Database(){
		try {

			File fXmlFile = new File("database.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("user");

			for (int i = 0; i < nList.getLength(); i++) {




				Node nNode = nList.item(i);


				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					ArrayList<Contact> Contacts = new ArrayList<Contact>();

					for (int j = 0; j < eElement.getElementsByTagName("contact").getLength(); j++) {

						Element econtact = (Element) eElement.getElementsByTagName("contact").item(j);

						int id = Integer.parseInt(econtact.getAttribute("id"));
						Contact tempContact = new Contact(econtact.getTextContent(),id);
						Contacts.add(tempContact);

					}

					int id = Integer.parseInt(eElement.getAttribute("id"));

					User tempUser = new User(eElement.getElementsByTagName("name").item(0).getTextContent() , id , Contacts , eElement.getElementsByTagName("mdp").item(0).getTextContent());

					Users.add(tempUser);





				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void reloadDatabase(){

		try {
			Users.clear();

			File fXmlFile = new File("database.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("user");

			for (int i = 0; i < nList.getLength(); i++) {




				Node nNode = nList.item(i);


				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					ArrayList<Contact> Contacts = new ArrayList<Contact>();

					for (int j = 0; j < eElement.getElementsByTagName("contact").getLength(); j++) {

						Element econtact = (Element) eElement.getElementsByTagName("contact").item(j);

						int id = Integer.parseInt(econtact.getAttribute("id"));
						Contact tempContact = new Contact(econtact.getTextContent(),id);
						Contacts.add(tempContact);

					}

					int id = Integer.parseInt(eElement.getAttribute("id"));

					User tempUser = new User(eElement.getElementsByTagName("name").item(0).getTextContent() , id , Contacts , eElement.getElementsByTagName("mdp").item(0).getTextContent());

					Users.add(tempUser);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean addUser(String Name, String Mdp){
		boolean AlreadyExists=false;

		for (User u : Users){
			if(u.name.equals(Name)) AlreadyExists=true;

		}

		if(!AlreadyExists){
			int maxid=this.Users.size();
			User tempUser = new User(Name,Mdp,maxid-1);

			Users.add(tempUser);

		}

		savedatabase();

		return AlreadyExists;
	}

	public boolean isIdentifierCorrect(String Name, String Mdp){
		boolean bool=false;
		for (User u : Users){

			if(u.getName().equals(Name)){

				if(u.getMdp().equals(Mdp)){
					bool=true;
				}

			}
		}	
		return bool;
	}

	public int getUserid(String Name){

		int UserId=-1;
		for(User u : Users){
			if(u.name.equals(Name))
				UserId=u.id;
		}
		return UserId;
	}

	public void addContact(int idUserRequesting, String NameContact){

		int idUser=idUserRequesting-1;
		boolean ContactAlreadyExists=false;
		
		for(Contact c : Users.get(idUserRequesting).contacts){
			if(c.name.equals(NameContact))
				ContactAlreadyExists=true;
		}

		if(!ContactAlreadyExists){
			int idContact=getUserid(NameContact);			

			Contact tempContact1 = new Contact(NameContact,idContact);
			Contact tempContact2 = new Contact(Users.get(idUserRequesting).name,idUserRequesting);

			Users.get(idUserRequesting).contacts.add(tempContact1);			
			Users.get(getUserid(NameContact)).contacts.add(tempContact2);

			savedatabase();
		}

	}

	public void removeContact(int idUser, String NameContact){	
		
		Iterator<Contact> it = Users.get(idUser+1).contacts.iterator();
		while (it.hasNext()) {
			Contact c1 = it.next();
			if (c1.getName().equals(NameContact)) {
				it.remove();
			}
		}
		
		Iterator<Contact> it2 = Users.get(getUserid(NameContact)).contacts.iterator();
		while (it2.hasNext()) {
			Contact c2 = it2.next();
			if (c2.getName().equals(Users.get(idUser+1).name)) {
				it2.remove();
			}
		}
		savedatabase();
	}

	public ArrayList<User> getUserlist(){

		return Users;		
	}

	public ArrayList<Contact> getContactsOfUser(int id){		
		ArrayList<Contact> Contacts=null;

		for (User u : Users){
			if(u.getId()==id){
				Contacts = u.getContacts();
			}
		}
		return Contacts;
	}

	public User getUser(String Name){

		User user=null;
		for (User u : Users){
			if(u.name.equals(Name))
				user=u;
		}
		return user;
	}

	public synchronized void savedatabase(){

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("database");
			doc.appendChild(rootElement);

			// users
			for(User u : Users){
				Element user = doc.createElement("user");
				Attr attr = doc.createAttribute("id");
				attr.setValue(Integer.toString(u.id));
				user.setAttributeNode(attr);

				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(u.name));
				user.appendChild(name);

				Element mdp = doc.createElement("mdp");
				mdp.appendChild(doc.createTextNode(u.mdp));
				user.appendChild(mdp);

				if(u.contacts != null){
					for(Contact c : u.contacts){
						Element contact = doc.createElement("contact");
						Attr attr1 = doc.createAttribute("id");
						attr1.setValue(Integer.toString(c.id));
						contact.setAttributeNode(attr1);
						contact.appendChild(doc.createTextNode(c.name));
						user.appendChild(contact);

					}
				}

				rootElement.appendChild(user);
			}


			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("database.xml"));

			transformer.transform(source, result);
			System.out.println("Database saved");

			reloadDatabase();

			System.out.println("Database reloaded");




		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}


}

class User
{
	protected String name;
	protected int id;
	protected String mdp;
	protected ArrayList<Contact> contacts ;

	User(String Name,int Id, ArrayList<Contact> Contacts, String Mdp){
		this.name=Name;
		this.id=Id;
		this.contacts=Contacts;
		this.mdp=Mdp;
	}

	User(String Name, String Mdp,int maxid){
		this.name=Name;
		this.id=maxid+1;
		this.mdp=Mdp;
	}

	public String getName()
	{
		return this.name;
	}

	public String getMdp()
	{
		return this.mdp;
	}


	public int getId()
	{
		return this.id;
	}

	public boolean setName(String name)
	{
		this.name = name;
		return true;
	}

	public boolean setId(int id)
	{
		this.id = id;
		return true;
	}

	public ArrayList<Contact> getContacts()
	{
		return this.contacts;
	}

	public Contact getContact(String Name){

		Contact contact=null;
		for (Contact c : contacts){
			if(c.name.equals(Name))
				contact=c;
		}
		return contact;
	}


}

class Contact
{
	protected String name;
	protected int id;

	Contact(String Name,int Id){
		this.name=Name;
		this.id=Id;
	}

	public String getName()
	{
		return this.name;
	}

	public int getId()
	{
		return this.id;
	}

	public boolean setName(String name)
	{
		this.name = name;
		return true;
	}

	public boolean setId(int id)
	{
		this.id = id;
		return true;
	}

}






