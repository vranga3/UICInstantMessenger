package com.example.chatworking;



import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class Gridview extends Activity{
	
	Jabber account,reference;
	XMPPConnection connection;
	//TextView tv;
	GridView gridview;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("test","onresume");
		/*
		connection.addPacketListener(new PacketListener() {

			@Override
			public void processPacket(final Packet packet) {
				Presence presence = (Presence) packet;
				if (presence.getType() == Type.subscribe && (!account.isSentRequest())) {
					Log.i("test",packet.getFrom());
					Gridview.this.runOnUiThread(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
							    @Override
							    public void onClick(DialogInterface dialog, int which) {
							        switch (which){
							        case DialogInterface.BUTTON_POSITIVE:
							        	AlertDialog.Builder alert = new AlertDialog.Builder(Gridview.this);

							        	alert.setTitle("Enter Nickname");
							        	final EditText input = new EditText(Gridview.this);
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

							AlertDialog.Builder builder = new AlertDialog.Builder(Gridview.this);
							if(!account.isInBuddyList(packet.getFrom()))
								
							{
							builder.setMessage("You have a friend request from "+packet.getFrom()).setPositiveButton("Accept", dialogClickListener)
							    .setNegativeButton("Reject", dialogClickListener).show();
							}
						}
						
					});
					
									} 				
			}
		}, new PacketTypeFilter(Presence.class));*/
		
	}

	/*
	@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	Log.i("test","onresume");
	connection.addPacketListener(new PacketListener() {

		@Override
		public void processPacket(final Packet packet) {
			Presence presence = (Presence) packet;
			if (presence.getType() == Type.subscribe && (!account.isSentRequest())) {
				Log.i("test",packet.getFrom());
				Gridview.this.runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
						        switch (which){
						        case DialogInterface.BUTTON_POSITIVE:
						        	AlertDialog.Builder alert = new AlertDialog.Builder(Gridview.this);

						        	alert.setTitle("Enter Nickname");
						        	final EditText input = new EditText(Gridview.this);
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

						AlertDialog.Builder builder = new AlertDialog.Builder(Gridview.this);
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
}*/
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		account.disconnect();
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		reference = (Jabber)getApplication();
		account = reference.getInstance();
		connection = account.getConnectionObject();

		//tv=(TextView) findViewById(R.id.textView1);
		gridview =(GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(getApplicationContext()));
	    
		gridview.setOnItemClickListener(new OnItemClickListener(){
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
       // Toast.makeText(Gridview.this, "" + position, Toast.LENGTH_SHORT).show();
		
		int pos= position;
		
		switch(pos) {
		case 0:// case for myprofile
			Intent i0=new Intent(getApplicationContext(),MyProfile.class);
	        i0.putExtra("id", position);
	        startActivity(i0);
			break;
		
		case 1:// case for mycontacts
			Intent i1=new Intent(getApplicationContext(),Contacts.class);
	        i1.putExtra("id", position);
	        startActivity(i1);
			
			break;
			
			case 2:// case for mysettings
				Intent i2=new Intent(getApplicationContext(),SettingsMenu.class);
		        i2.putExtra("id", position);
		        startActivity(i2);

			break;
		case 3:// case for startchatting
			Intent i3=new Intent(getApplicationContext(),OnlineBuddies.class);
	        i3.putExtra("id", position);
	        startActivity(i3);
			
			break;
			
			
		case 4:// case for add contacts
			Intent i4=new Intent(getApplicationContext(),AddFriend.class);
	        i4.putExtra("id", position);
	        startActivity(i4);
			
			break;
			
		case 5: // case for logout
			account.disconnect();
			break;	
		}//end of switch case
        
    }

});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.optionmenu, menu);
	    return true;
		
	}
	
}