package valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.Listar;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import valcolra.viiascreen.com.proyecto_viiascreen.CrearIncidencia;
import valcolra.viiascreen.com.proyecto_viiascreen.DetalleIncidente;
import valcolra.viiascreen.com.proyecto_viiascreen.R;
import valcolra.viiascreen.com.proyecto_viiascreen.adapter.CheckingAdapter;
import valcolra.viiascreen.com.proyecto_viiascreen.adapter.IncidenteAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListarIncidencias extends Fragment {
    ListView listView;
    ArrayAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public ListarIncidencias() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_listar_incidencia, container, false);

        swipeRefreshLayout =  view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setSize(8);

         swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    listView =  view.findViewById(R.id.listView2);
                    adapter = new IncidenteAdapter(getContext());
                    listView.setAdapter(adapter);
                }
            });

        listView =  view.findViewById(R.id.listView2);
        adapter = new IncidenteAdapter(getContext());
        listView.setAdapter(adapter);

        FloatingActionButton fabCrearIncidente =   view.findViewById(R.id.fabCrearIncidente);
        fabCrearIncidente.setBackgroundColor(getResources().getColor(R.color.floatcolor));
        fabCrearIncidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Creando Incidencia", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(getActivity(), CrearIncidencia.class);
                startActivity(i);
            }
        });

        return view;
    }

    /*Esto lo uso para actualizar cuando regreso o algo por que por ahora no hay socket inplementado*/
    @Override
    public void onStart() {
        super.onStart();
        adapter = new IncidenteAdapter(getContext());
        listView.setAdapter(adapter);
    }
    /*Esto lo uso para actualizar cuando regreso o algo por que por ahora no hay socket inplementado*/
    @Override
    public void onResume() {
        super.onResume();
        adapter = new IncidenteAdapter(getContext());
        listView.setAdapter(adapter);
    }
}