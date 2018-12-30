package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.data.Song;

import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.NoSuchElementException;

public class SongsDaoEm implements ISongsHandler {
    //    @Inject
    private EntityManager em;

    @Inject
    public SongsDaoEm(EntityManager em) {
        this.em = em;
    }

    public Song getSong(int id) {
        Song song = em.find(Song.class, id);

        if (song == null)
            throw new NoSuchElementException("No song with id "+id);

        return song;
    }

    public Collection<Song> getAllSongs() {
        TypedQuery<Song> query = em.createQuery("SELECT s from Song s", Song.class);
        return query.getResultList();
    }

    public int addSong(Song newSong) {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(newSong);
            transaction.commit();
            return newSong.getId();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding new song: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        }
    }

    @Transactional
    public boolean updateSong(int id, Song newSong) {
        Boolean delOk = deleteSong(id);

        if(!delOk) {
            return false;
        } else {
            addSong(newSong);
            return true;
        }
    }

//    @Transactional
//    public boolean updateSong(int id, Song newSong) {
//        Song song = getSong(id);
//
//        if (song != null) {
//            em.merge(newSong)
////            getSong(id);
////            em.getTransaction().begin();
////            em.merge(newSong);
////            em.getTransaction().commit();
//            return true;
//        } else {
//            return false;
//        }
//    }

    public boolean deleteSong(int id) {
        Song song = null;
        Boolean status = false;

        try {
            song = em.find(Song.class, id);
            if (song != null) {
                System.out.println("Deleting: " + song);
                em.getTransaction().begin();
                em.remove(song);
                em.getTransaction().commit();
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error removing song: " + e.getMessage());
            em.getTransaction().rollback();
            throw new PersistenceException("Could not remove entity: " + e.toString());
        }
        return status;
    }
}
