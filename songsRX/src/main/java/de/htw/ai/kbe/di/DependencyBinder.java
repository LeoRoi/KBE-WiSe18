package de.htw.ai.kbe.di;

import de.htw.ai.kbe.storage.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SongsHandler.class).to(ISongsHandler.class).in(Singleton.class);
        bind(new UsersHandler()).to(IUsersHandler.class);
    }
}