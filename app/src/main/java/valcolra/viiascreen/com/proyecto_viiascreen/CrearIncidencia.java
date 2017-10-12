package valcolra.viiascreen.com.proyecto_viiascreen;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CrearIncidencia extends AppCompatActivity {
    //declaramos variables
    private ProgressDialog mProgress;

    private ImageView m_imageFoto;
    private Button m_btnCrearIncidencia;
    private AutoCompleteTextView m_crear_incidencia_punto;
    private AutoCompleteTextView m_crear_incidencia_service;
    private TextInputEditText m_crear_incidencia_observation;

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private String mPath;
    private Button mOptionButton;

    private Bitmap bitmap = null;
    private CoordinatorLayout m_cordinator_crear_instancia;
    private FloatingActionButton fab;

    //esto son los servicios que vamos a usar
    private String subirFotoVictor = "https://api.everlive.com/v1/vqfkdc51v767oq7x/Functions/pushEmail";
    private String subirFoto = "http://legalmovil.com/invian/welcome/onlyImage/";
    private String subirDatosTelerik = "https://api.everlive.com/v1/vqfkdc51v767oq7x/viiascreen_incidentes/";

    //declaramos el autocompletable
    String[] autoCompleteStringPuntos = {"VMT-010 A", "SJM-004 A", "SUR-028 A", "SUR-026 B", "SUR-028 A", "SUR-027 A", "SUR-027 B", "LAV-004 A", "MRN-004 A", "SIN-001 A", "LAV-004 B"};
    String[] autoCompleteStringService = {"Módulo Malogrado","Mantenimiento", "Limpieza de pantalla", "Cambio de fuente", "Tablero averiado", "PC averiada", "Limpieza"};

    String[] PuntosArreglo = {"SJM-004 A","SUR-028 A","SUR-026 B","SUR-010 A","SUR-027 A","SUR-027 B","LAV-004 A","LAV-004 B","LAV-007 A","SIN-001 A","JM-002 U","MRN-004 A","VMT-010 A","ANT-001 A","CAL-001 A","IND-021 A","IND-021 B","SJL-009 A","SNM 013","JM 009 A","SNM 003 A","CAL 004 A","SJM 002 A","LMNA 01 B","SRQ 05 A","IND 006 A","CAL 014 A","CAL 018 A","SNBOR 001 A","SNM 009 A","LAV 008 A","ATE 013 A","PIU-055 A","CHI-004 A","TRU-046 U","ARQ-044 A","CUS 018 A","CAJ S/N","HUA S/N","ICA S/N"};
    String[] ServicioArreglo = {"Módulo Malogrado","Mantenimiento", "Limpieza de pantalla", "Cambio de fuente", "Tablero averiado", "PC averiada", "Limpieza"};
    String[] DireccionPuntosArreglo = {"AV. LOS HÉROES S/N", "AV BENAVIDES CDRA N°55", "AV. PRIMAVERA Nº 1357 CRUCE CON AV. EL POLO", "AV. EL DERBY CON AV. MANUEL OLGUIN", "AV. EL DERBY CON AV. EL POLO", "AV. EL DERBY CON AV. EL POLO", "AV. JAVIER PRADO 1094", "AV. JAVIER PRADO 1094", "AV. PASEO DE LA REPUBLICA CON AV CANADA", "AV. REPÚBLICA DE PANAMÁ CRUCE CON AV. TOMÁS MARSANO", "AV. 28 DE JULIO CON AV. FELIPE SALAVERRY", "AV. LA MARINA CDRA 20-SAN MIGUEL", "AV. PACHACUTEC CRUCE CON AV. 26 DE NOVIEMBRE", "AV. NICOLÁS AYLLÓN (CARRETERA CENTRAL)", "AV. FAUCETT ENTRADA AL AEROPUERTO", "PANAMERICANA NORTE CRUCE CON TOMAS VALLE", "PANAMERICANA NORTE CRUCE CON TOMAS VALLE", "AV. 9 DE OCTUBRE CDRA. 8 (BERMA CENTRAL)", "AV. ELMER FAUCETT CRUCE CON LA CALLE PASO DE LOS ANDES", "AV. EDUARDO AVAROA CON AV. FAUSTINO SANCHEZ CARRION CUADRA 07", "AV. COSTANERA CRUCE CON AV. ESCARDO-SAN MIGUEL (BAJADA ESCARDO)", "AV. 28 DE JULIO, ESQUINA CON FAUCETT", "AV. LOS HÉROES Cdra. 2 CERCA A METRO / SAGA FALABELLA Y PLAZA VEA", "AV. RAÚL FERRERO 1496", "AV. PASEO DE LA REPUBLICA CDRA. 45 ESQUINA CON JR. LEONARDO DE BARBIERI CDRA. 6", "AV. TOMÁS VALLE CON AV. ALFREDO MENDIOLA", "AV. ARGENTINA CDRA. 30 FRENTE A MINKA", "AV. FAUCETT CDRA. 20", "AV. CANADÁ CRUCE CON AV. SAN LUIS", "AV. UNIVERSITARIA CUADRA 11 CRUCE CON LA MAR", "AV. CANADÁ CON AV. NICOLÁS ARRIOLA", "CARRETERA CENTRAL CRUCE CON CALLE ESTRELLA", "AV CACERES CRUCE CON AV IRRAZOLA (FRENTE AL OPEN PLAZA DE PIURA)", "AV. BOLOGNESI CDRA. 11 CON GRAU", "AV. AMERICA OESTE", "AV. EJERCITO CDRA. 3 CRUCE CON CALLE AMPATACOCHA", "AV. ALAMEDA PACHACUTEC", "SIN UBICACIÓN CONFIRMADA", "SIN UBICACIÓN CONFIRMADA", "SIN UBICACIÓN CONFIRMADA" };
    String[] PuntosUbicacion = {"SAN JUAN DE MIRAFLORES","SURCO", "SURCO", "SURCO", "SURCO", "SURCO", "LA VICTORIA", "LA VICTORIA", "LA VICTORIA", "SAN ISIDRO", "JESUS MARIA", "SAN MIGUEL", "VILLA MARIA DEL TRIUNFO", "SANTA ANITA", "CALLAO", "INDEPENDENCIA", "INDEPENDENCIA", "SAN JUAN DE LURIGANCHO", "SAN MIGUEL", "JESUS MARIA", "SAN MIGUEL", "CALLAO", "SAN JUAN DE MIRAFLORES", "LA MOLINA", "SURQUILLO", "INDEPENDENCIA", "CALLAO", "CALLAO", "SAN BORJA", "SAN MIGUEL", "LA VICTORIA", "ATE", "PIURA", "CHICLAYO", "TRUJILLO", "AREQUIPA", "CUSCO", "CAJAMARCA", "HUANCAYO", "ICA"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_incidencia);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, PuntosArreglo);

        ArrayAdapter<String> adapterServicio = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, ServicioArreglo);


        mProgress = new ProgressDialog(this);
        AutoCompleteTextView autoCompleteTextViewPuntos = (AutoCompleteTextView) findViewById(R.id.crear_incidencia_punto);
        AutoCompleteTextView autoCompleteTextViewService = (AutoCompleteTextView) findViewById(R.id.crear_incidencia_service);

        m_imageFoto = (ImageView) findViewById(R.id.imageFoto);
        m_crear_incidencia_punto = (AutoCompleteTextView) findViewById(R.id.crear_incidencia_punto);
        m_crear_incidencia_service = (AutoCompleteTextView) findViewById(R.id.crear_incidencia_service);
        m_crear_incidencia_observation = (TextInputEditText) findViewById(R.id.crear_incidencia_observation);
        m_btnCrearIncidencia = (Button) findViewById(R.id.btnCrearInc);
        mOptionButton = (Button) findViewById(R.id.showOMenu);
        m_cordinator_crear_instancia = (CoordinatorLayout) findViewById(R.id.cordinator_crear_instancia);


        m_crear_incidencia_punto.setThreshold(1);//will start working from first character
        m_crear_incidencia_punto.setAdapter(adapter);//setting the adapter data into the

        m_crear_incidencia_service.setThreshold(1);//will start working from first character
        m_crear_incidencia_service.setAdapter(adapterServicio);//setting the adapter data into the


        /*ArrayAdapter adapterPuntos = new ArrayAdapter(this, android.R.layout.select_dialog_item, autoCompleteStringPuntos);
        autoCompleteTextViewPuntos.setAdapter(adapterPuntos);*/

        /*ArrayAdapter adapterServicio = new ArrayAdapter(this, android.R.layout.select_dialog_item, autoCompleteStringService);
        autoCompleteTextViewService.setAdapter(adapterServicio);*/

        if(mayRequestStoragePermission()) {
            mOptionButton.setEnabled(true);
        }else {
            mOptionButton.setEnabled(false);
        }
        //Metodo para crear incidencia
        m_btnCrearIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.setMessage("Subiendo...");
                mProgress.show();
                String cPunto = m_crear_incidencia_punto.getText().toString();
                String cService = m_crear_incidencia_service.getText().toString();

                if((cPunto.equals("") && cService.equals(""))|| (cPunto.equals("") || cService.equals(""))){
                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(),"Porfavor complete los campos",Toast.LENGTH_SHORT).show();
                }else{

                    int matchIndex = -1;
                    int contador = 0;
                    String direccionP ="";
                    String ubicacionP ="";
                    for(int w=0; w<PuntosArreglo.length; w++) {
                        if(PuntosArreglo[w].equals(cPunto)) {
                            Log.v("Encontré",PuntosArreglo[w]);
                            matchIndex = w;
                            Log.v("Encontré", String.valueOf(matchIndex));
                            Log.v("ES0","Bien");
                            contador++;
                             direccionP = DireccionPuntosArreglo[w];
                            ubicacionP = PuntosUbicacion[w];

                            break;
                        }else{
                            Log.v("ES0","Mal");
                            mProgress.dismiss();
                        }
                    }
                    if(contador == 1){
                        upTelerik(direccionP,ubicacionP);
                        contador =0;
                        mProgress.setMessage("Subiendo...");
                        mProgress.show();
                    }else{
                        Snackbar.make(view, "Punto Invalido ", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                   // Toast.makeText(getApplicationContext(),"Campos llenoscampos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //mostrar un titulo de showToolbar "CREAR INCIDENCIA"
        showToolbar("Crear Incidencia", false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());
        }
        //opcciones
        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });
        //view para fabTomarFoto
        fab= (FloatingActionButton) findViewById(R.id.fabTomarFoto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Foto Tomada ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //SubirFoto();
                showOptions();
            }
        });


    }
    //Metodo para guardar solo la imagen en el servicio
    private void onlyImage(final String getFecha) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,subirFoto ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                mProgress.dismiss();
                m_imageFoto.setImageResource(0);
                m_crear_incidencia_punto.setText("");
                m_crear_incidencia_service.setText("");
                m_crear_incidencia_observation.setText("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(CrearIncidencia.this,"error "+error,Toast.LENGTH_LONG).show();
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
                    String usuario = ("Kelson");
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

    //metodo de enviar la imagen al correo con datos
    private void upImage(final String getFecha) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,subirFotoVictor ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                mProgress.dismiss();
                m_imageFoto.setImageResource(0);
                m_crear_incidencia_punto.setText("");
                m_crear_incidencia_service.setText("");
                m_crear_incidencia_observation.setText("");
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearIncidencia.this,"error "+error,Toast.LENGTH_LONG).show();
                mProgress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                if(imageToString(bitmap) == ""){
                    String imagen_camara = "";
                    String name ="Viia Task";
                    String usuario = ("Kelson");
                    String punto_= m_crear_incidencia_punto.getText().toString().trim();
                    String observacion_= m_crear_incidencia_observation.getText().toString().trim();
                    String servicio_= m_crear_incidencia_service.getText().toString().trim();

                    //Parametros para victor email

                    params.put("producto","viia_incidencia");
                    params.put("usuario",usuario);
                    params.put("punto",punto_);
                    params.put("observacion",observacion_);
                    params.put("fecha",getFecha);

                    params.put("image","http://legalmovil.com/invian/public/uploads/VT_"+getFecha+".jpg");
                    params.put("email_viia","valdcolra@gmail.com");
                    Log.v("getFecha1",getFecha);
                }
                if(imageToString(bitmap) != "" ){
                    String imagen_camara = imageToString(bitmap);

                    String name ="viia_incidencia";
                    String usuario = ("Kelson");
                    String punto_= m_crear_incidencia_punto.getText().toString().trim();
                    String observacion_= m_crear_incidencia_observation.getText().toString().trim();
                    String servicio_= m_crear_incidencia_service.getText().toString().trim();


                    //Parametros para victor email
                    params.put("producto","viia_incidencia");
                    params.put("usuario",usuario);
                    params.put("punto",punto_);
                    params.put("observacion",observacion_);

                    params.put("fecha",getFecha);
                    params.put("image","http://legalmovil.com/invian/public/uploads/VT_"+getFecha+".jpg");
                    params.put("email_viia","valdcolra@gmail.com");
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


    /*Enviar a telerik*/
    private void upTelerik(final String direccionP, final String ubicacionP) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,subirDatosTelerik ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //aqui colocamos un mensaje para mostrar como respuesta
                mProgress.dismiss();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearIncidencia.this,"Hubo un error en el envio a telerik "+error,Toast.LENGTH_LONG).show();
                mProgress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                if(imageToString(bitmap) == "") {
                    String imagen_camara = "";
                     /*Obtener Fecha en dispositivo*/
                    SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss");
                    String getFecha = s.format(new Date());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String getFechaTelerik = simpleDateFormat.format(new Date());
                    String urlImagen ="";
                    String usuario = ("Kelson");
                    String punto_= m_crear_incidencia_punto.getText().toString().trim();
                    String observacion_= m_crear_incidencia_observation.getText().toString().trim();
                    String servicio_= m_crear_incidencia_service.getText().toString().trim();
                    params.put("usuario",usuario);
                    params.put("servicio",servicio_);
                    params.put("punto",punto_);
                    params.put("observacion1",observacion_);
                    params.put("observacion2","");
                    params.put("observacion3","");
                    params.put("urlImagen2","");
                    params.put("estado","pendiente");

                    params.put("direccion",direccionP);
                    params.put("ubicacion",ubicacionP);
                    params.put("area","");
                    //params.put("responsable","resp");
                    params.put("createdTask","");

                    params.put("closedTask","");
                    // params.put("fecha",getFecha);
                    params.put("urlImagen1","");
                     upImage(getFecha);
                    onlyImage(getFecha);
                }

                if(imageToString(bitmap) != "" ) {
                    String imagen_camara = imageToString(bitmap);
                    /*Obtener Fecha en dispositivo*/
                    SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss");
                    String getFecha = s.format(new Date());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String getFechaTelerik = simpleDateFormat.format(new Date());
                    String urlImagen ="http://legalmovil.com/invian/public/uploads/VT_"+getFecha+".jpg";
                    String usuario = ("Kelson");
                    String punto_= m_crear_incidencia_punto.getText().toString().trim();
                    String observacion_= m_crear_incidencia_observation.getText().toString().trim();
                    String servicio_= m_crear_incidencia_service.getText().toString().trim();
                    params.put("usuario",usuario);
                    params.put("servicio",servicio_);
                    params.put("punto",punto_);
                    params.put("observacion1",observacion_);
                    params.put("observacion2","");
                    params.put("observacion3","");
                    params.put("urlImagen2","");
                    params.put("estado","pendiente");
                    params.put("direccion",direccionP);
                    params.put("ubicacion",ubicacionP);
                    params.put("area","");
                    params.put("createdTask","");

                    params.put("closedTask","");
                    params.put("urlImagen1",urlImagen);
                    upImage(getFecha);
                    onlyImage(getFecha);
                }
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    //mostramos un showtoolbar con un titulo personalizable
    public void showToolbar(String tittle, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }
    //pedimos los permisos que son necesarios
    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(m_cordinator_crear_instancia, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }
    //mostramos opcciones
    private void showOptions() {
        final CharSequence[] option = {"Tomar foto","Elegir de galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(CrearIncidencia.this);
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
    //abrimos la camara
    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();
        //creamos un directorio si no exite
        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();
        //si existe un directorio guardamos
        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            //llamamos a la camara para guardar el directorio
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);

        }
    }
    //guardamos el estado
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
    //obtener resultados
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
                    try {

                        bitmap = BitmapFactory.decodeFile(mPath);
                        Log.v("donde", String.valueOf(bitmap));
                        m_imageFoto.setImageBitmap(bitmap);


                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                        Toast.makeText(CrearIncidencia.this, "Fallo al tomar la foto"+e, Toast.LENGTH_SHORT).show();
                        System.gc();
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
                Toast.makeText(CrearIncidencia.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }
    //mostramos un mensaje de persmisos acptados
    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CrearIncidencia.this);
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