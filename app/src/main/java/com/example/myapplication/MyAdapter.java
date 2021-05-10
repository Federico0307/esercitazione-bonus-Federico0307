package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "RecyclerAdapter";
    private ArrayList<Persona> persone, persone_all;
    Persona user;
    private Context context;

    public MyAdapter(ArrayList<Persona> persone, Context context, Persona user) {
        this.persone = persone;
        this.context = context;
        this.user = user;

        persone.remove(user);
        persone_all = new ArrayList<>();
        persone_all.addAll(persone);
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Persona user = persone.get(position);
        holder.nome.setText(user.getUsername());
        holder.tipo.setText("Ruolo: " + user.getType());
        holder.user = user;

        if (user.getType().equals("admin"))
        {
            holder.promuovi.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return persone.size();
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {

        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<Persona> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(persone_all);
            } else {
                for (Persona user: persone_all) {
                    if (user.getUsername().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(user);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            persone.clear();
            //forse al posto di Arraylist va Persona
            persone.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        Persona user;
        TextView nome, messaggio, tipo;
        Button promuovi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nome);
            tipo = itemView.findViewById(R.id.tipo);
            promuovi = itemView.findViewById(R.id.promuovi);
            messaggio = itemView.findViewById(R.id.promosso);

            promuovi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.setType("admin");

                    promuovi.setVisibility(View.GONE);
                    messaggio.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
