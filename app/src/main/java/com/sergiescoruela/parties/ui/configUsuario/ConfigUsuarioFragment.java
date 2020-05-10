package com.sergiescoruela.parties.ui.configUsuario;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.sergiescoruela.parties.MainActivity;
import com.sergiescoruela.parties.R;
import com.sergiescoruela.parties.pojo.Usuarios;

import java.util.ArrayList;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ConfigUsuarioFragment extends Fragment {

    private ConfigUsuarioViewModel mViewModel;
    private EditText txtnombre, txtContraseña,txtCorreo,txtFechaNacimiento;
    private ImageView imgUsuario;
    private Button btnConf ;
    private FirebaseAuth mAuth;
    private MainActivity mainActivity;
    private Map<String, Usuarios> mapaUsuario;
    private ArrayList<Usuarios> listaUsuarios;
    FirebaseUser currentUser;





    public static ConfigUsuarioFragment newInstance() {
        return new ConfigUsuarioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel =
                ViewModelProviders.of(this).get(ConfigUsuarioViewModel.class);
        View root = inflater.inflate(R.layout.config_usuario_fragment, container, false);



        txtnombre = root.findViewById(R.id.txtNombreConfigUsuario);
        txtContraseña = root.findViewById(R.id.txtContraseñaConfigUsuario);
        txtCorreo = root.findViewById(R.id.txtCorreoConfigUsuario);
        txtFechaNacimiento = root.findViewById(R.id.txtFechaNacimientoConfigUsuario);
        imgUsuario = root.findViewById(R.id.imgusuarioCrearConfigUsuario);
        btnConf = root.findViewById(R.id.btnConfigUsuario);


        //firebase

        //Obtener referencias de la base de datos solo la primera vez
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Crear referencia en la que queremos escribir
        DatabaseReference myRef = database.getReference("usuario");

        //TRAER DATOS DEL USUARIO

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();



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

                    // nav_imagen.setImageResource(R.drawable.download);

                }
                System.out.println("Config2   "+ listaUsuarios.size());

                System.out.println("Config1   "+ listaUsuarios.size());
                for (int i = 0; i <listaUsuarios.size(); i++) {
                    System.out.println("Config3");
                    if(currentUser.getEmail().equals(listaUsuarios.get(i).getCorreo())){

                        System.out.println(currentUser.getEmail() + "    config    "+listaUsuarios.get(i).getCorreo());

                        txtnombre.setText(listaUsuarios.get(i).getNombre());
                        txtCorreo.setText(listaUsuarios.get(i).getCorreo());
                        txtContraseña.setText(listaUsuarios.get(i).getContraseña());
                        txtFechaNacimiento.setText(listaUsuarios.get(i).getFechaNacimiento());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



       /* System.out.println("Config1   "+ listaUsuarios.size());
        for (int i = 0; i <listaUsuarios.size(); i++) {
            System.out.println("Config3");
            if(currentUser.getEmail().equals(listaUsuarios.get(i).getCorreo())){

                System.out.println(currentUser.getEmail() + "    config    "+listaUsuarios.get(i).getCorreo());

                txtnombre.setText(listaUsuarios.get(i).getNombre());
                txtCorreo.setText(listaUsuarios.get(i).getCorreo());
                txtContraseña.setText(listaUsuarios.get(i).getContraseña());
                txtFechaNacimiento.setText(listaUsuarios.get(i).getFechaNacimiento());
            }
        }*/





        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ConfigUsuarioViewModel.class);
        // TODO: Use the ViewModel
    }

}
