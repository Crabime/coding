package cn.crabime.felix.service.inner;

import cn.crabime.felix.service.PersonService;
import cn.crabime.felix.service.SimplePerson;

/**
 * @author crabime
 * @since 2021/03/28
 * service包下，但是不export，在另一个bundle中无法获取到
 */
public class InnerService {

    public void wrapPerson(SimplePerson person) {
        PersonService personService = PersonService.getInstance();
        personService.addPerson(person);
    }
}
