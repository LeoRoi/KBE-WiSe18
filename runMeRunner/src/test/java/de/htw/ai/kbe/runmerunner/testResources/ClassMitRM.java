package de.htw.ai.kbe.runmerunner.testResources;

import de.htw.ai.kbe.runmerunner.RunMe;

public class ClassMitRM {

    @RunMe
    public void methodWithAnnotationRunMe() {

    }

    @Deprecated
    public void methodWithRandomAnnotation() {

    }

    public void methodWithoutAnnotation() {

    }


    @RunMe
    public void methodWithParameter(int i) {

    }

    @RunMe
    protected void protectedMethodWithRunMe() {

    }

    protected void protectedMethodWithoutRunMe() {

    }

    }
