package valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcDash;


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
public class ViiaPlayer extends Fragment {


    public ViiaPlayer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_viia_player, container, false);
        ImageView viia_player = view.findViewById(R.id.viia_player);
        Glide.with(this).load(R.drawable.viia_player_minn).into(viia_player);
        return view;
    }

}
