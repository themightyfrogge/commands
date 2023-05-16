package io.github.themightyfrogge.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @deprecated 
 * It has no use, yet.
 * <p><b>Use {@link Command}'s constructor to add properties.</b>
 */
@Deprecated
@Target(ElementType.TYPE)
public @interface CommandProperties {
    CommandExecutorType executor();
}
