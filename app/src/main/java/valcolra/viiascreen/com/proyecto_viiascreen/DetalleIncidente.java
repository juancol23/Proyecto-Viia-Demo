package valcolra.viiascreen.com.proyecto_viiascreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import valcolra.viiascreen.com.proyecto_viiascreen.R;

/**
 * Created by Vic on 27/09/2017.
 */

public  class DetalleIncidente extends AppCompatActivity {
    TextInputEditText m_detalle_punto, m_detalle_fecha, m_detalle_localizacion, m_detalle_direccion, m_detalle_status, m_detalle_creado_por, m_detalle_observacion;
    ImageView m_detalle_imagen;
    Bitmap bitmap;


    TextView timerTextView;
    long startTime = 0;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 1000);
        }
    };


    private static final String URL_BASE = "http://api.everlive.com/v1/vqfkdc51v767oq7x/";
    private static final String URL_JSON = "viiascreen_incidentes/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_incidente);
      /*Inicializamos*/
        RequestQueue queue = Volley.newRequestQueue(this);

        showToolbar("", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade());
        }

        FloatingActionButton fabEnviarTarea = (FloatingActionButton) findViewById(R.id.fabEnviarTarea);
        fabEnviarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ahora es una tarea", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


        m_detalle_imagen =  findViewById(R.id.imageHeader2);
        m_detalle_punto =  findViewById(R.id.detalle_punto);
        m_detalle_fecha = findViewById(R.id.detalle_fecha);
        m_detalle_localizacion = findViewById(R.id.detalle_localizacion);
        m_detalle_direccion = findViewById(R.id.detalle_direccion);
        m_detalle_status =  findViewById(R.id.detalle_status);
        m_detalle_creado_por =  findViewById(R.id.detalle_creado_por);
        m_detalle_observacion =  findViewById(R.id.detalle_observacion);


             /*Obtener Data*/
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();

        Bitmap bitmap = getIntent().getParcelableExtra("image");
        Log.v("bitmappppp",""+bitmap);

        m_detalle_imagen.setImageBitmap(bitmap);
        final String IDs = (String) extras.get("ID"); //Obtengo el nombre
        String estado = (String) extras.get("estado"); //Obtengo el nombre

        String punto = (String)extras.get("punto");
        String area = (String)extras.get("area");


        String ClosedCheck = (String)extras.get("ClosedCheck");
        String CreateAt = (String)extras.get("CreateAt");
        String observacion1 = (String)extras.get("observacion1");
        String observacion2 = (String)extras.get("observacion2");
        String responsable = (String)extras.get("responsable");


            FloatingActionButton fabEditarTarea = (FloatingActionButton) findViewById(R.id.fabEnviarTarea);
            fabEditarTarea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Convertido a tarea", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                     convertirToTask(IDs);
                }
            });

        timerTextView = findViewById(R.id.timerTextView);
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 9);

    }

    private void convertirToTask(String ID) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,URL_BASE+URL_JSON+ID ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(DetalleIncidente.this,"Actualizado Correctamente "+response,Toast.LENGTH_LONG).show();
               /* Intent i = new Intent(DetalleIncidente.this,MainActivity.class);
                startActivity(i);*/
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetalleIncidente.this,"Hubo un error al actualizar"+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                params.put("estado","cerrado");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    public void showToolbar(String tittle, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

    }
}



