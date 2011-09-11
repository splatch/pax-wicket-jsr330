package org.ops4j.pax.wicket.jsr330.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.protocol.http.WebSession;
import org.ops4j.pax.wicket.api.PaxWicketInjector;
import org.ops4j.pax.wicket.jsr330.InjectionProvider;
import org.ops4j.pax.wicket.jsr330.QualifierTranslator;

public class Jsr330Injector implements PaxWicketInjector {

    private List<QualifierTranslator> translators = Arrays.asList(
    );

    private InjectionProvider provider;

    public Jsr330Injector(InjectionProvider provider) {
        this.provider = provider;
    }

    public void inject(Object toInject, Class<?> toHandle) {
        List<Field> fields = getFields(toHandle);

        for (Field field : fields) {
            Map<String, Object> qualifiers = new HashMap<String, Object>();
            Annotation[] annotations = field.getAnnotations();

            for (Annotation annotation : annotations) {
                for (QualifierTranslator translator : translators) {
                    Object translated = translator.translate(annotation);
                    if (translated == null) {
                        continue;
                    }

                    qualifiers.put(annotation.annotationType().getName(), translated);
                }

                Object lookup = provider.lookup(getBeanType(field), qualifiers);
                if (lookup != null) {
                    setField(toInject, field, lookup);
                }
            }

        }
    }

    protected boolean accepts(Field field) {
        return field.isAnnotationPresent(Inject.class);
    }

    protected List<Field> getSingleLevelOfFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!accepts(field)) {
                continue;
            }
            fields.add(field);
        }
        return fields;
    }

    protected List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();

        while (clazz != null && !isBoundaryClass(clazz)) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!accepts(field)) {
                    continue;
                }
                fields.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    protected boolean isBoundaryClass(Class<?> clazz) {
        if (clazz.equals(WebPage.class) || clazz.equals(Page.class) || clazz.equals(Panel.class)
                || clazz.equals(MarkupContainer.class) || clazz.equals(Component.class)
                || clazz.equals(AuthenticatedWebSession.class) || clazz.equals(WebSession.class)
                || clazz.equals(Session.class) || clazz.equals(Object.class)) {
            return true;
        }
        return false;
    }

    protected void setField(Object component, Field field, Object proxy) {
        try {
            checkAccessabilityOfField(field);
            field.set(component, proxy);
        } catch (Exception e) {
            throw new RuntimeException("Bumm", e);
        }
    }

    private void checkAccessabilityOfField(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    protected Class<?> getBeanType(Field field) {
        Class<?> beanType = field.getType();
        return beanType;
    }

}
