package com.example.chatworking;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class Introduction extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uiclogo);
		
		Thread startFirstPage = new Thread(){
			public void run()
			{
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					Intent start = new Intent("com.example.chatworking.MAINACTIVITY");
					startActivity(start);
					finish();
				}
				
		
			}
			
		};
		
		startFirstPage.start();	
	}

}
