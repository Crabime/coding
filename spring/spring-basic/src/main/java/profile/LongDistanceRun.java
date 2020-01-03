package profile;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Conditional(DistanceCondition.class)
public class LongDistanceRun {

    private final static Logger logger = Logger.getLogger(LongDistanceRun.class.getName());

    public void longRun() {
        logger.info("我在进行长跑");
    }

}
