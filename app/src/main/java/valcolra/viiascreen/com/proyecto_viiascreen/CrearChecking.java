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

public class CrearChecking extends AppCompatActivity {

    private ProgressDialog mProgress;

    private ImageView m_imagePhotoChecking;
    private AutoCompleteTextView m_create_name_spot_checking;
    private AutoCompleteTextView m_create_name_area_checking;
    private TextInputEditText m_create_name_observacion_checking;
    private Button m_boton_create_checking;
    private FloatingActionButton  m_fabTakePhotoChecking;

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private Bitmap bitmap = null;
    private String mPath;

    private CoordinatorLayout m_cordinator_crear_checking;

    private String URL_V_CHECKING = "https://api.everlive.com/v1/vqfkdc51v767oq7x/viiascreen_checking/";
    private String subirFoto = "http://legalmovil.com/invian/welcome/onlyImage/";

    String[] PuntosArreglo = {"SJM-004 A","SUR-028 A","SUR-026 B","SUR-010 A","SUR-027 A","SUR-027 B","LAV-004 A","LAV-004 B","LAV-007 A","SIN-001 A","JM-002 U","MRN-004 A","VMT-010 A","ANT-001 A","CAL-001 A","IND-021 A","IND-021 B","SJL-009 A","SNM 013","JM 009 A","SNM 003 A","CAL 004 A","SJM 002 A","LMNA 01 B","SRQ 05 A","IND 006 A","CAL 014 A","CAL 018 A","SNBOR 001 A","SNM 009 A","LAV 008 A","ATE 013 A","PIU-055 A","CHI-004 A","TRU-046 U","ARQ-044 A","CUS 018 A","CAJ S/N","HUA S/N","ICA S/N"};
    String[] ServicioArreglo = {"Módulo Malogrado","Mantenimiento", "Limpieza de pantalla", "Cambio de fuente", "Tablero averiado", "PC averiada", "Limpieza"};

    private String subirFotoVictor = "https://api.everlive.com/v1/vqfkdc51v767oq7x/Functions/pushEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_checking);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, PuntosArreglo);

