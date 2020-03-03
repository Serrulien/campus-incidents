package fil.eservices.campusincident.data.entity;

import android.net.Uri;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class Incident {

    private String id;
    private String title;
    private String description;
    private String category;
    private String otherCategory;
    private Uri picture;
    private LatLng location;

    public Incident(String id, String title, String description, String category, String otherCategory, Uri picture, LatLng location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.otherCategory = otherCategory;
        this.picture = picture;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOtherCategory() {
        return otherCategory;
    }

    public void setOtherCategory(String otherCategory) {
        this.otherCategory = otherCategory;
    }

    public Uri getPicture() {
        return picture;
    }

    public void setPicture(Uri picture) {
        this.picture = picture;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

}
