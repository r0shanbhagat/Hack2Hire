

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ///HeroesAdapter adapter;

    List<Hero> heroList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        recyclerView = findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

     /*   HeroesViewModel model = ViewModelProviders.of(this).get(HeroesViewModel.class);

        model.getHeroes().observe(this, new Observer<List<Hero>>() {
            @Override
            public void onChanged(@Nullable List<Hero> heroList) {
                adapter = new HeroesAdapter(MainActivity.this, heroList);
                recyclerView.setAdapter(adapter);
            }
        });*/
    }
}
