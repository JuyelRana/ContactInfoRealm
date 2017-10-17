package dreamycoding.com.contactinforealm.model;

import io.realm.RealmObject;

/**
 * Created by Juyel on 10/18/2017.
 */

public class ContactModel extends RealmObject{

    private String name,phone,age,email,image;

    public ContactModel() {
    }

    public ContactModel(String name, String phone, String age, String email, String image) {
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.email = email;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
