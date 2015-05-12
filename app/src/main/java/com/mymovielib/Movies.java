package com.mymovielib;

/**
 * Created by arthur on 20.12.2015.
 */
public class Movies {
    //private variables
    int _id;
    String _name;
    String _year;
    String _poster;
    String _director;
    String _director_img;
    String _cast_one;
    String _cast_two;
    String _cast_three;
    String _cast_four;
    String _cast_img_one;
    String _cast_img_two;
    String _cast_img_three;
    String _cast_img_four;
    String _description;
    String _genre;
    String _path;

    public String get_country() {
        return _country;
    }

    public void set_country(String _country) {
        this._country = _country;
    }

    String _country;



    // Empty constructor
    public Movies(){

    }
    // constructor
    public Movies(int id, String name, String year, String poster, String director, String director_img, String cast_one, String cast_two, String cast_three, String cast_four, String cast_img_one, String cast_img_two, String cast_img_three, String cast_img_four, String description, String genre, String path, String country){
        this._id = id;
        this._name = name;
        this._year = year;
        this._poster = poster;
        this._director = director;
        this._director_img = director_img;
        this._cast_one = cast_one;
        this._cast_two = cast_two;
        this._cast_three = cast_three;
        this._cast_four = cast_four;
        this._cast_img_one = cast_img_one;
        this._cast_img_two = cast_img_two;
        this._cast_img_three = cast_img_three;
        this._cast_img_four = cast_img_four;
        this._description = description;
        this._genre = genre;
        this._path = path;
        this._country = country;
    }

    // constructor
    public Movies(String name, String year, String poster, String director, String director_img, String cast_one, String cast_two, String cast_three, String cast_four, String cast_img_one, String cast_img_two, String cast_img_three, String cast_img_four, String _description, String genre, String path, String country){
      this._name = name;
        this._year = year;
        this._poster = poster;
        this._director = director;
        this._director_img = director_img;
        this._cast_one = cast_one;
        this._cast_two = cast_two;
        this._cast_three = cast_three;
        this._cast_four = cast_four;
        this._cast_img_one = cast_img_one;
        this._cast_img_two = cast_img_two;
        this._cast_img_three = cast_img_three;
        this._cast_img_four = cast_img_four;
        this._description = _description;
        this._genre = genre;
        this._path = path;
        this._country = country;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_year() {
        return _year;
    }

    public void set_year(String _year) {
        this._year = _year;
    }

    public String get_poster() {
        return _poster;
    }

    public void set_poster(String _poster) {
        this._poster = _poster;
    }

    public String get_director() {
        return _director;
    }

    public void set_director(String _director) {
        this._director = _director;
    }

    public String get_director_img() {
        return _director_img;
    }

    public void set_director_img(String _director_img) {
        this._director_img = _director_img;
    }

    public String get_cast_one() {
        return _cast_one;
    }

    public void set_cast_one(String _cast_one) {
        this._cast_one = _cast_one;
    }

    public String get_cast_two() {
        return _cast_two;
    }

    public void set_cast_two(String _cast_two) {
        this._cast_two = _cast_two;
    }

    public String get_cast_three() {
        return _cast_three;
    }

    public void set_cast_three(String _cast_three) {
        this._cast_three = _cast_three;
    }

    public String get_cast_four() {
        return _cast_four;
    }

    public void set_cast_four(String _cast_four) {
        this._cast_four = _cast_four;
    }

    public String get_cast_img_one() {
        return _cast_img_one;
    }

    public void set_cast_img_one(String _cast_img_one) {
        this._cast_img_one = _cast_img_one;
    }

    public String get_cast_img_two() {
        return _cast_img_two;
    }

    public void set_cast_img_two(String _cast_img_two) {
        this._cast_img_two = _cast_img_two;
    }

    public String get_cast_img_three() {
        return _cast_img_three;
    }

    public void set_cast_img_three(String _cast_img_three) {
        this._cast_img_three = _cast_img_three;
    }

    public String get_cast_img_four() {
        return _cast_img_four;
    }

    public void set_cast_img_four(String _cast_img_four) {
        this._cast_img_four = _cast_img_four;
    }
    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }
    public String get_genre() {
        return _genre;
    }

    public void set_genre(String _genre) {
        this._genre = _genre;
    }

    public String get_path() {
        return _path;
    }

    public void set_path(String _path) {
        this._path = _path;
    }
}
