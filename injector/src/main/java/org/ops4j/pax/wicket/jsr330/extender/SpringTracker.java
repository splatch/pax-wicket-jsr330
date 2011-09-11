package org.ops4j.pax.wicket.jsr330.extender;

import java.util.Properties;

import org.ops4j.pax.wicket.jsr330.InjectionProvider;
import org.ops4j.pax.wicket.jsr330.provider.SpringInjectionProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.springframework.context.ApplicationContext;

public class SpringTracker extends ServiceTracker {

    public SpringTracker(BundleContext context) {
        super(context, ApplicationContext.class.getName(), null);
    }

    public Object addingService(ServiceReference reference) {
        Bundle bundle = reference.getBundle();

        String imports = (String) bundle.getHeaders().get(Constants.IMPORT_PACKAGE);
        if (imports != null && imports.contains("org.apache.wicket")) {
            Properties props = new Properties();
            props.put("type", "spring");
            InjectionProvider injectionProvider = new SpringInjectionProvider((ApplicationContext) context.getService(reference));
            ServiceRegistration registration = reference.getBundle().getBundleContext().registerService(InjectionProvider.class.getName(),
                injectionProvider, props);
            return registration;
        }

        return null;
    }

    public void modifiedService(ServiceReference reference, Object service) {
        // TODO Auto-generated method stub

    }

    public void removedService(ServiceReference reference, Object service) {
        ((ServiceRegistration) service).unregister();
        // SpringInjectionProvider injectionProvider = (SpringInjectionProvider) service;
        // ok, let stop the injection provider
        context.ungetService(reference);
    }

}
