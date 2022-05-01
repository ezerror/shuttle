package me.ezerror.shuttle.context.mvc;

import com.sun.deploy.util.StringUtils;
import me.ezerror.shuttle.inter.ShuttlePathConverter;
import me.ezerror.shuttle.util.MethodUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

/**
 * @author ：shiyuan
 * @date ：Created in 2022/5/1 1:44
 * @description：
 * @version:
 */
@Component
public class MvcPathConverter implements ShuttlePathConverter {

    @Autowired
    RequestMappingHandlerMapping requestMapping;

    @Override
    public String mappingPath(String path) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            assert entry.getKey().getPatternsCondition() != null;
            String apiPath = StringUtils.join(entry.getKey().getPatternsCondition().getPatterns(), "/");
            String simplePath = MethodUtil.getPathWithSimpleArgs(entry.getValue().getMethod());
            if (path.equals(apiPath)) {
                return simplePath;
            }
        }
        return path;
    }
}
