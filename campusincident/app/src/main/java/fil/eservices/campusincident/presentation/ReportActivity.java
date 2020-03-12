package fil.eservices.campusincident.presentation;

import android.Manifest;
import android.Manifest.permission;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import fil.eservices.campusincident.R;
import fil.eservices.campusincident.data.api.CategoryControllerApi;
import fil.eservices.campusincident.data.api.IncidentControllerApi;
import fil.eservices.campusincident.data.api.LocationControllerApi;
import fil.eservices.campusincident.data.model.Category;
import fil.eservices.campusincident.data.model.Incident;
import fil.eservices.campusincident.data.model.IncidentDto;
import fil.eservices.campusincident.data.model.Location;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.parseColor;
import static android.view.View.GONE;


public class ReportActivity extends AppCompatActivity {

    private List<Category> categories;
    private IncidentDto incidentDto = new IncidentDto();

    private ImageView imageView;
    private Button mCaptureBtn;
    private Uri imageUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PERMISSION_CODE = 1000;
    private int IMAGE_CAPTURE_CODE = 1001;
    private ImageButton backButton;
    private Button btnCreate;
    private Spinner spinnerCategory;
    private List<Category> listCategories;
    private List<String> categorySelected = new ArrayList<>();
    AlertDialog.Builder builder;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);
        spinnerCategory = findViewById(R.id.categories_spinner);
        getCategories();
        takePhotoBtn();
        backButton();
        builder = new AlertDialog.Builder(this);
        setBtnCreate();
        loadingProgressBar = findViewById(R.id.progress_bar_new_incident);
    }

    /**
     * To get campus locations
     */
    private void getCategories() {
        new CategoryControllerApi().getAllCategoriesUsingGET(null,
                new Response.Listener<List<Category>>() {
                    @Override
                    public void onResponse(List<Category> response) {
                        listCategories = response;
                        setDefaultCategories();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API error", "API error", error.getCause());
                    }
                }
        );
    }

    /**
     * To set default locations in campus spinner
     */
    private void setDefaultCategories(){

        ArrayList<String> arrayList = new ArrayList<>();
        for (Category cat: listCategories) {
            arrayList.add(cat.getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelected.add(arrayList.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }

    /**
     * Finish activity when back button clicked
     */
    private void backButton(){
        backButton = findViewById(R.id.button_back_new_incident);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * To set on click on creation incident
     */
    private void setBtnCreate(){
        btnCreate = findViewById(R.id.btn_create_incident);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogBox();
            }
        });
    }

    /**
     * To create a dialog box
     */
    private void setDialogBox(){
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);


        // Add the buttons
        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                preparePost();
                post();
                new CountDownTimer(4000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        loadingProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        loadingProgressBar.setVisibility(GONE);
                    }
                }.start();
                Intent myIntent = new Intent(getBaseContext(),   MapActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        // setup to change color of the button
        dialog.show();

        Button bn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        bn.setTextColor(parseColor("#564d4d"));
        Button bp = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        bp.setTextColor(parseColor("#9C2976"));

    }

    /**
     * To set the take photo button
     */
    private void takePhotoBtn(){
        imageView = findViewById(R.id.shapeableImageView);
        mCaptureBtn = findViewById(R.id.btnCaptureImage);
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    if (checkSelfPermission(permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_CODE);
                    } else{
                        openCamera();
                    }
                }
            }
        });
    }

    /**
     * To open camera component
     */
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    /**
     * Permissions of camera component result
     * @param requestCode requestCode
     * @param permissions permissions
     * @param grantResults grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * To set image in image view after taking photo
     * @param requestCode requestCode
     * @param resultCode resultCode
     * @param data data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageView.setImageURI(imageUri);
        }
    }

    private void preparePost() {
        // choper les valeurs et les mettre dans incidentDto
        LatLng geoloc = getIntent().getParcelableExtra("geoloc");
        incidentDto.setLongitude(geoloc.getLongitude());
        incidentDto.setLatitude(geoloc.getLatitude());
        incidentDto.setTitle(((TextView) findViewById(R.id.report_title)).getText().toString());
        CharSequence desc = ((TextView) findViewById(R.id.report_description)).getText();
        if(desc == null) {
            incidentDto.setDescription("");
        } else {
            incidentDto.setDescription(desc.toString());
        }
        incidentDto.setAuthor("demo@me.com");
        incidentDto.setCreatedAt(new Date());
        incidentDto.setLocation(6l);
        incidentDto.setCategories(categorySelected);
    }

    private void post() {
        new IncidentControllerApi().newIncidentUsingPOST(incidentDto,
                new Response.Listener<Incident>() {
                    @Override
                    public void onResponse(Incident response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, "Erreur pendant l'envoi de l'incident", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
