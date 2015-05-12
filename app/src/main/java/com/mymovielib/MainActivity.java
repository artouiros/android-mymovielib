package com.mymovielib;




import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import info.awesomedevelopment.tvgrid.library.TVGridView;

public class MainActivity extends AppCompatActivity implements MovieInfoFragment.OnFragmentInteractionListener {
    public RecyclerView mRecyclerView;
    public TVGridView tvGridView;
    private MoviesAdapter mAdapter;
    private ListView mListView;
int  CURRENT_VIDEO_NUM;
    static JsonParser parser;
    private static final String TAG = MainActivity.class.getSimpleName();
    private TrackSelectionAdapter ts;
    private SlidingPaneLayout slidingPane;
    SQLiteHelper sq;
    private GridView mGridView;
    private GridViewAdapter mGridAdapter;
    public static Context ctx;
    private ExpandableListView expandableListView;
    List<Movies> listMovies;
    private List<String> parentHeaderInformation;
    ArrayList<GridItem> mGridData;
    List<Directors> listDirectors;
    Map<String, Integer> mapIndex;
    ListView directorstList;
    LinearLayout listViewView;
    ListView indexLayout;
    MovieInfoFragment f;
    Fragment contentFragment;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = getApplicationContext();
        listViewView = (LinearLayout)findViewById(R.id.listViewView);

/**
      //  mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
      //  mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        tvGridView = (TVGridView) findViewById(R.id.tv_grid_view);


       tvGridView.setLayoutManager(new GridLayoutManager(this, 6));


        mAdapter = new MoviesAdapter(this, tvGridView);
      //  mRecyclerView.setAdapter(mAdapter);
        tvGridView.setAdapter(mAdapter);
        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            movies.add(new Movie());
        }
        mAdapter.setMovieList(movies);
        tvGridView.scrollToPosition(0);
**/

        contentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_list);
    //    contentFragment.getView().setVisibility(View.INVISIBLE);

        getSupportFragmentManager().beginTransaction().hide(contentFragment).commit();

        parser = new JsonParser();
        sq = new SQLiteHelper(this);
        mGridData = new ArrayList<>();

        mGridView = (GridView) findViewById(R.id.gridView);
        slidingPane = (SlidingPaneLayout) findViewById(R.id.pane);

        mGridAdapter = new GridViewAdapter(this, R.layout.row_movie, mGridData);
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           //     Toast.makeText(MainActivity.this, "Clicked on: "+position, Toast.LENGTH_SHORT).show();
           //     Intent myIntent = new Intent(MainActivity.this, MovieInfoActivity.class);
               // myIntent.putExtra("key", value); //Optional parameters
            //    startActivity(myIntent);


                Bundle bundl = new Bundle();
                bundl.putString("mName", listMovies.get(position).get_name());
                bundl.putString("mDesc", listMovies.get(position).get_description());
                bundl.putString("mPoster", listMovies.get(position).get_poster());
                bundl.putString("mDirector", listMovies.get(position).get_director());
                bundl.putString("mDirectorImg", listMovies.get(position).get_director_img());
                bundl.putString("mCast1", listMovies.get(position).get_cast_one());
                bundl.putString("mCast2", listMovies.get(position).get_cast_two());
                bundl.putString("mCast3", listMovies.get(position).get_cast_three());
                bundl.putString("mCast4", listMovies.get(position).get_cast_four());
                bundl.putString("mCastImg1", listMovies.get(position).get_cast_img_one());
                bundl.putString("mCastImg2", listMovies.get(position).get_cast_img_two());
                bundl.putString("mCastImg3", listMovies.get(position).get_cast_img_three());
                bundl.putString("mCastImg4", listMovies.get(position).get_cast_img_four());
                bundl.putString("mPath", listMovies.get(position).get_path());



                MovieInfoFragment mf = new MovieInfoFragment();
                mf.setArguments(bundl);

                getSupportFragmentManager().beginTransaction()
                        . replace(R.id.fragment_list, mf,"introduction")
                        .commit();

