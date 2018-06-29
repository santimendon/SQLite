package com.android.smendon.sqlite.models;

/**
 * Created by Sanket Mendon on 27-06-2018.
 */
public class MovieRecord {
    private String title, language, genre, runtime, rating;

    public MovieRecord() {

    }

    public MovieRecord(String title, String genre, String language, String runtime, String rating) {
        this.title = title;
        this.genre = genre;
        this.language = language;
        this.runtime = runtime;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
