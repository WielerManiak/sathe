package com.example.stijn.sath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.GridView;

import com.example.stijn.sath.domain.Cinema;
import com.example.stijn.sath.domain.Film;
import com.example.stijn.sath.domain.Hall;
import com.example.stijn.sath.domain.Seat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Film> films = new ArrayList<>();
    private final static String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cinema c = new Cinema("Sathé", "lovensdijkstraatjtfytcgc 112", "breda", "idk");
        Hall h = new Hall(1);
        Seat s1 = new Seat(1, h.getHallNumber());
        h.addSeat(s1);
        Seat s2 = new Seat(2, h.getHallNumber());
        h.addSeat(s2);
        Seat s3 = new Seat(3, h.getHallNumber());
        h.addSeat(s3);
        Seat s4 = new Seat(4, h.getHallNumber());
        h.addSeat(s4);
        c.addHall(h);


        Film f = new Film(1, "the hangover", "comedy", "3:00", "doet aan kim denken", 12, h, null, "https://ia.media-imdb.com/images/M/MV5BNDAxMTZmZGItZmM2NC00M2E1LWI1NmEtZjhhODM2MGU0ZmJlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX675_CR0,0,675,999_AL_.jpg");
        films.add(f);

        Log.i("fuck you", f.toString());

        GridView gridView = findViewById(R.id.grdFilms);
        FilmAdapter adapter = new FilmAdapter(this, getLayoutInflater(), films);
        gridView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }
}