//contentFragment.getView().setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().show(contentFragment).commit();

            }
        });


        int dbCount = sq.getContactsCount();

        if(dbCount>0) {
            if(dbCount==getJsonFilesCount()){

                showLatestMovie();
                GrabDirectors();
            }else{
addJsonToDb();
            }


        }
      //  Set<String> s = new LinkedHashSet<>(sq.fetchMyRowid());
      //  ArrayList<String> al = new ArrayList<>(s);
      //  for(int i=0; i<al.size();i++){
      //      System.out.println(al.get(i));
      //  }







        parentHeaderInformation = new ArrayList<String>();
        parentHeaderInformation.add("Последние");
        parentHeaderInformation.add("Жанры");
        parentHeaderInformation.add("Страны");
        parentHeaderInformation.add("Режисёры");
        parentHeaderInformation.add("Годы");
        parentHeaderInformation.add("Сканировать");

        HashMap<String, List<String>> allChildItems = returnGroupedChildItems();
        expandableListView = (ExpandableListView)findViewById(R.id.expandableListView);
        ExpandableListViewAdapter expandableListViewAdapter = new ExpandableListViewAdapter(getApplicationContext(), parentHeaderInformation, allChildItems);
        expandableListView.setAdapter(expandableListViewAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {


                if(groupPosition==3){

                    slidingPane.closePane();
                   listViewView.setVisibility(View.VISIBLE);
                    directorstList.requestFocus();

                }
                if(groupPosition==0){
                    listViewView.setVisibility(View.INVISIBLE);
                    slidingPane.closePane();
                    mGridView.requestFocus();
                    showLatestMovie();

                }
                if(groupPosition==5){
                    listViewView.setVisibility(View.INVISIBLE);
                    slidingPane.closePane();
                    GrabJsonToDb();
                }
                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(
                    ExpandableListView parent, View v,
                    int groupPosition, int childPosition,
                    long id) {
                listViewView.setVisibility(View.INVISIBLE);
                mGridView.requestFocus();
                slidingPane.closePane();
                if(groupPosition==1){
                   final String genre =  parent.getExpandableListAdapter().getChild(groupPosition, childPosition).toString();
                    new Thread(new Runnable() {
                        public void run() {
                            char c[] = genre.toCharArray();
                            c[0] = Character.toLowerCase(c[0]);
                            String newgenre = new String(c);
                            listMovies.clear();
                            mGridData.clear();
                            listMovies = sq.getByGenre(newgenre);
                            for (int i = 0; i < listMovies.size(); i++) {
                                mGridData.add(new GridItem());
                            }

                            for(int i =0; i<listMovies.size();i++) {
                                mGridData.get(i).setTitle(listMovies.get(i).get_name());
                                mGridData.get(i).setImage(listMovies.get(i).get_poster());
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mGridAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }).start();

                }
                if(groupPosition==2){
                    final String country = parent.getExpandableListAdapter().getChild(groupPosition, childPosition).toString();
                    new Thread(new Runnable() {
                        public void run() {

                            listMovies.clear();
                            mGridData.clear();
                            listMovies = sq.getByCountry(country);
                            for (int i = 0; i < listMovies.size(); i++) {
                                mGridData.add(new GridItem());
                            }

                            for(int i =0; i<listMovies.size();i++) {
                                mGridData.get(i).setTitle(listMovies.get(i).get_name());
                                mGridData.get(i).setImage(listMovies.get(i).get_poster());
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mGridAdapter.notifyDataSetChanged();
                                }
                            });

                        }
                    }).start();

                }
                Toast.makeText(MainActivity.this, "Clicked on: Child"+childPosition+" Parent "+groupPosition, Toast.LENGTH_SHORT).show();

                return false;
            }
        });
        expandableListView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(slidingPane.isOpen() &&hasFocus==false){
                    slidingPane.closePane();
                }
            }
        });

       // List<File> files = getListFiles(new File("/storage/external_storage/sda1/olddata/КИНО"+"/json"));
       // Log.d("FILE", files.get(0).getName());





