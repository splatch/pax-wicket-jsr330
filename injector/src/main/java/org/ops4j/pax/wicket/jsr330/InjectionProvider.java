package org.ops4j.pax.wicket.jsr330;

import java.util.Map;

public interface InjectionProvider {

    <T> T lookup(Class<T> beanType, Map<String, Object> qualifiers);

}
