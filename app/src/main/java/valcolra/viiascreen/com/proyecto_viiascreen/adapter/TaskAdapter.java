package valcolra.viiascreen.com.proyecto_viiascreen.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import valcolra.viiascreen.com.proyecto_viiascreen.DetalleIncidente;
import valcolra.viiascreen.com.proyecto_viiascreen.DetalleTask;
import valcolra.viiascreen.com.proyecto_viiascreen.R;
import valcolra.viiascreen.com.proyecto_viiascreen.model.ModelIncidente;

/**
 * Created by Vic on 29/09/2017.
 */

public class TaskAdapter  extends ArrayAdapter {

    // Atributos
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;


    // private static final String URL_BASE = "https://api.everlive.com/v1/e82dy3vmux1jchlu/";
    private static final String URL_BASE = "http://api.everlive.com/v1/vqfkdc51v767oq7x/";
    //private static final String URL_JSON = "viiascreen_incidentes/?filter={\"estado\":{\"$ne\":\"visto\"}}";
    private static final String URL_JSON = "viiascreen_incidentes/?filter={\"estado\":{\"$in\":[\"tarea\",\"cerrado\"]}}";

    private static final String filterTask = "/viiascreen_incidentes";
    private static final String TAG = "TaskAdapter";
    List< ModelIncidente > items;

    public TaskAdapter(Context context) {
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
        TextView m_texto_status_Incidentes = (TextView) listItemView.
                findViewById(R.id.textoStatusIncidente2);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;

        try {
            d = sdf.parse(item.getFecha());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formattedTime = output.format(d);

        m_texto_fecha_Incidente.setText(formattedTime);

        // Actualizar los Views
        m_texto_puntos_Incidente.setText(item.getPunto());

        m_texto_usuario_Incidente.setText(item.getUsuario());
        m_texto_status_Incidentes.setText(item.getEstado());
        TextView m_numeroContador = listItemView.findViewById(R.id.numeroContador);
        m_numeroContador.setText("0000"+String.valueOf(position+1));

        final String estado = m_texto_status_Incidentes.getText().toString();

        ImageView mImageEstado = listItemView.findViewById(R.id.T_estadoImagen);

        if(estado.equals("cerrado")){
            mImageEstado.setImageResource(R.drawable.lockedplomo);

        }else if(estado.equals("tarea")){
            mImageEstado.setImageResource(R.drawable.unlocked);

        }

        LinearLayout m_incidenteListClick = listItemView.findViewById(R.id.incidenteListClick);

        m_incidenteListClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getContext(), DetalleTask.class);

                String Id                =  item.getId();
                String punto                =  item.getPunto();
                String estad                =  item.getEstado();
                String fecha                =  item.getFecha();
                String usuario                =  item.getUsuario();
                String observacion2                =  item.getObservacion();
                String urlImagen1                =  item.getImagen();
                String urlImagen2                =  item.getImagen2();
                String area                =  item.getArea();
                String responsable                =  item.getResponsable();
                String cierre                =  item.getCierre();




                if(cierre.equals("")){

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date d = null;
                    try {
                        d = sdf.parse(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String formattedTime = output.format(d);

                    cierre = (String) android.text.format.DateFormat.format("yyyy-MM-dd kk:mm:ss", new Date());
                    intent.putExtra("Id", Id);
                    intent.putExtra("punto", punto);
                    intent.putExtra("fecha", formattedTime);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("observacion2", observacion2);
                    intent.putExtra("estado", estado);
                    intent.putExtra("urlImagen1", urlImagen1);
                    intent.putExtra("urlImagen2", urlImagen2);
                    intent.putExtra("area", area);
                    intent.putExtra("responsable", responsable);
                    intent.putExtra("cierre", cierre);
                }else{


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date d = null;
                    try {
                        d = sdf.parse(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String formattedTime = output.format(d);
/*---------------------------------------------------------------------------------------------------------*/

                    intent.putExtra("Id", Id);
                    intent.putExtra("punto", punto);
                    intent.putExtra("fecha", formattedTime);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("observacion2", observacion2);
                    intent.putExtra("estado", estado);
                    intent.putExtra("urlImagen1", urlImagen1);
                    intent.putExtra("urlImagen2", urlImagen2);
                    intent.putExtra("area", area);
                    intent.putExtra("responsable", responsable);
                    intent.putExtra("cierre", cierre);

                }

                getContext().startActivity(intent);

            }
        });


        return listItemView;
    }

    public List < ModelIncidente > parseJson(JSONObject jsonObject) {
        // Variables locales
        List < ModelIncidente > posts = new ArrayList< >();
        JSONArray jsonArray = null;
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
                            objeto.getString("observacion2"),
                            objeto.getString("area"),
                            objeto.getString("usuario"),
                            objeto.getString("closedTask")
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
}
