package fil.eservices.campusincident.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;

import fil.eservices.campusincident.ImageApi;
import fil.eservices.campusincident.R;
import fil.eservices.campusincident.data.model.Category;
import fil.eservices.campusincident.data.model.Incident;

public class DetailsActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Button resolveButton;
    private TextView numberResolve;
    private String textViewValue;
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Incident incident = (Incident) getIntent().getSerializableExtra("incident");

        setContentView(R.layout.activity_details);

        ImageView illustration = findViewById(R.id.incident_detail_image);
        if (incident.getImageId() == null) {
            illustration.setVisibility(View.GONE);
        } else {
            Glide.with(this)
                    .load(ImageApi.getUrl(incident.getImageId()))
                    .fitCenter()
                    .placeholder(CircularProgressDrawableFactory.create(this))
                    .into(illustration);
        }


        numberResolve = findViewById(R.id.nombre_evaluation);

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

        setBtnCreate();
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

    /**
     * To set on click on wish resolve incident
     */
    private void setBtnCreate(){
        resolveButton = findViewById(R.id.btn_souhaiter_resolution);
        textViewValue = numberResolve.getText().toString();
        number = Integer.parseInt(textViewValue);
        resolveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number++;
                textViewValue = String.valueOf(number);
                numberResolve.setText(textViewValue);
                resolveButton.setEnabled(false);
            }
        });
    }
}
