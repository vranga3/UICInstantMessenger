package com.example.chatworking;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new android.widget.GridView.LayoutParams(
                    (int) mContext.getResources().getDimension(R.dimen.width),                                                                                   
                    (int) mContext.getResources().getDimension(R.dimen.height)));
          // imageView.setLayoutParams(new android.widget.GridView.LayoutParams((int) px, (int) px));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
           imageView.setPadding(8, 8, 8, 8);
            //imageView.setBackgroundColor(Color.BLUE);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
    		R.drawable.my_profile,
            R.drawable.my_contacts, 
            R.drawable.my_settings,
            R.drawable.start_chatting,
            R.drawable.add_contacts,
            R.drawable.log_out
            
    };
}
