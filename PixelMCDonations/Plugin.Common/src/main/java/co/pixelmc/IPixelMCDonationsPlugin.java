package co.pixelmc;

import co.pixelmc.datastorage.ConfigProvider;
import com.electronwill.nightconfig.core.Config;

import java.util.logging.Logger;

public interface IPixelMCDonationsPlugin {
    //DonationsLogic getDonationsLogic();

    ConfigProvider getConfigProvider();

    Config getAppConfig();

    Logger getLogger();
}
