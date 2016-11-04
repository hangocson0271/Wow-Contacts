package app.z.com.contactexchanging.CustomContactList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import app.z.com.contactexchanging.R;

public class MyArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] lContact;
    private final  Integer[] imgID;

    public MyArrayAdapter(Activity context, String[] lContact, Integer[] imgID) {
        super(context, R.layout.item_layout,lContact);
        this.context = context;
        this.lContact = lContact;
        this.imgID = imgID;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.item_layout, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtitem);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgitem);
        txtTitle.setText(lContact[position]);

        imageView.setImageResource(imgID[position]);
        return rowView;
    }
}