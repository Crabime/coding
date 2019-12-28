package profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("rush")
public class Rush implements RunInterface {

    @Override
    public void run() {
        logger.info("进行快跑...");
    }
}
