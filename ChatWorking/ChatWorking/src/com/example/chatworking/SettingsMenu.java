package com.example.chatworking;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsMenu extends ListActivity {
	String menuitems[]={"Help","About","Account","Profile","Chat Settings","Notifications"};
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
String local= menuitems[position];
		
		if(local.equals("Help"))
		{
			Intent mainactivity = new Intent("com.example.chatworking.HELPSETTINGS");
			startActivity(mainactivity);
		}
		
		if(local.equals("About"))
		{
			Intent mainactivity = new Intent("com.example.chatworking.ABOUTSETTINGS");
			startActivity(mainactivity);
		}
		
		if(local.equals("Account"))
		{
			Intent mainactivity = new Intent("com.example.chatworking.ACCOUNTSETTINGS");
			startActivity(mainactivity);
		}
		if(local.equals("Profile"))
		{
			Intent mainactivity = new Intent("com.example.chatworking.MYPROFILE");
			startActivity(mainactivity);
		}
		if(local.equals("Chat Settings"))
		{
			Intent mainactivity = new Intent("com.example.chatworking.CHATSETTINGS");
			startActivity(mainactivity);
		}
		if(local.equals("Notifications"))
		{
			Intent mainactivity = new Intent("com.example.chatworking.NOTIFICATIONS");
			startActivity(mainactivity);
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(SettingsMenu.this,android.R.layout.simple_list_item_1,menuitems));
		setTitle("Settings");
		
	}

}
