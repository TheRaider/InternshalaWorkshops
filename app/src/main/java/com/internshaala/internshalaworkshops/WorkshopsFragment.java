package com.internshaala.internshalaworkshops;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkshopsFragment extends Fragment {


    private ArrayList<Workshop> workshopArrayList = new ArrayList<>();
    private ArrayList<Workshop> dashBoardArrayList = new ArrayList<>();

    WorkShopsAdapter mAdapter;
    RecyclerView rvWorkshops;

    boolean loggedIn;

    public interface WorkShopListener {
        public void OnApplyClicked();
    }


    WorkShopListener mCallback;
    MainActivity mainActivity;

    String username,email;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View customView = inflater.inflate(R.layout.fragment_workshops, container, false);


        mainActivity = (MainActivity) getActivity();
        mCallback = (WorkShopListener) getActivity();


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        loggedIn = sharedPreferences.getBoolean("loggedIn",false);
        username = sharedPreferences.getString("username","username");
        email= sharedPreferences.getString("email","email");

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        workshopArrayList = dataBaseHelper.getWorkshops();
        dashBoardArrayList = dataBaseHelper.getUserWorkshops(username,email);

        for(Workshop workshopD:dashBoardArrayList){
            for(int i=0;i<workshopArrayList.size();i++){
                Log.d("IDs",workshopD.getWorkshopId().trim() + " : " + workshopArrayList.get(i).getWorkshopId().trim() );
                if(workshopD.getWorkshopId().trim().equalsIgnoreCase(workshopArrayList.get(i).getWorkshopId().trim())){
                    Workshop workshop = workshopArrayList.get(i);
                    workshop.setApplied(true);
                    workshopArrayList.set(i,workshop);
                }
            }
        }

        mAdapter = new WorkShopsAdapter();

        rvWorkshops = (RecyclerView) customView.findViewById(R.id.rvWorkshops);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvWorkshops.setLayoutManager(linearLayoutManager);

        rvWorkshops.setItemAnimator(new ScaleInAnimator(new OvershootInterpolator(1f)));
        ScaleInAnimationAdapter adapter = new ScaleInAnimationAdapter(mAdapter);
        adapter.setDuration(400);

        rvWorkshops.setAdapter(adapter);


        if(loggedIn){

        }
        return customView;
    }

    class WorkShopsAdapter extends RecyclerView.Adapter<WorkShopsAdapter.WorkshopsViewHolder>{


        class WorkshopsViewHolder extends RecyclerView.ViewHolder{

            TextView tvWorkshopTitle,tvCompany,tvLocation,tvDuration,tvCost,tvApplyBy;
            Button bApply;

            WorkshopsViewHolder(View itemView) {
                super(itemView);
                tvWorkshopTitle = (TextView) itemView.findViewById(R.id.tvWorkshopTitle);
                tvCompany = (TextView) itemView.findViewById(R.id.tvCompany);
                tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
                tvDuration = (TextView) itemView.findViewById(R.id.tvDuration);
                tvCost = (TextView) itemView.findViewById(R.id.tvCost);
                tvApplyBy = (TextView) itemView.findViewById(R.id.tvApplyBy);
                 bApply = (Button) itemView.findViewById(R.id.bApply);



            }
        }

        @Override
        public WorkshopsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_workshop, parent, false);
            return new WorkshopsViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final WorkshopsViewHolder holder, int position) {
            final Workshop workshop = workshopArrayList.get(position);

            holder.tvWorkshopTitle.setText(workshop.getTitle());
            holder.tvCompany.setText(workshop.getCompany());
            holder.tvLocation.setText(workshop.getLocation());
            holder.tvDuration.setText(workshop.getDuration());
            holder.tvCost.setText(workshop.getCost());
            holder.tvApplyBy.setText(workshop.getApplyby());

            if(workshop.isApplied()){
                holder.bApply.setText("APPLIED");
                holder.bApply.setOnClickListener(null);
            }else{
                holder.bApply.setText("APPLY");
                holder.bApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!loggedIn) {
                            holder.bApply.setText("APPLY");
                            mCallback.OnApplyClicked();
                        }else{

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setCancelable(true);
                            builder.setTitle("Are you sure to Apply?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                                    dataBaseHelper.addWorkshop(username,email,workshop.getWorkshopId());
                                    Toast.makeText(getContext(),"Applied to Workshop",Toast.LENGTH_SHORT).show();
                                    holder.bApply.setText("APPLIED");
                                    holder.bApply.setOnClickListener(null);

                                    dialog.cancel();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.setMessage("You want to Apply?");
                            builder.setCancelable(true);
                            AlertDialog alertDialog1 = builder.create();
                            alertDialog1.getWindow().setWindowAnimations(R.style.DialogAnimationCentreAlert);
                            alertDialog1.show();

                        }
                    }
                });
            }


        }


        @Override
        public int getItemCount() {
            return workshopArrayList.size();
        }
    }

}
