package valcolra.viiascreen.com.proyecto_viiascreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private ProgressDialog mProgress;
    private EditText mEdiTextUser;
    private EditText mEdiTextPasswordUser;

    private Button mBtnLogin;

    /*Servicio de Login*/
    private String validarAccesos = "https://api.everlive.com/v1/e82dy3vmux1jchlu/Users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

  /*instanciamos*/
        mEdiTextUser = (EditText) findViewById(R.id.username);
        mEdiTextPasswordUser = (EditText) findViewById(R.id.password);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mProgress = new ProgressDialog(this);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.setMessage("Accediendo...");
                mProgress.show();
                //login();
                login();
            }
        });

    }

    private void login() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, validarAccesos, new Response.Listener < String > () {
            @Override
            public void onResponse(String response) {
                String email = mEdiTextUser.getText().toString();
                Toast.makeText(getApplicationContext(), "Bienvenido: " + email, Toast.LENGTH_SHORT).show();
                mProgress.dismiss();
                welcome();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Detalle de error " + error, Toast.LENGTH_SHORT).show();
                mProgress.dismiss();
            }
        }) {
            @Override
            protected Map < String, String > getParams() throws AuthFailureError {
                Map < String, String > params = new HashMap < String, String > ();

                String email = mEdiTextUser.getText().toString();
                String password = mEdiTextPasswordUser.getText().toString();
                String grant_type = "password";
                params.put("Content-Type", "application/json");

                params.put("email", email);
                params.put("password", password);

                params.put("grant_type", grant_type);
                Log.v("Detalles: ", email + "," + password);


                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void welcome() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }


    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}