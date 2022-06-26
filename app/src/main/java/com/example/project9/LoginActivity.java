package com.example.project9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project9.Modelos.autor;
import  com.example.project9.Modelos.usuario;

import com.example.project9.configuracion.config;
import com.example.project9.retrofit.interfaceRetrofit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edTxtUsuario, edtxtPass;
    private FloatingActionButton fabLogin;
    private RelativeLayout backProgress;
    private TextView pgsTxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edTxtUsuario = findViewById(R.id.edTxtCUsuario);
        edtxtPass = findViewById(R.id.edTxtPass);
        fabLogin = findViewById(R.id.fabCLogin);
        pgsTxt = findViewById(R.id.txtVCProgress);
        backProgress = findViewById(R.id.lytProgress);

        fabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgsTxt.setText("Validando credenciales");
                backProgress.setVisibility(View.VISIBLE);
                validar( edTxtUsuario.getText().toString() , edtxtPass.getText().toString(),view);
            }
        });
    }
    private void validar(String username, String password, View v){
        //Variable para iniciar la petición Retrofit
        interfaceRetrofit peticion = config.getRetrofit().create(interfaceRetrofit.class);
        //Preparar la petición call (llamar)
        Call<List<autor>> call = peticion.validar(username, password);
        //Iniciar la petición con enqueue. el método incluye dos apartados para saber si la petición se llevó con éxito o fracaso
        //onResponse-onFailure
        call.enqueue(new Callback<List<autor>>() {
            @Override
            public void onResponse(Call<List<autor>> call, Response<List<autor>> response) {
                //En caso de éxito la variable users es la encargada de almacenar la respuesta del servidor.
                List <autor> users = response.body();
                //Si la respuesta es correcta se llama al navigation drawer.
                if(users.get(0) != null)
                {
                    backProgress.setVisibility(View.GONE);
                    Intent principal = new Intent(getApplicationContext(), MainActivity.class);
                    principal.putExtra("username", users.get(0).getUsername());
                    principal.putExtra("lastname", users.get(0).getLastname());
                    principal.putExtra("imagen", users.get(0).getImagen());
                    startActivity(principal);
                } else {
                    backProgress.setVisibility(View.GONE);
                    Snackbar msjPersonalizado = Snackbar.make(v, "Usuario o contraseña no válidos", Snackbar.LENGTH_SHORT);
                    msjPersonalizado.show();
                }
            }

            @Override
            public void onFailure(Call<List<autor>> call, Throwable t) {
                //en caso de fracaso
                backProgress.setVisibility(View.GONE);
                Snackbar msjPersonalizado = Snackbar.make(v, "Servidor inaccesible", Snackbar.LENGTH_SHORT);
                msjPersonalizado.show();
            }
        });

    }// fin de validar



}