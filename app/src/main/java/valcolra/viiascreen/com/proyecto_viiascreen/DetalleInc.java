package valcolra.viiascreen.com.proyecto_viiascreen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import valcolra.viiascreen.com.proyecto_viiascreen.model.ModelInc;

public class DetalleInc extends AppCompatActivity {


    private static final String URL_BASE = "http://api.everlive.com/v1/vqfkdc51v767oq7x/";
    private static final String URL_JSON = "viiascreen_incidentes/";


    private RequestQueue requestQueue;

    private ImageView m_imageDetalleIncHeader;
    private TextInputEditText m_imageDetalleIncId;
    private TextInputEditText m_imageDetalleIncArea;
    private TextInputEditText m_imageDetalleIncEstado;

    private TextInputEditText m_imageDetalleIncUsuario;
    private TextInputEditText m_imageDetalleIncClosedTask;
    private TextInputEditText m_imageDetalleIncCreateAt;
    private TextInputEditText m_imageDetalleIncObservacion1;
    private TextInputEditText m_imageDetalleIncObservacion2;
    private TextInputEditText m_imageDetalleIncObservacion3;
    private TextInputEditText m_imageDetalleIncServicio;
    private TextInputEditText m_imageDetalleIncUrlImagen1;
    private TextInputEditText m_imageDetalleIncUrlImagen2;
    private AutoCompleteTextView m_imageDetalleIncPunto;
    private TextInputEditText m_imageDetalleIncCreatedTask;

    FloatingActionButton m_fab_imageDetalle_converTask;
    String[] PuntosArreglo = {"SJM-004 A","SUR-028 A","SUR-026 B","SUR-010 A","SUR-027 A","SUR-027 B","LAV-004 A","LAV-004 B","LAV-007 A","SIN-001 A","JM-002 U","MRN-004 A","VMT-010 A","ANT-001 A","CAL-001 A","IND-021 A","IND-021 B","SJL-009 A","SNM 013","JM 009 A","SNM 003 A","CAL 004 A","SJM 002 A","LMNA 01 B","SRQ 05 A","IND 006 A","CAL 014 A","CAL 018 A","SNBOR 001 A","SNM 009 A","LAV 008 A","ATE 013 A","PIU-055 A","CHI-004 A","TRU-046 U","ARQ-044 A","CUS 018 A","CAJ S/N","HUA S/N","ICA S/N"};
    String[] Area = {"Operaciones","Comercial","Logística","Mantenimiento"};
    String[] PuntosUbicacion = {"SAN JUAN DE MIRAFLORES","SURCO", "SURCO", "SURCO", "SURCO", "SURCO", "LA VICTORIA", "LA VICTORIA", "LA VICTORIA", "SAN ISIDRO", "JESUS MARIA", "SAN MIGUEL", "VILLA MARIA DEL TRIUNFO", "SANTA ANITA", "CALLAO", "INDEPENDENCIA", "INDEPENDENCIA", "SAN JUAN DE LURIGANCHO", "SAN MIGUEL", "JESUS MARIA", "SAN MIGUEL", "CALLAO", "SAN JUAN DE MIRAFLORES", "LA MOLINA", "SURQUILLO", "INDEPENDENCIA", "CALLAO", "CALLAO", "SAN BORJA", "SAN MIGUEL", "LA VICTORIA", "ATE", "PIURA", "CHICLAYO", "TRUJILLO", "AREQUIPA", "CUSCO", "CAJAMARCA", "HUANCAYO", "ICA"};
    String[] DireccionPuntosArreglo = {"AV. LOS HÉROES S/N", "AV BENAVIDES CDRA N°55", "AV. PRIMAVERA Nº 1357 CRUCE CON AV. EL POLO", "AV. EL DERBY CON AV. MANUEL OLGUIN", "AV. EL DERBY CON AV. EL POLO", "AV. EL DERBY CON AV. EL POLO", "AV. JAVIER PRADO 1094", "AV. JAVIER PRADO 1094", "AV. PASEO DE LA REPUBLICA CON AV CANADA", "AV. REPÚBLICA DE PANAMÁ CRUCE CON AV. TOMÁS MARSANO", "AV. 28 DE JULIO CON AV. FELIPE SALAVERRY", "AV. LA MARINA CDRA 20-SAN MIGUEL", "AV. PACHACUTEC CRUCE CON AV. 26 DE NOVIEMBRE", "AV. NICOLÁS AYLLÓN (CARRETERA CENTRAL)", "AV. FAUCETT ENTRADA AL AEROPUERTO", "PANAMERICANA NORTE CRUCE CON TOMAS VALLE", "PANAMERICANA NORTE CRUCE CON TOMAS VALLE", "AV. 9 DE OCTUBRE CDRA. 8 (BERMA CENTRAL)", "AV. ELMER FAUCETT CRUCE CON LA CALLE PASO DE LOS ANDES", "AV. EDUARDO AVAROA CON AV. FAUSTINO SANCHEZ CARRION CUADRA 07", "AV. COSTANERA CRUCE CON AV. ESCARDO-SAN MIGUEL (BAJADA ESCARDO)", "AV. 28 DE JULIO, ESQUINA CON FAUCETT", "AV. LOS HÉROES Cdra. 2 CERCA A METRO / SAGA FALABELLA Y PLAZA VEA", "AV. RAÚL FERRERO 1496", "AV. PASEO DE LA REPUBLICA CDRA. 45 ESQUINA CON JR. LEONARDO DE BARBIERI CDRA. 6", "AV. TOMÁS VALLE CON AV. ALFREDO MENDIOLA", "AV. ARGENTINA CDRA. 30 FRENTE A MINKA", "AV. FAUCETT CDRA. 20", "AV. CANADÁ CRUCE CON AV. SAN LUIS", "AV. UNIVERSITARIA CUADRA 11 CRUCE CON LA MAR", "AV. CANADÁ CON AV. NICOLÁS ARRIOLA", "CARRETERA CENTRAL CRUCE CON CALLE ESTRELLA", "AV CACERES CRUCE CON AV IRRAZOLA (FRENTE AL OPEN PLAZA DE PIURA)", "AV. BOLOGNESI CDRA. 11 CON GRAU", "AV. AMERICA OESTE", "AV. EJERCITO CDRA. 3 CRUCE CON CALLE AMPATACOCHA", "AV. ALAMEDA PACHACUTEC", "SIN UBICACIÓN CONFIRMADA", "SIN UBICACIÓN CONFIRMADA", "SIN UBICACIÓN CONFIRMADA" };
    String[] ServicioArreglo = {"Módulo Malogrado","Mantenimiento","Limpieza de pantalla","Cambio de fuente","Tablero averiado","PC averiada","Limpieza"};

