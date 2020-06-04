package com.sergiescoruela.parties;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sergiescoruela.parties.Adapter.LocalAdapter;
import com.sergiescoruela.parties.pojo.Local;
import com.sergiescoruela.parties.pojo.Usuarios;
import com.sergiescoruela.parties.ui.home.HomeFragment;
import com.sergiescoruela.parties.ui.login.LoginFragment;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends AppCompatActivity {

    private static final long ONE_MEGABYTE = 1024 * 1024 *5 ;
    private static final int ADD_TAG_IF_NECESSARY = 1 ;
    private AppBarConfiguration mAppBarConfiguration;
    //header
    private ArrayList<Usuarios> listaUsuarios;
    private Map<String, Usuarios> mapaUsuario;
    private LocalAdapter adapter;
    private int elementoFila = R.layout.elemento_local;

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    FirebaseStorage storage = FirebaseStorage.getInstance();

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


                if (mapaUsuario != null) {
                    listaUsuarios.addAll(mapaUsuario.values());
                    // indices.addAll(mapaLocal.keySet());
                    changeNavHeaderDdata();


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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.action_logaut:

                if(currentUser != null){

                    FirebaseAuth.getInstance().signOut();
                }
                Fragment  sfragment = LoginFragment.newInstance(null);

                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.nav_host_fragment, sfragment)
                        .show(sfragment)
                        .commit();


        }


        //finish();
        return super.onOptionsItemSelected(item);
    }



    public void changeNavHeaderDdata(){

        currentUser = mAuth.getCurrentUser();

        if (currentUser!=null){
            nav_user.setText(currentUser.getDisplayName());
            nav_email.setText(currentUser.getEmail());



            mStorageRef = FirebaseStorage.getInstance().getReference();


            StorageReference islandRef = storage.getReferenceFromUrl("gs://parties-33bbc.appspot.com").child("fotos/"+currentUser.getEmail());

            File localFile = null;
            try {
                localFile = File.createTempFile("image", "jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }

            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmapIMG = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    nav_imagen.setImageBitmap(bitmapIMG);


                }
            });





/*

            Picasso.get()
                    .load(currentUser.getPhotoUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(nav_imagen);
            System.out.println(currentUser.getPhotoUrl()+"prueba de imagen1111111");
*/

            //nav_imagen.setImageResource( );
            System.out.println(currentUser.getEmail() + " current eso es una prueba del main");

        }

    }



}
