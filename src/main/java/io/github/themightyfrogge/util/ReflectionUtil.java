package io.github.themightyfrogge.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.github.themightyfrogge.command.Command;

public class ReflectionUtil {
    
    public static List<Method> getAnnotatedMethods(final Class<? extends Command> type, final Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<Method>();
        for (Method method : type.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation))
                methods.add(method);
        }
        return methods;
    }

}
