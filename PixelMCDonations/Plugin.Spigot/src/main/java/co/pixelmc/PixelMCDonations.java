package co.pixelmc;

import co.pixelmc.commands.PixelMCDonationsCommandExecutor;
import co.pixelmc.commands.RanksCommandExecutor;
import co.pixelmc.datastorage.ConfigProvider;
import co.pixelmc.logic.DonationsLogic;
import com.electronwill.nightconfig.core.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class PixelMCDonations extends JavaPlugin implements IPixelMCDonationsPlugin{
    private static PixelMCDonations _instance;
    private Config appConfig;
    private DonationsLogic donationsLogic;

    private final String rootConfigPath = "./plugins/pixelmcdonations/";
    private final ConfigProvider configProvider = new ConfigProvider(rootConfigPath);

    @Override
    public void onEnable() {
        _instance = this;

        appConfig = configProvider.provide(rootConfigPath + "PixelMCDonations.json", "/config/PixelMCDonations.json");
        donationsLogic = new DonationsLogic(getAppConfig(), getLogger(), getConfigProvider());

        this.getCommand("pixelmcdonations").setExecutor(new PixelMCDonationsCommandExecutor(this, donationsLogic));
        this.getCommand("ranks").setExecutor(new RanksCommandExecutor(this, donationsLogic));

        getLogger().info(this.getName() + " has started successfully.");
    }

    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    }

    public static PixelMCDonations getInstance() {
        return _instance;
    }

    public DonationsLogic getDonationsLogic() {
        return donationsLogic;
    }

    public ConfigProvider getConfigProvider() {
        return configProvider;
    }

    public Config getAppConfig() {
        return appConfig;
    }
}