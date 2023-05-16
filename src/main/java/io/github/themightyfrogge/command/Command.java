package io.github.themightyfrogge.command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public abstract class Command {

    /** The commands main handle (I.E. it's name) */
    private final String handle;

    /** Other usable handles for a command */
    private final List<String> aliases = new ArrayList<>();

    /** A command's required execution permission */
    @Nullable private final String permission;

    /** The amount of allowed parameters for a command */
    private final int max_arguments;

    /** All the sub-commands that the parent needs to run correctly */
    private final List<Method> subCommands;

    /** The type of user who's allowed to execute the command */
    private final CommandExecutorType allowedExecutor;
    
    /** Bukkit's CommandExecutor interface */
    private final CommandExecutor executor;

    /** Sender, for access in sub-commands. */
    private CommandSender sender;
    /** Parameters (I.E. Arguments) for access in sub-commands. */
    private String[] parameters;

    /**
     * Contructs a basic command.
     * @param handle (I.E. the name of the command.)
     * @param permission (The permission required to run the command, which can be null.)
     * @param max_arguments (The maximum amount of usable arguments.)
     * @param allowedExecutor (The type of intended user.)
     */
    public Command(String handle, String permission, CommandExecutorType allowedExecutor) {
        this.handle = handle;
        this.permission = permission;
        this.max_arguments = 0; // Not implemented, for now...
        this.allowedExecutor = allowedExecutor;
        this.subCommands = new ArrayList<>();

        this.executor = ((sender, command, label, params) -> {
            if(!CommandExecutorTest.performExecutorTest(sender, this)) return false; // If test is failed, we immediately stop the command execution.
            if(!(params.length > 0)) { // If command is sent without arguments we invoke execution().
                execution();
                return true;
            }
            if(!executeSubCommand(handle)) // Else we try to execute the correct sub-command, if the sub-command isn't found, we send the user an error. 
                sender.sendMessage("It appears that the sub-command you're looking for doesn't exist!"); 
            return true;
        });
    }

    /**
     * Executes a sub-command if found.
     * @param handle (The sub-command handle)
     * @return Whether the correct sub-command was found or not.
     */
    public boolean executeSubCommand(String handle) {
        if(getSubCommand(handle) == null || handle == null) return false;
        getSubCommand(handle).setAccessible(true);
        try {
            getSubCommand(handle).invoke(CommandManager.getInstance().getCommand(getHandle()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param handle (The sub-command handle)
     * @return a method that's annotated with {@link SubCommand}
     */
    public Method getSubCommand(String handle) {
        for(Method method : getSubCommands()) {
            SubCommand annotation = method.getAnnotation(SubCommand.class);
            if(annotation.handle().equalsIgnoreCase(handle)) return method;
        }
        return null;
    }

    /**
     * This is where the main command logic happens.
     */
    public abstract void execution();

}
