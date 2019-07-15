package method.override;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 手表持有者
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WatchOwner extends Date {

    public String getTimeForStr() {
        return super.toString();
    }
}
