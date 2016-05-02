package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crimekiller.safetrip.model.User;
import com.crimekiller.safetrip.R;

/**
 * Created by Wenlu on 3/27/16.
 */
public class AdminUserPagerFragment extends Fragment {

    public static final String USER_NAME = "USER_NAME";
    public static final String USER = "USER";

    private User user;
    private TextView usernameTextView;
    private TextView emailTextView;
    private TextView titleTextView;
    private Button AdminBackBtn;

    public static AdminUserPagerFragment newInstance(User user ) {
        Bundle args = new Bundle();
        args.putSerializable(USER, user);

        AdminUserPagerFragment fragment = new AdminUserPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //get parameters of the fragment when initialized
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String name = (String) getArguments().getSerializable( USER_NAME );
//        user = User.getUserByName(name);
        user = (User) getArguments().getSerializable( USER );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.ui_admin_user_pager_fragment, container, false);
        Typeface GoodDogtf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GoodDog.otf");

        usernameTextView = (TextView) v.findViewById(R.id.usernameTextView);
        emailTextView = (TextView) v.findViewById(R.id.emailTextView);

        AdminBackBtn = (Button)v.findViewById(R.id.AdminBackBtn);
        AdminBackBtn.setTypeface(GoodDogtf);    //Set Font
        titleTextView = (TextView)v.findViewById(R.id.Admin_User_Basic_Info_Title);
        titleTextView.setTypeface(GoodDogtf);    //Set Font

        usernameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());

        AdminBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminActivity.class);
                Bundle bundle =  new Bundle();
                bundle.putString("username", "admin");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return v;
    }
}
