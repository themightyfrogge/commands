package io.github.themightyfrogge.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.github.themightyfrogge.command.Command;
import io.github.themightyfrogge.command.SubCommand;

public class ReflectionUtil {
    
    public static List<Method> getAnnotatedMethods(final Class<? extends Command> type) {
        final List<Method> methods = new ArrayList<Method>();
        Class<?> klass = type;
        for (Method method : klass.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(SubCommand.class)) break;
            methods.add(method);
        }
        return methods;
    }

}
