package com.sergiescoruela.parties.ui.configUsuario;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sergiescoruela.parties.MainActivity;
import com.sergiescoruela.parties.R;
import com.sergiescoruela.parties.pojo.Usuarios;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class ConfigUsuarioFragment extends Fragment {

    private static final long ONE_MEGABYTE = 1024*1024 ;
    private ConfigUsuarioViewModel mViewModel;
    private EditText txtnombre, txtContraseña,txtCorreo,txtFechaNacimiento;
    private ImageView imgUsuario;
    private Button btnConf , btnsubirImagen;

    private FirebaseAuth mAuth;
    private MainActivity mainActivity;
    private Map<String, Usuarios> mapaUsuario;
    private ArrayList<Usuarios> listaUsuarios;
    FirebaseUser currentUser;
    private StorageReference mStorageRef;
    DatabaseReference imgref;

    private static final int GALLERY_INTENT = 1;
    Bitmap bitmap = null;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();



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
        btnsubirImagen = root.findViewById(R.id.btnSubirImagen);


        //firebase

        //Obtener referencias de la base de datos solo la primera vez
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Crear referencia en la que queremos escribir
        DatabaseReference myRef = database.getReference("usuario");

        //TRAER DATOS DEL USUARIO

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //firebase imagen





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
                System.out.println("Conig2   "+ listaUsuarios.size());

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


        btnConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String remplazar= currentUser.getEmail();
                //remplazar.replaceAll("[-+.^:,]","");
                // remplazar.replace(".","@");
                String replazar1= remplazar.replace(".","@");
                System.out.println(replazar1);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("usuario/"+replazar1);
                DatabaseReference myRef2 = database.getReference("usuario/"+replazar1);



                Usuarios usuarios = new Usuarios(txtnombre.getText().toString(),txtCorreo.getText().toString(),txtContraseña.getText().toString()
                        ,txtFechaNacimiento.getText().toString());


                for (int i = 0; i <listaUsuarios.size(); i++) {
                    System.out.println("Config3");
                    if(currentUser.getEmail().equals(listaUsuarios.get(i).getCorreo())){

                        System.out.println(currentUser.getEmail() + "    config    "+listaUsuarios.get(i).getCorreo());

                        myRef2.removeValue();

                    }
                }

                myRef.setValue(usuarios);

                Toast.makeText(getContext(), "Se han canbiado los datos", Toast.LENGTH_SHORT).show();



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


       //Firebase subir imagen

        mStorageRef = FirebaseStorage.getInstance().getReference();

        btnsubirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // CropImage.startPickImageActivity(getActivity());

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
                Toast.makeText(getContext(), "Se ha subido la imagen", Toast.LENGTH_SHORT).show();


            }
        });





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

                imgUsuario.setImageBitmap(bitmapIMG);


            }
        });



        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY_INTENT &&  resultCode == RESULT_OK){




            Uri resultUri = data.getData();

           File url = new File(resultUri.getPath());



           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

           final byte [] bytes = byteArrayOutputStream.toByteArray();


            final StorageReference filePath = mStorageRef.child("fotos").child(txtCorreo.getText().toString());
            UploadTask uploadTask = filePath.putBytes(bytes);





            filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUrl = uri;

                            Glide.with(getActivity())
                                    .load(downloadUrl)
                                    .fitCenter()
                                    .centerCrop()
                                    .into(imgUsuario);
                        }
                    });

                    Toast.makeText(getContext(), "Se ha subido la imagen", Toast.LENGTH_SHORT).show();
                }
            });





        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ConfigUsuarioViewModel.class);
        // TODO: Use the ViewModel
    }

}
