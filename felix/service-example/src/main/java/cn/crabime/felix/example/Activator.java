package cn.crabime.felix.example;

import cn.crabime.felix.service.PersonService;
import cn.crabime.felix.service.SimplePerson;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        ServiceReference<PersonService> serviceReference =
                context.getServiceReference(PersonService.class);

        PersonService personService = context.getService(serviceReference);
        SimplePerson p = personService.getPerson("张三");
        System.out.println(p.toString());
        System.out.println("重新更新一个bundle版本试试？");
    }

    @Override
    public void stop(BundleContext context) throws Exception {

    }
}