    /*Data hardcodeada estatica*/
    private TextInputEditText m_imageDetalleUbicacion;
    private TextInputEditText m_imageDetalleDireccion;

    /*Data hardcodeada estatica*/

    private FloatingActionButton m_fab_imageDetalle_editar;
    private FloatingActionButton m_fab_imageDetalle_save;
    private Dialog MyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_inc);



        showToolbar("Detalle Incidencia", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade());
        }
        requestQueue = Volley.newRequestQueue(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, PuntosArreglo);

         /*Obtener Data*/
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        /*Obtener data y guardarla*/
        Bitmap bitmap = getIntent().getParcelableExtra("image");


        final String Id = (String)extras.get("Id");
        String area = (String)extras.get("area");
        final String estado = (String)extras.get("estado");
        String usuario = (String)extras.get("usuario");
        String closedTask = (String)extras.get("closedTask");
        String createAt = (String)extras.get("createAt");
        String createdTask = (String)extras.get("createdTask");
        final String direccion = (String)extras.get("direccion");
        final String ubicacion = (String)extras.get("ubicacion");


        String observacion1 = (String)extras.get("observacion1");
        String observacion2 = (String)extras.get("observacion2");
        String observacion3 = (String)extras.get("observacion3");
        String servicio = (String)extras.get("servicio");
        String urlImagen1 = (String)extras.get("urlImagen1");
        String urlImagen2 = (String)extras.get("urlImagen2");
        final String punto = (String)extras.get("punto");

        Log.v("All-data",Id+area+estado+usuario);

        m_imageDetalleIncHeader  = findViewById(R.id.imageDetalleIncHeader);
        m_imageDetalleIncId = findViewById(R.id.imageDetalleIncId);
        m_imageDetalleIncArea = findViewById(R.id.imageDetalleIncArea);
        m_imageDetalleIncEstado = findViewById(R.id.imageDetalleIncEstado);
        m_imageDetalleUbicacion = findViewById(R.id.imageDetalleUbicacion);
        m_imageDetalleDireccion = findViewById(R.id.imageDetalleDireccion);
        m_imageDetalleIncUsuario = findViewById(R.id.imageDetalleIncUsuario);
        m_imageDetalleIncClosedTask = findViewById(R.id.imageDetalleIncClosedTask);
        m_imageDetalleIncCreateAt = findViewById(R.id.imageDetalleIncCreateAt);
        m_imageDetalleIncCreatedTask = findViewById(R.id.imageDetalleIncCreatedTask);
        m_imageDetalleIncObservacion1 = findViewById(R.id.imageDetalleIncObservacion1);
        m_imageDetalleIncObservacion2 = findViewById(R.id.imageDetalleIncObservacion2);
        m_imageDetalleIncObservacion3 = findViewById(R.id.imageDetalleIncObservacion3);
        m_imageDetalleIncServicio = findViewById(R.id.imageDetalleIncServicio);
        m_imageDetalleIncUrlImagen1 = findViewById(R.id.imageDetalleIncUrlImagen1);
        m_imageDetalleIncUrlImagen2 = findViewById(R.id.imageDetalleIncUrlImagen2);
        m_imageDetalleIncPunto = findViewById(R.id.imageDetalleIncPunto);

        Log.v("urlImagen1",urlImagen1);

        if(urlImagen1.equals("")){
            Log.v("Quehay","nada"+urlImagen1);
            m_imageDetalleIncHeader.setImageDrawable(getResources().getDrawable(R.drawable.image));
             m_imageDetalleIncHeader.setPadding(260,120,260,120);
             m_imageDetalleIncHeader.setPadding(260,120,260,120);
            m_imageDetalleIncHeader.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
            Log.v("Quehay","todo"+urlImagen1);
            Glide.with(getApplicationContext()).load(urlImagen1).into(m_imageDetalleIncHeader);
        }

        m_imageDetalleIncId.setText(Id);
        m_imageDetalleIncArea.setText(area);
        m_imageDetalleIncEstado.setText(estado);
        m_imageDetalleIncUsuario.setText(usuario);
        m_imageDetalleIncClosedTask.setText(closedTask);
        m_imageDetalleIncCreatedTask.setText(createdTask);
        m_imageDetalleIncCreateAt.setText(createAt);

        m_imageDetalleDireccion.setText(direccion);
        m_imageDetalleUbicacion.setText(ubicacion);

        m_imageDetalleIncObservacion1.setText(observacion1);
        m_imageDetalleIncObservacion2.setText(observacion2);
        m_imageDetalleIncObservacion3.setText(observacion3);
        m_imageDetalleIncServicio.setText(servicio);
        m_imageDetalleIncUrlImagen1.setText(urlImagen1);
        m_imageDetalleIncUrlImagen2.setText(urlImagen2);
        m_imageDetalleIncPunto.setText(punto);

        m_imageDetalleIncPunto.setThreshold(1);//will start working from first character
        m_imageDetalleIncPunto.setAdapter(adapter);//setting the adapter data into the

        m_fab_imageDetalle_editar = findViewById(R.id.fab_imageDetalle_editar);
        m_fab_imageDetalle_save = findViewById(R.id.fab_imageDetalle_save);

        if(estado.equals("tarea")){
            m_fab_imageDetalle_save.setVisibility(View.GONE);
            m_fab_imageDetalle_editar.setVisibility(View.GONE);
          //  m_fab_imageDetalle_converTask.setVisibility(View.GONE);
        }

        m_fab_imageDetalle_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            m_imageDetalleIncObservacion1.setEnabled(true);
            m_imageDetalleIncPunto.setEnabled(true);
            m_imageDetalleDireccion.setEnabled(true);
            m_fab_imageDetalle_editar.setVisibility(View.GONE);
                m_fab_imageDetalle_save.setVisibility(View.VISIBLE);
                m_imageDetalleIncPunto.setFocusable(true);
            }
        });

        m_fab_imageDetalle_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String punto_ = m_imageDetalleIncPunto.getText().toString();
                String p = punto_.replace(" ","");
                int matchIndex = -1;
                String ubicacionP ="";
                String direccionP ="";

                if(estado.equals("pendiente")){
                    m_imageDetalleIncEstado.setText("Visto");
                }

                int contador = 0;
                for(int w=0; w<PuntosArreglo.length; w++) {
                    if(PuntosArreglo[w].equals(punto_)) {
                        Log.v("Encontré",PuntosArreglo[w]);
                        matchIndex = w;
                        Log.v("Encontré", String.valueOf(matchIndex));
                        Log.v("ES0","Bien");
                        m_imageDetalleIncObservacion1.setEnabled(false);
                        m_imageDetalleIncPunto.setEnabled(false);
                        m_imageDetalleDireccion.setEnabled(false);
                        m_fab_imageDetalle_save.setVisibility(View.GONE);
                        m_fab_imageDetalle_editar.setVisibility(View.VISIBLE);
                        ubicacionP = PuntosUbicacion[w];
                        direccionP = DireccionPuntosArreglo[w];

                        contador++;
                        break;
                    }else{
                        //Log.v("ES0","Mal");
                    }
                }
                if(contador == 1){
                    editarIncidente(Id,view);
                    m_imageDetalleUbicacion.setText(ubicacionP);
                   // m_imageDetalleDireccion.setText(direccion);


                    contador =0;
                }else{
                    Snackbar.make(view, "Punto Invalido ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        m_fab_imageDetalle_converTask = findViewById(R.id.fab_imageDetalle_converTask);
        m_fab_imageDetalle_converTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Convertido a tarea", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                //convertirToTask(Id);
                MyCustomAlertDialog(Id);
            }
        });

        if(estado.equals("tarea")){
            m_fab_imageDetalle_editar.setVisibility(View.GONE);
            m_fab_imageDetalle_save.setVisibility(View.GONE);
            m_fab_imageDetalle_converTask.setVisibility(View.GONE);

        }
    }

    private void convertirToTask(String ID, final String obser, final String area) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,URL_BASE+URL_JSON+ID ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetalleInc.this,"Hubo un error al actualizar"+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String getFechaTelerik = simpleDateFormat.format(new Date());

                String punto = m_imageDetalleIncPunto.getText().toString();
                String direccion = m_imageDetalleDireccion.getText().toString();
                String observacion = m_imageDetalleIncObservacion1.getText().toString();
                params.put("punto", punto);
                params.put("direccion",direccion);
                params.put("observacion1",observacion);
                params.put("observacion2",obser);
                params.put("area",area);
                params.put("createdTask",getFechaTelerik);

                params.put("estado","tarea");
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



    private void editarIncidente(String ID, final View v) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,URL_BASE+URL_JSON+ID ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Snackbar.make(v, "Incidente Editado ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetalleInc.this,"Hubo un error al actualizar"+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                String punto = m_imageDetalleIncPunto.getText().toString();
                String direccion = m_imageDetalleDireccion.getText().toString();
                String observacion = m_imageDetalleIncObservacion1.getText().toString();

                params.put("punto", punto);
                params.put("direccion",direccion);
                params.put("observacion1",observacion);
                Log.v("editado","esto:"+params);
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


    public void MyCustomAlertDialog(final String Id){
        MyDialog = new Dialog(DetalleInc.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.dialogconvertoinctotask);

        // MyDialog.setTitle("My Custom Dialog");
        Button close = MyDialog.findViewById(R.id.mydialog_imageDetalleIncArea_observacion_clconvertirtotask);
        final TextInputEditText m_dialog_observacion = MyDialog.findViewById(R.id.mydialog_imageDetalleIncArea_observacion);
        final AutoCompleteTextView m_dialog_area = MyDialog.findViewById(R.id.mydialog_imageDetalleIncArea_area);


        ArrayAdapter<String> adapterArea = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, Area);


        m_dialog_area.setThreshold(1);//will start working from first character
        m_dialog_area.setAdapter(adapterArea);//setting the adapter data into the



        close.setEnabled(true);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText observacion2 = MyDialog.findViewById(R.id.mydialog_imageDetalleIncArea_observacion);
                AutoCompleteTextView m_area = MyDialog.findViewById(R.id.mydialog_imageDetalleIncArea_area);

                String observacion = observacion2.getText().toString().trim();
                String area = m_area.getText().toString().trim();

                if(observacion2.equals("")||area.equals("")){
                    Toast.makeText(DetalleInc.this, "Llena Todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    convertirToTask(Id,observacion,area);
                  //  MyDialog.cancel();
                }

            }
        });
        MyDialog.show();

    }

    public void showToolbar(String tittle, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }




}
