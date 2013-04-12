package com.example.chatworking;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.NinePatch;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class Chatting extends Activity  {
	Jabber account,reference;
	Button send;
	EditText toSend;
	ScrollView scroller;
	LinearLayout scroller_child;
	Bundle receive;
	String talkTo;
	String username;
	XMPPConnection connection;
	Chat chat;
	Resources res;
	ChatManager chatmanager;
	Drawable left;
	Drawable right;
	Handler mHandler = new Handler();
	boolean selfinitiated = false;
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public void toDisplay()
	{
		EditText to;
		Drawable r = right.getConstantState().newDrawable();
		to = new EditText(Chatting.this);
		to.setFocusable(false);
		to.setBackground(r);
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		p.gravity = Gravity.RIGHT;
		to.setLayoutParams(p);
		//scroller_child.setLayoutParams(p);
		to.setText("Me:\n"+toSend.getText());
		scroller_child.addView(to);
		scroller.post(new Runnable() {
			@Override
			public void run() {
				scroller.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
		r = null;
	}

	public void fromDisplay(final String username,final String message)
	{
		Chatting.this.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				EditText from;
				Drawable l = left.getConstantState().newDrawable();
				from = new EditText(Chatting.this);
				from.setFocusable(false);
				from.setBackground(l);
				from.setText(username+" says :\n"+message);
				Log.i("test",message);
				LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				p.gravity = Gravity.LEFT;
				from.setLayoutParams(p);
				scroller_child.addView(from);
				scroller.post(new Runnable() {
					@Override
					public void run() {
						scroller.fullScroll(ScrollView.FOCUS_DOWN);
					}
				});
				l = null;
			}
		
		});
		
	}
	
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		receive = getIntent().getExtras();
		talkTo = receive.getString("netid");
		Log.i("test","Received the value from MyContacts"+talkTo);
		reference = (Jabber)getApplication();
		account = reference.getInstance();
		connection = account.getConnectionObject();
		chatmanager= connection.getChatManager();
		setContentView(R.layout.chat);
		toSend=(EditText) findViewById(R.id.editText1);
		send = (Button) findViewById(R.id.button1);
		scroller = (ScrollView) findViewById(R.id.ScrollView1);
		scroller_child = (LinearLayout) scroller.getChildAt(0);
		username = account.getNameFromUser(talkTo);
		Resources res = getApplicationContext().getResources();
		left = res.getDrawable(R.drawable.bubble_yellow);
		right = res.getDrawable(R.drawable.bubble_green);
		
		Log.i("test","BeforeCreatingChat");


		//final Chat newChat = chatmanager.createChat(talkTo, Chatting.this);

		Log.i("test","AfterCreatingChat");
		
		//PacketFilter filter = new MessageTypeFilter(Message.Type.);
		PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class),new FromContainsFilter(talkTo));
		PacketListener p = new PacketListener(){

			@Override
			public void processPacket(Packet packet) {
				// TODO Auto-generated method stub
				 final Message message = (Message) packet;
				 final String fromName = StringUtils.parseBareAddress(message.getFrom());
		        	 mHandler.post(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							fromDisplay(fromName, message.getBody());
						}
		        		 
		        	 });
		        	 
				 Log.i("test", " Text Recieved " + message.getBody() + " from " +  fromName);
			}
			
		};
		connection.addPacketListener(p, filter);
		/*
		chatmanager.addChatListener(new ChatManagerListener()
		{
			public void chatCreated(final Chat chat, final boolean createdLocally)
			{
				if(!createdLocally)
				{
					Log.i("test","ListenerNotCreatedHenceCreatingOne[othersinitiate]");
					chat.addMessageListener(m);
				}
			}
		});
		 */
		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				// TODO Auto-generated method stub
				selfinitiated = true;
				toDisplay();
				// TODO Auto-generated method stub
				try {
				//	newChat.sendMessage(toSend.getText().toString());
					Message msg = new Message(talkTo,Message.Type.chat);
					msg.setBody(toSend.getText().toString());
					connection.sendPacket(msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//account.sendMessage(toSend.getText().toString(),talkTo);
				toSend.setText(null);
				// TODO Auto-generated catch block

			}
		});


		Log.i("test","AfterOnClick:)");

	}

	/*@Override
	public void processMessage(Chat arg0, final Message message) {
		// TODO Auto-generated method stub
		Chatting.this.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				fromDisplay(username, message.getBody());
			}

		});

	}
*/
	







}
