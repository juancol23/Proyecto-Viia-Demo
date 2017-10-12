package valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcLateral;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import valcolra.viiascreen.com.proyecto_viiascreen.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViiaRoles extends Fragment {


    public ViiaRoles() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =   inflater.inflate(R.layout.fragment_viia_roles, container, false);
        ImageView viia_roles = view.findViewById(R.id.viia_roles);
        Glide.with(this).load(R.drawable.viia_role_min).into(viia_roles);
        return view;
    }

}