Thread th = new Thread(new Runnable() {
    @Override
    public void run() {
        ArrayList<String> fl = new ArrayList<>();
        try {

            File dir = new File("/storage/external_storage/sda1/olddata/КИНО");
            String[] extensions = new String[] { "json", "mpg", "mkv" };
            System.out.println("Getting all video files in " + dir.getCanonicalPath()
                    + " including those in subdirectories");
            List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
            System.out.println("Всего файлов "+files.size());
            int count = 1;
            for (File file : files) {
                //   System.out.println("file: " + file.getParent());
                String json = FileUtils.readFileToString(file);
                JsonObject mainObject = parser.parse(json).getAsJsonObject();
                String poster = mainObject.getAsJsonObject("movie").getAsJsonObject("poster").getAsJsonObject("big").get("url").getAsString();
                String name = mainObject.getAsJsonObject("movie").get("title_russian").getAsString();
                String year = mainObject.getAsJsonObject("movie").get("year").toString();
                String director = null;
                String directorimage = null;
                if(mainObject.getAsJsonObject("movie").getAsJsonObject("director").get("person").isJsonArray()){
                    directorimage = mainObject.getAsJsonObject("movie").getAsJsonObject("director").getAsJsonArray("person").get(0).getAsJsonObject().getAsJsonObject("photo").getAsJsonObject("big").get("url").getAsString();
                    director = mainObject.getAsJsonObject("movie").getAsJsonObject("director").getAsJsonArray("person").get(0).getAsJsonObject().get("name").getAsString();

                }else{
                    directorimage = mainObject.getAsJsonObject("movie").getAsJsonObject("director").getAsJsonObject("person").getAsJsonObject("photo").getAsJsonObject("big").get("url").getAsString();
                    director = mainObject.getAsJsonObject("movie").getAsJsonObject("director").getAsJsonObject("person").get("name").getAsString();
                }
                ArrayList<String> cast = new ArrayList<>();
                ArrayList<String> castimage = new ArrayList<>();
                for(int i =0; i<mainObject.getAsJsonObject("movie").getAsJsonObject("cast").getAsJsonArray("person").size(); i++){
                    cast.add(mainObject.getAsJsonObject("movie").getAsJsonObject("cast").getAsJsonArray("person").get(i).getAsJsonObject().get("name").getAsString());
                    castimage.add(mainObject.getAsJsonObject("movie").getAsJsonObject("cast").getAsJsonArray("person").get(i).getAsJsonObject().getAsJsonObject("photo").getAsJsonObject("big").get("url").getAsString());
                }
                String description =  mainObject.getAsJsonObject("movie").get("description").getAsString();
                ArrayList<String> genre = new ArrayList<>();
                if(mainObject.getAsJsonObject("movie").getAsJsonObject("genre").get("name").isJsonArray()){
                    for(int i=0; i<mainObject.getAsJsonObject("movie").getAsJsonObject("genre").getAsJsonArray("name").size();i++) {
                        genre.add(mainObject.getAsJsonObject("movie").getAsJsonObject("genre").getAsJsonArray("name").get(i).getAsString());
                    }

                }else{
                    genre.add(mainObject.getAsJsonObject("movie").getAsJsonObject("genre").get("name").getAsString());
                }
                String genresstring = TextUtils.join(", ", genre);
                String path = file.getAbsolutePath();
                String country = null;
                if(mainObject.getAsJsonObject("movie").getAsJsonObject("country").get("name").isJsonArray()){
                   country = mainObject.getAsJsonObject("movie").getAsJsonObject("country").getAsJsonArray("name").get(0).getAsString();
                }else{
                    country = mainObject.getAsJsonObject("movie").getAsJsonObject("country").get("name").getAsString();
                   }
                sq.addContact(new Movies(name, year, poster, director, directorimage, cast.get(0),  cast.get(1),  cast.get(2),  cast.get(3), castimage.get(0), castimage.get(1), castimage.get(2), castimage.get(3), description,genresstring, path, country));
                count=count+1;
                System.out.println("Фильм"+count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
});
     //   th.start();

/***
        SQLiteHelper sq = new SQLiteHelper(this);
        sq.getWritableDatabase();
        sq.close();
        for(int i =0; i<100;i++) {
            sq.addContact(new Movies("Томагавк"+i, "2015"+i, "fd", "fd", "fd"+i, "fd", "fd", "fd", "fd", "fd", "fd", "fhfghr2454d", "ghgfhgf"+i));
        }
***/
/**END**/
    }

    public int getJsonFilesCount(){
        File dir = new File("/storage/external_storage/sda1/olddata/КИНО");
        String[] extensions = new String[] { "json" };

        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);

        return files.size();
    }
public void GrabDirectors(){
    listDirectors = sq.getDirectors();
    ArrayList<String> dl = new ArrayList<>();
    for(int i =0; i<listDirectors.size();i++){
        String [] arr = listDirectors.get(i).getName().split(" ", 2);
        dl.add(arr[1]+", "+arr[0]);
    }
    directorstList = (ListView) findViewById(R.id.list_fruits);
    directorstList.setItemsCanFocus(true);
    directorstList.setFocusable(true);
    directorstList.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    Collections.sort(dl);
    directorstList.setAdapter(new ArrayAdapter<String>(this,
            R.layout.listviewitem, dl));

    directorstList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String data=(String)parent.getItemAtPosition(position);
            listViewView.setVisibility(View.INVISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    listMovies.clear();
                    mGridData.clear();


                    String[] d = data.split("\\s*,\\s*");
                    // System.out.println(d[0]+" "+d[1]);
                    listMovies = sq.getByDirector(d[1]+" "+d[0]);
                    for (int i = 0; i < listMovies.size(); i++) {
                        mGridData.add(new GridItem());
                    }

                    for(int i =0; i<listMovies.size();i++) {
                        mGridData.get(i).setTitle(listMovies.get(i).get_name());
                        mGridData.get(i).setImage(listMovies.get(i).get_poster());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mGridAdapter.notifyDataSetChanged();
                            mGridView.requestFocus();

                        }
                    });
                }
            }).start();


        }
    });


    getIndexList(dl);

    displayIndex();
}
    public void showLatestMovie(){

                if(mGridData.size()>0&&listMovies.size()>0){
                    listMovies.clear();
                    mGridData.clear();
                }
                listMovies = sq.getLatestMovies();
                for (int i = 0; i < listMovies.size(); i++) {
                    mGridData.add(new GridItem());
                    mGridData.get(i).setTitle(listMovies.get(i).get_name());
                    mGridData.get(i).setImage(listMovies.get(i).get_poster());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mGridAdapter.notifyDataSetChanged();
                        mGridView.requestFocus();
                    }
                });



    }

