package valcolra.viiascreen.com.proyecto_viiascreen;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class DetalleTask extends AppCompatActivity {

    private static final String URL_BASE = "http://api.everlive.com/v1/vqfkdc51v767oq7x/";
    private static final String URL_JSON = "viiascreen_incidentes/";

    private RequestQueue requestQueue;

    private ImageView m_imageDetalleIncHeader;
    private TextInputEditText m_imageDetalleIncId;
    private AutoCompleteTextView m_imageDetalleIncArea;
    private TextInputEditText m_imageDetalleIncEstado;



    private TextInputEditText m_imageDetalleIncUsuario;
    private TextInputEditText m_imageDetalleIncClosedTask;
    private TextInputEditText m_imageDetalleIncCreateAt;
    private TextInputEditText m_imageDetalleIncObservacion1;
    private TextInputEditText m_imageDetalleIncObservacion2;
    private TextView m_T_sin_imagen;
    private TextInputEditText m_imageDetalleIncObservacion3;
    private TextInputEditText m_imageDetalleIncServicio;
    private TextInputEditText m_imageDetalleIncUrlImagen1;
    private ImageView m_imageDetalleIncUrlImagen2;
    private AutoCompleteTextView m_imageDetalleIncPunto;
    private TextInputEditText m_imageDetalleIncCreatedTask;

    /*Data hardcodeada estatica*/
    private TextInputEditText m_imageDetalleUbicacion;
    private TextInputEditText m_imageDetalleDireccion;
    private TextInputEditText m_imageDetalleIncResponsable;


    String[] PuntosArreglo = {"SJM-004 A","SUR-028 A","SUR-026 B","SUR-010 A","SUR-027 A","SUR-027 B","LAV-004 A","LAV-004 B","LAV-007 A","SIN-001 A","JM-002 U","MRN-004 A","VMT-010 A","ANT-001 A","CAL-001 A","IND-021 A","IND-021 B","SJL-009 A","SNM 013","JM 009 A","SNM 003 A","CAL 004 A","SJM 002 A","LMNA 01 B","SRQ 05 A","IND 006 A","CAL 014 A","CAL 018 A","SNBOR 001 A","SNM 009 A","LAV 008 A","ATE 013 A","PIU-055 A","CHI-004 A","TRU-046 U","ARQ-044 A","CUS 018 A","CAJ S/N","HUA S/N","ICA S/N"};
    String[] Area = {"Operaciones","Comercial","Logística","Mantenimiento"};
    String[] PuntosUbicacion = {"SAN JUAN DE MIRAFLORES","SURCO", "SURCO", "SURCO", "SURCO", "SURCO", "LA VICTORIA", "LA VICTORIA", "LA VICTORIA", "SAN ISIDRO", "JESUS MARIA", "SAN MIGUEL", "VILLA MARIA DEL TRIUNFO", "SANTA ANITA", "CALLAO", "INDEPENDENCIA", "INDEPENDENCIA", "SAN JUAN DE LURIGANCHO", "SAN MIGUEL", "JESUS MARIA", "SAN MIGUEL", "CALLAO", "SAN JUAN DE MIRAFLORES", "LA MOLINA", "SURQUILLO", "INDEPENDENCIA", "CALLAO", "CALLAO", "SAN BORJA", "SAN MIGUEL", "LA VICTORIA", "ATE", "PIURA", "CHICLAYO", "TRUJILLO", "AREQUIPA", "CUSCO", "CAJAMARCA", "HUANCAYO", "ICA"};
    String[] DireccionPuntosArreglo = {"AV. LOS HÉROES S/N", "AV BENAVIDES CDRA N°55", "AV. PRIMAVERA Nº 1357 CRUCE CON AV. EL POLO", "AV. EL DERBY CON AV. MANUEL OLGUIN", "AV. EL DERBY CON AV. EL POLO", "AV. EL DERBY CON AV. EL POLO", "AV. JAVIER PRADO 1094", "AV. JAVIER PRADO 1094", "AV. PASEO DE LA REPUBLICA CON AV CANADA", "AV. REPÚBLICA DE PANAMÁ CRUCE CON AV. TOMÁS MARSANO", "AV. 28 DE JULIO CON AV. FELIPE SALAVERRY", "AV. LA MARINA CDRA 20-SAN MIGUEL", "AV. PACHACUTEC CRUCE CON AV. 26 DE NOVIEMBRE", "AV. NICOLÁS AYLLÓN (CARRETERA CENTRAL)", "AV. FAUCETT ENTRADA AL AEROPUERTO", "PANAMERICANA NORTE CRUCE CON TOMAS VALLE", "PANAMERICANA NORTE CRUCE CON TOMAS VALLE", "AV. 9 DE OCTUBRE CDRA. 8 (BERMA CENTRAL)", "AV. ELMER FAUCETT CRUCE CON LA CALLE PASO DE LOS ANDES", "AV. EDUARDO AVAROA CON AV. FAUSTINO SANCHEZ CARRION CUADRA 07", "AV. COSTANERA CRUCE CON AV. ESCARDO-SAN MIGUEL (BAJADA ESCARDO)", "AV. 28 DE JULIO, ESQUINA CON FAUCETT", "AV. LOS HÉROES Cdra. 2 CERCA A METRO / SAGA FALABELLA Y PLAZA VEA", "AV. RAÚL FERRERO 1496", "AV. PASEO DE LA REPUBLICA CDRA. 45 ESQUINA CON JR. LEONARDO DE BARBIERI CDRA. 6", "AV. TOMÁS VALLE CON AV. ALFREDO MENDIOLA", "AV. ARGENTINA CDRA. 30 FRENTE A MINKA", "AV. FAUCETT CDRA. 20", "AV. CANADÁ CRUCE CON AV. SAN LUIS", "AV. UNIVERSITARIA CUADRA 11 CRUCE CON LA MAR", "AV. CANADÁ CON AV. NICOLÁS ARRIOLA", "CARRETERA CENTRAL CRUCE CON CALLE ESTRELLA", "AV CACERES CRUCE CON AV IRRAZOLA (FRENTE AL OPEN PLAZA DE PIURA)", "AV. BOLOGNESI CDRA. 11 CON GRAU", "AV. AMERICA OESTE", "AV. EJERCITO CDRA. 3 CRUCE CON CALLE AMPATACOCHA", "AV. ALAMEDA PACHACUTEC", "SIN UBICACIÓN CONFIRMADA", "SIN UBICACIÓN CONFIRMADA", "SIN UBICACIÓN CONFIRMADA" };

    private ProgressDialog mProgress;

    FloatingActionButton m_fab_imageDetalle_converTask;
    FloatingActionButton m_fab_imageDetalle_editar;
    FloatingActionButton m_fab_imageDetalle_save;

    private Dialog MyDialog;


    private String subirFoto = "http://legalmovil.com/invian/welcome/onlyImage/";

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private Bitmap bitmap = null;
    private String mPath;
    LinearLayout m_T_linear_cierre_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_task);

        mProgress = new ProgressDialog(this);

        showToolbar("Detalle Task", false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade());
        }
        requestQueue = Volley.newRequestQueue(this);


        ArrayAdapter<String> adapterPunto = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, PuntosArreglo);


        ArrayAdapter<String> adapterArea = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, Area);



        /*Obtener Data*/
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        /*Obtener data y guardarla*/

        final String Id = (String) extras.get("Id");
        String punto = (String) extras.get("punto");
        final String fecha = (String) extras.get("fecha");
        String usuario = (String) extras.get("usuario");
        final String area = (String) extras.get("area");
        final String observacion2 = (String) extras.get("observacion2");
        final String observacion3 = (String) extras.get("observacion2");

        String urlImagen1 = (String) extras.get("urlImagen1");
        String urlImagen2 = (String) extras.get("urlImagen2");

        String estado = (String) extras.get("estado");
        String responsable = (String) extras.get("responsable");
        final String cierre = (String) extras.get("cierre");

        Log.v("asdsasd", urlImagen2);


        m_imageDetalleIncHeader = findViewById(R.id.imageDetalleIncHeader);
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
        m_imageDetalleIncResponsable = findViewById(R.id.imageDetalleIncResponsable);

        m_T_linear_cierre_task = findViewById(R.id.T_linear_cierre_task);

        m_fab_imageDetalle_converTask = findViewById(R.id.fab_imageDetalle_converTask);
        m_fab_imageDetalle_editar = findViewById(R.id.fab_imageDetalle_editar);
        m_fab_imageDetalle_save = findViewById(R.id.fab_imageDetalle_save);
        m_T_sin_imagen = findViewById(R.id.T_sin_imagen);


        m_imageDetalleIncPunto.setText(punto);
        m_imageDetalleIncUsuario.setText(usuario);
        m_imageDetalleIncObservacion2.setText(observacion2);
        m_imageDetalleIncArea.setText(area);
        m_imageDetalleIncEstado.setText(estado);

        m_imageDetalleIncResponsable.setText(responsable);


        m_imageDetalleIncPunto.setThreshold(1);//will start working from first character
        m_imageDetalleIncPunto.setAdapter(adapterPunto);//setting the adapter data into the


        m_imageDetalleIncArea.setThreshold(1);//will start working from first character
        m_imageDetalleIncArea.setAdapter(adapterArea);//setting the adapter data into the




        m_imageDetalleIncObservacion3.setText(observacion3);



        Log.v("re2", "" + fecha);

        Log.v("re2", "" + cierre);


        m_imageDetalleIncCreateAt.setText(fecha);
        m_imageDetalleIncClosedTask.setText(cierre);

        if (urlImagen1.equals("")) {
            Log.v("Quehay", "nada" + urlImagen1);
            m_imageDetalleIncHeader.setImageDrawable(getResources().getDrawable(R.drawable.image));
            m_imageDetalleIncHeader.setPadding(260, 120, 260, 120);
            m_imageDetalleIncHeader.setPadding(260, 120, 260, 120);
            m_imageDetalleIncHeader.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            Log.v("Quehay", "todo" + urlImagen1);
            Glide.with(getApplicationContext()).load(urlImagen1).into(m_imageDetalleIncHeader);
        }

        if (urlImagen2.equals("")) {
            Log.v("Quehay", "nada" + urlImagen2);
            /*m_imageDetalleIncUrlImagen2.setImageDrawable(getResources().getDrawable(R.drawable.image));
            m_imageDetalleIncUrlImagen2.setPadding(260,120,260,120);
            m_imageDetalleIncUrlImagen2.setPadding(260,120,260,120);
            m_imageDetalleIncUrlImagen2.setScaleType(ImageView.ScaleType.CENTER_CROP);*/
            m_imageDetalleIncUrlImagen2.setVisibility(View.GONE);
            m_T_sin_imagen.setVisibility(View.VISIBLE);

        } else {
            m_T_sin_imagen.setVisibility(View.GONE);
            Log.v("Quehay", "todo" + urlImagen2);
            Glide.with(getApplicationContext()).load(urlImagen2).into(m_imageDetalleIncUrlImagen2);
        }


        String punt = m_imageDetalleIncPunto.getText().toString();
        int matchIndex = -1;
        String ubicacionC = "";
        String direccionC = "";
        int contador = 0;
        for (int w = 0; w < PuntosArreglo.length; w++) {
            if (PuntosArreglo[w].equals(punt)) {
                Log.v("Encontré", PuntosArreglo[w]);
                matchIndex = w;
                ubicacionC = PuntosUbicacion[w];
                direccionC = DireccionPuntosArreglo[w];
                Log.v("Encontré", "" + ubicacionC);
                contador++;
                break;
            } else {
                Log.v("ES0", "Mal");
            }
        }

        if (contador == 1) {
            m_imageDetalleUbicacion.setText(ubicacionC);
            m_imageDetalleDireccion.setText(direccionC);
            contador = 0;
        }


        if (estado.equals("cerrado")) {
            m_fab_imageDetalle_converTask.setVisibility(View.GONE);
            m_fab_imageDetalle_editar.setVisibility(View.GONE);
            m_fab_imageDetalle_save.setVisibility(View.GONE);
            m_T_linear_cierre_task.setVisibility(View.VISIBLE);
        } else {
            m_T_linear_cierre_task.setVisibility(View.GONE);
        }

        m_fab_imageDetalle_converTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  convertirToTask(Id);
                MyCustomAlertDialog(Id);
            }
        });
        m_fab_imageDetalle_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_imageDetalleIncPunto.setEnabled(true);
                m_imageDetalleIncObservacion2.setEnabled(true);
                m_imageDetalleIncArea.setEnabled(true);
                m_fab_imageDetalle_save.setVisibility(View.VISIBLE);
                m_fab_imageDetalle_editar.setVisibility(View.GONE);

            }
        });

        m_fab_imageDetalle_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String punto_ = m_imageDetalleIncPunto.getText().toString();
                String p = punto_.replace(" ", "");

                int matchIndex = -1;
                String ubicacionP = "";

                int contador = 0;
                for (int w = 0; w < PuntosArreglo.length; w++) {
                    if (PuntosArreglo[w].equals(punto_)) {
                        Log.v("Encontré", PuntosArreglo[w]);
                        ubicacionP = PuntosUbicacion[w];
                        matchIndex = w;
                        Log.v("Encontré", String.valueOf(matchIndex));
                        Log.v("ES0", "Bien");
                        m_imageDetalleIncPunto.setEnabled(false);
                        m_imageDetalleIncObservacion2.setEnabled(false);
                        m_imageDetalleIncArea.setEnabled(false);
                        m_fab_imageDetalle_editar.setVisibility(View.VISIBLE);
                        m_fab_imageDetalle_save.setVisibility(View.GONE);

                        contador++;
                        break;
                    } else {
                        //Log.v("ES0","Mal");
                    }
                }
                if (contador == 1) {
                    editToTask(Id, v);
                    m_imageDetalleUbicacion.setText(ubicacionP);
                    contador = 0;
                } else {
                    Snackbar.make(v, "Punto Invalido ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });







        final Handler handler = new Handler();


        if(estado.equals("cerrado")){



                    String dateStart = fecha;

                    String dateStop = cierre;

                    SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

                    Date d1 = null;
                    Date d2 = null;
                    try {
                        d1 = format.parse(dateStart);
                        d2 = format.parse(dateStop);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long diff = d2.getTime() - d1.getTime();

                    final long diffSeconds = diff / 1000 % 60;
                    final long diffMinutes = diff / (60 * 1000) % 60;

                    final long diffHours = diff / (60 * 60 * 1000);


                    Log.v("fec", "" + diffSeconds + ":" + diffMinutes + ":" + diffHours);
                    Log.v("fec", "formattedTime" + fecha + "     formattedTimes" + cierre);


                    System.out.println("Time in hours: " + diffHours + " hours.");
                    System.out.println("Time in minutes: " + diffMinutes + " minutes.");
                    System.out.println("Time in seconds: " + diffSeconds + " seconds.");
                    TextView m_userNameDetail = findViewById(R.id.diferenciadorFechaTask);
                    m_userNameDetail.setText("" + diffHours + ":" + diffMinutes + ":" + diffSeconds);
                    Log.v("actru","actualizando"+ diffHours + ":" + diffMinutes + ":" + diffSeconds);




        }else{
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms

                    String dateStart = fecha;
                    String dateStop = cierre;


                    if(dateStop=="") {
                        String date = (String) android.text.format.DateFormat.format("yyyy-MM-dd kk:mm:ss", new java.util.Date());
                        dateStop=date;
                    }

                    String date = (String) android.text.format.DateFormat.format("yyyy-MM-dd kk:mm:ss", new java.util.Date());

                    SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

                    Date d1 = null;
                    Date d2 = null;
                    try {
                        d1 = format.parse(dateStart);
                        d2 = format.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

// Get msec from each, and subtract.
                    long diff = d2.getTime() - d1.getTime();

                    final long diffSeconds = diff / 1000 % 60;
                    final long diffMinutes = diff / (60 * 1000) % 60;

                    final long diffHours = diff / (60 * 60 * 1000);


                    Log.v("fec", "" + diffSeconds + ":" + diffMinutes + ":" + diffHours);
                    Log.v("fec", "formattedTime" + fecha + "     formattedTimes" + cierre);


                    System.out.println("Time in hours: " + diffHours + " hours.");
                    System.out.println("Time in minutes: " + diffMinutes + " minutes.");
                    System.out.println("Time in seconds: " + diffSeconds + " seconds.");
                    TextView m_userNameDetail = findViewById(R.id.diferenciadorFechaTask);
                    m_userNameDetail.setText("" + diffHours + ":" + diffMinutes + ":" + diffSeconds);
                    Log.v("actru","actualizando"+ diffHours + ":" + diffMinutes + ":" + diffSeconds);
                    handler.postDelayed(this, 1000);
                }
            }, 1000);

        }


    }




    public void showToolbar(String tittle, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void editToTask(String ID, final View v) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,URL_BASE+URL_JSON+ID ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(DetalleTask.this,"Actualizado Correctamente "+response,Toast.LENGTH_LONG).show();
               /* Intent i = new Intent(DetalleIncidente.this,MainActivity.class);
                startActivity(i);*/
                Snackbar.make(v, "Task Editado ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(DetalleTask.this,"Hubo un error al actualizar"+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                String punto = m_imageDetalleIncPunto.getText().toString();
                String area = m_imageDetalleIncArea.getText().toString();
                String observacion2 = m_imageDetalleIncObservacion2.getText().toString();

                  params.put("punto",punto);

                params.put("area",area);
                params.put("observacion2",observacion2);
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

    private void convertirToTask(String ID) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,URL_BASE+URL_JSON+ID ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(DetalleTask.this,"Actualizado Correctamente "+response,Toast.LENGTH_LONG).show();
               /* Intent i = new Intent(DetalleIncidente.this,MainActivity.class);
                startActivity(i);*/
                finish();
                mProgress.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(DetalleTask.this,"Hubo un error al actualizar"+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
               // params.put("estado","cerrado");

                TextInputEditText m_dialog_custom_checking_cerrar_observacion2 = MyDialog.findViewById(R.id.dialog_custom_checking_cerrar_observacion2);
                TextInputEditText m_dialog_custom_checking_cerrar_responsable = MyDialog.findViewById(R.id.dialog_custom_checking_cerrar_responsable);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getFechaTelerik = simpleDateFormat.format(new Date());


                SimpleDateFormat simpleDateF = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
                String getFecha = simpleDateF.format(new Date());



                String observacion2 = m_dialog_custom_checking_cerrar_observacion2.getText().toString();
                String responsable= m_dialog_custom_checking_cerrar_responsable.getText().toString();
                params.put("estado","cerrado");
                params.put("responsable",responsable);

                params.put("observacion3",observacion2);
                params.put("closedTask",getFechaTelerik);
                params.put("urlImagen2","http://legalmovil.com/invian/public/uploads/VT_"+getFecha+".jpg");
                onlyImage(getFecha);


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


    //mandar la imagen al servicio para guardarlo en el servidor
    private void onlyImage(final String getFecha) {
        //metodo para el post y subir la foto
        StringRequest stringRequest = new StringRequest(Request.Method.POST,subirFoto ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("response",""+response);

                /*Intent i = new Intent(DetalleCompleteChecking.this,MainActivity.class);
                startActivity(i);*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                if(imageToString(bitmap) == ""){
                    String imagen_camara = "";
                    params.put("fecha",getFecha);
                    params.put("image",imagen_camara);
                }
                if(imageToString(bitmap) != "" ){
                    String imagen_camara = imageToString(bitmap);
                    params.put("fecha",getFecha);
                    params.put("image",imagen_camara);
                }

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);


    }
    //agregamos una alerta tipo bootstrap o materialize
    public void MyCustomAlertDialog(final String Id){
        MyDialog = new Dialog(DetalleTask.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.customdialogcerrartask);

        // MyDialog.setTitle("My Custom Dialog");
        Button close = MyDialog.findViewById(R.id.m_dialog_custom_checking_cerrar_close);
        FloatingActionButton m_fabTakePhotoCheckingCierre = MyDialog.findViewById(R.id.fabTakePhotoCheckingCierre);

        m_fabTakePhotoCheckingCierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        /*Snackbar.make(v, "Foto Tomada ", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();*/
                //SubirFoto();
                //Toast.makeText(DetalleCompleteChecking.this, "Foto No asd Correctamente", Toast.LENGTH_SHORT).show();
                showOptions();
            }
        });
        if(mayRequestStoragePermission()) {
            m_fabTakePhotoCheckingCierre.setEnabled(true);
        }else {
            m_fabTakePhotoCheckingCierre.setEnabled(false);
        }
        //cerrarmos y realizamo sun aaccion
        close.setEnabled(true);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText m_dialog_custom_checking_cerrar_observacion2 = MyDialog.findViewById(R.id.dialog_custom_checking_cerrar_observacion2);
                TextInputEditText m_dialog_custom_checking_cerrar_responsable = MyDialog.findViewById(R.id.dialog_custom_checking_cerrar_responsable);

                String observacion = m_dialog_custom_checking_cerrar_observacion2.getText().toString();
                String responsable = m_dialog_custom_checking_cerrar_responsable.getText().toString();

                if(observacion.equals("")||responsable.equals("")){
                    Toast.makeText(DetalleTask.this, "Llena Todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    convertirToTask(Id);
                    MyDialog.cancel();
                    mProgress.setMessage("Subiendo...");
                    mProgress.show();
                }
            }
        });
        MyDialog.show();

    }

   /*START METHOD FOR CAMERA*/

    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
          /*  Snackbar.make(m_cordinator_crear_checking, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });*/
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    private void showOptions() {
        final CharSequence[] option = {"Tomar foto", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(DetalleTask.this);
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    openCamera();
                }else if(option[which] == "Elegir de galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Log.v("TAG_PHOTO_CHECKING","Imagen Guardada en: "+newFile);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);


        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });
                    bitmap = BitmapFactory.decodeFile(mPath);

                    if(bitmap==null){
                        Toast.makeText(DetalleTask.this, "Foto No Tomada Correctamente", Toast.LENGTH_SHORT).show();

                    }else{
                        ImageView m_imageCloseChecking = MyDialog.findViewById(R.id.imageCloseChecking);
                        m_imageCloseChecking.setImageBitmap(bitmap);

                    }

                case SELECT_PICTURE:
                    // Uri path = data.getData();
                    // bitmap.setImageURI(path);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(DetalleTask.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                //mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetalleTask.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    /*END FOR METHOD CAMERA*/

    private String imageToString(Bitmap bitmap){
        if(bitmap==null){
            return "";
        }else{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
            byte[] imgBytes = byteArrayOutputStream.toByteArray();
            bitmap.createScaledBitmap(bitmap,5,3,true);
            return Base64.encodeToString(imgBytes,Base64.DEFAULT);
        }

    }





}

























