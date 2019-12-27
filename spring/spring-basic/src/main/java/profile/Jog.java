package profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("jog")
public class Jog implements RunInterface {

    @Override
    public void run() {
        logger.info("慢跑中...");
    }
}
