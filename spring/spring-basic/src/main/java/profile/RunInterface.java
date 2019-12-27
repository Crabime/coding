package profile;

import java.util.logging.Logger;

public interface RunInterface {

    Logger logger = Logger.getLogger(RunInterface.class.getName());

    void run();
}
