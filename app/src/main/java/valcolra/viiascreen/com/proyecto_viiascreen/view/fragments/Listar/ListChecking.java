package valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.Listar;


import android.content.Context;
import android.content.Intent;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import valcolra.viiascreen.com.proyecto_viiascreen.CrearChecking;
import valcolra.viiascreen.com.proyecto_viiascreen.CrearTask;
import valcolra.viiascreen.com.proyecto_viiascreen.DetalleCompleteChecking;
import valcolra.viiascreen.com.proyecto_viiascreen.R;
import valcolra.viiascreen.com.proyecto_viiascreen.adapter.CheckingAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListChecking extends Fragment {
    ListView listView;
    ArrayAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public ListChecking() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_list_checking, container, false);

        swipeRefreshLayout =  v.findViewById(R.id.swiperefreshTask);
        swipeRefreshLayout.setSize(8);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                listView =  v.findViewById(R.id.listViewChecking);
                adapter = new CheckingAdapter(getContext());
                listView.setAdapter(adapter);
            }
        });

        listView =  v.findViewById(R.id.listViewChecking);
        adapter = new CheckingAdapter(getContext());
        listView.setAdapter(adapter);

        FloatingActionButton fabCrearIncidente = v.findViewById(R.id.fabCrearChecking);
        fabCrearIncidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Crear Checking", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
               Intent i = new Intent(getActivity(), CrearChecking.class);
               startActivity(i);
            }
        });

        fabCrearIncidente.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.rojoLock)));

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new CheckingAdapter(getContext());
        listView.setAdapter(adapter);
    }
}