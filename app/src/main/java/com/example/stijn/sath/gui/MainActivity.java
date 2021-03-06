package com.example.stijn.sath.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.stijn.sath.R;
import com.example.stijn.sath.dataAccess.CinemaFilmAPI;
import com.example.stijn.sath.domain.Film;
import com.example.stijn.sath.domain.Hall;
import com.example.stijn.sath.gui.adapters.FilmAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, CinemaFilmAPI.OnFilmAvailable, AdapterView.OnItemSelectedListener{

    public final static String IMAGEURL = "filmposter";
    public final static String FILMNAME = "filmname";
    public final static String FILMDESCRIPTION = "filmdescription";
    public final static String FILMID = "filmId";

    private ArrayList<Film> films = new ArrayList<>();
    private final static String TAG = MainActivity.class.getSimpleName();
    private FilmAdapter adapter;
    private HashMap genreMap = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadAllGenres();

        //find spinner
        Spinner spinner = findViewById(R.id.spnrGenre);
        ArrayAdapter<CharSequence> spnrAdapter = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_dropdown_item);
        spnrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spnrAdapter);
        spinner.setOnItemSelectedListener(this);

        //find Gridview and add adapter
        GridView gridView = findViewById(R.id.grdFilms);
        adapter = new FilmAdapter(this, getLayoutInflater(), films);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();

    }

    //listener for gridview
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Film film = films.get(position);
        Intent i = new Intent(getApplicationContext(), FilmDetailActivity.class);
        i.putExtra(IMAGEURL, film.getImageURL());
        i.putExtra(FILMNAME, film.getName());
        i.putExtra(FILMDESCRIPTION, film.getDesription());
        i.putExtra(FILMID, film.getId());

        startActivity(i);
    }

    @Override
    public void onFilmAvailable(Film film) {
        Log.i(TAG, "onFilmAvailable():" + film.toString());
        films.add(film);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(TAG, "onItemSelected(): selected item: " + i);
        films.clear();
        String[] stringArray = getApplicationContext().getResources().getStringArray(R.array.genres);
        String selectedGenre = stringArray[i];
        int genreId = (Integer) genreMap.get(selectedGenre);
        String url = "https://api.themoviedb.org/3/genre/" + genreId + "/movies?api_key=6edfc04651891fd32f7c7b7a98565c08&language=en-US&include_adult=false&sort_by=created_at.asc";
        fetchFilms(url);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void fetchFilms(String url){
        Log.i(TAG, "fetchFilms():");
        CinemaFilmAPI getFilm = new CinemaFilmAPI(this);
        getFilm.execute(url);
    }

    private void LoadAllGenres() {
        Log.i(TAG, "LoadAllGenres():");
        String[] stringArray = getApplicationContext().getResources().getStringArray(R.array.genres);
        for(String s : stringArray){
            switch (s){
                case "Action":
                    genreMap.put(s, 28);
                    break;
                case "Thriller":
                    genreMap.put(s, 53);
                    break;
                case "Romance":
                    genreMap.put(s, 10402);
                    break;
                case "Horror":
                    genreMap.put(s, 27);
                    break;
                case "Fantasy":
                    genreMap.put(s, 14);
                    break;
                case "Science Fiction":
                    genreMap.put(s, 878);
                    break;
            }
        }
    }

    //adds custom menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.available_activities, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent i;
        switch(id){
            case R.id.mnuMyTickets:
                i = new Intent(getApplicationContext(), ETicketActivity.class);
                startActivity(i);
                break;
            case R.id.mnuFilms:
                break;
            case R.id.mnuContact:
                i = new Intent(getApplicationContext(), CinemaAboutActivity.class);
                startActivity(i);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
