package org.ops4j.pax.wicket.jsr330.provider;

import java.util.Map;

import org.ops4j.pax.wicket.jsr330.InjectionProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OsgiInjectionProvider implements InjectionProvider {

    private BundleContext context;
    private Logger logger = LoggerFactory.getLogger(OsgiInjectionProvider.class);

    public OsgiInjectionProvider(BundleContext bundleContext) {
        context = bundleContext;
    }

    @SuppressWarnings("unchecked")
    public <T> T lookup(Class<T> beanType, Map<String, Object> qualifiers) {

        ServiceReference reference = context.getServiceReference(beanType.getName());

        if (reference != null) {
            logger.debug("An instance of service of type {} (properties: {}) has been found in osgi service registry",
                beanType, reference.getPropertyKeys());
            return (T) context.getService(reference);
        }
        return null;
    }

}
