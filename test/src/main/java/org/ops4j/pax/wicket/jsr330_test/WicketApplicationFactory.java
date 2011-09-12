package org.ops4j.pax.wicket.jsr330_test;

import javax.servlet.ServletContext;

import org.apache.wicket.protocol.http.ContextParamWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.ops4j.pax.wicket.internal.injection.ComponentInstantiationListenerFacade;
import org.ops4j.pax.wicket.jsr330.internal.Jsr330Injector;
import org.ops4j.pax.wicket.jsr330.servlet.SprintServletInjectionProvider;

public class WicketApplicationFactory extends ContextParamWebApplicationFactory {

    public WebApplication createApplication(WicketFilter filter) {

        ServletContext sc = filter.getFilterConfig().getServletContext();

        SprintServletInjectionProvider provider = new SprintServletInjectionProvider(sc);
        Jsr330Injector jsr330Injector = new Jsr330Injector(provider);

        WebApplication application = super.createApplication(WicketApplication.class.getName());
        application.addComponentInstantiationListener(new ComponentInstantiationListenerFacade(jsr330Injector));

        return application;
    }

}
