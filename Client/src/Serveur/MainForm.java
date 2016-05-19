package Serveur;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainForm.java
 *
 * Created on Aug 17, 2013, 12:30:44 PM
 */


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author John
 */
public class MainForm extends JFrame{
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	static JLabel jLabel4;
	private JLabel jLabel5;
	static JLabel jLabel6;
	private static JLabel jLabel7;
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;
	private JTextArea jTextArea1;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private JTextField jTextField3;
	private static JTextField jTextField4;
	private JPasswordField jPassword;
	static JList jList;

	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JButton jButton5;

	private static JFrame f1;
	static JFrame f2;
	static JFrame f3;
	private static String username;
	private char[] password;
	public MainForm() {
		initComponents();        
	}

	private void initComponents() {
		jPanel1 = new JPanel();
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
		jLabel4 = new JLabel();
		jLabel5 = new JLabel();
		jLabel6 = new JLabel();
		jLabel7 = new JLabel();
		jScrollPane1 = new JScrollPane();
		jTextArea1 = new JTextArea();
		jTextField1 = new JTextField();
		jTextField2 = new JTextField();
		jTextField3 = new JTextField();

		jTextField4 = new JTextField();
		jButton5 = new JButton("Ajouter le contact");

		jButton2 = new JButton("Se Connecter");
		jButton3 = new JButton("+");
		jButton4 = new JButton("-");
		jPassword = new JPasswordField();
		jList = new JList();

		f1 = new JFrame("KeepInTouch");
		f2 = new JFrame("KeepInTouch : connexion");
		f3 = new JFrame();

		f1.setLocation(400, 250);
		f1.setSize(600, 422);
		f1.setResizable(false);
		f1.setLayout(null);




		//mainFrom
		jLabel1.setText("  Contacts en ligne");
		jTextArea1.setEditable(false);
		DefaultCaret caret = (DefaultCaret)jTextArea1.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		jScrollPane1.setViewportView(jTextArea1);
		jLabel1.setBounds(0, 0, 200, 30);
		jButton3.setBounds(140,0,30,30);
		jButton4.setBounds(170,0,30,30);
		jList.setBounds(0, 30, 200, 370);
		jScrollPane1.setBounds(200, 0, 400, 370);
		jTextField1.setBounds(200, 370, 400,30);

		f1.add(jTextField1);
		f1.add( jScrollPane1);
		f1.add(jLabel1);
		f1.add(jButton3);
		f1.add(jButton4);
		f1.add(jList);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//LoginForm
		f2.setLocation(500, 350);
		f2.setSize(400, 280);
		f2.setLayout(null);
		f2.setResizable(false);

		jLabel2.setText("Pseudo");
		jLabel3.setText("Mot de passe");
		jLabel4.setText("Connectez-vous ou créez un nouveau compte");
		jLabel2.setBounds(10, 130, 110, 20);
		jLabel3.setBounds(10, 160, 110, 20);
		jLabel4.setBounds(10, 195, 380, 20);
		jTextField2.setBounds(125, 130, 265, 20);
		jTextField3.setBounds(125, 160, 265, 20);
		jPassword.setBounds(125, 160, 265, 20);
		jButton2.setBounds(10, 225, 380, 20);
		
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("keepintouch.png"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setBounds(0, 0, 400, 100);
			f2.add(picLabel);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		

		f2.add(jLabel2);
		f2.add(jLabel3);
		f2.add(jTextField2);
		//f2.add(jTextField3);
		f2.add(jLabel4);
		f2.add(jPassword);
		f2.add(jButton2);
		f2.setVisible(true);
		f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Add contact form
		f3.setLocation(500, 350);
		f3.setSize(400, 170);
		f3.setLayout(null);
		f3.setResizable(false);

		jLabel5.setBounds(10,10,380,20);
		jLabel6.setBounds(10,40,380,20);
		jLabel6.setForeground(Color.red);
		jLabel7.setBounds(10,70,70,20);
		jTextField4.setBounds(80,70,310,20);
		jButton5.setBounds(10,110,380,20);
		jLabel5.setText("Rentrez le nom d'utilisateur du contact à rajouter");
		jLabel6.setText("");
		jLabel7.setText("Pseudo :");
		f3.add(jLabel5);
		f3.add(jLabel6);
		f3.add(jLabel7);
		f3.add(jTextField4);
		f3.add(jButton5);


		jButton2.addMouseListener(new ButtonListener2());
		jButton3.addMouseListener(new ButtonListener3());
		jButton4.addMouseListener(new ButtonListener4());
		jButton5.addMouseListener(new ButtonListener5());
		jTextField1.addActionListener(new ActionListener() {
			//On envoie les messages en appuyant sur entrée
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if(jTextField1.getText().trim().length()!=0){
						if(!jList.isSelectionEmpty()){

							iserver.MsgToServer(jTextField1.getText(), username, jList.getSelectedValue().toString());
							jTextField1.setText("");

						}
						else{
							ClientObj.showAlert("Veuillez sélectionner un destinataire");
						}
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block

					e1.printStackTrace();
				}
			}

		});
		//	jList.addListSelectionListener(new ListListener());

	}

