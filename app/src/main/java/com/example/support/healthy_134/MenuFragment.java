package com.example.support.healthy_134;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.support.healthy_134.post.PostFragment;
import com.example.support.healthy_134.sleep.SleepFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.support.healthy_134.weight.Weight;
import com.example.support.healthy_134.weight.WeightFragment;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment{

    ArrayList<String> menu = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //  map view with fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        menu.clear();
        menu.add("BMI");
        menu.add("Weight");
        menu.add("Sleep Time");
        menu.add("Post");
        menu.add("Sign out");

        ArrayAdapter<String> menuAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_list_item_1, menu);

        // get ListView from fragment_menu.xml
        ListView menuList = getView().findViewById(R.id.menu_list);
        menuList.setAdapter(menuAdapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MENU", "Click on menu = " + menu.get(position));

                if(position < 4){
                    Fragment fragment[] = {new BmiFragment(), new WeightFragment(), new SleepFragment(), new PostFragment()};
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, fragment[position])
                            .addToBackStack(null)
                            .commit();
                } else {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    getFragmentManager().popBackStack();
//                    getActivity().getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.main_view, new LoginFragment())
//                            .addToBackStack(null)
//                            .commit();
                }

            }
        });

        TextView eMail = getView().findViewById(R.id.menu_username);
        FirebaseAuth user = FirebaseAuth.getInstance();
        if(user.getCurrentUser() != null) eMail.setText(user.getCurrentUser().getEmail());
    }
}
