package jstl;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by crabime on 10/28/17.
 */
public class RandomUUIDTest extends TestCase {

    @Test
    public void testUUID(){
        System.out.println(UUID.randomUUID().toString());
    }
}
