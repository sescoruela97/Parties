package com.sergiescoruela.parties.ui.mostrarPrecio;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.sergiescoruela.parties.R;
import com.sergiescoruela.parties.pojo.PaypalConfig;
import com.sergiescoruela.parties.pojo.Precio;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class MostrarPreioFragment extends Fragment {

    private MostrarPreioViewModel mViewModel;
    private Precio precio1;
    private TextView txtNombre, txtDescripcion,txtPrecio;
    private Button btnComprar;
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;



    public static MostrarPreioFragment newInstance() {
        return new MostrarPreioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel =
                ViewModelProviders.of(this).get(MostrarPreioViewModel.class);
        View root = inflater.inflate(R.layout.mostrar_preio_fragment, container, false);



        Intent intent= new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        getActivity().startService(intent);



        txtNombre=root.findViewById(R.id.lblNombrePrecioMostrar);
        txtPrecio=root.findViewById(R.id.lblPrecioMostrar);
        txtDescripcion=root.findViewById(R.id.lblDescripcionPrecioMostrar);
        btnComprar=root.findViewById(R.id.btnComprarMostrar);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

         precio1 = new Precio();
        //lblNombreMostrar = view.findViewById(R.id.lblnombreMostrarLocal);

        if (getArguments() != null) {
            precio1 = (Precio) getArguments().getParcelable("precioUnico");
            System.out.println("llega mostrar precio");


            System.out.println(precio1.getTipoEntrada());
            txtNombre.setText(precio1.getTipoEntrada());
            txtPrecio.setText(precio1.getPrecio());
            txtDescripcion.setText(precio1.getDescripcionEntrada());

        }

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                try {
                    payPalPay();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MostrarPreioViewModel.class);
        // TODO: Use the ViewModel
    }

    private int PAYPAL_REQUEST_CODE =1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PaypalConfig.PAYPAL_CLIENT_ID);

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean payPalPay() throws ParseException {

        Float precio =  Float.parseFloat(precio1.getPrecio().toString());

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(precio),
                "EUR", "PARTIES", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
        return true;
    }

    public void onDestroy() {

        //stopService(new Intent(getActivity(),PayPalService.class));
        super.onDestroy();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode==PAYPAL_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {

                System.out.println("se a realizado el pago");
                Toast.makeText(getContext(), "Se ha comprado correctamnete", Toast.LENGTH_SHORT).show();


                String enviarcorreo = currentUser.getEmail().toString();
                String enviarasunto = "Confirmacion de la compra de su entrada";
                String enviarmensaje = "¡Hola " + currentUser.getDisplayName() + "! Te confirmamos la compra de tu entrada a trabes de nuestra aplicacion \n" +
                        "\n" +
                        "¡Gracias por confiar en nosotros y que disfrute de su velada!";

                // Defino mi Intent y hago uso del objeto ACTION_SEND
               /* Intent intent = new Intent(Intent.ACTION_SEND);

                // Defino los Strings Email, Asunto y Mensaje con la función putExtra
                intent.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{enviarcorreo});
                intent.putExtra(Intent.EXTRA_SUBJECT, enviarasunto);
                intent.putExtra(Intent.EXTRA_TEXT, enviarmensaje);

                // Establezco el tipo de Intent
                intent.setType("message/rfc822");

                // Lanzo el selector de cliente de Correo
              //  startActivity(Intent.createChooser(intent, "Elije un cliente de Correo:"));*/




            } else {
                System.out.println("no a realizado el pago 1");

            }
        }

    }
}
