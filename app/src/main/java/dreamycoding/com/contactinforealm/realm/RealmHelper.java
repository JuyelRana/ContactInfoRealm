package dreamycoding.com.contactinforealm.realm;

import java.util.ArrayList;
import java.util.List;

import dreamycoding.com.contactinforealm.model.ContactModel;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by Juyel on 10/18/2017.
 */

public class RealmHelper {
    private Realm realm;
    private RealmResults<ContactModel> contactModelRealmResults;
    private boolean isAdd = false;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    //Add contact
    public boolean addContact(final ContactModel model) {
        if (model == null) {
            isAdd = false;
        } else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (model.getName() != null) {
                        try {
                            ContactModel contactModel = realm.copyToRealm(model);
                            isAdd = true;
                        } catch (RealmException e) {
                            e.printStackTrace();
                            isAdd = false;
                        }
                    } else {
                        isAdd = false;
                    }
                }
            });
        }
        return isAdd;
    }

    //Retrive Realm Results
    public void retriveContact() {
        contactModelRealmResults = realm.where(ContactModel.class).findAll();
    }

    //return the contact list
    public List<ContactModel> retriveContactList() {
        List<ContactModel> contactModelList = new ArrayList<>();

        for (ContactModel cm : contactModelRealmResults) {
            contactModelList.add(cm);
        }

        return contactModelList;
    }
}
