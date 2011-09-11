package org.ops4j.pax.wicket.jsr330_test;

import javax.inject.Inject;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class HomePage extends WebPage {

    @Inject
    private Service service;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        add(new Label("hi", service != null ? service.sayHello() : " NO SERVICE "));
    }

    public HomePage() {
        this(null);
    }
}
