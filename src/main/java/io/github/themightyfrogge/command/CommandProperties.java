package io.github.themightyfrogge.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandProperties {

    CommandExecutorType allowedExecutor();
    
    // Not used at all, this is for the user's own purposes,
    // if said user wants to actually change these values and work with it. (Until I get it working as intended)
    String usage() default "/<command>";
    String description() default "No description provided!";
}
