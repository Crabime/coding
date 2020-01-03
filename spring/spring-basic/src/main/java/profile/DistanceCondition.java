package profile;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

import java.util.logging.Logger;

public class DistanceCondition implements Condition {

    private final static Logger logger = Logger.getLogger(DistanceCondition.class.getName());

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        if (beanFactory instanceof DefaultListableBeanFactory) {
            DefaultListableBeanFactory bf = (DefaultListableBeanFactory) beanFactory;
            if (!StringUtils.isEmpty(bf.getSerializationId())) {
                boolean jogContains = context.getRegistry().containsBeanDefinition("jog");
                if (!jogContains) {
                    logger.info("未注册jog实例，当前类可以注册");
                    return true;
                }
            }
        }
        return false;
    }
}
