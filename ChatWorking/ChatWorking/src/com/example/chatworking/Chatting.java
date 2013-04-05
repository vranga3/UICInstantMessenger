package com.example.chatworking;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class Chatting extends Activity {
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
		setContentView(R.layout.chat);
		toSend=(EditText) findViewById(R.id.editText1);
		send = (Button) findViewById(R.id.button1);
		scroller = (ScrollView) findViewById(R.id.ScrollView1);
		scroller_child = (LinearLayout) scroller.getChildAt(0);
		username = account.getNameFromUser(talkTo);
		

		connection.getChatManager().addChatListener(new ChatManagerListener()
		{
			public void chatCreated(final Chat chat, final boolean createdLocally)
			{
				chat.addMessageListener(new MessageListener()
				{
					public void processMessage(final Chat chat, final Message message)
					{

						//	sb.append(message.getBody());

						Chatting.this.runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								EditText from;  
								from = new EditText(Chatting.this);
								from.setFocusable(false);
								from.setText(username+" says :\n"+message.getBody());
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
							}


						});


					}
				});
			}
		});





		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

					EditText to;
					to = new EditText(Chatting.this);
					to.setFocusable(false);
					LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
					p.gravity = Gravity.RIGHT;
					to.setLayoutParams(p);
					to.setText("Me:\n"+toSend.getText());
					scroller_child.addView(to);
					scroller.post(new Runnable() {
					    @Override
					    public void run() {
					        scroller.fullScroll(ScrollView.FOCUS_DOWN);
					    }
					});
					try {
						account.sendMessage(toSend.getText().toString(),talkTo);
						toSend.setText(null);
					} catch (XMPPException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});




	}







}
