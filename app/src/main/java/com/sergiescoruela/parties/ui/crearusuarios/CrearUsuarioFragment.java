package com.sergiescoruela.parties.ui.crearusuarios;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sergiescoruela.parties.MainActivity;
import com.sergiescoruela.parties.R;
import com.sergiescoruela.parties.pojo.Usuarios;

public class CrearUsuarioFragment extends Fragment {

    private CrearUsuarioViewModel mViewModel;
    private EditText txtnombre, txtContraseña,txtCorreo,txtFechaNacimiento;
    private ImageView imgUsuario;
    private Button btnReg ;
    private FirebaseAuth mAuth;
    private MainActivity mainActivity;


    public static CrearUsuarioFragment newInstance() {
        return new CrearUsuarioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mViewModel =
                ViewModelProviders.of(this).get(CrearUsuarioViewModel.class);
        View root = inflater.inflate(R.layout.crear_usuario_fragment, container, false);



        txtnombre = root.findViewById(R.id.txtNombreUsuario);
        txtContraseña = root.findViewById(R.id.txtContraseñaUsuario);
        txtCorreo = root.findViewById(R.id.txtCorreoUsuario);
        txtFechaNacimiento = root.findViewById(R.id.txtFechaNacimientoUsuario);
        imgUsuario = root.findViewById(R.id.imgusuarioCrear);
        btnReg = root.findViewById(R.id.btnRegUsuario);

// Write a message to the database

        mAuth = FirebaseAuth.getInstance();



        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("usuario");

               // listaLocal.add(new Local("Goku","Son Gokū es el protagonista del manga y anime Dragon Ball creado por Akira Toriyama.",R.drawable.download,"02:00","pop","18"));

                Usuarios usuarios = new Usuarios(txtnombre.getText().toString(),txtCorreo.getText().toString(),txtContraseña.getText().toString()
                        ,txtFechaNacimiento.getText().toString());


                myRef.push().setValue(usuarios);

                doRegistro(txtCorreo.getText().toString(),txtContraseña.getText().toString());
            }
        });






        return root;



    }


    private void doRegistro(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("REGISTER", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mainActivity.changeNavHeaderDdata();
                            updateUI(user,getView());

                            user.sendEmailVerification();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("REGISTER", "createUserWithEmail:failure", task.getException());

                            // updateUI(null);
                        }

                        // ...
                    }
                });
    }


    private void updateUI(FirebaseUser currentUser,View view) {

        //Lo usare como toma de decisones para
        //abrir la ventana inicial

        String completeName = txtnombre.getText().toString();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(completeName)
                .build();


        if(currentUser != null){
            currentUser.updateProfile(profileUpdates);
            Navigation.findNavController(view).navigate(R.id.nav_home);




        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CrearUsuarioViewModel.class);
        // TODO: Use the ViewModel
    }


    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        mainActivity = (MainActivity) context;

    }

}
