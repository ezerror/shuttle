package me.ezerror.shuttle.context.mvc.processor;

import me.ezerror.shuttle.context.mvc.configuration.ShuttleRequestMapping;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.Map;
import java.util.Objects;

@Component
public class ShuttleMvcAdviceProcessor implements BeanPostProcessor {

    private static boolean isMapping = false;

    ShuttleRequestMapping config;
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!isMapping && !Objects.equals("shuttleMapping", beanName)) {
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = config.shuttleMapping().getHandlerMethods();
            if (!handlerMethods.isEmpty()) {
                System.out.println(handlerMethods);
                isMapping = !isMapping;
            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }


    @Autowired
    public void setConfig(ShuttleRequestMapping config) {
        this.config = config;
    }
}
