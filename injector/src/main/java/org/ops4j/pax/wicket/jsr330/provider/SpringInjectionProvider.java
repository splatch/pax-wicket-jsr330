package org.ops4j.pax.wicket.jsr330.provider;

import java.util.Map;

import javax.inject.Named;

import org.ops4j.pax.wicket.jsr330.InjectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class SpringInjectionProvider implements InjectionProvider {

    private Logger logger = LoggerFactory.getLogger(SpringInjectionProvider.class);

    private ApplicationContext context;

    public SpringInjectionProvider(ApplicationContext service) {
        context = service;
    }

    public <T> T lookup(Class<T> beanType, Map<String, Object> qualifiers) {
        String name = (String) qualifiers.get(Named.class.getName());

        if (name != null) {
            return context.getBean(name, beanType);
        } else {
            Map<String, T> beans = context.getBeansOfType(beanType);
            if (beans.size() > 1) {
                logger.warn("Multiple beans of type {} has been found. Injection was skipped", beanType);
            } else if (beans.size() == 1) {
                logger.debug("An instance of bean {} has been found in spring context", beans);
                return beans.values().iterator().next();
            }
        }

        return null;
    }

}
