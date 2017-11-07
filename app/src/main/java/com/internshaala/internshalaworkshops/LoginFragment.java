package com.internshaala.internshalaworkshops;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    TextView tvRegister,tvLogo;
    Button bLogin;
    TextInputLayout tilEmailId,tilPassword;
    String  password,emailid;

    // Container Activity must implement this interface
    public interface LoginListener {
        public void onSignUpClicked();
        public void onLogin(String username, String password);
    }

    LoginListener mCallback;
    MainActivity mainActivity;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View customView =  inflater.inflate(R.layout.fragment_login, container, false);

        mainActivity = (MainActivity) getActivity();
        mCallback = (LoginListener) getActivity();


        tvLogo = (TextView)customView.findViewById(R.id.tvLogo);
        tilEmailId = (TextInputLayout) customView.findViewById(R.id.tilEmailId);
        tilPassword = (TextInputLayout) customView.findViewById(R.id.tilPassword);
        bLogin = (Button) customView.findViewById(R.id.bLogin);
        tvRegister = (TextView) customView.findViewById(R.id.tvRegister);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailid = tilEmailId.getEditText().getText().toString();
                password = tilPassword.getEditText().getText().toString();

                if (emailid.isEmpty()) {
                    tilEmailId.getEditText().setError("Please enter your Email-Id");
                } else if (password.isEmpty()) {
                    tilPassword.getEditText().setError("Please enter Password");
                } else if (!isValidEmailAddress(emailid)) {
                    tilEmailId.getEditText().setError("Please enter a valid Email Address");
                } else if (!isValidPassword(password)) {
                    tilPassword.getEditText().setError("Password is too short");
                } else {
                    // Load Main activity
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                    final boolean crctDetails = dataBaseHelper.isValid(emailid,password);
                    final ProgressDialog dialog = new ProgressDialog(getActivity());
                    dialog.setTitle("Verifying Credentials...");
                    dialog.setMessage("Please wait..");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.show();

                    long delayInMillis = 500;
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            dialog.dismiss();

                        }
                    }, delayInMillis);

                    if(crctDetails){
                        String name =" ";
                        name= dataBaseHelper.getName(emailid);
                        mCallback.onLogin(name,emailid);

                    }else{
                        Toast.makeText(getContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                    }


                }

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onSignUpClicked();
            }
        });

        return customView;

    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean isValidPassword(String password){
        if(password.length()>=6){
            return  true;
        } else {
            return false;
        }
    }






}
