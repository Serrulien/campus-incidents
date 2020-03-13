package fil.eservices.campusincident.presentation;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import fil.eservices.campusincident.R;
import fil.eservices.campusincident.data.api.CategoryControllerApi;
import fil.eservices.campusincident.data.api.ImageControllerApi;
import fil.eservices.campusincident.data.api.ImageControllerApi2;
import fil.eservices.campusincident.data.api.IncidentControllerApi;
import fil.eservices.campusincident.data.api.LocationControllerApi;
import fil.eservices.campusincident.data.model.Category;
import fil.eservices.campusincident.data.model.ImageSaved;
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
    private Uri photoUri;
    private File photoFile;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PERMISSION_CODE = 1000;
    private int IMAGE_CAPTURE_CODE = 1001;
    private ImageButton backButton;
    private Button btnCreate;
    private Spinner spinnerCategory;
    private List<Category> listCategories;
    private boolean noCategory = true;
    private List<String> categorySelected = new ArrayList<>();
    AlertDialog.Builder builder;
    private ProgressBar loadingProgressBar;

    private boolean hasTakenImage = false;
    private boolean isImageUploadDone = false;
    private Long saveImageId;
    private EditText reportTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);
        spinnerCategory = findViewById(R.id.categories_spinner);
        reportTitle = findViewById(R.id.report_title);
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
        arrayList.add("aucune");
        for (Category cat: listCategories) {
            arrayList.add(cat.getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = arrayList.get(position);
                if(item.equals("aucune")) {
                    categorySelected = new ArrayList();
                } else {
                    categorySelected = new ArrayList();
                    categorySelected.add(arrayList.get(position));
                }
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
                if ("".contentEquals(reportTitle.getText())){
                    Toast.makeText(ReportActivity.this, "Le champ Titre est obligatoire", Toast.LENGTH_LONG).show();
                }else {
                    dialog.dismiss();
                    Log.e("PPP", "COUCOU");
                    Log.e("PPP", String.valueOf(hasTakenImage));
                    Log.e("PPP", String.valueOf(isImageUploadDone));
                    if (hasTakenImage && !isImageUploadDone) {
                        Toast.makeText(ReportActivity.this, "Veuillez attendre la fin du téléversement de l'image", Toast.LENGTH_LONG).show();
                        return;
                    }
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
                    Intent myIntent = new Intent(getBaseContext(), MapActivity.class);
                    startActivity(myIntent);
                    finish();
                }
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }


    /**
     * To open camera component
     */
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            photoFile = null;
            try {
                photoFile = this.createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this,
                        "fil.eservices.campusincident.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, IMAGE_CAPTURE_CODE);
            }
        }
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
        Log.e("PPP", ""+requestCode+" "+resultCode);
        if (resultCode == RESULT_OK && requestCode == IMAGE_CAPTURE_CODE) {
            imageView.setImageURI(photoUri);
            hasTakenImage = true;
            uploadImage(photoFile);
        }
    }

    private void uploadImage(File image) {
        new ImageControllerApi2().handleFileUploadUsingPOST(image,
                (Response.Listener<ImageSaved>) response -> {
                    isImageUploadDone = true;
                    saveImageId = response.getId();
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        isImageUploadDone = false;
                        Log.e("PPP", error.toString());
                        Toast.makeText(ReportActivity.this, "Erreur pendant le téléversement de l'image", Toast.LENGTH_SHORT).show();
                    }
                });
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
        incidentDto.setImageId(saveImageId);
        incidentDto.setCategories(categorySelected);
        Log.e("PPP", incidentDto.toString());
    }

    private void post() {
        Log.e("PPP", "POST");
        preparePost();
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
