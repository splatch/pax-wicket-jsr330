package org.ops4j.pax.wicket.jsr330.servlet;

import javax.servlet.ServletContext;

import org.ops4j.pax.wicket.jsr330.provider.SpringInjectionProvider;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SprintServletInjectionProvider extends SpringInjectionProvider {

    public SprintServletInjectionProvider(ServletContext context) {
        super(WebApplicationContextUtils.getWebApplicationContext(context));
    }

}
