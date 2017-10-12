package valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.Listar;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import valcolra.viiascreen.com.proyecto_viiascreen.CrearTask;
import valcolra.viiascreen.com.proyecto_viiascreen.R;
import valcolra.viiascreen.com.proyecto_viiascreen.adapter.TaskAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListarTask extends Fragment {

    ListView listView;
    ArrayAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    public ListarTask() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.fragment_listar_task2, container, false);
        swipeRefreshLayout =  v.findViewById(R.id.swiperefreshTask);
        swipeRefreshLayout.setSize(8);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                listView =  v.findViewById(R.id.listViewTash);
                adapter = new TaskAdapter(getContext());
                listView.setAdapter(adapter);
            }
        });

        listView =  v.findViewById(R.id.listViewTash);
        adapter = new TaskAdapter(getContext());
        listView.setAdapter(adapter);

        FloatingActionButton fabCrearIncidente = v.findViewById(R.id.fabCrearTask);
        fabCrearIncidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Crear Task", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(getActivity(), CrearTask.class);
                startActivity(i);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new TaskAdapter(getContext());
        listView.setAdapter(adapter);
    }

}
