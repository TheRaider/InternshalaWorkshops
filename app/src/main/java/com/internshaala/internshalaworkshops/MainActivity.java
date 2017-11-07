package com.internshaala.internshalaworkshops;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SignUpFragment.SignupListener,LoginFragment.LoginListener ,WorkshopsFragment.WorkShopListener{


    int prevId = 0;
    Toolbar toolbar;
    NavigationView navigationView;
    boolean loggedIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("IS Workshops");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loggedIn = sharedPreferences.getBoolean("loggedIn",false);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));



        if(loggedIn){
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_login_signup).setVisible(false);
            navMenu.findItem(R.id.nav_dashboard).setVisible(true);
            navMenu.findItem(R.id.nav_logout).setVisible(true);


        }else{
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_login_signup).setVisible(true);
            navMenu.findItem(R.id.nav_dashboard).setVisible(false);
            navMenu.findItem(R.id.nav_logout).setVisible(false);

        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //donot reload on same item is reselected

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_workshops) {
            toolbar.setTitle("Workshops");
            // Handle the camera action
            if (prevId != id) {
                WorkshopsFragment workshopsFragment = new WorkshopsFragment();
                transaction.replace(R.id.fragment_container, workshopsFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack("0");
                transaction.commit();
                prevId = id;
            }
        } else if (id == R.id.nav_login_signup) {
            toolbar.setTitle("Login/Signup");
            if (prevId != id) {
                LoginFragment loginFragment = new LoginFragment();
                transaction.replace(R.id.fragment_container, loginFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack("2");
                transaction.commit();
                prevId = id;
            }

        } else if (id == R.id.nav_dashboard) {
            toolbar.setTitle("DashBoard");
            if (prevId != id) {
                DashBoardFragment dashBoardFragment = new DashBoardFragment();
                transaction.replace(R.id.fragment_container, dashBoardFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack("1");
                transaction.commit();
                prevId = id;
            }

        } else if (id == R.id.nav_logout) {



            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Are you sure?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();

                    editor.putBoolean("loggedIn",false);
                    editor.commit();


                    Menu navMenu = navigationView.getMenu();
                    navMenu.findItem(R.id.nav_login_signup).setVisible(true);
                    navMenu.findItem(R.id.nav_dashboard).setVisible(false);
                    navMenu.findItem(R.id.nav_logout).setVisible(false);

                    navigationView.setCheckedItem(R.id.nav_login_signup);
                    onNavigationItemSelected(navigationView.getMenu().getItem(2));

                    Toast.makeText(MainActivity.this,"Successfully Logged out",Toast.LENGTH_LONG).show();

                    dialog.cancel();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setMessage("You want to log out?");
            builder.setCancelable(true);
            AlertDialog alertDialog1 = builder.create();
            alertDialog1.getWindow().setWindowAnimations(R.style.DialogAnimationCentreAlert);
            alertDialog1.show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    boolean doubleBackToExitPressedOnce = false;
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            int count = getSupportFragmentManager().getBackStackEntryCount();

            if (count == 1) {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    finish();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Snackbar.make(findViewById(android.R.id.content), "Please click BACK again to exit", Snackbar.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);


            } else {
                getSupportFragmentManager().popBackStack();
                int pos = Integer.parseInt(getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 2).getName());


                switch (pos) {
                    case 0:
                        navigationView.setCheckedItem(R.id.nav_workshops);
                        prevId = R.id.nav_workshops;
                        break;
                    case 1:

                        navigationView.setCheckedItem(R.id.nav_dashboard);
                        prevId = R.id.nav_dashboard;
                        break;
                    case 2:
                        navigationView.setCheckedItem(R.id.nav_login_signup);
                        prevId = R.id.nav_login_signup;
                        break;
                }
            }
        }

    }

    @Override
    public void onSignUpClicked() {
        toolbar.setTitle("Login/Signup");
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SignUpFragment signUpFragment = new SignUpFragment();
        transaction.replace(R.id.fragment_container, signUpFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack("2");
        transaction.commit();
        prevId = R.id.nav_login_signup;
        navigationView.setCheckedItem(R.id.nav_login_signup);

    }

    @Override
    public void onLogin(String username, String email) {
        Menu navMenu = navigationView.getMenu();
        navMenu.findItem(R.id.nav_login_signup).setVisible(false);
        navMenu.findItem(R.id.nav_dashboard).setVisible(true);
        navMenu.findItem(R.id.nav_logout).setVisible(true);

        toolbar.setTitle("DashBoard");
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DashBoardFragment dashBoardFragment = new DashBoardFragment();
        transaction.replace(R.id.fragment_container, dashBoardFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack("1");
        transaction.commit();
        prevId = R.id.nav_dashboard;
        navigationView.setCheckedItem(R.id.nav_dashboard);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("loggedIn",true);
        editor.putString("username",username);
        editor.putString("email",email);
        editor.commit();

        Toast.makeText(getBaseContext(),"Logged In Successfully",Toast.LENGTH_LONG).show();




    }

    @Override
    public void onSignUp() {
        toolbar.setTitle("Login/Signup");
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        transaction.replace(R.id.fragment_container, loginFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack("2");
        transaction.commit();
        prevId = R.id.nav_login_signup;
        navigationView.setCheckedItem(R.id.nav_login_signup);

    }

    @Override
    public void onLoginClicked() {
        toolbar.setTitle("Login/Signup");
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        transaction.replace(R.id.fragment_container, loginFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack("2");
        transaction.commit();
        prevId = R.id.nav_login_signup;
        navigationView.setCheckedItem(R.id.nav_login_signup);
    }

    @Override
    public void OnApplyClicked() {
        toolbar.setTitle("Login/Signup");
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        transaction.replace(R.id.fragment_container, loginFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack("2");
        transaction.commit();
        prevId = R.id.nav_login_signup;
        navigationView.setCheckedItem(R.id.nav_login_signup);
        Snackbar.make(findViewById(android.R.id.content), "Please Login to Apply", Snackbar.LENGTH_LONG).show();

    }
}
