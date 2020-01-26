package fil.eservices.campusincident;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerNotifAdapter extends RecyclerView.Adapter<RecyclerNotifAdapter.MyViewHolder> {


    private List<String> notifications = new ArrayList<>(Arrays.asList("Incident signalé","Incident en cours de validation","Signalement validé","Incident en cours de résolution"));

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notification_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String notification = notifications.get(position);
        holder.display(notification);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView notifTitle;

        public MyViewHolder(final View itemView) {
            super(itemView);

            notifTitle = ((TextView) itemView.findViewById(R.id.notif_title));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Item clicked");
                }
            });
        }

        public void display(String notif) {
            notifTitle.setText(notif);
        }
    }

}
