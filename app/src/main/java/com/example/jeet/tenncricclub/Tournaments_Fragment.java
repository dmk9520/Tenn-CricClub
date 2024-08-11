package com.example.jeet.tenncricclub;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.VISIBLE;

/**
 * Created by Dhruvit Katrodiya on 21-02-2018.
 */

public class Tournaments_Fragment extends Fragment {
    public static tournamentAdapter mAdapter;
    static TournamentModel tm1;

    private static ArrayList<TournamentModel> tournamentlistdata = new ArrayList<>();
    private static RecyclerView recyclerView;
    ArrayList<TournamentModel> list = new ArrayList<>();
    FloatingActionButton fab;
    String type;
    private RecyclerView.LayoutManager mLayoutManager;

    public static void setData() {
        if (tm1 == null) {
            // while()
            for (int i = 0; tournamentfetch_service.ttime.size() != i; i++) {
                tm1 = new TournamentModel(tournamentfetch_service.teamjson1.get(i), tournamentfetch_service.teamjson2.get(i), tournamentfetch_service.tdate.get(i), tournamentfetch_service.ttime.get(i));

                tournamentlistdata.add(tm1);
            }
            try {
                mAdapter = new tournamentAdapter(tournamentlistdata);
                Log.e("sdasfas", "Data added in recycler view");
                recyclerView.setAdapter(mAdapter);
                // notify adapter about data set changes
                // so that it will render the list with new data
                mAdapter.notifyDataSetChanged();
            }
            catch(Exception e){

            }
        } else {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tournaments, container, false);

        //getActivity().registerReceiver(new FragmentReceiver1(), new IntentFilter("fragmentupdater"));
        SharedPreferences sp = getActivity().getSharedPreferences("loginprefrence", MODE_PRIVATE);
        type = sp.getString("type", null);
        fab = view.findViewById(R.id.floatcreate);
        if (type.equals("Organizer")) {
            fab.setVisibility(VISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), create_game.class));
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        View view = getView();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.rcview);


        recyclerView.setHasFixedSize(false);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new tournamentAdapter(tournamentlistdata);
        recyclerView.setAdapter(mAdapter);

        // row click listener
/*
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movie movie = movieList.get(position);
                Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

*/
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TournamentModel model = tournamentlistdata.get(position);
                Intent intent = new Intent(getActivity(), match_details.class);
                startActivity(intent);

                SharedPreferences sp = getActivity().getSharedPreferences("matchdetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                for (int i = 0; tournamentfetch_service.ttime.size() != i; i++) {

                    if (i == position) {
                        editor.putString("teamone", tournamentfetch_service.teamjson1.get(position));
                        editor.putString("teamtwo", tournamentfetch_service.teamjson2.get(position));
                        editor.putString("date", tournamentfetch_service.tdate.get(position));
                        editor.putString("time", tournamentfetch_service.ttime.get(position));
                        editor.putString("venue", tournamentfetch_service.tvenue.get(position));
                        editor.putString("gname", tournamentfetch_service.tgname.get(position));
                        editor.putString("mobile", tournamentfetch_service.tmobile.get(position));

                        editor.apply();
                    } else {
                        Log.e("Value of i : ", "" + i);
                    }
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        prepareMovieData();
    }

    /**
     * Prepares sample data to provide data set to adapter
     */
    private void prepareMovieData() {

        getActivity().startService(new Intent(getActivity(), tournamentfetch_service.class));

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //Write down your refresh code here, it will call every time user come to this fragment.
            //If you are using listview with custom adapter, just call notifyDataSetChanged().
            mAdapter.notifyDataSetChanged();
        }
    }

    interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

}
