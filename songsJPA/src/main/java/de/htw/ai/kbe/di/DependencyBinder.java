package de.htw.ai.kbe.di;

import de.htw.ai.kbe.handler.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind (Persistence
                .createEntityManagerFactory("songDB-PU"))
                .to(EntityManagerFactory.class);
        bind(SongsDaoEmf.class).to(ISongsHandler.class).in(Singleton.class);
        bind(new UsersHandler()).to(IUsersHandler.class);
        bind(PlaylistsDaoEmf.class).to(IPlaylistsHandler.class);

    }
}