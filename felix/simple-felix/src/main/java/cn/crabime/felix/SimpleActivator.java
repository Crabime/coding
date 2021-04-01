package cn.crabime.felix;

import cn.crabime.felix.service.PersonService;
import cn.crabime.felix.service.SimplePerson;
import cn.crabime.felix.service.inner.InnerService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.Hashtable;

public class SimpleActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        PersonService personService = PersonService.getInstance();
        personService.addPerson(new SimplePerson("张三", 27));
        bundleContext.registerService(PersonService.class, personService, new Hashtable<>());
        System.out.println("bundle start already!");

        InnerService innerService = new InnerService();
        innerService.wrapPerson(new SimplePerson("Trump", 71));
        System.out.println("register trump already");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("bundle stopped already!");
        ServiceReference<PersonService> serviceReference = bundleContext.getServiceReference(PersonService.class);
        PersonService personService = bundleContext.getService(serviceReference);
        SimplePerson simplePerson = personService.getPerson("张三");
        System.out.println("正在卸载person" + simplePerson.toString());
        Bundle bundle = serviceReference.getBundle();

    }
}
