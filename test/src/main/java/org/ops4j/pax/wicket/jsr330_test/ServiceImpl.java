package org.ops4j.pax.wicket.jsr330_test;

public class ServiceImpl implements Service {

    public String sayHello() {
        return "Hello " + Math.random();
    }

}
