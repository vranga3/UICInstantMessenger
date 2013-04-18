package com.example.chatworking;

import java.util.ArrayList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class OnlineBuddies extends ListActivity {
	Jabber account,reference;
	//XMPPConnection connection;
	Chat chat;
	ArrayList<String> buddies;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try{
		buddies = account.getOnlineBuddyList();
		}
		catch(NullPointerException e)
		{
			Log.i("test",e.toString());
		}
		setListAdapter(new ArrayAdapter<String>(OnlineBuddies.this,android.R.layout.simple_list_item_1,buddies));

	}
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		reference = (Jabber)getApplication();
		account = reference.getInstance();
		try{
			buddies = account.getOnlineBuddyList();
			}
			catch(NullPointerException e)
			{
				Log.i("test",e.toString());
			}
		setTitle("Online Buddies");
		setListAdapter(new ArrayAdapter<String>(OnlineBuddies.this,android.R.layout.simple_list_item_1,buddies));
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
		//table[position]=true;
		
		Intent chatting = new Intent(OnlineBuddies.this,Chatting.class);
		chatting.putExtras(b);
		startActivity(chatting);
		
		
	}// end of onListItemClick

	

}
