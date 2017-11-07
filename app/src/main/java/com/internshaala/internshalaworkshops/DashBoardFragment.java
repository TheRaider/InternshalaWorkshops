package com.internshaala.internshalaworkshops;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {


    public DashBoardFragment() {
        // Required empty public constructor
    }

    private ArrayList<Workshop> dashBoardArrayList = new ArrayList<>();
    LinearLayout llNoWorkshops;


    DashBoardAdapter mAdapter;
    RecyclerView rvDashboard;

    boolean loggedIn;
    String username,email;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View customView = inflater.inflate(R.layout.fragment_dash_board, container, false);



        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        loggedIn = sharedPreferences.getBoolean("loggedIn",false);
        username = sharedPreferences.getString("username","username");
        email= sharedPreferences.getString("email","email");

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        dashBoardArrayList = dataBaseHelper.getUserWorkshops(username,email);
       // dashBoardArrayList = dataBaseHelper.getWorkshops();
        llNoWorkshops = (LinearLayout) customView.findViewById(R.id.llNoWorkshops);


        if(dashBoardArrayList.size()==0){
            llNoWorkshops.setVisibility(View.VISIBLE);
        }else{
            llNoWorkshops.setVisibility(View.GONE);
        }
        mAdapter = new DashBoardAdapter();

        rvDashboard = (RecyclerView) customView.findViewById(R.id.rvDashboard);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvDashboard.setLayoutManager(linearLayoutManager);

        rvDashboard.setItemAnimator(new ScaleInAnimator(new OvershootInterpolator(1f)));
        ScaleInAnimationAdapter adapter = new ScaleInAnimationAdapter(mAdapter);
        adapter.setDuration(400);

        rvDashboard.setAdapter(adapter);


        return customView;


    }

    @Override
    public void onResume() {
        super.onResume();

//        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
//        dashBoardArrayList.clear();
//        dashBoardArrayList.addAll(dataBaseHelper.getUserWorkshops(username,email));
//
//        mAdapter.notifyDataSetChanged();




    }

    class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.DashBoardViewHolder>{


        class DashBoardViewHolder extends RecyclerView.ViewHolder{

            TextView tvWorkshopTitle,tvCompany,tvLocation,tvDuration,tvCost,tvApplyBy;
            Button bApply;

            DashBoardViewHolder(View itemView) {
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
        public DashBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_workshop, parent, false);
            return new DashBoardViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final DashBoardViewHolder holder, int position) {
            final Workshop workshop = dashBoardArrayList.get(position);

            holder.tvWorkshopTitle.setText(workshop.getTitle());
            holder.tvCompany.setText(workshop.getCompany());
            holder.tvLocation.setText(workshop.getLocation());
            holder.tvDuration.setText(workshop.getDuration());
            holder.tvCost.setText(workshop.getCost());
            holder.tvApplyBy.setText(workshop.getApplyby());

            holder.bApply.setText("APPLIED");
        }


        @Override
        public int getItemCount() {
            return dashBoardArrayList.size();
        }
    }

}
