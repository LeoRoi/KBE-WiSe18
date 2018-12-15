package de.htw.ai.kbe.di;

import de.htw.ai.kbe.storage.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SongsHandler.class).to(iSongsHandler.class).in(Singleton.class);
        bind(UsersHandler.class).to(IUsersHandler.class);
    }
}