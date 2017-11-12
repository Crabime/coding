package jstl;

import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by crabime on 10/28/17.
 */
public class RandomUUIDTest extends TestCase {

    private static final Logger logger = LoggerFactory.getLogger(RandomUUIDTest.class);

    @Test
    public void testUUID(){
        logger.info(UUID.randomUUID().toString());
    }
}
