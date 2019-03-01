package de.htw.ai.kbe.handler;

import de.htw.ai.kbe.entity.Playlist;
import de.htw.ai.kbe.entity.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;

public class PlaylistsDaoEm implements IPlaylistsHandler {
    private EntityManager em;

    @Inject
    public PlaylistsDaoEm(EntityManager em) {
        this.em = em;
    }


    @Override
    public List<Playlist> getUsersPlaylists(User owner) {
        return null;
    }

    @Override
    public List<Playlist> getUsersPublicPlaylists(User owner) {
        return null;
    }

    @Override
    public Playlist getUserPlaylistById(int pid) {

        return null;
    }

    @Override
    public int addPlaylist(Playlist playlist) {
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
        }
    }

    @Override
    public boolean deletePlaylist(int pid) {
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
        }
        return false;
    }
}
