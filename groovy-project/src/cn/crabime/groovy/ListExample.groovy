package cn.crabime.groovy

/**
 * Created by crabime on 11/22/17.
 * groovy测试脚本
 */
(1..3).each {
    println "Number : ${it}"
}

8.times {
    println it
}

def myWith(Closure closure){
    closure()
}
myWith {
    println it
}