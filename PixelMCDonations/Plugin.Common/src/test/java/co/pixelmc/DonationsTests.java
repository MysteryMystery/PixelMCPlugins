package co.pixelmc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import co.pixelmc.datastorage.ConfigProvider;
import co.pixelmc.exceptions.InvalidPerkException;
import co.pixelmc.logic.DonationsLogic;
import co.pixelmc.logic.IDonationsLogic;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

public class DonationsTests {
    private final ConfigProvider configProvider = new ConfigProvider("testConfig");
    private final Config appConfig = configProvider.provide("appConfig.json", "config/PixelMCDonations.json");
    private final Logger logger = Logger.getLogger("testsLogger");

    private final IDonationsLogic logic = new DonationsLogic(appConfig, logger, configProvider);

    @Test
    void hasCorrectTestResources(){
        String resourceName = "config/PixelMCDonations.json";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        logger.info(file.getAbsolutePath());

        assertTrue(file.exists());
    }

    @Test
    void addDonation_shouldAdd10Dollars(){
        UUID uuid = UUID.randomUUID();
        //logic.addDonation(uuid, "fly", 10.0);

        Double result;
        try(FileConfig config = configProvider.provide("storage/" + uuid.toString())){
            result = config.get("donationAmount");
        }

        assertEquals(result, 10.0);
    }
}
