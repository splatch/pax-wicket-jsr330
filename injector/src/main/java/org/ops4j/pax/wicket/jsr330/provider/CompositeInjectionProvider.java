package org.ops4j.pax.wicket.jsr330.provider;

import java.util.List;
import java.util.Map;

import org.ops4j.pax.wicket.jsr330.InjectionProvider;

public class CompositeInjectionProvider implements InjectionProvider {

    private List<InjectionProvider> providers;

    public CompositeInjectionProvider(List<InjectionProvider> providers) {
        this.providers = providers;
    }

    public <T> T lookup(Class<T> beanType, Map<String, Object> qualifiers) {
        for (InjectionProvider provider : providers) {
            T value = provider.lookup(beanType, qualifiers);
            if (value != null) {
                return value;
            }
        }

        return null;
    }

}
