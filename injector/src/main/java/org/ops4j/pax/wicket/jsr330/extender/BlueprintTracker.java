package org.ops4j.pax.wicket.jsr330.extender;

import java.util.Properties;

import org.ops4j.pax.wicket.jsr330.InjectionProvider;
import org.ops4j.pax.wicket.jsr330.provider.BlueprintInjectionProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.osgi.util.tracker.ServiceTracker;

public class BlueprintTracker extends ServiceTracker {

    public BlueprintTracker(BundleContext context) {
        super(context, BlueprintContainer.class.getName(), null);
    }

    public Object addingService(ServiceReference reference) {
        Bundle bundle = reference.getBundle();

        String imports = (String) bundle.getHeaders().get(Constants.IMPORT_PACKAGE);
        if (imports != null && imports.contains("org.apache.wicket")) {
            Properties props = new Properties();
            props.put("type", "blueprint");

            InjectionProvider injectionProvider = new BlueprintInjectionProvider((BlueprintContainer) context.getService(reference));
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
        // BlueprintInjectionProvider injectionProvider = (BlueprintInjectionProvider) service;
        // ok, let stop the injection provider
        context.ungetService(reference);
    }

}
