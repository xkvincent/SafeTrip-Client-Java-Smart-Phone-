package com.crimekiller.safetrip.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crimekiller.safetrip.R;

import java.util.ArrayList;

/**
 * Created by shuangliang on 4/17/16.
 */
public class AllPostAdapter extends ArrayAdapter<Post> {

    public AllPostAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Post post = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_friend_post, parent, false);
        }

        // Lookup view for data population
        TextView tvUsername = (TextView) convertView.findViewById(R.id.ViewAllPost_Username);
        TextView tvDate = (TextView) convertView.findViewById(R.id.ViewAllPost_Date);
        TextView tvPlate = (TextView) convertView.findViewById(R.id.ViewAllPost_LicensePlate);
        TextView tvDestination = (TextView) convertView.findViewById(R.id.ViewAllPost_Destination);
        TextView tvModel = (TextView) convertView.findViewById(R.id.ViewAllPost_CarModel);
        TextView tvColor = (TextView) convertView.findViewById(R.id.ViewAllPost_CarColor);
        TextView tvDeparture = (TextView) convertView.findViewById(R.id.ViewAllPost_Departure);


        // Populate the data into the template view using the data object
        tvUsername.setText(post.getOwner());
        tvDate.setText(post.getDate());
        tvPlate.setText(post.getLicensePlate());
        tvDestination.setText(post.getDestination());
        tvModel.setText(post.getModel());
        tvColor.setText(post.getColor());
        tvDeparture.setText(post.getDeparture());

        // Return the completed view to render on screen
        return convertView;
    }
}
