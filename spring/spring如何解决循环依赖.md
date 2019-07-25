### `AbstractBeanFactory#doGetBean`源码解析
首先看`AbstractBeanFactory`中`doGetBean`方法，
```
protected <T> T doGetBean(
			final String name, final Class<T> requiredType, final Object[] args, boolean typeCheckOnly)
			throws BeansException {

    final String beanName = transformedBeanName(name);
    
    // Eagerly check singleton cache for manually registered singletons.
    Object sharedInstance = getSingleton(beanName);
    
    // 次数省略中间很多行代码
    ...
    // 调用getSingleton方法获取到实例
    sharedInstance = getSingleton(beanName, new ObjectFactory<Object>() {
                        @Override
                        public Object getObject() throws BeansException {
                            try {
                                return createBean(beanName, mbd, args);
                            }
                            catch (BeansException ex) {
                                // Explicitly remove instance from singleton cache: It might have been put there
                                // eagerly by the creation process, to allow for circular reference resolution.
                                // Also remove any beans that received a temporary reference to the bean.
                                destroySingleton(beanName);
                                throw ex;
                            }
                        }
                    });
}
```
上面第一个`getSingleton`返回的一个单例，spring检查系统内存中该bean是否创建，但是这里很显然，我们的单例还没有开始创建，所以这里返回null。
**注意：这个方法很重要，是循环依赖核心，后面会用到** 
继续往下看，这里又会调用一个`getSingleton`方法，这里会传入一个`ObjectFactory`，里面只有一个`getObject`方法，返回创建的bean实例。
[`createBean`方法源码解析](#doCreateBean)
该方法源码及注释如下：
```
public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
    synchronized (this.singletonObjects) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject == null) {
            if (this.singletonsCurrentlyInDestruction) {
                throw new BeanCreationNotAllowedException(beanName,
                        "Singleton bean creation not allowed while the singletons of this factory are in destruction " +
                        "(Do not request a bean from a BeanFactory in a destroy method implementation!)");
            }
            
            beforeSingletonCreation(beanName);
            ...
            try {
                // 调用getObject，创建bean
                singletonObject = singletonFactory.getObject();
                newSingleton = true;
            }
            ...
            finally {
                afterSingletonCreation(beanName);
            }
            if (newSingleton) {
                // 将创建的bean添加到spring集合中
                addSingleton(beanName, singletonObject);
            }
        }
        return (singletonObject != NULL_OBJECT ? singletonObject : null);
    }
}
protected void addSingleton(String beanName, Object singletonObject) {
    synchronized (this.singletonObjects) {
        this.singletonObjects.put(beanName, (singletonObject != null ? singletonObject : NULL_OBJECT));
        this.singletonFactories.remove(beanName);
        this.earlySingletonObjects.remove(beanName);
        this.registeredSingletons.add(beanName);
    }
}
```

### 理解spring中几个重要实例缓存集合
**singletonObjects**：缓存已完成的bean实例
**singletonFactories**：缓存每个bean以及创建该bean的实例工厂ObjectFactory
**earlySingletonObjects**：缓存早期创建的bean，早期也就是该bean已经完成实例化，但是通过IoC注入的依赖还没注入。简单的说就是只调用了构造器方法，未调用任何setter方法。
**registeredSingletons**：缓存注册在spring容器中的bean，是一个set集合，只记录bean名字

### <div id="doCreateBean">`doCreateBean`源码解析</div>
```
protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final Object[] args) throws BeanCreationException {

    ...
    // 首先创建bean实例
    createBeanInstance(beanName, mbd, args);
    final Object bean = (instanceWrapper != null ? instanceWrapper.getWrappedInstance() : null);
    Class<?> beanType = (instanceWrapper != null ? instanceWrapper.getWrappedClass() : null);
    mbd.resolvedTargetType = beanType;

    ...
    // 早期缓存单例
    boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences && isSingletonCurrentlyInCreation(beanName));
    if (earlySingletonExposure) {
        
        // 将bean与该bean的早期对象引用添加到map中
        addSingletonFactory(beanName, new ObjectFactory<Object>() {
            @Override
            public Object getObject() throws BeansException {
                return getEarlyBeanReference(beanName, mbd, bean);
            }
        });
    }

    // Initialize the bean instance.
    Object exposedObject = bean;
    try {
        // 管理创建的bean属性，也就是对使用@Autowire类进行设值处理
        populateBean(beanName, mbd, instanceWrapper);
        if (exposedObject != null) {
            // 如果该Bean实现了BeanNameAware或BeanFactoryAware等接口，调用对应方法对bean进行初始化
            exposedObject = initializeBean(beanName, exposedObject, mbd);
        }
    }
    
    if (earlySingletonExposure) {
        Object earlySingletonReference = getSingleton(beanName, false);
        if (earlySingletonReference != null) {
            if (exposedObject == bean) {
                exposedObject = earlySingletonReference;
            }
            else if (!this.allowRawInjectionDespiteWrapping && hasDependentBean(beanName)) {
                String[] dependentBeans = getDependentBeans(beanName);
                Set<String> actualDependentBeans = new LinkedHashSet<String>(dependentBeans.length);
                for (String dependentBean : dependentBeans) {
                    if (!removeSingletonIfCreatedForTypeCheckOnly(dependentBean)) {
                        actualDependentBeans.add(dependentBean);
                    }
                }
                if (!actualDependentBeans.isEmpty()) {
                    throw new BeanCurrentlyInCreationException(beanName,
                            "Bean with name '" + beanName + "' has been injected into other beans [" +
                            StringUtils.collectionToCommaDelimitedString(actualDependentBeans) +
                            "] in its raw version as part of a circular reference, but has eventually been " +
                            "wrapped. This means that said other beans do not use the final version of the " +
                            "bean. This is often the result of over-eager type matching - consider using " +
                            "'getBeanNamesOfType' with the 'allowEagerInit' flag turned off, for example.");
                }
            }
        }
    }

    // Register bean as disposable.
    try {
        registerDisposableBeanIfNecessary(beanName, bean, mbd);
    }
    catch (BeanDefinitionValidationException ex) {
        throw new BeanCreationException(
                mbd.getResourceDescription(), beanName, "Invalid destruction signature", ex);
    }

    return exposedObject;
}
```
spring在调用`createBeanInstance`方法时会判断当前这个bean构造方法上是否存在注入的类，如果存在则调用`autowireConstructor`创建实例，
否则调用`instantiateBean`.`instantiateBean`比较简单，一种是通过CGLIB进行动态代理，如果目标类scope为`prototype`或有`@Lookup`注解标识，
当然大部分还是通过java反射来创建(Class.forName(xxx))。下一步调用`populateBean`，如果spring发现某些属性是注进来的，对这些属性进行
赋值时需要初始化这些类，把引用赋给当前bean的类变量，这里举个例子：
```
@Component
class A {

    @Autowire
    private B b;
}

@Component
class B {
    
    @Autowire
    private A a;
}
```
spring在对A进行`doCreateBean`会经历`createBeanInstance` -> `populateBean` -> `initializeBean`这三个核心阶段。
调用`createBeanInstance`也相当于执行`new A()`，这时我们可以获取到A这个类的引用了。第二步执行`populateBean`，对`A`中的属性`b`进行赋值。
spring这时发现`B`还没开始初始化，于是开始初始化`B`,仍然从上面`doGetBean`步骤开始，当然走到最后会遇到`A`一样的问题，对属性`a`进行赋值时，
会调用`getBean`获取`A`对象的引用，程序又来到了上面的`doGetBean`方法，但是这里再次执行`getSingleton`方法时，会有神奇的效果：
```
protected Object getSingleton(String beanName, boolean allowEarlyReference) {
    // 由于singletonObjects里面存放的是创建且实例化完成的bean，而这里由于A/B均有部分属性未初始化完成，所以还不在singletonObjects集合中
    Object singletonObject = this.singletonObjects.get(beanName);
    
    // 该bean当前正在创建过程中，这里isSingletonCurrentlyInCreation返回的也是true
    if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
        synchronized (this.singletonObjects) {
            // 由于前面doCreateBean中调用了addSingletonFactory，他会将实例化后的bean添加到registeredSingletons中，
            // 将对应的ObjectFactory添加到singletonFactories集合中，并从earlySingletonObjects中将该bean移除，所以这里返回null
            singletonObject = this.earlySingletonObjects.get(beanName);
            if (singletonObject == null && allowEarlyReference) {
                
                // 获取到该bean的ObjectFactory，通过该factory获取到该bean的earlySingletonObject，也就是早期对象
                ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    // 获取早期对象，也就是实例化对象的引用
                    singletonObject = singletonFactory.getObject();
                    this.earlySingletonObjects.put(beanName, singletonObject);
                    this.singletonFactories.remove(beanName);
                }
            }
        }
    }
    return (singletonObject != NULL_OBJECT ? singletonObject : null);
}
```
这里对`B`进行初始化并调用`populateBean`时，对`A`对象的循环引用问题就解决了，`B`对象正常生成后，`A`也就执行`populateBean`下一步`initializeBean`了。
到此，spring的循环依赖问题就解决完了。

### 循环依赖的全过程流程图
![环形引用流程图](/images/circular-flowchart.jpg)

### spring无法解决的循环依赖方式：构造器注入
前面我们讲解了spring如何解决循环依赖及原理，那是不是spring可以解决一切循环依赖呢？答案当然是错的。先看下面一个demo：
```
@Component
class A {

    private B b;
    
    @Autowire
    public A(B b) {
        this.b = b;
    }
}

@Component
class B {
    
    private A a;
    
    @Autowire
    public B(A a) {
        this.a = a;
    }
}
```
这里在创建A实例过程中，也就是`createBeanInstance`时，spring发现构造方法上有`@Autowire`注解，那么会先执行注入的bean初始化，如上面
在生成`A`实例时，发现`A`上有依赖`B`，那么会执行`B`的依赖注入，在`getBean(b)`时，发现`B`仍未初始化，那么执行`B`的初始化，当发现`B`
有依赖`A`且spring的`earlySingletonObjects`中并没有`A`的引用，就会产生循环依赖报错。

