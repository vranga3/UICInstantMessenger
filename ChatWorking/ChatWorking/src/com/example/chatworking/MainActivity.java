package com.example.chatworking;

import java.util.Collection;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.OrFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.Roster;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.packet.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
	Button login;
	TextView netid; 
	TextView pwd; 
	Jabber account,reference;
	XMPPConnection connection;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		reference = (Jabber)getApplication();
		account = reference.getInstance();
		connection = account.getConnectionObject();
		login = (Button) findViewById(R.id.login);
		netid = (TextView) findViewById(R.id.netid);
		pwd = (TextView) findViewById(R.id.pwd);
		connection.getRoster().setDefaultSubscriptionMode(SubscriptionMode.manual);
		Log.i("test","Entering the Login window");
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pwds, netids;
				netids = netid.getText().toString();
				pwds = pwd.getText().toString();
				//netids = "vranga3";
				//pwds = "Redcatonsky1";
				if( netids.length() == 0 || pwds.length() == 0 )
				{
					AlertDialog builder = new AlertDialog.Builder(MainActivity.this).create();
					builder.setTitle("Error");
					builder.setMessage("Please enter your NetId and Password");
					builder.setButton(DialogInterface.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
					builder.show();

				}
				else
				{

					Log.i("test",netids);
					Log.i("test",pwds);
					try {
						account.login(netids, pwds);
						new AsyncTask<Object, Object, Object>(){

							@Override
							protected Object doInBackground(Object... arg0) {
								// TODO Auto-generated method stub

								listeningForMessages();
								return null;
							}

						}.execute();

						Intent next = new Intent("com.example.chatworking.GRIDVIEW");
						startActivity(next);


					} catch (XMPPException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						AlertDialog builder = new AlertDialog.Builder(MainActivity.this).create();
						builder.setTitle("Incorrect Netid/Password");
						builder.setMessage("Please enter your NetId and Password correctly");
						builder.setButton(DialogInterface.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub

							}
						});
						builder.show();
					}

				}

			}
		});


	}

	//packet collector which collects all the packets when user logins
	public void listeningForMessages() {
		Log.v("test", "in message listener");
		PacketFilter filter = new OrFilter(new PacketTypeFilter(Message.class),new PacketTypeFilter(Presence.class));
		PacketCollector collector = connection.createPacketCollector(filter);
		while (true) {
			Packet packet = collector.nextResult();
			if (packet instanceof Message) {
				Message message = (Message) packet;
				if (message != null && message.getBody() != null)
					Log.i("test","Received message from " + packet.getFrom() + " : "
							+ (message != null ? message.getBody() : "NULL"));
			}
			if (packet instanceof Presence)
			{
				Presence presence = (Presence) packet;
				if(presence.getType() == Presence.Type.subscribe)
				{
					Log.i("test","Friend request from "+packet.getFrom());
					
					//When you sent the friend request and you get subcribe back
					if(connection.getRoster().contains(packet.getFrom()))
					{
						Log.i("test","Inside this thing");
						Presence response = new Presence(Type.subscribed);
						response.setTo(packet.getFrom());
						response.setFrom(connection.getUser());
						connection.sendPacket(response);
					}
					//When someone sends you a friend request!
					else
					{
						account.addToFriendRequestList(packet.getFrom());
						Log.i("test","List of friend requests so far"+account.getFriendRequestList());
					}

				}
				if(presence.getType() == Presence.Type.subscribed)
				{
					Log.i("test",packet.getFrom()+" accepted your friend request");

				}
				if(presence.getType() == Presence.Type.unsubscribe)
				{
					
					Presence response = new Presence(Type.unsubscribed);
					response.setTo(packet.getFrom());
					response.setFrom(connection.getUser());
					connection.sendPacket(response);	
				
					if(connection.getRoster().contains(packet.getFrom()))
					{
						RosterEntry unfriend = connection.getRoster().getEntry(packet.getFrom());
						try {
							connection.getRoster().removeEntry(unfriend);
						} catch (XMPPException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
		}
	}


}
