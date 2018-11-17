package de.htw.ai.kbe.songs_servlet.testResources;

import de.htw.ai.kbe.songs_servlet.RunMe;

public abstract class AbstractClassMitRM {

    public abstract void abstractMethodWithoutAnnotation();

    @RunMe
    public abstract void abstractMethodWithAnnotation();

}
