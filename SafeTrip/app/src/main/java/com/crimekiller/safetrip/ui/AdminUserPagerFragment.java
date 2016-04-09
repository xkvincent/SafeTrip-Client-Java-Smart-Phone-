package com.crimekiller.safetrip.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crimekiller.safetrip.Model.User;
import com.crimekiller.safetrip.R;

/**
 * Created by Wenlu on 3/27/16.
 */
public class AdminUserPagerFragment extends Fragment {

    public static final String USER_NAME = "USER_NAME";

    private User user;
    private TextView usernameTextView;

    public static AdminUserPagerFragment newInstance(String name ) {
        Bundle args = new Bundle();
        args.putSerializable( USER_NAME, name );

        AdminUserPagerFragment fragment = new AdminUserPagerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    //get parameters of the fragment when initialized
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = (String) getArguments().getSerializable( USER_NAME );
        user = User.getUserByName(name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.ui_admin_user_pager_fragment, container, false);
        usernameTextView = (TextView) v.findViewById(R.id.usernameTextView);

        usernameTextView.setText( user.getName() );

        return v;
    }
}
