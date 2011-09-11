package org.ops4j.pax.wicket.jsr330.provider;

import java.util.Map;

import org.ops4j.pax.wicket.jsr330.InjectionProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class OsgiInjectionProvider implements InjectionProvider {

    private BundleContext context;

    public OsgiInjectionProvider(BundleContext bundleContext) {
        context = bundleContext;
    }

    @SuppressWarnings("unchecked")
    public <T> T lookup(Class<T> beanType, Map<String, Object> qualifiers) {

        ServiceReference reference = context.getServiceReference(beanType.getName());

        if (reference != null) {
            return (T) context.getService(reference);
        }
        return null;
    }

}
