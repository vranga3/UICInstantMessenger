package com.example.chatworking;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.packet.Presence.Type;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

public class Jabber extends Application implements MessageListener{
	private Jabber account = null;
	private XMPPConnection connection;
	String filepath = null; 
	boolean login=true;
	boolean sentRequest=false;
	boolean filepathset = false; 
	LinkedList<String> friendRequests = new LinkedList<String>();
	
	public void addToFriendRequestList(String request)
	{
		friendRequests.add(request);
	}
	
	public LinkedList<String> getFriendRequestList()
	{
		return friendRequests;
	}
	
	public boolean isFilepathset() {
		return filepathset;
	}

	public void setFilepathset(boolean filepathset) {
		this.filepathset = filepathset;
	}

	public void setFilePath(String path)
	{
		filepath = path ; 
	}
	
	public String getFilePath()
	{
		return filepath;
	}
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		try {
			account = new Jabber();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public XMPPConnection getConnectionObject()
	{
		return connection;
	}

	public Jabber getInstance(){
		return account;
	}

	public void PacketListener()
	{
		connection.addPacketListener(new PacketListener() {

			@Override
			public void processPacket(Packet packet) {
				Presence presence = (Presence) packet;
				if (presence.getType() == Type.subscribe) {
					Log.i("test",packet.getFrom());

				} 				
			}
		}, new PacketTypeFilter(Presence.class));




	}

	public boolean isSentRequest() {
		return sentRequest;
	}

	public void setSentRequest(boolean sentRequest) {
		this.sentRequest = sentRequest;
	}

	public Jabber() throws XMPPException
	{
		login=true;
		ConnectionConfiguration config = new ConnectionConfiguration("im.uic.edu", 5223);
		config.setSocketFactory(new DummySSLSocketFactory());
		connection = new XMPPConnection(config);
		new AsyncTask<Object, Object, Object>(){

			@Override
			protected Object doInBackground(Object... arg0) {
				// TODO Auto-generated method stub
				try {
					connection.connect();
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

		}.execute();

		SASLAuthentication.supportSASLMechanism("PLAIN", 0);	
	}

	


	public void login(String userName, String password) throws XMPPException{
		if(login==false)
		{
			login=true;
			ConnectionConfiguration config = new ConnectionConfiguration("im.uic.edu", 5223);
			config.setSocketFactory(new DummySSLSocketFactory());
			connection = new XMPPConnection(config);
			new AsyncTask<Object, Object, Object>(){

				@Override
				protected Object doInBackground(Object... arg0) {
					// TODO Auto-generated method stub
					try {
						connection.connect();
					} catch (XMPPException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}

			}.execute();


			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			connection.login(userName, password);
		}
		else
		{
			connection.login(userName, password);
		}
	}
//comment to check 
	public void sendMessage(String message, String to) throws XMPPException {
		Chat chat = connection.getChatManager().createChat(to, this);
		chat.sendMessage(message);
	}

	public ArrayList<String> getBuddyList() {
		Roster roster = connection.getRoster();
		roster.reload();
		Collection<RosterEntry> entries = roster.getEntries();
		ArrayList<String> result = new ArrayList<String>();
		int i=0;
		for (RosterEntry r : entries) {
			if(r.getStatus() != RosterPacket.ItemStatus.SUBSCRIPTION_PENDING)
			{
			Log.i("test",r.getUser().toString());
			Presence p = roster.getPresence(r.getUser());
			Boolean e = p.isAvailable();
			Log.i("test",e.toString());
			result.add(r.getName()); 
			}
		}
		
		for (RosterEntry r : entries) {
			if(r.getStatus() == RosterPacket.ItemStatus.SUBSCRIPTION_PENDING)
			{
				result.add(r.getName()+" - Pending Approval");
			}
		}
		
		
		
		return result;
	}

	public ArrayList<String> getOnlineBuddyList() {
		Roster roster = connection.getRoster();
		roster.reload();
		Collection<RosterEntry> entries = roster.getEntries();
		ArrayList<String> result = new ArrayList<String>();
		int i=0;
		for (RosterEntry r : entries) {
			Presence p = roster.getPresence(r.getUser());
			if(p.isAvailable())
			{
				Log.i("test",r.getName());
				result.add(r.getName()); 
			}
		}
		return result;
	}
		
	
	public boolean isInBuddyList(String username) {
		Roster roster = connection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		for (RosterEntry r : entries) {
			if(r.getUser().equals(username))
			return true;   
		}
		return false;
	}
	
	public String getUserFromeName(String username)
	{
		Roster roster = connection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		String result[] = new String[entries.size()];

		for (RosterEntry r : entries) {
			if(r.getName().equals(username))
				return r.getUser().toString();
		}
		return null;
	}

	public String getNameFromUser(String username)
	{
		Roster roster = connection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		String result[] = new String[entries.size()];

		for (RosterEntry r : entries) {
			if(r.getUser().equals(username))
				return r.getName().toString();
		}
		return null;
	}

	public void disconnect() {
		login=false;
		connection=null;
		System.exit(0);
	}

	public void processMessage(Chat chat, Message message) {
		if (message.getType() == Message.Type.chat) {
			System.out.println(chat.getParticipant() + " says: " + message.getBody());
		}
	}

	public void chumma(String args[]) throws XMPPException, IOException {
		// declare variables
		Jabber c = new Jabber();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String msg;


		// turn on the enhanced debugger
		// XMPPConnection.DEBUG_ENABLED = true;


		// Enter your login information here
		c.login("vranga3", "Redcatonsky1");

		//  c.displayBuddyList();

		System.out.println("-----");

		System.out.println("Who do you want to talk to? - Type contacts full email address:");
		String talkTo = br.readLine();

		System.out.println("-----");
		System.out.println("All messages will be sent to " + talkTo);
		System.out.println("Enter your message in the console:");
		System.out.println("-----\n");

		while (!(msg = br.readLine()).equals("bye")) {
			c.sendMessage(msg, talkTo);
		}

		c.disconnect();
		System.exit(0);
	}


}
