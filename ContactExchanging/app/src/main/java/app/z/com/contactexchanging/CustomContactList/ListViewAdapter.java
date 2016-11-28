package app.z.com.contactexchanging.CustomContactList;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import app.z.com.contactexchanging.ListContacts;
import app.z.com.contactexchanging.R;
import app.z.com.contactexchanging.ViewContact;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<person> worldpopulationlist = null;
    private ArrayList<person> arraylist;

    public ListViewAdapter(Context context,
                           List<person> worldpopulationlist) {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<person>();
        this.arraylist.addAll(worldpopulationlist);
    }

    public class ViewHolder {
        TextView name;
        ImageView avatar;
    }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public person getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_layout, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.txtitem);

            holder.avatar = (ImageView) view.findViewById(R.id.imgitem);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(worldpopulationlist.get(position).getName());
        holder.avatar.setImageResource(worldpopulationlist.get(position)
                .getAva());
        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(mContext, ViewContact.class);
                intent.putExtra("Name",
                        (worldpopulationlist.get(position).getName()));

                intent.putExtra("Avatar",
                        (worldpopulationlist.get(position).getAva()));
                intent.putExtra("Phone","null");
                intent.putExtra("Email","null");

                mContext.startActivity(intent);
            }
        }
        );

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        } else {
            for (person wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
