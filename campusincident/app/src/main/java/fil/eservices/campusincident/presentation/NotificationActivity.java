package fil.eservices.campusincident.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fil.eservices.campusincident.R;

public class NotificationActivity extends AppCompatActivity {

    private ImageButton backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setupRecyclerView();
        backButton();
    }

    private void setupRecyclerView(){
        final RecyclerView rv = (RecyclerView) findViewById(R.id.notif_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecyclerNotifAdapter());

    }


    private void backButton(){
        backButton = findViewById(R.id.button_back_notifs_incident);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
