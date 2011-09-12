package org.ops4j.pax.wicket.jsr330.provider;

import java.util.Map;
import java.util.Set;

import javax.inject.Named;

import org.ops4j.pax.wicket.jsr330.InjectionProvider;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlueprintInjectionProvider implements InjectionProvider {

    private Logger logger = LoggerFactory.getLogger(BlueprintInjectionProvider.class);

    private BlueprintContainer container;

    public BlueprintInjectionProvider(BlueprintContainer container) {
        this.container = container;
    }

    public <T> T lookup(Class<T> beanType, Map<String, Object> qualifiers) {
        String name = (String) qualifiers.get(Named.class.getName());

        if (name != null) {
            return lookupByName(beanType, name);
        } else {
            // some kind of auto-wire
            Set<String> componentIds = container.getComponentIds();

            for (String id : componentIds) {
                T instance = lookupByName(beanType, id);
                if (instance != null) {
                    return instance;
                }
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> T lookupByName(Class<T> beanType, String name) {
        Object instance = container.getComponentInstance(name);

        if (instance != null) {
            if (beanType.isAssignableFrom(instance.getClass())) {
                logger.debug("An instance of bean named {} with type {} has been found in blueprint container",
                    name, instance.getClass());
                return (T) instance;
            }
        }
        return null;
    }

}
