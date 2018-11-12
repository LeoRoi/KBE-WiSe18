package de.htw.ai.kbe.runmerunner.testResources;

import de.htw.ai.kbe.runmerunner.RunMe;

public interface InterfaceMitRM {

    @RunMe
    void methodWithAnnotation();

    void methodWithoutAnnotation();


}
