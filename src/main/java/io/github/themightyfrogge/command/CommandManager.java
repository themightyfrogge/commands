package io.github.themightyfrogge.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.themightyfrogge.util.ReflectionUtil;
import lombok.Getter;

/**
 * <b>Use makeInstance() in your onEnable() before using getInstance()!</b>
 * To access anything inside the class, please use getInstance() instead of making a new instance every time you need to use it.
 */
@Getter
public class CommandManager {
    
    /** 
     * A list of all the commands that can be executed.
     */
    private final List<Command> registeredCommands = new ArrayList<>();
    private final JavaPlugin main;

    private static CommandManager instance;

    // I used to use a makeInstance() method... Yeah, I don't know either how that thought came in mind when I already knew what constructors are...
    public CommandManager(JavaPlugin main) {
        if(instance == null) instance = this;
        this.main = main;
    }

    public static CommandManager getInstance() {
        assert(instance != null); // If that happens, it means you haven't used makeInstance().
        return instance;
    }

    /**
     * Finds a registered command from its own handle.
     * @param handle (i.e. the name of the command)
     * @return the command from it's handle
     */
    public Command getCommand(String handle) {
        for(Command command : getRegisteredCommands())
            if(command.getHandle().equalsIgnoreCase(handle)) return command;
        return null;
    }

    /**
     * Finds commands & its sub-command methods and loads them...
     */
    public void loadCommands() {
        getRegisteredCommands().forEach(command -> {

            // register command properies, if found. also add stuff to plugin.yml
            if(command.getClass().isAnnotationPresent(CommandProperties.class)) {
                command.setProperties(command.getClass().getAnnotation(CommandProperties.class));
            }

            command.getSubCommands().addAll(
                ReflectionUtil.getAnnotatedMethods(command.getClass(), SubCommand.class)
            );

            main.getCommand(command.getHandle()).setExecutor(command.getExecutor());
        });
    }
}
