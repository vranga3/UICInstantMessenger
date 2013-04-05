package com.example.chatworking;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//this is a comment by sonali 
//this is a comment by karthik
public class AddFriend extends Activity{
	Jabber account,reference;
	Button sendRequest;
	EditText netid,nickname,group;
	XMPPConnection connection;
	String str[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addfriends);
		reference = (Jabber)getApplication();
		account = reference.getInstance();
		netid = (EditText)findViewById(R.id.editText1);
		nickname = (EditText)findViewById(R.id.editText2);
		group = (EditText)findViewById(R.id.editText3);
		sendRequest = (Button) findViewById(R.id.button1);
		connection = account.getConnectionObject();
		str = new String[1];
		
		
		sendRequest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				str[0]=group.getText().toString();
				/*
				Presence response = new Presence(Type.subscribe);
				response.setTo(netid.getText().toString());
				response.setFrom(connection.getUser());
				connection.sendPacket(response);
				*/
				Roster roster = connection.getRoster();
				try {
					if(!account.isInBuddyList(netid.getText().toString()))
					{
					roster.createEntry(netid.getText().toString(), nickname.getText().toString(), str);
					account.setSentRequest(true);
					}
					} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			
			
		});
		
	}	
	
}
