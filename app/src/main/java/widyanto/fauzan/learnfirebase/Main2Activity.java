package widyanto.fauzan.learnfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Main2Activity extends AppCompatActivity {

    private EditText email,password;
    private Button daftar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPass);
        daftar = findViewById(R.id.daftar);

        auth = FirebaseAuth.getInstance();

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cekEmail = email.getText().toString();
                String cekPass = password.getText().toString();
                if (TextUtils.isEmpty(cekEmail)){
                    email.setError("Email tidak boleh kosong");
                }else if (TextUtils.isEmpty(cekPass)){
                    password.setError("Password tidak boleh kosong");
                }else{
                    auth.createUserWithEmailAndPassword(cekEmail,cekPass).addOnCompleteListener(Main2Activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = auth.getCurrentUser();
                                Intent i = new Intent(Main2Activity.this, Dashboard.class);
                                i.putExtra("email", user.getEmail());
                                i.putExtra("Uid", user.getUid());
                                startActivity(i);
                            }else {
                                Toast.makeText(Main2Activity.this, "Gagal Daftar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
