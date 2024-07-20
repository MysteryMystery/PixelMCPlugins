package co.pixelmc.pixelmcmenu.commands;

import co.pixelmc.pixelmcmenu.PixelMCMenu;
import co.pixelmc.pixelmcmenu.inventory.CommandMenuInventory;
import co.pixelmc.pixelmcmenu.models.CommandMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PixelMCCommand implements CommandExecutor {

    private CommandMenu commandMenu;

    public PixelMCCommand(CommandMenu menu){
        this.commandMenu = menu;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender || !(sender instanceof Player && sender.hasPermission("pixelmcmenu.base")))
            return false;

        CommandMenuInventory inventory = new CommandMenuInventory(commandMenu, (Player) sender);
        inventory.open();

        return true;
    }
}
