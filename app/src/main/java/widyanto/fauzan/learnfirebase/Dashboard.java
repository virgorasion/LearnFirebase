package widyanto.fauzan.learnfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    private TextView email, uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        email = findViewById(R.id.email);
        uid = findViewById(R.id.Uid);

        Intent i = getIntent();
        String emailParse = i.getStringExtra("email");
        String uidParse = i.getStringExtra("Uid");
        email.setText(emailParse);
        uid.setText(uidParse);

    }
}
