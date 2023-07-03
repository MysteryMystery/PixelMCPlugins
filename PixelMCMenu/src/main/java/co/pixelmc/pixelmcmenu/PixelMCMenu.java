package co.pixelmc.pixelmcmenu;

import co.pixelmc.pixelmcmenu.commands.PixelMCCommand;
import co.pixelmc.pixelmcmenu.models.CommandMenu;
import org.bukkit.plugin.java.JavaPlugin;

public final class PixelMCMenu extends JavaPlugin {
    private CommandMenu commandMenu;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        commandMenu = this.getConfig().getObject("", CommandMenu.class);

        this.getCommand("pixelmc").setExecutor(new PixelMCCommand(getCommandMenu()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public CommandMenu getCommandMenu() {
        return commandMenu;
    }
}
