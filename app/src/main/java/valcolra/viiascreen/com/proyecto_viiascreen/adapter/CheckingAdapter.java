package valcolra.viiascreen.com.proyecto_viiascreen.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import valcolra.viiascreen.com.proyecto_viiascreen.DetalleCompleteChecking;
import valcolra.viiascreen.com.proyecto_viiascreen.R;
import valcolra.viiascreen.com.proyecto_viiascreen.model.ModelChecking;
import valcolra.viiascreen.com.proyecto_viiascreen.model.ModelIncidente;

/**
 * Created by Vic on 29/09/2017.
 */

public class CheckingAdapter extends ArrayAdapter {
        /*Variables*/

        private LinearLayout m_linearIdDetailsChecking;
        /*voley nueva solicitud*/
        private RequestQueue requestQueue;
        JsonObjectRequest jsArrayRequest;

        /*Servicio de la url*/
        private static final String URL_BASE = "http://api.everlive.com/v1/vqfkdc51v767oq7x/";
        private static final String URL_JSON = "viiascreen_checking/";
        /*Tag identificador*/
        private static final String TAG = "TaskAdapter";
        List<ModelChecking> items;

    public CheckingAdapter(Context context) {

        super(context, 0);

        // Crear nueva cola de peticiones
        requestQueue = Volley.newRequestQueue(context);

        // Nueva petición JSONObject
        jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_BASE + URL_JSON,
                null,
                new Response.Listener <JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        items = parseJson(response);
                        notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());
                    }
                }
        );

        // Añadir petición a la cola
        requestQueue.add(jsArrayRequest);
    }

    // Comparator for Ascending Order
    public static Comparator<String> StringAscComparator = new Comparator<String>() {

        public int compare(String app1, String app2) {

            String stringName1 = app1;
            String stringName2 = app2;

            return stringName1.compareToIgnoreCase(stringName2);
        }
    };
    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Referencia del view procesado
        View listItemView;

        //Comprobando si el View no existe
        listItemView = null == convertView ? layoutInflater.inflate(
                R.layout.lista_item_design_checking,
                parent,
                false) : convertView;



        // Obtener el item actual
        final ModelChecking item = items.get(position);

        // Obtener Views
        final ImageView m_viia_list_desgin_checking_urlImagen1 =  listItemView.
                findViewById(R.id.viia_list_desgin_checking_urlImagen1);
        final ImageView m_viia_list_desgin_checking_urlImagen2 =  listItemView.
                findViewById(R.id.viia_list_desgin_checking_urlImagen2);





        final TextView m_viia_list_desgin_checking_id = listItemView.
                findViewById(R.id.viia_list_desgin_checking_id);

        TextView m_viia_list_desgin_checking_CreatedAt = listItemView.
                findViewById(R.id.viia_list_desgin_checking_CreatedAt);
        TextView m_viia_list_desgin_checking_estado =   listItemView.
                findViewById(R.id.viia_list_desgin_checking_estado);

        TextView m_viia_list_desgin_checking_punto = listItemView.
                findViewById(R.id.viia_list_desgin_checking_punto);
        TextView m_viia_list_desgin_checking_usuario =  listItemView.
                findViewById(R.id.viia_list_desgin_checking_usuario);
        /*TextView m_viia_list_desgin_checking_responsable =   listItemView.
                findViewById(R.id.viia_list_desgin_checking_responsable);*/

        //String dates = item.getCreatedAt().substring(0, 10);

      // Actualizar los Views


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(item.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);

        m_viia_list_desgin_checking_id.setText(item.getId());
        m_viia_list_desgin_checking_CreatedAt.setText(formattedTime);
        m_viia_list_desgin_checking_estado.setText(item.getEstado());
        m_viia_list_desgin_checking_punto.setText(item.getPunto());
        m_viia_list_desgin_checking_usuario.setText(item.getUsuario());
        //m_viia_list_desgin_checking_responsable.setText(item.getResponsable());

        //Obtenemos stado
        String estado = m_viia_list_desgin_checking_estado.getText().toString();

        //Imagen del estado que trabajaremos
        ImageView mImageEstado = listItemView.findViewById(R.id.estadoImagen);

        /*Setear el icono segun el estado*/
        if(estado.equals("pendiente")){
            mImageEstado.setImageResource(R.drawable.unlocked);

        }else if(estado.equals("cerrado")){
            mImageEstado.setImageResource(R.drawable.lockedplomo);
        }
        /*FIN Setear el icono segun el estado*/

        TextView m_numeroContador = listItemView.findViewById(R.id.numeroContador);
        m_numeroContador.setText("0000"+String.valueOf(position+1));


        /*Agregaremos un escuchador (Listener) */
        m_linearIdDetailsChecking = listItemView.findViewById(R.id.linearIdDetailsChecking);
        /*cuando se hace click en esta variable m_linearIdDetailsChecking*/
        m_linearIdDetailsChecking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Envio un mensaje por log para visualizar el ID*/
                Log.v("Id-item",item.getId());
                /*Guardo la imagen en cache y envio a otro mensaje*/
                ImageView m_viia_list_checking_desgin_checking_urlImagen2 = view.findViewById(R.id.viia_list_desgin_checking_urlImagen2);
               // m_viia_list_checking_desgin_checking_urlImagen2.buildDrawingCache();
               // Bitmap bitmap = m_viia_list_checking_desgin_checking_urlImagen2.getDrawingCache();

                /*Recuperar datos de la data*/
                String Id =  item.getId();
                String area =  item.getArea();
                String ClosedCheck =  item.getClosedCheck();
                String CreateAt =  item.getCreatedAt();
                String estado =  item.getEstado();
                String observacion1 =  item.getObservacion1();
                String observacion2 =  item.getObservacion2();
                String punto =  item.getPunto();
                String responsable =  item.getResponsable();
                String UrlImagen2 =  item.getUrlImagen2();

                /*Declara un intenet y especificar a que actividad queremos que se diriga*/
                Intent intent = new Intent(getContext(), DetalleCompleteChecking.class);

                /*Agregar la data que se se guardo en una variabkle y mandarla en el intenet*/
                //intent.putExtra("image", bitmap);
                intent.putExtra("Id", Id);
                intent.putExtra("area", area);
                intent.putExtra("punto", punto);
                intent.putExtra("ClosedCheck", ClosedCheck);
                intent.putExtra("CreateAt", CreateAt);
                intent.putExtra("estado", estado);
                intent.putExtra("UrlImagen2", UrlImagen2);
                intent.putExtra("observacion1", observacion1);
                intent.putExtra("observacion2", observacion2);
                intent.putExtra("responsable", responsable);
                /*Obtenemos el contexto y startActivity*/
                getContext().startActivity(intent);

                //obtenemos el id para pasarlo
                String ids = item.getId();
                //si el estado es pendiente y se le dio click envia
                //el Id y mandalo a visto
                if(estado.equals("pendiente")){
                    //recibimos el ID
                    pasarVisto(ids);
                    //Mostramos un mensaje tipo Snackbar con un mensaje dentro
                    Snackbar.make(view, "Visto Por Kelson", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        // Petición para obtener la imagen
        ImageRequest request = new ImageRequest(
                item.getUrlImagen2(),
                new Response.Listener <Bitmap> () {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        //Glide.with(getContext()).load(imageToString(bitmap)).into(m_imagen_Incidente);

                        /*seteamos la imagen recuperada en cada ImageView*/
                        m_viia_list_desgin_checking_urlImagen2.setImageBitmap(bitmap);
                       // m_viia_list_desgin_checking_urlImagen2.setVisibility(View.VISIBLE);

                        //Picasso.with(getContext()).load(String.valueOf(imageToString(bitmap))).into(m_viia_list_desgin_checking_urlImagen2);
                     }
                }, 0, 0, null, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        /*Mostramos un mensaje de error por log*/
                        Log.d(TAG, "Error en respuesta Bitmap: " + error.getMessage());
                        /*en este caso mostramos un mensaje de error*/
                        m_viia_list_desgin_checking_urlImagen2.setImageResource(R.drawable.noimages);
                      //  m_viia_list_desgin_checking_urlImagen2.setVisibility(View.VISIBLE);
                    }
                });
        // Añadir petición a la cola
        requestQueue.add(request);
        //retornaremos el listItemView
        return listItemView;
    }


    public List <ModelChecking> parseJson(JSONObject jsonObject) {
        // Variables locales
        List <ModelChecking> posts = new ArrayList< >();
        JSONArray jsonArray = null;
        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("Result");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    /*Dentro de este objeto referenciamos al constructor declarado en la case Modelo
                    * y obtenemos la misma cantidad de valores que ingresamos en el constructor*/
                    ModelChecking post = new ModelChecking(
                            objeto.getString("Id"),
                            objeto.getString("area"),
                            objeto.getString("closedCheck"),
                            objeto.getString("CreatedAt"),
                            objeto.getString("estado"),
                            objeto.getString("observacion1"),
                            objeto.getString("observacion2"),
                            objeto.getString("punto"),
                            objeto.getString("usuario"),
                            objeto.getString("urlImagen1"),
                            objeto.getString("urlImagen2"),
                            objeto.getString("responsable")
                    );
                    //agregamos al post este objeto dependiendo de la cantidad de data que reciba
                    posts.add(post);

                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return posts;
    }


    //Esto convierte la imagen a string osea el bitmap
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


    //este metodo recibe un Id para poder hacer una actualización y representar un visto
    private void pasarVisto(String ID) {
        //colocamos la URL BASE + el identificar a modificar
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,URL_BASE+URL_JSON+ID ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*si todos sale bien mostrar un mensaje o realizar una accion en esta section de Response*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Ocurrio un error inesperado"+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                //parametro a actualizar
                params.put("estado","visto");
                return params;
            }
        };
        //esto me ayudará a aplazar
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
        //enviamos una peticion
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


}
