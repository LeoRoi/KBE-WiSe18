package de.htw.ai.kbe.songs_servlet.testResources;

import de.htw.ai.kbe.songs_servlet.RunMe;

public interface InterfaceMitRM {

    @RunMe
    void methodWithAnnotation();

    void methodWithoutAnnotation();


}
