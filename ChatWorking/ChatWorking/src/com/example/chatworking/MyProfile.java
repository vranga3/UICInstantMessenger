package com.example.chatworking;

//this is myprofile class

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MyProfile extends Activity {
	
	private Uri mUri;
	Bitmap bitmap;
	Jabber reference,account;
	Activity activity;
	Context context;
	EditText et;
	TextView tv;
	
	private static int RESULT_LOAD_IMAGE = 1;
	ArrayList<String> image_list=new ArrayList<String>();
   // ArrayList<Drawable> image_drawable=new ArrayList<Drawable>();
	String path="";
	ImageButton IB1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myprofile);
		setTitle(" My Profile");
	 IB1=(ImageButton) findViewById(R.id.imageButton1);
	 registerForContextMenu(IB1);
		 tv=(TextView) findViewById(R.id.textView1);
		et=(EditText) findViewById(R.id.editText1);
		
		
		
		Intent i=getIntent();
		int position= (Integer) i.getExtras().get("id");

		
		activity=MyProfile.this;
	     context=MyProfile.this;
	        
	        
	  
	}// END OF ONCREATE METHOD

	@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
		 super.onCreateContextMenu(menu, v, menuInfo);
	        menu.setHeaderTitle("Post Image");
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.contextmenuforimages, menu);
	}//END OF onCreateContextMenu

	  @Override
	    public boolean onContextItemSelected(MenuItem item)
	    {
	      switch (item.getItemId())
	      {
	          case R.id.take_photo:
	             Toast.makeText(MyProfile.this, "Selected Take Photo", Toast.LENGTH_SHORT).show();
	             takePhoto();
	              break;
	          
	          case R.id.choose_gallery:
	             Toast.makeText(MyProfile.this, "Selected Gallery", Toast.LENGTH_SHORT).show();
	              Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
	              photoPickerIntent.setType("image/*");
	              startActivityForResult(photoPickerIntent, 1);
	              
	              break;
	        
	          case R.id.share_cancel:
	              closeContextMenu();
	              break;
	          default:
	            return super.onContextItemSelected(item);
	      }
	      return true;
	    }
	  
	  
	  public void takePhoto() 
	    {
	         Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	         File folder = new File(Environment.getExternalStorageDirectory() + "/LoadImg");

	         if(!folder.exists())
	         {
	             folder.mkdir();
	         }         
	         final Calendar c = Calendar.getInstance();
	         String new_Date= c.get(Calendar.DAY_OF_MONTH)+"-"+((c.get(Calendar.MONTH))+1)   +"-" + c.get(Calendar.YEAR) +" " + c.get(Calendar.HOUR) + "-" + c.get(Calendar.MINUTE)+ "-"+ c.get(Calendar.SECOND);
	         Log.v("test", new_Date);
	         path= String.format(Environment.getExternalStorageDirectory() +"/LoadImg/%s.png","LoadImg("+new_Date+")");
	         Log.v("test", path);
	         File photo = new File(path); 
	         mUri =Uri.fromFile(photo);
	         intent.putExtra(MediaStore.EXTRA_OUTPUT,mUri);
	         startActivityForResult(intent, 2);
	    }//end of takePhoto
	  
	  @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) 
	    {
	        super.onActivityResult(requestCode, resultCode, data);
	        
	        if(requestCode==1)
	        {
	            Uri photoUri = data.getData();
	            Log.v("test", "mmmmmmm");
	            if (photoUri != null)
	            {
	                String[] filePathColumn = {MediaStore.Images.Media.DATA};
	                Cursor cursor = getContentResolver().query(photoUri, filePathColumn, null, null, null);
	                cursor.moveToFirst();
	                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	                String filePath = cursor.getString(columnIndex);
	                cursor.close();
	                Log.v("test", "Gallery File Path=====>>>"+filePath);
	              bitmap = BitmapFactory.decodeFile(filePath);
	              bitmap = Bitmap.createScaledBitmap(bitmap,350, 350, true);
	              IB1.setImageBitmap(bitmap);
	                
	         
	            }
	        }
	        
	        if(requestCode==2)
	        {/*
	        	 Log.v("test", "Camera File Path=====>>>"+path);
	        	
	        	getContentResolver().notifyChange(mUri, null);
	               ContentResolver cr = getContentResolver();
	              try{
	                   bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mUri);
	                   final ImageButton IB1=(ImageButton) findViewById(R.id.imageButton1);
		                IB1.setImageBitmap(bitmap);
	              }catch(Exception e) {
	                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
	                  }
	        	 
	        	 Bitmap bitmap = BitmapFactory.decodeFile(path);
	             bitmap = Bitmap.createScaledBitmap(bitmap,500, 500, true);
	             final ImageButton IB1=(ImageButton) findViewById(R.id.imageButton1);
	             IB1.setImageBitmap(bitmap);   
	             */
	        	
	        	if (resultCode == RESULT_OK) {
	                // Image captured and saved to fileUri specified in the Intent
	              //  Toast.makeText(this, "Image saved to:\n" +
	                      // data.getData(), Toast.LENGTH_LONG).show();
	        		try{
	                 bitmap = BitmapFactory.decodeFile(path);
	                bitmap = Bitmap.createScaledBitmap(bitmap,350, 350, true);
	               IB1.setImageBitmap(bitmap);
	              
	                
	        		} catch (Exception e) {
	        			Log.i("test","BITMAP ERROR"+e.toString());
	        		}
	            } else if (resultCode == RESULT_CANCELED) {
	               Log.v("test","image saving has been cancelled");
	            } else {
	                // Image capture failed, advise user
	            	Log.v("test","image Failure");
	            }
	          
	        }
	    }



	
	  /*
	  public class GetImages extends AsyncTask<Void, Void, Void> 
    {
        public ProgressDialog progDialog=null;
        
        protected void onPreExecute() 
        {
            progDialog=ProgressDialog.show(context, "", "Loading...",true);
        }
        @Override
        protected Void doInBackground(Void... params) 
        {
        	
            for(int i=0; i<image_list.size(); i++)
            {
                Bitmap bitmap = BitmapFactory.decodeFile(image_list.get(i).toString().trim());
                bitmap = Bitmap.createScaledBitmap(bitmap,350, 350, true);
              
            }
            return null;
        } 
        
        protected void onPostExecute(Void result) 
        {
            if(progDialog.isShowing())
            {
                progDialog.dismiss();
            }
            IB1.setImageBitmap(bitmap);
        }
    }
    */
}// end of class