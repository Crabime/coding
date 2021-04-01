package cn.crabime.felix.service;

import java.util.HashMap;
import java.util.Map;

public class PersonService {

    private Map<String, SimplePerson> personMap = new HashMap<>();

    private static final PersonService personService = new PersonService();

    private PersonService() {

    }

    public static PersonService getInstance() {
        return personService;
    }

    public void addPerson(SimplePerson p) {
        System.out.println("add person " + p.toString());
        personMap.put(p.getName(), p);
    }

    public SimplePerson getPerson(String name) {
        return personMap.get(name);
    }
}
