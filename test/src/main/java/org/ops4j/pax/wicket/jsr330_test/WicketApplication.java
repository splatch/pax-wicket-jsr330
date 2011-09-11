package org.ops4j.pax.wicket.jsr330_test;

import org.apache.wicket.protocol.http.WebApplication;

public class WicketApplication extends WebApplication {

    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

}
