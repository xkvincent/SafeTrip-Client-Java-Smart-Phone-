package com.crimekiller.safetrip.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crimekiller.safetrip.R;

import java.util.ArrayList;

/**
 * Created by shuangliang on 5/1/16.
 */
public class AdminPostAdapter extends ArrayAdapter<Post> {

    public AdminPostAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Post post = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {

//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_admin_simple_post, parent, false);
//        }
//        // Lookup view for data population
//        TextView tvUser = (TextView) convertView.findViewById(R.id.Admin_Simple_Post_User);
//        TextView tvDate = (TextView) convertView.findViewById(R.id.Admin_Simple_Post_Date);
//        TextView tvDestination = (TextView) convertView.findViewById(R.id.Admin_Simple_Post_Destination);

            convertView =
                    LayoutInflater.from(getContext()).inflate(R.layout.item_admin_simple_post,
                            parent, false);
        }
        // Lookup view for data population
        TextView tvUser = (TextView)
                convertView.findViewById(R.id.Admin_Simple_Post_User);
        TextView tvDate = (TextView)
                convertView.findViewById(R.id.Admin_Simple_Post_Date);
        TextView tvDestination = (TextView)
                convertView.findViewById(R.id.Admin_Simple_Post_Destination);

        // Populate the data into the template view using the data object
        tvUser.setText(post.getOwner());
        tvDate.setText(post.getDate());
        tvDestination.setText(post.getDestination());
        // Return the completed view to render on screen
        return convertView;
    }

}

