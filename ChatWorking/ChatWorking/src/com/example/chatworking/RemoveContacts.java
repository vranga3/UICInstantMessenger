package com.example.chatworking;

import java.util.ArrayList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class RemoveContacts extends ListActivity {
	Jabber account,reference;
	XMPPConnection connection;
	Chat chat;
	ArrayList<String> buddies;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setListAdapter(new ArrayAdapter<String>(RemoveContacts.this,android.R.layout.simple_list_item_1,account.getBuddyList()));

	}

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		reference = (Jabber)getApplication();
		account = reference.getInstance();
		connection = account.getConnectionObject();
		buddies = account.getBuddyList();
		setTitle("My Contacts");
		setListAdapter(new ArrayAdapter<String>(RemoveContacts.this,android.R.layout.simple_list_item_1,buddies));
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		String username= buddies.get(position);
		String netid;
		netid = account.getUserFromeName(username);
		Log.i("test",netid);
		Bundle b = new Bundle();
		b.putString("netid",netid);
		final Roster r = connection.getRoster();
		final RosterEntry unfriend = r.getEntry(netid);
		
		AlertDialog.Builder message = new AlertDialog.Builder(RemoveContacts.this);
		message.setTitle("Please Confirm!");
		message.setMessage("Are you sure, you want to remove "+username+" from your friends list?");
		message.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					r.removeEntry(unfriend);
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		message.setNegativeButton("No",null);
		message.create().show();


	}// end of onListItemClick



}