        ArrayAdapter<String> adapterServicio = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, ServicioArreglo);




        mProgress = new ProgressDialog(this);
        m_imagePhotoChecking = findViewById(R.id.imagePhotoChecking);
        m_fabTakePhotoChecking = findViewById(R.id.fabTakePhotoChecking);
        m_cordinator_crear_checking = findViewById(R.id.cordinator_crear_checking);

        /*Campos*/
        m_create_name_spot_checking = findViewById(R.id.create_name_spot_checking);
        m_create_name_area_checking = findViewById(R.id.create_name_area_checking);
        m_create_name_observacion_checking = findViewById(R.id.create_name_observacion_checking);


        m_create_name_spot_checking.setThreshold(1);//will start working from first character
        m_create_name_spot_checking.setAdapter(adapter);//setting the adapter data into the

        m_create_name_area_checking.setThreshold(1);//will start working from first character
        m_create_name_area_checking.setAdapter(adapterServicio);//setting the adapter data into the






        m_boton_create_checking = findViewById(R.id.boton_create_checking);

        m_boton_create_checking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.setMessage("Subiendo...");
                mProgress.show();

                String checkingSpot = m_create_name_spot_checking.getText().toString();
                String checkingArea= m_create_name_area_checking.getText().toString();
                String checkingObservacion = m_create_name_observacion_checking.getText().toString();

                if((checkingSpot.equals("") && checkingArea.equals(""))|| (checkingSpot.equals("") || checkingArea.equals(""))){
                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(),"Porfavor complete los campos",Toast.LENGTH_SHORT).show();

                }else{
                    int matchIndex = -1;
                    int contador = 0;
                    for(int w=0; w<PuntosArreglo.length; w++) {
                        if(PuntosArreglo[w].equals(checkingSpot)) {
                            Log.v("Encontré",PuntosArreglo[w]);
                            matchIndex = w;
                            Log.v("Encontré", String.valueOf(matchIndex));
                            Log.v("ES0","Bien");
                            contador++;
                            break;
                        }else{
                            Log.v("ES0","Mal");

                        }
                    }
                    if(contador == 1){

                        upTelerik();
                        contador =0;
                    }else{
                        mProgress.dismiss();
                        Snackbar.make(view, "Punto Invalido ", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    // Toast.makeText(getApplicationContext(),"Campos llenoscampos",Toast.LENGTH_SHORT).show();
                }
            }
        });

        m_fabTakePhotoChecking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Foto Tomada ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //SubirFoto();
                showOptions();
            }
        });

        showToolbar("Crear Checking", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());
        }
        if(mayRequestStoragePermission()) {
            m_fabTakePhotoChecking.setEnabled(true);
        }else {
            m_fabTakePhotoChecking.setEnabled(false);
        }
    }

    /*Enviar a telerik*/
    private void upTelerik() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_V_CHECKING ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgress.dismiss();
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearChecking.this,"Hubo un error en el envio a telerik "+error,Toast.LENGTH_LONG).show();
                mProgress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                if(imageToString(bitmap) == "") {
                    String checkingSpot = m_create_name_spot_checking.getText().toString();
                    String checkingArea= m_create_name_area_checking.getText().toString();
                    String checkingObservacion = m_create_name_observacion_checking.getText().toString();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String getFechaTelerik = simpleDateFormat.format(new Date());

                    SimpleDateFormat simpleDateFormats = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
                    String getFecha = simpleDateFormats.format(new Date());
                   // String imagen_camara = imageToString(bitmap);

                    params.put("producto","viia_checking");
                    params.put("usuario","Kelson");
                    params.put("estado","pendiente");
                    params.put("area",checkingArea);
                    params.put("punto",checkingSpot);

                    params.put("observacion1",checkingObservacion);
                    params.put("observacion2","");

                    params.put("urlImagen1","");
                    params.put("urlImagen2","");

                    params.put("responsable","");
                    params.put("CreatedAt",getFechaTelerik);
                    upImage(getFecha);
                    params.put("closedCheck","");

                }else {
                    String checkingSpot = m_create_name_spot_checking.getText().toString();
                    String checkingArea= m_create_name_area_checking.getText().toString();
                    String checkingObservacion = m_create_name_observacion_checking.getText().toString();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String getFechaTelerik = simpleDateFormat.format(new Date());

                    SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss");
                    String getFecha = s.format(new Date());

                    params.put("producto","viia_checking");
                    params.put("usuario","Kelson");
                    params.put("estado","pendiente");
                    params.put("area",checkingArea);
                    params.put("punto",checkingSpot);

                    params.put("observacion1",checkingObservacion);
                    params.put("observacion2","");

                    params.put("urlImagen1","");
                    params.put("urlImagen2","http://legalmovil.com/invian/public/uploads/VT_"+getFecha+".jpg");

                    params.put("responsable","");
                    upImage(getFecha);

                    params.put("closedCheck","");
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


    private void onlyImage(final String getFecha) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,subirFoto ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgress.dismiss();

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

    /*START METHOD FOR CAMERA*/

    private boolean mayRequestStoragePermission() {
        //esta parte nos sirve para poder ver la version en caso de ser incompatible
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(m_cordinator_crear_checking, "Los permisos son necesarios para poder usar la aplicación",
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


    /*metodo para msotrar las opcciones*/

    private void showOptions() {
        final CharSequence[] option = {"Tomar foto","Elegir de galeria" ,"Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(CrearChecking.this);
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


    private void upImage(final String getFecha) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,subirFotoVictor ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                mProgress.dismiss();
                m_imagePhotoChecking.setImageResource(0);
                m_create_name_spot_checking.setText("");
                m_create_name_area_checking.setText("");

                m_create_name_observacion_checking.setText("");
                /*Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearChecking.this,"error "+error,Toast.LENGTH_LONG).show();
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
                    String punto_= m_create_name_spot_checking.getText().toString().trim();

                    String area= m_create_name_area_checking.getText().toString().trim();
                    String observacion_= m_create_name_observacion_checking.getText().toString().trim();

//Parametros para victor email

                    params.put("producto","viia_incidencia");
                    params.put("usuario",usuario);
                    params.put("punto",punto_);
                    params.put("area",area);
                    params.put("observacion" +
                            "",observacion_);
                    params.put("fecha",getFecha);

                    params.put("image","http://legalmovil.com/invian/public/uploads/VT_"+getFecha+".jpg");
                    params.put("email_viia","valdcolra@gmail.com");
                    Log.v("getFecha1",getFecha);
                }
                if(imageToString(bitmap) != "" ){
                    String imagen_camara = imageToString(bitmap);

                    String name ="viia_incidencia";
                    String usuario = ("Kelson");
                    String punto_= m_create_name_spot_checking.getText().toString().trim();

                    String area= m_create_name_area_checking.getText().toString().trim();
                    String observacion_= m_create_name_observacion_checking.getText().toString().trim();

//Parametros para victor email
                    params.put("producto","viia_incidencia");
                    params.put("usuario",usuario);
                    params.put("punto",punto_);
                    params.put("area",area);
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




    //guardamos la instancia
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }
    //Restauramos la isntacia del estado
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPath = savedInstanceState.getString("file_path");
    }
    //cuando obtenemos un resultado
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
                        Toast.makeText(CrearChecking.this, "Foto No Tomada Correctamente", Toast.LENGTH_SHORT).show();

                    }else{
                        m_imagePhotoChecking.setImageBitmap(bitmap);

                    }

                case SELECT_PICTURE:
                   // Uri path = data.getData();
                    // bitmap.setImageURI(path);
                    break;
            }
        }
    }
    //damos un mensaje si los permisos estan ok
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(CrearChecking.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                //mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }
    //mostramos una mensaje en caso que no tenga los permisos
    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CrearChecking.this);
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

    //convertir la imagen a string osea  base64
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
    //mostrar el shwoToolbar
    public void showToolbar(String tittle, boolean upButton) {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
         //colocamos en false
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

}
