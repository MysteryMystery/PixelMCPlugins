package co.pixelmc.pixelmcdonations;

import co.pixelmc.pixelmcdonations.commands.PixelMCDonationsCommand;
import co.pixelmc.pixelmcdonations.models.config.DatabaseOptions;
import co.pixelmc.pixelmcdonations.repositories.ServerRepository;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PixelMCDonations extends JavaPlugin {

    private FileConfiguration _config;
    private DatabaseOptions _databaseOptions;
    private static PixelMCDonations _instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        _instance = this;

        this.saveDefaultConfig();

        _config = this.getConfig();

        _databaseOptions = new DatabaseOptions(
          this._config.getString("database.connection_string"),
          this._config.getString("database.username"),
          this._config.getString("database.password")
        );

        //registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands(){
        this.getCommand("pixelmcdonations").setExecutor(new PixelMCDonationsCommand());
    }

    public static PixelMCDonations getInstance() {
        return _instance;
    }

    public DatabaseOptions getDatabaseOptions(){
        return _databaseOptions;
    }
}
