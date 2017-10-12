package valcolra.viiascreen.com.proyecto_viiascreen.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import valcolra.viiascreen.com.proyecto_viiascreen.R;

public class DesignInc extends AppCompatActivity {

    /*en este caso creamos una actividad para referenciar un diseño y sea mas facil buscarlo*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_inc);

    }
}
