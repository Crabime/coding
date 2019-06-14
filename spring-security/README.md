一步步了解spring security特色
===
spring security框架在刚开始学习时感觉非常晦涩难懂，配置太多，在踩了一个又一个坑之后，觉得是时候
对它做一个深入的理解，这里主要是从源码出发，逐步深入到spring security安全架构内部。

### I 前言
* [1、spring security hello world](#1)
* [2、方法级别权限控制](#2)
* [3、角色层级(Role Hierarchy)](#3)
* [4、SecurityBuilder And SecurityConfigurer](#4)

<h3 id="1">1、spring security hello world</h3>
&emsp;&emsp;该模块为最基本spring security配置，这里我将用户认证信息存放到数据库，当然这样做就复杂些。

<h3 id="2">2、方法级别权限控制</h3>
&emsp;&emsp;方法级别权限控制指代码中如果定义了一个方法,该方法仅限于具有某种权限的用户访问,但低于该权限
的用户调用该方法时将会返回权限异常。详情参见CommonController类中findUserInfo方法,该类虽然所有普通
用户均可以访问,但是由于UserDao中指定了findUserById方法必须要管理员权限,所以这里所以普通用户请求该方法
时会报错。

<h3 id="3">3、角色层级</h3>
&emsp;&emsp;以前在开发过程中碰到一个场景,匿名用户有[1]权限,普通用户有[1,2]权限,管理员有[1,2,3]权限。
那么在数据库配置,假设管理员用户id为1,普通用户id为2,匿名用户id为3,角色-资源映射表中会有如下记录:<br/>
---
ROLE_ADMIN <br/>
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;|____1 - 1<br/>
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;|____1 - 2<br/>
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;|____1 - 3<br/>

ROLE_USER <br/>
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;|____2 - 1<br/>
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;|____2 - 2<br/>

ROLE_ANONYMOUS <br/>
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;|____3 - 1<br/>
---
问题: 每次我增加一个secured object(需被拦截的URL),我需要在上面的关联表中增加三条记录,那如果系统中有
20个角色那就麻烦了,那增加一个安全对象还要改死人,况且还有可能开发人员疏忽了,没有把表中记录补全,系统上线
后就发现某一类人根本就打不开那个页面(权限异常!),那就尴尬了,那有没有办法保证每种角色只需要负责它自己的acl
列表,其它比它权限大的可以包含它所包含的权限呢?答案是当然的:<br/>
Spring Security为我们提供了Role Hierarchy这个好东西,它允许我们配置哪些权限可以包含其它权限,一种
include关系,例如上面的admin用户应该包含user用户的所有权限。实现Role Hierarchy有如下几个步骤:
1. spring容器中定义一个RoleHierarchyVoter bean,该bean也是一种RoleVoter实现
2. 定义好层级关系,直接在定义一个RoleHierarchyImpl bean,配置好它的hierarchy属性
3. 将定义好的RoleHierarchyImpl bean作为构造参数注入到AccessDecisionManager中
下面是我的配置:<br/>
```xml
<bean id="roleHierarchy" class="org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl">
        <property name="hierarchy">
            <value>
                ROLE_ADMIN > ROLE_USER
                ROLE_USER > ROLE_ANONYMOUS
            </value>
        </property>
    </bean>

    <bean id="roleVoter" class="org.springframework.security.access.vote.RoleHierarchyVoter">
        <constructor-arg ref="roleHierarchy" />
    </bean>

    <bean id="accessDecisionManager" class="org.springframework.security.access.vote.AffirmativeBased">
        <constructor-arg>
            <list>
                <ref bean="roleVoter" />
            </list>
        </constructor-arg>
    </bean>
```
注意:这里的`>`符号很重要,并且也只能使用该符号,表示前面的权限大于后面权限,包含关系。

<h3 id="4">SecurityBuilder And SecurityConfigurer</h3>