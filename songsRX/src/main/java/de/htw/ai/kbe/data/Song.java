package de.htw.ai.kbe.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "song")
public class Song {
    private int id;
    private String title;
    private String artist;
    private String album;
    private int released;

    public Song(){}

    public Song(int id, String title, String artist, String album, int released) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.released = released;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public int getReleased() {
        return released;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setReleased(int released) {
        this.released = released;
    }

    @Override
    public String toString() {
        return "Song [id=" + id + ", title=" + title + ", artist=" + artist + ", album=" + album + ", released="
                + released + "]";
    }
}