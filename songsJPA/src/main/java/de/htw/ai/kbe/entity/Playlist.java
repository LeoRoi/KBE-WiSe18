package de.htw.ai.kbe.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity
@Table(name="playlists")
@XmlRootElement
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean open;

    @ManyToOne
    private User owner;

    @ManyToMany
    @JoinTable(name = "psmap", joinColumns = { @JoinColumn(name = "pid") }, inverseJoinColumns = {            @JoinColumn(name = "sid") })
    private List<Song> content;

    public Playlist(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Song> getContent() {
        return content;
    }

    public void setContent(List<Song> content) {
        this.content = content;
    }
}
