package valcolra.viiascreen.com.proyecto_viiascreen.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import valcolra.viiascreen.com.proyecto_viiascreen.DetalleCompleteChecking;
import valcolra.viiascreen.com.proyecto_viiascreen.DetalleIncidente;
import valcolra.viiascreen.com.proyecto_viiascreen.R;
import valcolra.viiascreen.com.proyecto_viiascreen.model.ModelIncidente;

import static android.os.Build.ID;

/**
 * Created by Vic on 25/09/2017.
 */

public class IncidenteAdapter extends ArrayAdapter {

    // Atributos
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;


    // private static final String URL_BASE = "https://api.everlive.com/v1/e82dy3vmux1jchlu/";
    private static final String URL_BASE = "http://api.everlive.com/v1/vqfkdc51v767oq7x/";
    private static final String URL_JSON = "viiascreen_incidentes/";
    private static final String filterTask = "/viiascreen_incidentes";
    private static final String TAG = "IncidenteAdapter";
    List < ModelIncidente > items;

    public IncidenteAdapter(Context context) {
        super(context, 0);

        // Crear nueva cola de peticiones
        requestQueue = Volley.newRequestQueue(context);

        // Nueva petición JSONObject
        jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_BASE + URL_JSON,
                null,
                new Response.Listener < JSONObject > () {
                    @Override
                    public void onResponse(JSONObject response) {
                        items = parseJson(response);
                        notifyDataSetChanged();
                        Log.v("eeeee",""+items);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());

                    }
                }
        )


        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("punto", "-1");
                params.put("cache-control", "no-cache");
                params.put("postman-token", "ef2f4d4d-5277-6e82-8f10-d5a6a76c3fc0");
                Log.v("eeeee",""+params);
                return params;
            }
        };

        // Añadir petición a la cola
        requestQueue.add(jsArrayRequest);

    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Referencia del view procesado
        final View listItemView;

        //Comprobando si el View no existe
        listItemView = null == convertView ? layoutInflater.inflate(
                R.layout.lista_item_design_incidente,
                parent,
                false) : convertView;




        // Obtener el item actual
        final ModelIncidente item = items.get(position);


        // Obtener Views
        final ImageView m_imagen_Incidente = (ImageView) listItemView.
                findViewById(R.id.imagenPostIncidente2);
        TextView m_texto_puntos_Incidente = (TextView) listItemView.
                findViewById(R.id.textoPuntoIncidente);
        TextView m_texto_fecha_Incidente = (TextView) listItemView.
                findViewById(R.id.textoFechaIncidente);
        TextView m_texto_usuario_Incidente = (TextView) listItemView.
                findViewById(R.id.textoUsuarioIncidente);
       // TextView m_viia_list_desgin_checking_responsable = (TextView) listItemView.
           //     findViewById(R.id.viia_list_desgin_checking_responsable);

        TextView m_numeroContador = listItemView.findViewById(R.id.numeroContador);

        m_numeroContador.setText("0000"+String.valueOf(position+1));

        TextView m_texto_status_Incidentes = (TextView) listItemView.
                findViewById(R.id.textoStatusIncidente2);

        // m_viia_list_desgin_checking_responsable.setText(item.getPunto());
        // Actualizar los Views
        m_texto_puntos_Incidente.setText(item.getPunto());
        m_texto_fecha_Incidente.setText(item.getFecha());
        m_texto_usuario_Incidente.setText(item.getUsuario());

        m_texto_status_Incidentes.setText(item.getEstado());

        final String estado = m_texto_status_Incidentes.getText().toString();

        final ImageView mImageEstado = listItemView.findViewById(R.id.estadoImagen);
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

        LinearLayout m_incidenteListClick = listItemView.findViewById(R.id.incidenteListClick);
        m_incidenteListClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageView mImageEstado = listItemView.findViewById(R.id.estadoImagen);

                String visto = item.getId();

                if(estado.equals("pendiente")){
                    pasarVisto(visto);
                    Toast.makeText(getContext(),"Visto Por Kelson",Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(getContext(), DetalleIncidente.class);

                String ID = item.getId();
                ImageView m_viia_list_checking_desgin_checking_urlImagen2 = view.findViewById(R.id.imagenPostIncidente2);
                m_viia_list_checking_desgin_checking_urlImagen2.buildDrawingCache();
                Bitmap bitmap = m_viia_list_checking_desgin_checking_urlImagen2.getDrawingCache();


                TextView m_textoPuntos = view.findViewById(R.id.textoPuntoIncidente);
                TextView m_textofecha = view.findViewById(R.id.textoFechaIncidente);
                TextView m_textoUsuario = view.findViewById(R.id.textoUsuarioIncidente);
                TextView m_textoEstado = view.findViewById(R.id.textoStatusIncidente2);
                TextView m_textoServicio = view.findViewById(R.id.textoUsuarioIncidente);
                TextView juanObser = view.findViewById(R.id.textoUsuarioIncidente);

                String punto = m_textoPuntos.getText().toString();

                String fecha = m_textofecha.getText().toString();


                String usuario = m_textoUsuario.getText().toString();
                String estado = m_textoEstado.getText().toString();
                String obser = juanObser.getText().toString();


                intent.putExtra("incidentePunto", punto);
                intent.putExtra("fechaIncidenteP", fecha);
                intent.putExtra("localizacionIncidente", "Javier Prado Este 488");
                intent.putExtra("direccionIncidente", "Javier Prado Este 488 - Lado B");
                intent.putExtra("statusIncidente", estado);
                intent.putExtra("creadoPorIncidente", usuario);
                intent.putExtra("observacionIncidente", obser);

                intent.putExtra("image", bitmap);
                intent.putExtra("ID", ID);


                getContext().startActivity(intent);
            }
        });

        // Petición para obtener la imagen
        ImageRequest request = new ImageRequest(
                item.getImagen(),
                new Response.Listener < Bitmap > () {
                    @Override
                    public void onResponse(Bitmap bitmap) {

                       Glide.with(getContext()).load(imageToString(bitmap)).into(m_imagen_Incidente);
                      //  m_imagen_Incidente.setImageBitmap(bitmap);
                       // m_imagen_Incidente.setImageBitmap(bitmap);
                      //  m_imagen_Incidente.setVisibility(View.VISIBLE);
                       // Picasso.with(getContext()).load(String.valueOf(imageToString(bitmap))).into(m_imagen_Incidente);

                    }
                }, 0, 0, null, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error en respuesta Bitmap: " + error.getMessage());
                        //m_imagen_Incidente.setImageResource(R.drawable.noimages);
                        //m_imagen_Incidente.setVisibility(View.VISIBLE);
                    }
                });
        // Añadir petición a la cola
        requestQueue.add(request);


        return listItemView;
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



    public List < ModelIncidente > parseJson(JSONObject jsonObject) {
        // Variables locales
        List < ModelIncidente > posts = new ArrayList < > ();
        JSONArray jsonArray = null;

        Collections.reverse(posts);
        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("Result");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);
                    ModelIncidente post = new ModelIncidente(
                            objeto.getString("urlImagen1"),
                            objeto.getString("urlImagen2"),
                            objeto.getString("punto"),
                            objeto.getString("createdTask"),
                            objeto.getString("usuario"),
                            objeto.getString("estado"),
                            objeto.getString("Id"),
                            objeto.getString("Id"),
                            objeto.getString("Id"),
                            objeto.getString("Id"),
                            objeto.getString("Id")
                    );
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


    private byte[] imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 1, byteArrayOutputStream);
        bitmap.createScaledBitmap(bitmap, 5, 3, true);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return imgBytes;
    }




}