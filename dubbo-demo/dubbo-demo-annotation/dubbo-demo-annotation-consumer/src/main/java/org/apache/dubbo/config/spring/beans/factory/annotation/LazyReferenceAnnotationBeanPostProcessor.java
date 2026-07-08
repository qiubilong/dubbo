package org.apache.dubbo.config.spring.beans.factory.annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.dubbo.config.spring.ReferenceBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;


/**
 * 懒加载版本的 ReferenceAnnotationBeanPostProcessor
 *
 * 关键：放在同包下，才能访问 package-private 的 ReferenceBeanBuilder
 * 构建 ReferenceBean 时不触发 refer，只在代理方法首次调用时才 refer
 */
public class LazyReferenceAnnotationBeanPostProcessor
        extends ReferenceAnnotationBeanPostProcessor {

    // 父类 applicationContext 是 private，子类自己持有
    private ApplicationContext applicationContext;

    // 父类 referenceBeanCache 是 private，自维护缓存
    private final ConcurrentMap<String, ReferenceBean<?>> referenceBeanCache = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        super.setApplicationContext(applicationContext);
        this.applicationContext = applicationContext;
    }

    @Override
    protected Object doGetInjectedBean(AnnotationAttributes attributes, Object bean, String beanName,
            Class<?> injectedType,
            InjectionMetadata.InjectedElement injectedElement) throws Exception {

        String cacheKey = buildCacheKey(attributes, injectedType);

        // 同包 → 可直接调用 ReferenceBeanBuilder.create()
        // build() 内部会走 afterPropertiesSet()，默认 init=false，不会触发 refer
        ReferenceBean<?> referenceBean = referenceBeanCache.computeIfAbsent(cacheKey, key -> {
            try {
                return ReferenceBeanBuilder.create(attributes, applicationContext)
                        .interfaceClass(injectedType)
                        .build();
            } catch (Exception e) {
                throw new RuntimeException("Failed to build ReferenceBean for " + injectedType.getName(), e);
            }
        });

        // 返回懒加载代理，不调用 referenceBean.get()
        return Proxy.newProxyInstance(
                injectedType.getClassLoader(),
                new Class[]{injectedType},
                new LazyReferenceInvocationHandler(referenceBean)
        );
    }

    private String buildCacheKey(AnnotationAttributes attributes, Class<?> injectedType) {
        return injectedType.getName() + "#" + attributes;
    }

    /**
     * 懒加载代理：第一次方法调用时才触发 referenceBean.get() → init() → refer()
     */
    private static class LazyReferenceInvocationHandler implements InvocationHandler {

        private final ReferenceBean<?> referenceBean;
        private volatile Object target;

        LazyReferenceInvocationHandler(ReferenceBean<?> referenceBean) {
            this.referenceBean = referenceBean;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (target == null) {
                synchronized (this) {
                    if (target == null) {
                        // 此刻才触发 get() → init() → createProxy() → REF_PROTOCOL.refer()
                        target = referenceBean.get();
                    }
                }
            }
            return method.invoke(target, args);
        }
    }
}
