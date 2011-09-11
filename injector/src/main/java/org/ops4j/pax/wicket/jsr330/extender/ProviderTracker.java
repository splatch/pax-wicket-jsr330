package org.ops4j.pax.wicket.jsr330.extender;

import java.util.List;

import org.ops4j.pax.wicket.jsr330.InjectionProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class ProviderTracker extends ServiceTracker {

    private List<InjectionProvider> providers;

    public ProviderTracker(BundleContext context, List<InjectionProvider> providers) {
        super(context, InjectionProvider.class.getName(), null);
        this.providers = providers;
    }

    @Override
    public Object addingService(ServiceReference reference) {
        providers.add((InjectionProvider) context.getService(reference));
        return new Object();
    }

    @Override
    public void removedService(ServiceReference reference, Object service) {
        context.ungetService(reference);
    }

}
