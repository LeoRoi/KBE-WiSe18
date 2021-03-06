package de.htw.ai.kbe.handler;

import de.htw.ai.kbe.entity.Song;
import de.htw.ai.kbe.utils.PostgreCloser;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Collection;

/**
 * did not work
 */
@Singleton
public class SongsDaoEmf implements ISongsHandler {
    private EntityManagerFactory emf;

    @Inject
    public SongsDaoEmf(EntityManagerFactory emf) {
        this.emf = emf;
        PostgreCloser.addEntityManagerFactory(emf);
    }

    public Song getSong(int id) {
        EntityManager em = emf.createEntityManager();
        Song entity = null;

        try {
            entity = em.find(Song.class, id);
        } finally {
            em.close();
        }
        return entity;
    }

    public Collection<Song> getAllSongs() {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Song> query = em.createQuery("SELECT s from Song s", Song.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public int addSong(Song newSong) {
        EntityManager em = emf.createEntityManager();
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
        } finally {
            em.close();
        }
    }

    @Transactional
    public boolean updateSong(int id, Song newSong) {
        EntityManager em = emf.createEntityManager();
        Song song = getSong(id);

        if (song != null) {
            em.getTransaction().begin();
            Song dbSong = em.find(Song.class, id);

            dbSong.setReleased(newSong.getReleased());
            dbSong.setArtist(newSong.getArtist());
            dbSong.setTitle(newSong.getTitle());
            dbSong.setAlbum(newSong.getAlbum());

            em.getTransaction().commit();
            return true;
        } else {
            return false;
        }
    }


    public boolean deleteSong(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Song song = null;
        Boolean status = false;

        try {
            song = em.find(Song.class, id);
            if (song != null) {
                System.out.println("Deleting: " + song);
                transaction.begin();
                em.remove(song);
                transaction.commit();
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error removing song: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not remove entity: " + e.toString());
        } finally {
            em.close();
        }
        return status;
    }
}
