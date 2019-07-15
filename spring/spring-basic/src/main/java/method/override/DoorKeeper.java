package method.override;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * 门卫需要看时间
 */
@Component
public abstract class DoorKeeper {

    @Autowired
    private FoolWatchOwner foolWatchOwner;

    public void showMeTime(BeanType type) {
        if (type.equals(BeanType.PROTOTYPE)) {
            System.out.println("当前时间为：" + watchOwner().getTimeForStr());
        } else {
            System.out.println("当前时间为：" + foolWatchOwner.getCachedTimeForStr());
        }
    }

    @Lookup
    public abstract WatchOwner watchOwner();
}
