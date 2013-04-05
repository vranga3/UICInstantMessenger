package com.example.chatworking;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
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
import android.widget.ListView;

public class Contacts extends ListActivity {

	Jabber account,reference;
	String buddies[]={"MyContacts","AddContacts"};


	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		reference = (Jabber)getApplication();
		account = reference.getInstance();
		

		setListAdapter(new ArrayAdapter<String>(Contacts.this,android.R.layout.simple_list_item_1,buddies));
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String local= buddies[position];
		
		if(local.equals("MyContacts"))
		{
			Intent mainactivity = new Intent("com.example.chatworking.MYCONTACTS");
			startActivity(mainactivity);
		}
		if(local.equals("AddContacts"))
		{
			Intent mainactivity = new Intent("com.example.chatworking.ADDFRIEND");
			startActivity(mainactivity);
		}
		
		
		
	}

	

}
