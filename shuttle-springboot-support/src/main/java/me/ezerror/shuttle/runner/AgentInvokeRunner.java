package me.ezerror.shuttle.runner;

import me.ezerror.shuttle.aware.ShuttleAdviceContainer;
import me.ezerror.shuttle.inter.HookRegister;
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

/**
 * @author ：shiyuan
 * @date ：Created in 2022/5/2 3:03
 * @description：
 * @version:
 */
@Component
@Order(3)
public class AgentInvokeRunner implements ApplicationRunner {

    @Autowired
    private HookRegister hookRegister;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        hookRegister.register(proxiedMethodList);
    }
}
