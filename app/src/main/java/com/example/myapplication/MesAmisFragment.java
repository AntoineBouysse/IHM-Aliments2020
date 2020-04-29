package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;

public class MesAmisFragment extends Fragment {

    //La liste ou sont actuellement stocké les données, on n'y touche pas
    private ArrayList<String> listeAmis = new ArrayList<>();
    //La vu, on y touche pas
    private ListView listeAmisView;
    //Le lien entre la liste et la vue, c'est ici qu'on ajoute ou enlève des éléments
    private ArrayAdapter listeAmisAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        refreshContactList();
        return view;
    }

    public void refreshContactList(){

    }
}