package dreamycoding.com.contactinforealm.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dreamycoding.com.contactinforealm.R;

/**
 * Created by Juyel on 10/18/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView txtName, txtPhone, txtAge, txtEmail;
    public ImageView imgView;

    public MyViewHolder(View itemView) {
        super(itemView);

        txtName = (TextView) itemView.findViewById(R.id.txtName);
        txtPhone = (TextView) itemView.findViewById(R.id.txtPhone);
        txtAge = (TextView) itemView.findViewById(R.id.txtAge);
        txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
        imgView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
