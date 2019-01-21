package de.htw.ai.kbe.handler;

import de.htw.ai.kbe.entity.Playlist;
import de.htw.ai.kbe.entity.User;
import de.htw.ai.kbe.utils.PostgreCloser;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.*;
import java.util.List;

@Singleton
public class PlaylistsDaoEmf implements IPlaylistsHandler {
    private EntityManagerFactory emf;

    @Inject
    public PlaylistsDaoEmf(EntityManagerFactory emf) {
        this.emf = emf;
        PostgreCloser.addEntityManagerFactory(emf);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Playlist> getUsersPlaylists(User owner) {
        EntityManager em = emf.createEntityManager();
        List<Playlist> p = null;
        TypedQuery<Playlist> query;

        try {
            query = em.createQuery("SELECT pl from Playlist pl where pl.owner = :owner", Playlist.class)
                    .setParameter("owner", owner);
            p = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error getting all playlist's: " + e.getMessage());
        } finally {
            em.close();
        }
        return p;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Playlist> getUsersPublicPlaylists(User owner) {
        EntityManager em = emf.createEntityManager();
        List<Playlist> p = null;
        TypedQuery<Playlist> query;

        try {
            query = em.createQuery("SELECT pl from Playlist pl where pl.owner =:owner  and pl.open = true", Playlist.class)
                    .setParameter("owner", owner);
            p = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error getting all playlist's: " + e.getMessage());
        } finally {
            em.close();
        }
        return p;
    }


    @Override
    public Playlist getUserPlaylistById(int pid) {
        EntityManager em = emf.createEntityManager();
        Playlist playlist = null;

        try {
            playlist = em.find(Playlist.class, pid);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error getting playlist: " + e.getMessage());
        } finally {
            em.close();
        }
        return playlist;
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
            System.out.println(" listId=" + list.getId() + " owner=" + list.getOwner());
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
