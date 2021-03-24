package cn.crabime.felix;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class SimpleActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("bundle start already!");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("bundle stopped already!");
    }
}
