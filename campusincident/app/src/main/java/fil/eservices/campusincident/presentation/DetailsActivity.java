package fil.eservices.campusincident.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import fil.eservices.campusincident.R;
import fil.eservices.campusincident.data.model.Category;
import fil.eservices.campusincident.data.model.Incident;

public class DetailsActivity extends AppCompatActivity {

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Incident incident = (Incident) getIntent().getSerializableExtra("incident");

        setContentView(R.layout.activity_details);

        TextView title = findViewById(R.id.incident_name);
        title.setText(incident.getTitle());

        TextView date = findViewById(R.id.incident_date);
        date.setText(incident.getCreatedAt().toString());

        String description = incident.getDescription();
        if(description != null) {
            TextView descTV = findViewById(R.id.incident_description);
            descTV.setText(description);
        }

        StringBuilder categories = new StringBuilder(" ");
        for (Category cat: incident.getCategories()) {
            categories.append(cat.getName());
            categories.append(", ");
        }
        TextView catTV = findViewById(R.id.incident_category);
        catTV.setText(categories.toString());

        backButton();
    }

    /**
     * Finish activity when back button clicked
     */
    private void backButton(){
        backButton = findViewById(R.id.button_back_details_incident);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
