package org.ops4j.pax.wicket.jsr330.extender;

import java.util.Properties;

import org.ops4j.pax.wicket.jsr330.InjectionProvider;
import org.ops4j.pax.wicket.jsr330.provider.OsgiInjectionProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.BundleTracker;

public class OsgiTracker extends BundleTracker {

    public OsgiTracker(BundleContext context) {
        super(context, Bundle.ACTIVE, null);
    }

    @Override
    public Object addingBundle(Bundle bundle, BundleEvent event) {
        String imports = (String) bundle.getHeaders().get(Constants.IMPORT_PACKAGE);
        if (imports != null && imports.contains("org.apache.wicket")) {
            Properties props = new Properties();
            props.put("type", "osgi");

            InjectionProvider injectionProvider = new OsgiInjectionProvider(bundle.getBundleContext());
            ServiceRegistration registration = bundle.getBundleContext().registerService(InjectionProvider.class.getName(),
                injectionProvider, props);
            return registration;
        }

        return null;
    }

    @Override
    public void modifiedBundle(Bundle bundle, BundleEvent event, Object object) {
        // TODO Auto-generated method stub
    }

    @Override
    public void removedBundle(Bundle bundle, BundleEvent event, Object object) {
        ((ServiceRegistration) object).unregister();
        // OsgiInjectionProvider injectionProvider = (OsgiInjectionProvider) service;
        // ok, let stop the injection provider
        // context.ungetService(reference);
    }

}
