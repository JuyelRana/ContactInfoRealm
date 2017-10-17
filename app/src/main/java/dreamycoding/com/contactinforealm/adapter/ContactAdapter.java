package dreamycoding.com.contactinforealm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dreamycoding.com.contactinforealm.R;
import dreamycoding.com.contactinforealm.model.ContactModel;
import dreamycoding.com.contactinforealm.ui.MyViewHolder;
import dreamycoding.com.contactinforealm.utility.RealmUtility;

/**
 * Created by Juyel on 10/18/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<MyViewHolder> {

    public Context context;
    public List<ContactModel> contactModelList;

    public ContactAdapter(Context context, List<ContactModel> contactModelList) {
        this.context = context;
        this.contactModelList = contactModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ContactModel model = contactModelList.get(position);

        holder.txtName.setText(model.getName());
        holder.txtEmail.setText(model.getEmail());
        holder.txtPhone.setText(model.getPhone());
        holder.txtAge.setText(model.getAge());
        holder.imgView.setImageBitmap(RealmUtility.stringToBitmap(model.getImage()));
    }

    @Override
    public int getItemCount() {
        return contactModelList.size();
    }
}
