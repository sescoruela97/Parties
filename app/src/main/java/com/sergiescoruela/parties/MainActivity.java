package com.sergiescoruela.parties;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.sergiescoruela.parties.Adapter.LocalAdapter;
import com.sergiescoruela.parties.pojo.Local;
import com.sergiescoruela.parties.pojo.Usuarios;
import com.sergiescoruela.parties.ui.login.LoginFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    //header
    private ArrayList<Usuarios> listaUsuarios;
    private Map<String, Usuarios> mapaUsuario;
    private LocalAdapter adapter;
    private int elementoFila = R.layout.elemento_local;

    private FirebaseAuth mAuth;

    private String emailLoguin;
    private String nombrelLoguin;

    public static String PRUEBA = "";
    private View view;
    FirebaseUser currentUser;
    private TextView nav_user;
    private TextView nav_email;
    private ImageView nav_imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_filtros, R.id.configUsuarioFragment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       // NavController navController = Navigation.findNavController(this, R.id.loginFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        //TRAER DATOS DEL USUARIO

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
         currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {

            emailLoguin = currentUser.getEmail();
            nombrelLoguin = currentUser.getDisplayName();
            System.out.println(emailLoguin + " current eso es una prueba ");

        }
     //header
         view = navigationView.getHeaderView(0);
          nav_user = view.findViewById(R.id.txtnombreheader);
          nav_email = view.findViewById(R.id.txtemailheader);
          nav_imagen = view.findViewById(R.id.imgHeader);
        //nav_user.setText("PEPE");





        //firebase

        //Obtener referencias de la base de datos solo la primera vez
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Crear referencia en la que queremos escribir
        DatabaseReference myRef = database.getReference("usuario");



        listaUsuarios= new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                GenericTypeIndicator<Map<String, Usuarios>> genericTypeIndicator =
                        new GenericTypeIndicator<Map<String, Usuarios>>() {};

                mapaUsuario = dataSnapshot.getValue(genericTypeIndicator);

             //   listaUsuarios.add(new Local("Goku","Son Gokū es el protagonista del manga y anime Dragon Ball creado por Akira Toriyama.",R.drawable.download,"02:00","pop","18"));

                if (mapaUsuario != null) {
                    listaUsuarios.addAll(mapaUsuario.values());
                    // indices.addAll(mapaLocal.keySet());
                    nav_user.setText(emailLoguin);
                    nav_email.setText(nombrelLoguin);
                   // nav_imagen.setImageResource(R.drawable.download);

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        FirebaseAuth.getInstance().signOut();
        finish();
        return true;
    }
*/

    public void changeNavHeaderDdata(){

        currentUser = mAuth.getCurrentUser();

        if (currentUser!=null){
            nav_user.setText(currentUser.getDisplayName());
            nav_email.setText(currentUser.getEmail());
            nav_imagen.setImageResource(R.drawable.download);
            System.out.println(currentUser.getEmail() + " current eso es una prueba del main");

        }

    }



}
