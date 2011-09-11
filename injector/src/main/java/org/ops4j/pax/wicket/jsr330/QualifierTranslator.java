package org.ops4j.pax.wicket.jsr330;

import java.lang.annotation.Annotation;

public interface QualifierTranslator {

    Object translate(Annotation annotation);

}
