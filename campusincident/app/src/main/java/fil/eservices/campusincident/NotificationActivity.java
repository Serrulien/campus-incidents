package fil.eservices.campusincident;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        setupRecyclerView();
    }

    private void setupRecyclerView(){
//        recyclerView = findViewById(R.id.recycler_view);
//        recyclerNotifAdapter = new RecyclerNotifAdapter(this.getBaseContext(),this);
//        recyclerView.setAdapter(recyclerNotifAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecyclerNotifAdapter());

    }
}
