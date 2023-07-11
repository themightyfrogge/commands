package io.github.themightyfrogge.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommand {

    /**
     * @return The sub-command's handle (I.E. its name).
     */
    String handle();

    /**
     * Not that useful, can be grabbed though.
     * 
     * Example: /command <string> <int> (What the player/console will see)
     * @return The sub-command's syntax.
     */
    String syntax() default "";
}
