package com.example.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

//import com.example.myapplication.DataBase.UserDataBase;
//import com.example.myapplication.Items.Restaurant;
//import com.example.myapplication.Items.User;

import java.util.ArrayList;
import java.util.Arrays;

public class ParametresFragment extends Fragment {
    private boolean aImporterContact = false;
    private static final int READ_CONTACT_PERMISSIONS_REQUEST = 1;
    private CheckBox vegetBox;
    private CheckBox sportifBox;
    private CheckBox equilibreBox;
    private CheckBox fastfoodBox;
    private CheckBox grasBox;
    //private User connectedUser;
    private Button saveButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


       // this.connectedUser = UserDataBase.getConnectedUser();


       // View view = inflater.inflate(R.layout.fragment_parametres, container, false);
        //final Button importerContact = view.findViewById(R.id.importerContact);
        /**importerContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!aImporterContact) {
                    getContactList();
                    aImporterContact = true;
                }
            }*
        });**/

       /** vegetBox = view.findViewById(R.id.vegetarienBox);
        sportifBox = view.findViewById(R.id.sportifBox);
        equilibreBox = view.findViewById(R.id.equilibreBox);
        fastfoodBox = view.findViewById(R.id.fastfoodBox);
        grasBox = view.findViewById(R.id.grasBox);

        saveButton = view.findViewById(R.id.sauvegarder);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveParam();
            }
        });**/

        initBox();

        //return view;
        return null;
    }

    private void getContactList() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadContact();
        }
        ContentResolver cr = getContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    /*while (*/pCur.moveToNext();//) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNo = phoneNo.replaceAll("\\s+","");
                        //On ajoute ici le contact dans la base de donnée
                       // UserDataBase.addUser(new User(name, "none", new ArrayList<Restaurant>(), new String[0], phoneNo));
                    //}
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }

    public void initBox() {
        //ArrayList<String> categ = new ArrayList<String>(Arrays.asList(connectedUser.getCategories()));
       /** this.vegetBox.setChecked(categ.contains("Végétarien"));
        this.sportifBox.setChecked(categ.contains("Sportif"));
        this.grasBox.setChecked(categ.contains("Gras"));
        this.equilibreBox.setChecked(categ.contains("Equilibré"));
        this.fastfoodBox.setChecked(categ.contains("Fast Food"));**/
    }

    public void getPermissionToReadContact() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_CONTACTS)) {
                //Toast.makeText(this, "Accordez la permission !", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    READ_CONTACT_PERMISSIONS_REQUEST);
        }

    }

    public void saveParam() {
        ArrayList<String> arrayList = new ArrayList<>();
        if (this.vegetBox.isChecked())arrayList.add("Végétarien");
        if (this.sportifBox.isChecked())arrayList.add("Sportif");
        if (this.grasBox.isChecked())arrayList.add("Gras");
        if (this.equilibreBox.isChecked())arrayList.add("Equilibré");
        if (this.fastfoodBox.isChecked())arrayList.add("Fast Food");


        String[] stringArray = arrayList.toArray(new String[0]);
       // this.connectedUser.setCategories(stringArray);
    }
}
