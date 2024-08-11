package com.example.jeet.tenncricclub;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class tournamentAdapter extends RecyclerView.Adapter<tournamentAdapter.tournamentdataviewholder> {

    static LinearLayout linearcard;
    static View v;
    static int i;
    private ArrayList<TournamentModel> tournamentlist;

    public tournamentAdapter(ArrayList<TournamentModel> list) {
        this.tournamentlist = list;
    }

    public static void setColor() {

    }

    @Override
    public tournamentdataviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_card, parent, false);
        linearcard = v.findViewById(R.id.linearcard);
        fetchdata();
        tournamentdataviewholder tvs = new tournamentdataviewholder(v);
        return tvs;
    }

    @Override
    public void onBindViewHolder(tournamentdataviewholder holder, int position) {
        TournamentModel tournamentdateholder = tournamentlist.get(position);
        holder.team1.setText(tournamentdateholder.getTeamname1());
        holder.time.setText(tournamentdateholder.getTime());
        holder.date.setText(tournamentdateholder.getDate());
        holder.team2.setText(tournamentdateholder.getTeamname2());
    }

    public void fetchdata() {
        Home.c.startService(new Intent(Home.c, playerjoin_service.class));
        Log.e("Service ", "Called");
        //Intent intent=new Intent();


    }

    @Override
    public int getItemCount() {
        return tournamentlist.size();
    }

    public static class tournamentdataviewholder extends RecyclerView.ViewHolder {
        private TextView team1;
        private TextView date;
        private TextView time;

        private TextView team2;

        public tournamentdataviewholder(View itemView) {
            super(itemView);
            team1 = (TextView) itemView.findViewById(R.id.team1);
            team2 = (TextView) itemView.findViewById(R.id.team2);

            date = (TextView) itemView.findViewById(R.id.date);

            time = (TextView) itemView.findViewById(R.id.time);


        }


    }
}