	public static void setLoginFormLabel(String Message){
		jLabel4.setForeground(Color.red);
		jLabel4.setText(Message);
	}

	public static void disposeLoginframe(){
		f2.setVisible(false);
		f1.setTitle("KeepInTouch : "+username);
		f1.setVisible(true);
	}

	private class ButtonListener2 implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if(jTextField2.getText().trim().length()!=0){
				if(jPassword.getPassword().length==0){
					setLoginFormLabel("Veuillez entrer un mot de passe");
					return;
				}
				username = jTextField2.getText();
				//password = jTextField3.getText();
				password = jPassword.getPassword();
				try {
					iserver.RegisterToServer(clientObj, username, password);
					mf.setTitle("User : " + username);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				setLoginFormLabel("Veuillez saisir un pseudo");
			}

		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
	}

	private class ButtonListener3 implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			jLabel6.setText("");
			f3.setVisible(true);		
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
	}

	private class ButtonListener4 implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(jList.isSelectionEmpty()){
				ClientObj.showAlert("Veuillez sélectionner un contact à supprimer");
			}
			else{
				try {
					iserver.removeContact(username,jList.getSelectedValue().toString());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
	}

	private class ButtonListener5 implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			jLabel6.setText("");
			if(jTextField4.getText().trim().length()==0){
				jLabel6.setText("Veuillez saisir un nom d'utilisateur");
			}
			else{
				try {
					iserver.addContact(username,jTextField4.getText());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
	}

	public static void setAddContactLabel(String Message, int status){
		if(status==0){
			jLabel6.setText(Message);
			jTextField4.setText(null);
		}
		else if(status==1){
			jLabel6.setText(Message);
		}
		else{
			jLabel6.setText(Message);
		}
	}
	public static void main(String args[]) {

		mf = new MainForm(); 
		try {
			String nomserveur = "//localhost:2005/transformeur";
			iserver = (IServer)Naming.lookup(nomserveur);
			clientObj = new ClientObj();
		}
		catch(Exception ex) {
			MainForm.setLoginFormLabel("Impossible de se connecter au serveur");
			System.out.println("Exception ; "+ ex.getMessage());
			ex. printStackTrace();
			return;
		}

	}

	static MainForm mf;
	static ClientObj clientObj;
	static IServer iserver;
	HashMap<String , List<String>> messages = new HashMap<String , List<String>>();

	public static void _MsgArrived(String msg, String FromUser)
	{ 

		mf.NewMsg(msg, FromUser);
	}
	public static void _UpdateUserList(List<String> ContactsOnline)
	{
		mf.NewUser(ContactsOnline);
	}
	public void NewUser(List<String> ContactsOnline)
	{
		jList.removeAll();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		int i =0;
		for(String UserName : ContactsOnline)
		{
			if(UserName.length()>15)
			{
				UserName = UserName.substring(0,15)+"..";
			}
			listModel.add(i, UserName);
			i++; 

		}
		this.jList.setModel(listModel);
	}

	public void NewMsg(String msg, String FromUser)
	{
		
		jTextArea1.append(FromUser +" : "+ msg + "\n");
		
	}


}