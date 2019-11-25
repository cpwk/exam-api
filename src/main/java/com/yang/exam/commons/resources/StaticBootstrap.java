package com.yang.exam.commons.resources;


import com.yang.exam.commons.utils.L;
import com.yang.exam.commons.utils.ProcessUtils;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
public class StaticBootstrap {

    @PostConstruct
    public void init() {
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages("com.yang.exam"));
        {
            Set<Class<?>> staticInitClasses = reflections.getTypesAnnotatedWith(StaticInit.class);
            for (Class<?> clazz : staticInitClasses) {
                try {
                    if (L.isInfoEnabled()) {
                        L.info("[StaticBootstrap] calling: " + clazz.getCanonicalName());
                    }
                    Class.forName(clazz.getCanonicalName());
                } catch (Throwable t) {
                    ProcessUtils.exitWithMessage(null, t);
                }
            }
        }
        if (L.isInfoEnabled()) {
            L.info("[StaticBootstrap] done");
        }
    }

}
