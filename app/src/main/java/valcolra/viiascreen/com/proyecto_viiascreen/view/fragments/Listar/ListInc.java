package valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.Listar;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import valcolra.viiascreen.com.proyecto_viiascreen.CrearChecking;
import valcolra.viiascreen.com.proyecto_viiascreen.CrearIncidencia;
import valcolra.viiascreen.com.proyecto_viiascreen.R;
import valcolra.viiascreen.com.proyecto_viiascreen.adapter.CheckingAdapter;
import valcolra.viiascreen.com.proyecto_viiascreen.adapter.IncAdapter;
import valcolra.viiascreen.com.proyecto_viiascreen.model.ModelTask;

import static java.util.Collections.reverse;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListInc extends Fragment {

    ListView listView;
    ArrayAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    public ListInc() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.fragment_list_inc, container, false);
        swipeRefreshLayout =  v.findViewById(R.id.swiperefreshInc);
        swipeRefreshLayout.setSize(8);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                listView =  v.findViewById(R.id.listViewInc);
                adapter = new IncAdapter(getContext());
                listView.setAdapter(adapter);
            }
        });

        adapter = new IncAdapter(getContext());
        listView =  v.findViewById(R.id.listViewInc);

        listView.setAdapter(adapter);


        FloatingActionButton fabCrearIncidente = v.findViewById(R.id.fabCrearInc);
        fabCrearIncidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Crear Inc", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                Intent i = new Intent(getActivity(), CrearIncidencia.class);
                startActivity(i);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new IncAdapter(getContext());
        listView.setAdapter(adapter);
    }

}
