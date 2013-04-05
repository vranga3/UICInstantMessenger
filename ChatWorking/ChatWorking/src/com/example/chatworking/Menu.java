package com.example.chatworking;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class Menu extends ListActivity {
	Jabber account,reference;
String buddies[]={"Start Chatting","Contacts","MyProfile","Logout"};
XMPPConnection connection;


	
	@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	connection.addPacketListener(new PacketListener() {

		@Override
		public void processPacket(final Packet packet) {
			Presence presence = (Presence) packet;
			if (presence.getType() == Type.subscribe && (!account.isSentRequest())) {
				Log.i("test",packet.getFrom());
				Menu.this.runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
						        switch (which){
						        case DialogInterface.BUTTON_POSITIVE:
						        	AlertDialog.Builder alert = new AlertDialog.Builder(Menu.this);

						        	alert.setTitle("Enter Nickname");
						        	final EditText input = new EditText(Menu.this);
						        	alert.setView(input);
						        	
						        	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						        		public void onClick(DialogInterface dialog, int whichButton) {
						        			Roster roster = connection.getRoster();
								        	try {
								        		Log.i("test",packet.getFrom());
								        		roster.createEntry(packet.getFrom(),input.getText().toString(),null);
											} catch (XMPPException e) {
												// TODO Auto-generated catch block
													Log.i("test",e.toString());
											}
						        		  }
						        		});

						        		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						        		  public void onClick(DialogInterface dialog, int whichButton) {
						        		    // Canceled.
						        		  }
						        		});

						        		alert.show();
						        	break;

						        case DialogInterface.BUTTON_NEGATIVE:
						            
						        	break;
						        }
						    }
						};

						AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
						if(!account.isInBuddyList(packet.getFrom()))
							
						{
						builder.setMessage("You have a friend request from "+packet.getFrom()).setPositiveButton("Accept", dialogClickListener)
						    .setNegativeButton("Reject", dialogClickListener).show();
						}
					}
					
				});
				
								} 				
		}
	}, new PacketTypeFilter(Presence.class));
}
	@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	account.disconnect();
}
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		reference = (Jabber)getApplication();
		account = reference.getInstance();
		connection = account.getConnectionObject();

		
		setListAdapter(new ArrayAdapter<String>(Menu.this,android.R.layout.simple_list_item_1,buddies));
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String local= buddies[position];
		
		if(local.equals("Logout"))
		{
			account.disconnect();
		}
		
		if(local.equals("Contacts"))
		{
			Intent mainactivity = new Intent("com.example.chatworking.CONTACTS");
			startActivity(mainactivity);
		}
		
		if(local.equals("Start Chatting"))
		{
			Intent mainactivity = new Intent("com.example.chatworking.MYCONTACTS");
			startActivity(mainactivity);
		}
		
		
	}

	

}
