package com.sergiescoruela.parties.ui.login;

import androidx.lifecycle.ViewModelProviders;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sergiescoruela.parties.MainActivity;
import com.sergiescoruela.parties.R;
import com.sergiescoruela.parties.ui.home.HomeFragment;

import java.util.concurrent.Executor;

public class LoginFragment extends Fragment {

    public static final String ARG_SECTION_TITLE = "section_number";

    private LoginViewModel mViewModel;
    private EditText txtEmail, txtPass;
    private Button btnReg , btnAcceder;
    private ProgressBar pbloading;
    private TextView lblrecuperar;

    private FirebaseAuth mAuth;
    private MainActivity mainActivity;

    private AppBarConfiguration mAppBarConfiguration;


    public static LoginFragment newInstance(Object o) {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mViewModel =
                ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.login_fragment, container, false);

        txtEmail = root.findViewById(R.id.txtEmailLogin);
        txtPass = root.findViewById(R.id.txtPassLogin);
        pbloading = root.findViewById(R.id.pbLoguin);
        btnReg = root.findViewById(R.id.btnRegistrar);
        btnAcceder = root.findViewById(R.id.btnLoguin);
        lblrecuperar = root.findViewById(R.id.lblRecuperar);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pbloading.setVisibility(View.VISIBLE);

                String email = txtEmail.getText().toString();
                String password = txtPass.getText().toString();

                doLoguin(email,password,view);

            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbloading.setVisibility(View.VISIBLE);
                // Comprabar todo lo necesario .......
            //    String email = txtEmail.getText().toString();
              //  String password = txtPass.getText().toString();

              //  doRegistro(email, password);

                Navigation.findNavController(view).navigate(R.id.crearUsuarioFragment);

            }
        });

        lblrecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.recuperarFragment);
            }
        });


        return root;
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser,getView());
    }

    public void updateUI( FirebaseUser currentUser, View view) {

        //Lo usare como toma de decisones para
        //abrir la ventana inicial



        if(currentUser != null){
            Navigation.findNavController(view).navigate(R.id.nav_home);

             String usuLoguin = currentUser.getEmail();


        }

    }



    private void doRegistro(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pbloading.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("REGISTER", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mainActivity.changeNavHeaderDdata();
                            updateUI(user,getView());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("REGISTER", "createUserWithEmail:failure", task.getException());

                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }
    private void doLoguin(String email, String password, final View view) {

        //Lamar  firebaseAuth

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        pbloading.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("ACCEDER", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mainActivity.changeNavHeaderDdata();
                            updateUI(user,view);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ACCEDER", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }


                    }
                });


     /*   mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener()
        */

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }



    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        System.out.println(" current eso es una prueba del loguin");

        mainActivity = (MainActivity) context;

    }


}
