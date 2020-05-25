package sc.hotpot.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tx_hellow).setOnClickListener((v) -> {
            Intent it = new Intent(MainActivity.this, TestActivity.class);
            startActivity(it);
        });
    }
}
