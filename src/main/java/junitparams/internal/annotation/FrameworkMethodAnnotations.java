package junitparams.internal.annotation;

import junitparams.Parameters;
import junitparams.custom.CustomParameters;
import org.junit.runners.model.FrameworkMethod;

import java.lang.annotation.Annotation;

public class FrameworkMethodAnnotations {

    private final FrameworkMethod frameworkMethod;

    public FrameworkMethodAnnotations(FrameworkMethod frameworkMethod) {
        this.frameworkMethod = frameworkMethod;
    }

    public boolean isParametrised() {
        return hasAnnotation(Parameters.class)
                || hasCustomParameters();
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotation) {
        return getAnnotation(annotation) != null;
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        return frameworkMethod.getAnnotation(annotationType);
    }

    public boolean hasCustomParameters() {
        return getCustomParameters() != null;
    }

    public CustomParametersDescriptor getCustomParameters() {
        CustomParameters customParameters = frameworkMethod.getAnnotation(CustomParameters.class);
        if (customParameters != null) {
            return new CustomParametersDescriptor(customParameters);
        }

        for (Annotation annotation : frameworkMethod.getAnnotations()) {
            customParameters = annotation.annotationType().getAnnotation(CustomParameters.class);
            if (customParameters != null) {
                return new CustomParametersDescriptor(customParameters, annotation);
            }
        }
        return null;
    }

    public boolean isParallel() {
        if (!hasAnnotation((Parameters.class))) {
            return false;
        }
        Parameters parameters = getAnnotation(Parameters.class);
        return parameters.parallel();
    }

    public Annotation[] allAnnotations() {
        return frameworkMethod.getAnnotations();
    }
}
