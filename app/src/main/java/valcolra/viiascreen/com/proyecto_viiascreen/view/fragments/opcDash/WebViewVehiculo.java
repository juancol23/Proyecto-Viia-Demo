package valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcDash;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import valcolra.viiascreen.com.proyecto_viiascreen.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewVehiculo extends Fragment {
    public WebView mWebView;
    public SwipeRefreshLayout swipeRefreshLayout;
    final public String BASEURL = "http://legalmovil.com/de/sense.html#viia_sense";
    ImageView mImagenVehiculo;
    public WebViewVehiculo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web_view_vehiculo, container, false);
        mImagenVehiculo = view.findViewById(R.id.viia_web_vehiculo);

        Glide.with(this).load(R.drawable.viia_vehiculo_min).into(mImagenVehiculo);

        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setSize(8);
        mWebView = (WebView) view.findViewById(R.id.WebViewDashboard2);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //mWebView.loadUrl(BASEURL);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isOnline(getActivity())) {
                    swipeRefreshLayout.setRefreshing(false);

                    //mWebView.loadUrl(BASEURL);
                }else {
                    Toast.makeText(getActivity(), "Compruebe su conexión a internet",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView v, String url) {

                swipeRefreshLayout.setRefreshing(false);

            }

        });

        return view;


    }
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

}
