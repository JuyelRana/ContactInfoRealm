package dreamycoding.com.contactinforealm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import dreamycoding.com.contactinforealm.R;
import dreamycoding.com.contactinforealm.adapter.ContactAdapter;
import dreamycoding.com.contactinforealm.model.ContactModel;
import dreamycoding.com.contactinforealm.realm.RealmHelper;
import dreamycoding.com.contactinforealm.utility.RealmUtility;
import io.realm.Realm;
import io.realm.RealmChangeListener;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;

    private static final int GALLERY_REQUEST = 1;
    private ImageView imgInput;

    private Realm realm;
    private List<ContactModel> contactModelList;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;

    private RealmChangeListener realmChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(floatingActionButtonClickListner);

        //Setup RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Realm Configuration
        realm = Realm.getDefaultInstance();
        final RealmHelper realmHelper = new RealmHelper(realm);

        //Retrive contact
        realmHelper.retriveContact();

        //setup adapter
        adapter = new ContactAdapter(this, realmHelper.retriveContactList());
        recyclerView.setAdapter(adapter);

        //Data change event and refresh
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                adapter = new ContactAdapter(MainActivity.this, realmHelper.retriveContactList());
                recyclerView.setAdapter(adapter);
            }
        };

        //Add realmChangeListener to realm
        realm.addChangeListener(realmChangeListener);
    }

    View.OnClickListener floatingActionButtonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            inputDialog();
        }
    };

    private void inputDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Add Contact");
        dialog.setContentView(R.layout.dialog);

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;

        final EditText etName = (EditText) dialog.findViewById(R.id.etName);
        final EditText etPhone = (EditText) dialog.findViewById(R.id.etPhone);
        final EditText etAge = (EditText) dialog.findViewById(R.id.etAge);
        final EditText etEmail = (EditText) dialog.findViewById(R.id.etEmail);
        imgInput = (ImageView) dialog.findViewById(R.id.inputImageView);

        Button btnSelectImage = (Button) dialog.findViewById(R.id.btnSelectImage);
        Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String age = etAge.getText().toString();
                String image = RealmUtility.bitmapToString(((BitmapDrawable) imgInput.getDrawable()).getBitmap());

                ContactModel model = new ContactModel(name, phone, age, email, image);

                if (name != null && name.length() >= 0) {
                    RealmHelper realmHelper = new RealmHelper(realm);
                    //Add contact to realm database
                    if (realmHelper.addContact(model)) {
                        Toast.makeText(MainActivity.this, "Contact successfully added!!", Toast.LENGTH_SHORT).show();
                        etName.setText("");
                        etEmail.setText("");
                        etPhone.setText("");
                        etAge.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid input!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Name can't be empty!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {

                Uri selectedImageURI = data.getData();

                Picasso.with(MainActivity.this).load(selectedImageURI).noPlaceholder().centerCrop().fit()
                        .into(imgInput);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Refresh Realm
        realm.removeChangeListener(realmChangeListener);
        realm.close();
    }
}
