package de.htw.ai.kbe.handler;

import de.htw.ai.kbe.entity.Playlist;
import de.htw.ai.kbe.entity.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

public class PlaylistsDaoEmf implements IPlaylistsHandler {
    private EntityManagerFactory emf;

    @Inject
    public PlaylistsDaoEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Playlist> getUserPlaylists(int uid) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery("SELECT pl from Playlist pl where pl.owner = :uid", Playlist.class).setParameter("uid", uid);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Playlist getUserPlaylistsWithId(User user, int pid) {

        return null;
    }

    @Override
    public int addPlaylist(Playlist playlist) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(playlist);
            em.getTransaction().commit();
            return playlist.getId();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding new playlist: " + e.getMessage());
            em.getTransaction().rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deletePlaylist(int pid) {
        EntityManager em = emf.createEntityManager();
        try {
            Playlist list = em.find(Playlist.class, pid);
            if (list != null) {
                System.out.println("Deleting: " + list);
                em.getTransaction().begin();
                em.remove(list);
                em.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error removing playlist: " + e.getMessage());
            em.getTransaction().rollback();
            throw new PersistenceException("Could not remove entity: " + e.toString());
        } finally {
            em.close();
        }
        return false;
    }
}