package fil.eservices.campusincident.presentation;

import android.content.Context;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

public class CircularProgressDrawableFactory {

    /**
     * You will have to call CircularProgressDrawable.start() afterwards
     */
    static CircularProgressDrawable create(Context context) {
        CircularProgressDrawable loader = new CircularProgressDrawable(context);
        loader.setStrokeWidth(5f);
        loader.setCenterRadius(30f);
        return loader;
    }

}
