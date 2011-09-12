package org.ops4j.pax.wicket.jsr330_test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class HomePage extends WebPage {

    @Inject
    private Service service;

    @Inject
    private FeaturesService features;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        add(new Label("hi", service != null ? service.sayHello() : " NO SERVICE "));

        List<Feature> data = new ArrayList<Feature>();

        if (features != null) {
            try {
                data =  Arrays.asList(features.listFeatures());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        add(new ListView<Feature>("features", data) {
            @Override
            protected void populateItem(ListItem<Feature> item) {
                item.add(new Label("feature", item.getModelObject().getName()));
            }
        });
    }

    public HomePage() {
        this(null);
    }

}
