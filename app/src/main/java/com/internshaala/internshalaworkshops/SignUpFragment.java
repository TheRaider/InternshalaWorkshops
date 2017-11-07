package com.internshaala.internshalaworkshops;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    TextView tvLogo,tvAlreadyReg;
    Button bRegister;
    TextInputLayout tilName,tilEmailId,tilPassword;
    String  name,emailid,password;


    // Container Activity must implement this interface
    public interface SignupListener {
        public void onSignUp();
        public void onLoginClicked();
    }

    SignupListener mCallback;
    MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View customView =  inflater.inflate(R.layout.fragment_sign_up, container, false);



        mainActivity = (MainActivity) getActivity();
        mCallback = (SignupListener) getActivity();

        tvLogo = (TextView)customView.findViewById(R.id.tvLogo);
        tilName = (TextInputLayout) customView.findViewById(R.id.tilName);
        tilEmailId = (TextInputLayout) customView.findViewById(R.id.tilEmailId);

        tilPassword = (TextInputLayout) customView.findViewById(R.id.tilPassword);
        bRegister = (Button) customView.findViewById(R.id.bRegister);
        tvAlreadyReg = (TextView) customView.findViewById(R.id.tvAlreadyReg);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = tilName.getEditText().getText().toString();
                emailid = tilEmailId.getEditText().getText().toString();
                password = tilPassword.getEditText().getText().toString();

                if(name.isEmpty()) {
                    tilName.getEditText().setError("Please enter your Name");
                }else if (emailid.isEmpty()) {
                    tilEmailId.getEditText().setError("Please enter your Email-Id");
                }else if (password.isEmpty()) {
                    tilPassword.getEditText().setError("Please enter Password");
                }else if(!isValidName(name)) {
                    tilName.getEditText().setError("Name is too short");
                }else if (!isValidEmailAddress(emailid)) {
                    tilEmailId.getEditText().setError("Please enter a valid Email Address");
                }else if (!isValidPassword(password)) {
                    tilPassword.getEditText().setError("Password is too short");
                }else{

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                    Person person = new Person();
                    person.setPersonId("person"+ UUID.randomUUID().toString());
                    person.setName(name);
                    person.setEmail(emailid);
                    person.setPassword(password);
                    person.setWorkshops("");
                    if(dataBaseHelper.registerUser(person)){
                        mCallback.onSignUp();
                        Toast.makeText(getContext(),"User Registered Succesfully. \n Login to Apply",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(),"Sorry! Error in Registration",Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });


        tvAlreadyReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onLoginClicked();
            }
        });


        return customView;
    }


    public boolean isValidName(String name){
        if(name.length()>=1){
            return  true;
        } else {
            return false;
        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public boolean isValidPhone(String password){
        if(password.length()>=6){
            return  true;
        } else {
            return false;
        }
    }



    public boolean isValidPassword(String password){
        if(password.length()>=6){
            return  true;
        } else {
            return false;
        }
    }


}
