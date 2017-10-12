package valcolra.viiascreen.com.proyecto_viiascreen.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import valcolra.viiascreen.com.proyecto_viiascreen.DetalleCompleteChecking;
import valcolra.viiascreen.com.proyecto_viiascreen.DetalleInc;
import valcolra.viiascreen.com.proyecto_viiascreen.DetalleIncidente;
import valcolra.viiascreen.com.proyecto_viiascreen.R;
import valcolra.viiascreen.com.proyecto_viiascreen.model.ModelChecking;
import valcolra.viiascreen.com.proyecto_viiascreen.model.ModelInc;

/**
 * Created by Vic on 3/10/2017.
 */

public class IncAdapter extends ArrayAdapter {


    private LinearLayout m_IncListClick;
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;

    private static final String URL_BASE = "http://api.everlive.com/v1/vqfkdc51v767oq7x/";
    private static final String URL_JSON = "viiascreen_incidentes/";
    private static final String TAG = "IncAdapter";
    List<ModelInc> items;

    public IncAdapter(Context context) {
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
                        Log.d(TAG, "Responde" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());
                    }
                }
        )  {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                // params.put("X-Everlive-Sort", "CreatedAt:1");
                return params;
            }
    };

        // Añadir petición a la cola
        requestQueue.add(jsArrayRequest);
    }

    @Override
    public Object getItem(int position) {
        return getCount() - position - 1;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Referencia del view procesado
        View listItemView;

        //Comprobando si el View no existe
        listItemView = null == convertView ? layoutInflater.inflate(
                R.layout.activity_design_inc,
                parent,
                false) : convertView;

        // Obtener el item actual
        final ModelInc item = items.get(position);



        // Obtener Views
        final ImageView m_design_inc_urlImagen1 =  listItemView.
                findViewById(R.id.design_inc_urlImagen1);
        TextView m_design_inc_Id =  listItemView.findViewById(R.id.design_inc_Id);
        TextView m_design_inc_area =  listItemView.findViewById(R.id.design_inc_area);
        final TextView m_design_inc_estado =  listItemView.findViewById(R.id.design_inc_estado);
        TextView m_design_inc_usuario =   listItemView.findViewById(R.id.design_inc_usuario);
        TextView m_design_inc_closedTask =  listItemView.findViewById(R.id.design_inc_closedTask);
        TextView m_design_inc_createAt =   listItemView.findViewById(R.id.design_inc_createAt);
        TextView m_design_inc_createdTask =   listItemView.findViewById(R.id.design_inc_createdTask);
        TextView m_design_inc_observacion1 =  listItemView.findViewById(R.id.design_inc_observacion1);
        TextView m_design_inc_observacion2 =  listItemView.findViewById(R.id.design_inc_observacion2);
        TextView m_design_inc_observacion3 =  listItemView.findViewById(R.id.design_inc_observacion3);
        TextView m_design_inc_servicio =   listItemView.findViewById(R.id.design_inc_servicio);
        ImageView m_design_inc_urlImagen2 =   listItemView.findViewById(R.id.design_inc_urlImagen2);
        TextView m_design_inc_punto =  listItemView.findViewById(R.id.design_inc_punto);

        // Actualizar los Views
        m_design_inc_Id.setText(item.getId());
        m_design_inc_area.setText(item.getArea());
        m_design_inc_estado.setText(item.getEstado());
        m_design_inc_usuario.setText(item.getUsuario());

        m_design_inc_closedTask.setText(item.getClosedTask());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(item.getCreateAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);

        m_design_inc_createAt.setText(formattedTime);


        m_design_inc_createdTask.setText(item.getCreatedTask());
        m_design_inc_observacion1.setText(item.getObservacion1());
        m_design_inc_observacion2.setText(item.getObservacion2());
        m_design_inc_observacion3.setText(item.getObservacion3());
        m_design_inc_servicio.setText(item.getServicio());
        //m_design_inc_urlImagen2.setText(item.getUrlImagen2());
        m_design_inc_punto.setText(item.getPunto());


        m_IncListClick = listItemView.findViewById(R.id.IncListClick);

        m_IncListClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageView m_design_inc_urlImagen1 = view.findViewById(R.id.design_inc_urlImagen1);
                m_design_inc_urlImagen1.buildDrawingCache();
                Bitmap bitmap = m_design_inc_urlImagen1.getDrawingCache();


                String estados = m_design_inc_estado.getText().toString();
                String ids = item.getId();

                if(estados.equals("pendiente")){
                    pasarVisto(ids);
                    Toast.makeText(getContext(),"Visto Por Kelson ",Toast.LENGTH_LONG).show();

                    Snackbar.make(view, "Visto Por Kelson", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                String Id                =  item.getId();
                String area              =  item.getArea();
                String estado            =  item.getEstado();
                String usuario           =  item.getUsuario();
                String closedTask        =  item.getClosedTask();
                String createAt          =  item.getCreateAt();
                String direccion          =  item.getDireccion();
                String ubicacion          =  item.getUbicacion();
                String createdTask       =  item.getCreatedTask();
                String observacion1      =  item.getObservacion1();
                String observacion2      =  item.getObservacion2();
                String observacion3      =  item.getObservacion3();
                String servicio          =  item.getServicio();
                String urlImagen1        =  item.getUrlImagen1();
                String urlImagen2        =  item.getUrlImagen2();
                String punto             =  item.getPunto();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = null;
                try {
                    d = sdf.parse(item.getCreateAt());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String formattedTime = output.format(d);



                Intent intent = new Intent(getContext(), DetalleInc.class);

                intent.putExtra("image",                    bitmap);
                intent.putExtra("Id",                       Id);
                intent.putExtra("area",                     area);
                intent.putExtra("estado",                   estado);
                intent.putExtra("usuario",                  usuario);
                intent.putExtra("closedTask",               closedTask);
                intent.putExtra("direccion",                direccion);
                intent.putExtra("ubicacion",                ubicacion);
                intent.putExtra("createAt",                 formattedTime);
                intent.putExtra("createdTask",              createdTask);
                intent.putExtra("observacion1",             observacion1);
                intent.putExtra("observacion2",             observacion2);
                intent.putExtra("observacion3",             observacion3);
                intent.putExtra("servicio",                 servicio);
                intent.putExtra("urlImagen1",               urlImagen1);
                intent.putExtra("urlImagen2",               urlImagen2);
                intent.putExtra("punto",                    punto);
                getContext().startActivity(intent);
            }

        });

        String estado = m_design_inc_estado.getText().toString();

        ImageView mImageEstado = listItemView.findViewById(R.id.estadoImagen);

        if(estado.equals("pendiente")){
            mImageEstado.setImageResource(R.drawable.checknovisto);

        }else if(estado.equals("visto")){
            mImageEstado.setImageResource(R.drawable.checkvisto);
        }else if(estado.equals("cerrado")){
            mImageEstado.setImageResource(R.drawable.checksend);
        }
        else if(estado.equals("tarea")){
            mImageEstado.setImageResource(R.drawable.checksend);
        }

        TextView m_numeroContador = listItemView.findViewById(R.id.numeroContador);
        m_numeroContador.setText("0000"+String.valueOf(position+1));

        return listItemView;
}


    public List <ModelInc> parseJson(JSONObject jsonObject) {
        // Variables locales
        List <ModelInc> posts = new ArrayList< >();
        JSONArray jsonArray = null;



        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("Result");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    ModelInc post = new ModelInc(
                            objeto.getString("Id"),
                            objeto.getString("area"),
                            objeto.getString("estado"),
                            objeto.getString("usuario"),
                            objeto.getString("CreatedAt"),
                            objeto.getString("CreatedAt"),
                            objeto.getString("createdTask"),
                            objeto.getString("direccion"),
                            objeto.getString("ubicacion"),
                            objeto.getString("observacion1"),
                            objeto.getString("observacion2"),
                            objeto.getString("observacion3"),
                            objeto.getString("servicio"),
                            objeto.getString("urlImagen1"),
                            objeto.getString("urlImagen2"),
                            objeto.getString("punto")
                    );
                    posts.add(post);

                    Log.v("objeto",""+objeto.getString("CreatedAt"));
                    Log.v("RECUPERANDO", String.valueOf(post));

                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("posts",""+posts);


        return posts;
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
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
    private void pasarVisto(String ID) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,URL_BASE+URL_JSON+ID ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getContext(),"Actualizado Correctamente "+response,Toast.LENGTH_LONG).show();

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

                params.put("estado","visto");

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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
