package widyanto.fauzan.learnfirebase;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button submit;
    private TextView daftar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.mainEmail);
        password = findViewById(R.id.mainPassword);
        submit = findViewById(R.id.submit);
        daftar = findViewById(R.id.daftar);
        auth = FirebaseAuth.getInstance();

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cekEmaile = email.getText().toString();
                String cekPassword = password.getText().toString();
                if (TextUtils.isEmpty(cekEmaile))
                {
                    email.setError("Email tidak boleh kosong");
                    email.requestFocus();
                }else if (TextUtils.isEmpty(cekPassword)){
                    password.setError("Password tidak boleh kosong");
                    password.requestFocus();
                }else{
                    auth.signInWithEmailAndPassword(cekEmaile,cekPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        FirebaseUser user = auth.getCurrentUser();

                                        FirebaseInstanceId
                                                .getInstance()
                                                .getInstanceId()
                                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<InstanceIdResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                if (task.isSuccessful()){
                                                    Log.d("TOKEN", task.getResult().getToken());
                                                }
                                            }
                                        });

                                        if (user != null){
                                            Intent i = new Intent(MainActivity.this, Dashboard.class);
                                            i.putExtra("email", user.getEmail());
                                            i.putExtra("Uid", user.getUid());
                                            startActivity(i);
                                        }else{
                                            Toast.makeText(MainActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();

                                        }
                                    }else{
                                        Toast.makeText(MainActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });
    }
}
