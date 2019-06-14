package cn.crabime;

import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by crabime on 6/26/17.
 * spring EL表达式
 */
public class SpringElTest {
    private ExpressionParser parser;
    private Expression exp = null;
    @Before
    public void setUp(){
        parser = new SpelExpressionParser();
    }
    /**
     * Spring EL 获取直接量表达式
     */
    @Test
    public void testGetPrimitiveValue(){
        exp = parser.parseExpression("'hello world'");
        System.out.println(exp.getValue(String.class));
        exp = parser.parseExpression("0.23");
        System.out.println(exp.getValue(Double.class));
    }

    /**
     * Spring EL创建数组
     */
    @Test
    public void testDefineArray(){
        exp = parser.parseExpression("new String[]{'china', 'american', 'canada'}");
        System.out.println(exp.getValue());
    }

    @Test
    public void testGetCollectionElement(){
        List<String> names = new ArrayList<>();
        names.add("john skeet");
        names.add("donald trump");
        Map<String, Double> subjects = new HashMap<>();
        subjects.put("chinese", 105.0);
        subjects.put("math", 130.0);
        EvaluationContext atx = new StandardEvaluationContext();
        //将集合放到全局中
        atx.setVariable("cnames", names);
        atx.setVariable("csubjects", subjects);
        //访问List集合中的第二个元素
        System.out.println("list集合中的第二个元素的值为:" + parser.parseExpression("#cnames[1]").getValue(atx));
        //获取map集合中的key为math的值
        System.out.println("Map中key为math的值为:" + parser.parseExpression("#csubjects['math']").getValue(atx));

        //在expression中调用方法
        System.out.println("list集合的大小为:" + parser.parseExpression("#cnames.size()").getValue(atx));
    }

    /**
     * 使用Spring EL中类型运算符T(),告诉Spring把运算符内的字符串当成"类"来处理
     * 避免Spring错误解析
     */
    @Test
    public void testStaticMethodInvoking(){
        System.out.println("获取一个随机数:" + parser.parseExpression("T(java.lang.Math).random()").getValue());
        System.out.println(parser.parseExpression("T(System).getProperty('os.name')").getValue());
    }
}
