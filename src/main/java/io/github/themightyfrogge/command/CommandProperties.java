package io.github.themightyfrogge.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface CommandProperties {

    CommandExecutorType allowedExecutor();
    
    String usage();
    String description();
}
