package com.sergiescoruela.parties.ui.filtros;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.sergiescoruela.parties.R;
import com.sergiescoruela.parties.pojo.Local;
import com.sergiescoruela.parties.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FiltroFragment extends Fragment {

    private FiltroViewModel filtroViewModel;
    private Spinner spiMusica, spiHoras, spiEdad;
    private Button btnFiltrar;
    private CheckBox ckbPub,ckbDisco;

    private Map<String, Local> mapaFiltro;
    private ArrayList<Local> listafiltros;
    private ArrayList<Local> listaEnviar;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        filtroViewModel =
                ViewModelProviders.of(this).get(FiltroViewModel.class);
        View root = inflater.inflate(R.layout.fragment_filtro, container, false);

        listafiltros= new ArrayList<>();
        listaEnviar= new ArrayList<>();



        // Write a message to the database
        //Obtener referencias de la base de datos solo la primera vez
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Crear referencia en la que queremos escribir
        DatabaseReference myRef = database.getReference("local");



        spiMusica =  root.findViewById(R.id.spiMusica);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterMusica = ArrayAdapter.createFromResource(getActivity(),
                R.array.tiposMusica, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterMusica.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spiMusica.setAdapter(adapterMusica);


        spiHoras =  root.findViewById(R.id.spiHora);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterHoras = ArrayAdapter.createFromResource(getActivity(),
                R.array.horasCierre, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterHoras.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spiHoras.setAdapter(adapterHoras);





        spiEdad =  root.findViewById(R.id.spiEdad);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterEdad = ArrayAdapter.createFromResource(getActivity(),
                R.array.Edad, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterEdad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spiEdad.setAdapter(adapterEdad);

        ckbPub= root.findViewById(R.id.ckBoxPub);
        ckbDisco= root.findViewById(R.id.ckBoxDisco);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                GenericTypeIndicator<Map<String, Local>> genericTypeIndicator =
                        new GenericTypeIndicator<Map<String, Local>>() {};



                listafiltros.clear();
                mapaFiltro = dataSnapshot.getValue(genericTypeIndicator);

                if (mapaFiltro != null) {

                    listafiltros.addAll(mapaFiltro.values());
                    // indices.addAll(mapaLocal.keySet());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        btnFiltrar = root.findViewById(R.id.btnFiltro);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for (Local local : listafiltros) {
                    System.out.println(local.getNombre());
                    System.out.println(local.getEdad());
                    System.out.println(local.getHora());
                    System.out.println(local.getMusica());

                    if(local.getEdad()!=null && local.getHora()!=null && local.getMusica()!=null){


                        System.out.println("Entra1");
                        if (ckbPub.isChecked()==true){
                            if (local.getEdad().equals(spiEdad.getSelectedItem())
                                    && local.getMusica().equals(spiMusica.getSelectedItem())
                                    && local.getHora().equals(spiHoras.getSelectedItem())
                                    && local.getDescripcion().equalsIgnoreCase("Pub")){
                                System.out.println("Entra2");


                                listaEnviar.add(local);
                                System.out.println(local.getNombre());

                            }

                        }else if(ckbDisco.isChecked()==true){
                            if (local.getEdad().equals(spiEdad.getSelectedItem())
                                    && local.getMusica().equals(spiMusica.getSelectedItem())
                                    && local.getHora().equals(spiHoras.getSelectedItem())
                                    && local.getDescripcion().equalsIgnoreCase("Discoteca")){
                                System.out.println("Entra2");


                                listaEnviar.add(local);
                                System.out.println(local.getNombre());

                            }
                        }else {
                            if (local.getEdad().equals(spiEdad.getSelectedItem())
                                    && local.getMusica().equals(spiMusica.getSelectedItem())
                                    && local.getHora().equals(spiHoras.getSelectedItem())
                            ) {
                                System.out.println("Entra2");


                                listaEnviar.add(local);
                                System.out.println(local.getNombre());
                            }
                        }

                    }else {

                        System.out.println("ESTE DATO ES NULLO");
                        System.out.println(local.getNombre());
                    }

                }

                for (Local local : listaEnviar) {
                    System.out.println(local.getNombre() + "listaEnviar");

                }




               /* Intent intent = new Intent(getActivity(), HomeFragment.class);
                intent.putParcelableArrayListExtra("local", listaEnviar);
                startActivity(intent);*/

                //Aqui llamas el fragment que necesitas
                //Creas el nuevo fragmento y una nueva transacción.
             /*  Fragment nuevoFragmento = new HomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_gallery, nuevoFragmento);
                transaction.addToBackStack(null);
                transaction.commit();*/


                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("local", listaEnviar);
                Navigation.findNavController(view).navigate(R.id.nav_home,bundle);

            }
        });



        return root;
    }
}
