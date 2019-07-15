package method.override;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 比较懒的手表持有者，看一次手表，后面就不看了
 */
@Component
public class FoolWatchOwner extends Date {

    public String getCachedTimeForStr() {
        return super.toString();
    }
}
