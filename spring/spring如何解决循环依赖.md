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
[`createBean`方法源码解析](/#doCreateBean)
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

### `doCreateBean源码解析` <div id="doCreateBean"></div>