public void GrabJsonToDb(){

    Thread th = new Thread(new Runnable() {
        @Override
        public void run() {
            ArrayList<String> fl = new ArrayList<>();
            try {

                File dir = new File("/storage/external_storage/sda1/olddata/КИНО");
                String[] extensions = new String[] { "json" };
                System.out.println("Getting all video files in " + dir.getCanonicalPath()
                        + " including those in subdirectories");
                List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
                System.out.println("Всего файлов "+files.size());
                int count = 1;
                for (File file : files) {
                    //   System.out.println("file: " + file.getParent());
                    String json = FileUtils.readFileToString(file);
                    JsonObject mainObject = parser.parse(json).getAsJsonObject();
                    String poster = mainObject.getAsJsonObject("movie").getAsJsonObject("poster").getAsJsonObject("big").get("url").getAsString();
                    String name = mainObject.getAsJsonObject("movie").get("title_russian").getAsString();
                    String year = mainObject.getAsJsonObject("movie").get("year").toString();
                    String director = null;
                    String directorimage = null;
                    if(mainObject.getAsJsonObject("movie").getAsJsonObject("director").get("person").isJsonArray()){
                        directorimage = mainObject.getAsJsonObject("movie").getAsJsonObject("director").getAsJsonArray("person").get(0).getAsJsonObject().getAsJsonObject("photo").getAsJsonObject("big").get("url").getAsString();
                        director = mainObject.getAsJsonObject("movie").getAsJsonObject("director").getAsJsonArray("person").get(0).getAsJsonObject().get("name").getAsString();

                    }else{
                        directorimage = mainObject.getAsJsonObject("movie").getAsJsonObject("director").getAsJsonObject("person").getAsJsonObject("photo").getAsJsonObject("big").get("url").getAsString();
                        director = mainObject.getAsJsonObject("movie").getAsJsonObject("director").getAsJsonObject("person").get("name").getAsString();
                    }
                    ArrayList<String> cast = new ArrayList<>();
                    ArrayList<String> castimage = new ArrayList<>();
                    for(int i =0; i<mainObject.getAsJsonObject("movie").getAsJsonObject("cast").getAsJsonArray("person").size(); i++){
                        cast.add(mainObject.getAsJsonObject("movie").getAsJsonObject("cast").getAsJsonArray("person").get(i).getAsJsonObject().get("name").getAsString());
                        castimage.add(mainObject.getAsJsonObject("movie").getAsJsonObject("cast").getAsJsonArray("person").get(i).getAsJsonObject().getAsJsonObject("photo").getAsJsonObject("big").get("url").getAsString());
                    }
                    String description =  mainObject.getAsJsonObject("movie").get("description").getAsString();
                    ArrayList<String> genre = new ArrayList<>();
                    if(mainObject.getAsJsonObject("movie").getAsJsonObject("genre").get("name").isJsonArray()){
                        for(int i=0; i<mainObject.getAsJsonObject("movie").getAsJsonObject("genre").getAsJsonArray("name").size();i++) {
                            genre.add(mainObject.getAsJsonObject("movie").getAsJsonObject("genre").getAsJsonArray("name").get(i).getAsString());
                        }

                    }else{
                        genre.add(mainObject.getAsJsonObject("movie").getAsJsonObject("genre").get("name").getAsString());
                    }
                    String genresstring = TextUtils.join(", ", genre);
                    String path = file.getAbsolutePath();
                    String country = null;
                    if(mainObject.getAsJsonObject("movie").getAsJsonObject("country").get("name").isJsonArray()){
                        country = mainObject.getAsJsonObject("movie").getAsJsonObject("country").getAsJsonArray("name").get(0).getAsString();
                    }else{
                        country = mainObject.getAsJsonObject("movie").getAsJsonObject("country").get("name").getAsString();
                    }
                    sq.addContact(new Movies(name, year, poster, director, directorimage, cast.get(0),  cast.get(1),  cast.get(2),  cast.get(3), castimage.get(0), castimage.get(1), castimage.get(2), castimage.get(3), description,genresstring, path, country));
                    count=count+1;
                    System.out.println("Фильм"+count);
                }
                showLatestMovie();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    });
       th.start();
}

    public void addJsonToDb(){

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> fl = new ArrayList<>();
                try {

                    File dir = new File("/storage/external_storage/sda1/olddata/КИНО");
                    String[] extensions = new String[] { "json" };
                    System.out.println("Getting all video files in " + dir.getCanonicalPath()
                            + " including those in subdirectories");
                    List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
                    System.out.println("Всего файлов "+files.size());
                    int count = 1;
                    ArrayList<String> pathes = sq.getAllPath();
                    for (File file : files) {
                        if(!pathes.contains(file.getAbsolutePath())){
                        //   System.out.println("file: " + file.getParent());
                        String json = FileUtils.readFileToString(file);
                        JsonObject mainObject = parser.parse(json).getAsJsonObject();
                        String poster = mainObject.getAsJsonObject("movie").getAsJsonObject("poster").getAsJsonObject("big").get("url").getAsString();
                        String name = mainObject.getAsJsonObject("movie").get("title_russian").getAsString();
                        String year = mainObject.getAsJsonObject("movie").get("year").toString();
                        String director = null;
                        String directorimage = null;
                        if (mainObject.getAsJsonObject("movie").getAsJsonObject("director").get("person").isJsonArray()) {
                            directorimage = mainObject.getAsJsonObject("movie").getAsJsonObject("director").getAsJsonArray("person").get(0).getAsJsonObject().getAsJsonObject("photo").getAsJsonObject("big").get("url").getAsString();
                            director = mainObject.getAsJsonObject("movie").getAsJsonObject("director").getAsJsonArray("person").get(0).getAsJsonObject().get("name").getAsString();

                        } else {
                            directorimage = mainObject.getAsJsonObject("movie").getAsJsonObject("director").getAsJsonObject("person").getAsJsonObject("photo").getAsJsonObject("big").get("url").getAsString();
                            director = mainObject.getAsJsonObject("movie").getAsJsonObject("director").getAsJsonObject("person").get("name").getAsString();
                        }
                        ArrayList<String> cast = new ArrayList<>();
                        ArrayList<String> castimage = new ArrayList<>();
                        for (int i = 0; i < mainObject.getAsJsonObject("movie").getAsJsonObject("cast").getAsJsonArray("person").size(); i++) {
                            cast.add(mainObject.getAsJsonObject("movie").getAsJsonObject("cast").getAsJsonArray("person").get(i).getAsJsonObject().get("name").getAsString());
                            castimage.add(mainObject.getAsJsonObject("movie").getAsJsonObject("cast").getAsJsonArray("person").get(i).getAsJsonObject().getAsJsonObject("photo").getAsJsonObject("big").get("url").getAsString());
                        }
                        String description = mainObject.getAsJsonObject("movie").get("description").getAsString();
                        ArrayList<String> genre = new ArrayList<>();
                        if (mainObject.getAsJsonObject("movie").getAsJsonObject("genre").get("name").isJsonArray()) {
                            for (int i = 0; i < mainObject.getAsJsonObject("movie").getAsJsonObject("genre").getAsJsonArray("name").size(); i++) {
                                genre.add(mainObject.getAsJsonObject("movie").getAsJsonObject("genre").getAsJsonArray("name").get(i).getAsString());
                            }

                        } else {
                            genre.add(mainObject.getAsJsonObject("movie").getAsJsonObject("genre").get("name").getAsString());
                        }
                        String genresstring = TextUtils.join(", ", genre);
                        String path = file.getAbsolutePath();
                        String country = null;
                        if (mainObject.getAsJsonObject("movie").getAsJsonObject("country").get("name").isJsonArray()) {
                            country = mainObject.getAsJsonObject("movie").getAsJsonObject("country").getAsJsonArray("name").get(0).getAsString();
                        } else {
                            country = mainObject.getAsJsonObject("movie").getAsJsonObject("country").get("name").getAsString();
                        }
                        sq.addContact(new Movies(name, year, poster, director, directorimage, cast.get(0), cast.get(1), cast.get(2), cast.get(3), castimage.get(0), castimage.get(1), castimage.get(2), castimage.get(3), description, genresstring, path, country));
                        count = count + 1;
                        System.out.println("Фильм" + count);
                    }
                    }
                    showLatestMovie();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        th.start();
    }



    private void getIndexList(ArrayList<String> fruits) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < fruits.size(); i++) {
            String fruit = fruits.get(i);
           // String lastWord = fruit.substring(fruit.lastIndexOf(" ")+1);
            String index = fruit.substring(0, 1);

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    private void displayIndex() {
        indexLayout = (ListView) findViewById(R.id.side_index);

        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());

        indexLayout.setAdapter(new ArrayAdapter<String>(this,
                R.layout.side_index_item, indexList));
        indexLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView selectedIndex = (TextView) view;
                System.out.println(selectedIndex.getText());
                directorstList.requestFocus();
                directorstList.setSelection(mapIndex.get(selectedIndex.getText()));

                directorstList.smoothScrollToPosition(mapIndex.get(selectedIndex.getText()));


            }
        });
    }


    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".json")){
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }
    private HashMap<String, List<String>> returnGroupedChildItems(){
        HashMap<String, List<String>> childContent = new HashMap<String, List<String>>();
        List<String> cars = new ArrayList<String>();
        cars.add("Фантастика");
        cars.add("История");
        cars.add("Драма");
        cars.add("Криминал");
        cars.add("Вестерн");
        cars.add("Комедия");
        cars.add("Приключения");
        cars.add("Военный");
        cars.add("Биография");
        cars.add("Детектив");
        cars.add("Мелодрамма");
        cars.add("Триллер");
        List<String> houses = new ArrayList<String>();
        houses.add("Зарубежные");
        houses.add("СССР");
        houses.add("Россия");

        childContent.put(parentHeaderInformation.get(0), new ArrayList<String>());
        childContent.put(parentHeaderInformation.get(1), cars);
        childContent.put(parentHeaderInformation.get(2), houses);
        childContent.put(parentHeaderInformation.get(3), new ArrayList<String>());
        childContent.put(parentHeaderInformation.get(4), new ArrayList<String>());
        childContent.put(parentHeaderInformation.get(5), new ArrayList<String>());
        return childContent;

    }
    @Override
    public void onBackPressed() {
       if(contentFragment.isVisible()){
           getSupportFragmentManager().beginTransaction().hide(contentFragment).commit();
           mGridView.requestFocus();
         //  contentFragment.getView().setVisibility(View.INVISIBLE);
       }else{
           if (doubleBackToExitPressedOnce) {
               super.onBackPressed();
               return;
           }
           this.doubleBackToExitPressedOnce = true;
           Toast.makeText(this, "Нажмите кнопку назад еще раз, чтобы выйти", Toast.LENGTH_SHORT).show();

           new Handler().postDelayed(new Runnable() {

               @Override
               public void run() {
                   doubleBackToExitPressedOnce=false;
               }
           }, 2000);
       }
    }
    @Override
    public void onFragmentInteraction(Uri string) {
        // Do different stuff
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(slidingPane.isOpen()){
            slidingPane.closePane();
            mGridView.requestFocus();
        }else{
            slidingPane.openPane();
            expandableListView.requestFocus();
        }
        return false;
    }

}