package cn.crabime.aspectj;

/**
 * Created by crabime on 11/22/17.
 */
public aspect HelloAspect {
    pointcut one(): execution(* hello());

    before() : one(){
        System.out.println("before test execution");
    }
}
