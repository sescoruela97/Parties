package com.sergiescoruela.parties.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.util.JsonUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sergiescoruela.parties.Adapter.LocalAdapter;
import com.sergiescoruela.parties.MainActivity;
import com.sergiescoruela.parties.R;
import com.sergiescoruela.parties.pojo.Local;
import com.sergiescoruela.parties.pojo.Precio;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    //Contenedor
    //Fila ->Elemento

    private int elementoFila = R.layout.elemento_local;

    //Conunto de datos
    private ArrayList<Local> listaLocal;

    //Adapter

    private LocalAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView recyclerView;
    private Map<String, Local> mapaLocal;

    //Obtener referencias de la base de datos solo la primera vez
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //Crear referencia en la que queremos escribir
    DatabaseReference myRef = database.getReference("local");




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        // Write a message to the database


        //myRef.setValue("Hola sergi");


        recyclerView = root.findViewById(R.id.listaInicio);
        listaLocal= new ArrayList<>();
        adapter = new LocalAdapter(getActivity(), elementoFila ,listaLocal);
        layoutManager = new GridLayoutManager(getActivity(),1);

       // listaLocal.add(new Local("Goku","Son Gokū es el protagonista del manga y anime Dragon Ball creado por Akira Toriyama.",R.drawable.download,"02:00","pop","18"));


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        // Read from the database



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                GenericTypeIndicator<Map<String, Local>> genericTypeIndicator =
                        new GenericTypeIndicator<Map<String, Local>>() {};
                mapaLocal = dataSnapshot.getValue(genericTypeIndicator);



                listaLocal.clear();
                if (mapaLocal != null) {
                    listaLocal.addAll(mapaLocal.values());
                   // indices.addAll(mapaLocal.keySet());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });





        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;





    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            listaLocal.clear();
            listaLocal.addAll(getArguments().<Local>getParcelableArrayList("local"));
            System.out.println("llega home");
            for (Local local : listaLocal) {
                System.out.println(local.getNombre() + "listaLocal");
            }
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listaLocal.clear();
                    listaLocal.addAll(getArguments().<Local>getParcelableArrayList("local"));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            adapter.notifyDataSetChanged();

        }


    }




}
