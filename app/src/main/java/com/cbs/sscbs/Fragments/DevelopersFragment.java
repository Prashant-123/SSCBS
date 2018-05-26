package com.cbs.sscbs.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbs.sscbs.NewsUpdates.NewsAdapter;
import com.cbs.sscbs.R;
import com.cbs.sscbs.TeachersTimetable.TeacherDataClass;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class DevelopersFragment extends Fragment {
    public DevelopersFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.fragment_developers, container, false);
        return myView;
    }
}
