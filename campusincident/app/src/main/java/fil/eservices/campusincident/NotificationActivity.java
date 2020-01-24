package fil.eservices.campusincident;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
        final RecyclerView rv = (RecyclerView) findViewById(R.id.notif_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecyclerNotifAdapter());

        Toolbar mToolbar = (Toolbar) findViewById(R.id.notif_toolbar);
        mToolbar.setTitle(getString(R.string.notifications));
        mToolbar.setNavigationIcon(R.drawable.back_arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
