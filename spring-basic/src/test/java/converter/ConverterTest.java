package converter;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static com.google.common.truth.Truth.assertThat;

/**
 * Created by crabime on 9/15/17.
 */
public class ConverterTest extends TestCase {
    private ApplicationContext context;

    @Override
    protected void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext("converter.xml");
    }

    @Test
    public void testOriginReservation(){
        Reservation reservation = context.getBean("dummyReservation", Reservation.class);
        assertThat(reservation.getDuration()).isEqualTo(12);
        assertThat(reservation.getPlayer().getGender()).isEqualTo('M'); //Spring使用CharacterEditor
        assertThat(reservation.getPlayer().getHabits()).hasLength(2);
        assertThat(reservation.getPlayer().getIcon().getName()).isEqualTo("me.jpg");
    }

    @Test
    public void testClassArrayEditor(){
        ClassArrayEditorBean classArrayBean = context.getBean("classArrayBean", ClassArrayEditorBean.class);
        assertThat(classArrayBean.getClasses().length).isEqualTo(2);
    }

    @Test
    public void testCustomizeEditor(){
        Reservation reservation = context.getBean("dummyReservation", Reservation.class);
        assertThat(reservation.getType().getName()).isEqualTo("TENNIS");
    }

    /**
     * 使用Spring AnnotationBeanUtils进行Bean注解转换
     */
    @Test
    public void testAnnotationBeanUtils(){
        Reservation reservation = context.getBean("dummyReservation", Reservation.class);
        Player player = reservation.getPlayer();

    }
}
