package valcolra.viiascreen.com.proyecto_viiascreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.Listar.ListChecking;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.Listar.ListInc;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.Listar.ListarIncidencias;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.Listar.ListarTask;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcDash.ViiaChecking;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcDash.ViiaCrow;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcDash.ViiaLive;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcDash.ViiaPlayer;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcDash.ViiaScreen;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcDash.Viia_kpi;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcDash.Viia_task;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcDash.WebViewDashboard;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcDash.WebViewVehiculo;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcLateral.ViiaManager;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcLateral.ViiaPerfil;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcLateral.ViiaPuntos;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcLateral.ViiaReporte;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.opcLateral.ViiaRoles;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegation);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListInc ListInc = new ListInc();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenido, ListInc)
                .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                .addToBackStack(null).commit();

        //mostrar un mensaje el toolbar personalizar
        showToolbar("Lista de Incidentes");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //referenciamos el navigation
        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    //metodo de titulo de toolbar personalizable
    public void showToolbar(String tittle) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarCI);
    }
    //cuando regresamos no valla atras si no que se cierre
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //opciones de menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegation, menu);
        return true;
    }
    //titulo cambiado
    private void titleChanged() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }


    //Cuando se presione algun item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.viia_vehiculo) {
            showToolbar("Viia Vehiculo");
            WebViewVehiculo webViewVehiculo = new WebViewVehiculo();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, webViewVehiculo)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();
            titleChanged();
            return true;
        }
        if (id == R.id.viia_sense) {
            showToolbar("Viia Sense");
            WebViewDashboard webViewDashboard = new WebViewDashboard();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, webViewDashboard)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();
            titleChanged();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.viia_crowd) {
            showToolbar("Viia Crowd");
            ViiaCrow viiaCrow = new ViiaCrow();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, viiaCrow)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();
            titleChanged();
            return true;
        }
        if (id == R.id.viia_screen) {
            showToolbar("Viia Screen");
            ViiaScreen ViiaScreen = new ViiaScreen();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, ViiaScreen)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();
            titleChanged();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.viia_live) {
            showToolbar("Viia Live");
            ViiaLive ViiaLive = new ViiaLive();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, ViiaLive)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();
            titleChanged();
            return true;
        }
        if (id == R.id.player) {
            showToolbar("Viia Player");
            ViiaPlayer ViiaPlayer = new ViiaPlayer();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, ViiaPlayer)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();
            titleChanged();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.viia_checking) {
            ViiaChecking ViiaChecking = new ViiaChecking();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, ViiaChecking)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();
            titleChanged();
            return true;
        }
        if (id == R.id.viia_task) {
            showToolbar("Viia kpi");
            Viia_task Viia_task = new Viia_task();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, Viia_task)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();
            titleChanged();
            return true;
        }
        if (id == R.id.viia_kpi) {
            showToolbar("Viia kpi");
            Viia_kpi Viia_kpi = new Viia_kpi();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, Viia_kpi)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();
            titleChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //cuando se seleccione el item de navigation
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_incidencias) {
            // Handle the camera action

            ListInc ListInc = new ListInc();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, ListInc)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();



            showToolbar("Listar Incidencias");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();



        } else if (id == R.id.nav_tarea) {

            ListarTask ListarTask = new ListarTask();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, ListarTask)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null).commit();

            showToolbar("Listar Tareas");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();


        } else if (id == R.id.nav_checking) {

            ListChecking  listChecking = new ListChecking();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, listChecking)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null).commit();

            showToolbar("Listar Checking");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

        } else if (id == R.id.nav_dashboard) {

            showToolbar("Viia Vehiculo");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            WebViewVehiculo webViewVehiculo = new WebViewVehiculo();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, webViewVehiculo)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();

        } else if (id == R.id.nav_reporte) {

            showToolbar("Reporte");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            ViiaReporte ViiaReporte = new ViiaReporte();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, ViiaReporte)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();



        } else if (id == R.id.nav_perfil) {

            showToolbar("Perfil");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            ViiaPerfil ViiaPerfil = new ViiaPerfil();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, ViiaPerfil)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();

        } else if (id == R.id.nav_puntos) {

            showToolbar("Puntos");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            ViiaPuntos ViiaPuntos = new ViiaPuntos();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, ViiaPuntos)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();


        } else if (id == R.id.nav_usuarios) {

            showToolbar("Usuarios");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();


            ViiaManager ViiaManager = new ViiaManager();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, ViiaManager)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();

        } else if (id == R.id.nav_rol) {

            showToolbar("Roles");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();


            ViiaRoles ViiaRoles = new ViiaRoles();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido, ViiaRoles)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}