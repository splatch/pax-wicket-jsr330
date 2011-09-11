package org.ops4j.pax.wicket.jsr330.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.ops4j.pax.wicket.api.PaxWicketInjector;
import org.ops4j.pax.wicket.jsr330.InjectionProvider;
import org.ops4j.pax.wicket.jsr330.extender.BlueprintTracker;
import org.ops4j.pax.wicket.jsr330.extender.OsgiTracker;
import org.ops4j.pax.wicket.jsr330.extender.ProviderTracker;
import org.ops4j.pax.wicket.jsr330.extender.SpringTracker;
import org.ops4j.pax.wicket.jsr330.provider.CompositeInjectionProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private BlueprintTracker blueprint;
    private SpringTracker spring;
    private OsgiTracker osgi;
    private ServiceRegistration registration;
    private ProviderTracker provider;

    public void start(BundleContext context) throws Exception {

        blueprint = new BlueprintTracker(context);
        blueprint.open(true);

        spring = new SpringTracker(context);
        spring.open(true);

        osgi = new OsgiTracker(context);
        osgi.open();

        List<InjectionProvider> providers = Collections.synchronizedList(new ArrayList<InjectionProvider>());
        provider = new ProviderTracker(context, providers);
        provider.open();

        Jsr330Injector injector = new Jsr330Injector(new CompositeInjectionProvider(providers));

        registration = context.registerService(PaxWicketInjector.class.getName(), injector, new Properties());
    }

    public void stop(BundleContext context) throws Exception {
        provider.close();

        registration.unregister();

        osgi.close();
        spring.close();
        blueprint.close();
    }

}
