package me.ezerror.shuttle.runner;

import me.ezerror.shuttle.aware.ShuttleAdviceContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;

import static me.ezerror.shuttle.aware.ShuttleAdviceContainer.proxiedMethodList;

@Component
@Order(2)
public class ProxiedMethodCollectRunner implements ApplicationRunner {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
        Resource[] resources = resolver.getResources("classpath*:**/**/*.class");

        for (Resource r : resources) {
            MetadataReader reader = metaReader.getMetadataReader(r);
            final String className = reader.getClassMetadata().getClassName();
            final Method[] methods;
            try {
                if (ShuttleAdviceContainer.isClassInvoked(className)) {
                    final Class<?> currentClass = Class.forName(className);
                    methods = currentClass.getDeclaredMethods();
                    for (Method method : methods) {
                        if (ShuttleAdviceContainer.isMethodInvoked(method) ) {
                            proxiedMethodList.add(method);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
