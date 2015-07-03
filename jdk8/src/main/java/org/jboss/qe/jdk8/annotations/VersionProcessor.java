package org.jboss.qe.jdk8.annotations;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import java.util.Set;

/*
[pkremens@localhost src] (master *+%)$ pwd
/home/pkremens/devel/drunken-octo-shame/jdk8/src
[pkremens@localhost src] (master *+%)$ javac org/jboss/qe/jdk8/annotations/VersionProcessor.java [pkremens@localhost src] (master *+%)$ javac -processor org.jboss.qe.jdk8.annotations.VersionProcessor org/jboss/qe/jdk8/annotations/VersionProcessorTest.java
TEST
org/jboss/qe/jdk8/annotations/VersionProcessorTest.java:4: error: Version cannot be negative. major=-1 minor=2
public class VersionProcessorTest {
       ^
org/jboss/qe/jdk8/annotations/VersionProcessorTest.java:10: error: Version cannot be negative. major=-2 minor=1
    public void m2() {
                ^
TEST
2 errors
 */

/**
 * Beginning Java 8 Language Features
 *
 * @author Kishori Sharan
 */
@SupportedAnnotationTypes({"org.jboss.qe.jdk8.annotations.Version"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class VersionProcessor extends AbstractProcessor {
    // A no-args constructor is required for an annotation processor
    public VersionProcessor() {
    }

    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        // Process all annotations
        for (TypeElement currentAnnotation : annotations) {
            Name qualifiedName = currentAnnotation.getQualifiedName();

            // check if it is a Version annotation
            if (qualifiedName.contentEquals("org.jboss.qe.jdk8.annotations.Version")) {
                // Look at all elements that have Version annotations
                Set<? extends Element> annotatedElements;
                annotatedElements = roundEnv.getElementsAnnotatedWith(
                        currentAnnotation);
                for (Element element : annotatedElements) {
                    Version v = element.getAnnotation(Version.class);
                    int major = v.major();
                    int minor = v.minor();
                    if (major < 0 || minor < 0) {
                        // Print the error message
                        String errorMsg = "Version cannot" +
                                " be negative." +
                                " major=" + major +
                                " minor=" + minor;

                        Messager messager =
                                this.processingEnv.getMessager();

                        messager.printMessage(Kind.ERROR,
                                errorMsg, element);
                    }
                }
            }
        }

        return true;
    }
}

