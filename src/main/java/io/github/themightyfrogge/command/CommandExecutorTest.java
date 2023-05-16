package io.github.themightyfrogge.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecutorTest {

    /**
     * @param sender (I.E. command executor)
     * @param command (To get its allowed executor types)
     * @return If the executor is supposed to use the command.
     */
    private static boolean isCorrectExecutor(CommandSender sender, Command command) {
        switch(command.getAllowedExecutor()) {
            case ALL: return true;
            case CONSOLE:
                if(sender instanceof Player) return false;
                return true;
            case PLAYER:
                if(!(sender instanceof Player)) return false;
                return true;
            default: // I'm not exactly sure how that's possible, but sure...
                return false;
        }
    }

    /**
     * @param player (The executing user)
     * @param command (To get its permsission)
     * @return If the player has sufficient permissions to execute a command.
     */
    private static boolean hasCorrectPermission(Player player, Command command) {
        /*
         * Add your own no permission logic here.
         * (No permission message, trigger events, etc...)
         */
        return player.hasPermission(command.getPermission());
    }
    
    public static boolean performExecutorTest(CommandSender sender, Command command) {
        // Check if the user type is correct...
        if(!isCorrectExecutor(sender, command)) return false;

        // If the user has passed first test, and is console, 
        // we stop; We don't want to cast sender to Player and make an  error.
        if(!(sender instanceof Player)) return true;

        // If player doesn't have the sufficient permissions, the test is failed.
        if(!hasCorrectPermission((Player)sender, command)) return false;

        // Else, the player passed. (Congratulations!!!!)
        return true;
    }
}
