package de.htw.ai.kbe.runmerunner;

public class UseMe {

    void methodWithoutAnnotation1(){
        System.out.println("methodWithoutAnnotation1");
    }

    void methodWithoutAnnotation2(){
        System.out.println("methodWithoutAnnotation2");
    }

    @RunMe
    void methodWithAnnotation1(){
        System.out.println("modules created annotation class");
    }

    @RunMe
    void methodWithAnnotationAndArgs(String iWillRuinU) {
        System.out.println(iWillRuinU);
    }

}
