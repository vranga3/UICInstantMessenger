package com.example.chatworking;

import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		reference = (Jabber)getApplication();
		account = reference.getInstance();
		login = (Button) findViewById(R.id.login);
		netid = (TextView) findViewById(R.id.netid);
		pwd = (TextView) findViewById(R.id.pwd);
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



}
