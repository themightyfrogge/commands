package io.github.themightyfrogge.command.plugincommands;

import io.github.themightyfrogge.command.Command;
import io.github.themightyfrogge.command.CommandExecutorType;
import io.github.themightyfrogge.command.SubCommand;

public class DemoCommand extends Command {

    // might add custom parameters (of any type) and adapters, and the manager will detect them according to your set adapters
    // like:
    /**
     * public void kick(Player player, String reason) {
     *  
     *      // do stuff here
     * 
     * }
     */ 

    public DemoCommand() {
        super("demo", "io.github.themightyfrogge.admin.demo", CommandExecutorType.CONSOLE);
    }

    @Override
    public void execution() {
        getSender().sendMessage("This is a demo for io.github.themightyfrogge's command manager!");
    }
    
    @SubCommand(handle = "info")
    public void info() {
        getSender().sendMessage(
            String.format(
                "Command info:\n" +
                " - Handle: %s\n" +
                " - Permission: %s\n" + 
                " - Allowed Executor Type: %s",
                getHandle(),
                getPermission(),
                getAllowedExecutor().toString().toUpperCase()
            )
        );

        getSender().sendMessage(" - Sub-commands:");
        getSubCommands().forEach(subCommand -> {
            getSender().sendMessage(" o /" + getHandle() + subCommand.getAnnotation(SubCommand.class).handle()); 
            // Hacky way, I know. I store the sub-command methods, so you'd have to get the annotation to get it's name as shown...
        });
    }

}
