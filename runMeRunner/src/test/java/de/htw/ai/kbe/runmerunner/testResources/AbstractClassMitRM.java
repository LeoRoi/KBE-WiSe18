package de.htw.ai.kbe.runmerunner.testResources;

import de.htw.ai.kbe.runmerunner.RunMe;

public abstract class AbstractClassMitRM {

    public abstract void abstractMethodWithoutAnnotation();

    @RunMe
    public abstract void abstractMethodWithAnnotation();

}
