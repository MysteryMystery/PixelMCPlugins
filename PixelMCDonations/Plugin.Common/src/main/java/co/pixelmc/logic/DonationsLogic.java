package co.pixelmc.logic;

import co.pixelmc.datastorage.ConfigProvider;
import co.pixelmc.exceptions.InvalidPerkException;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DonationsLogic implements IDonationsLogic{
    private final Config CONFIG;
    private final Logger LOGGER;
    private final ConfigProvider CONFIG_PROVIDER;

    public DonationsLogic(Config config, Logger logger, ConfigProvider configProvider){
        this.CONFIG = config;
        this.LOGGER = logger;
        this.CONFIG_PROVIDER = configProvider;
    }

    private FileConfig providePlayerConfig(UUID playerUuid){
        return CONFIG_PROVIDER.provide("storage/" + playerUuid.toString() + ".json", "/config/DefaultPlayerStorage.json");
    }

    /**
     * Apply pending donations for a player based on their UUID.
     *
     * @param playerUuid
     * @return list of unparsed commands to be run by the console
     */
    public List<String> claimDonation(UUID playerUuid){
        LOGGER.info("Providing config for " + playerUuid.toString());

        List<String> unparsedCommands = new ArrayList<>();

        Double amountDonated;
        List<String> claimedRanks;
        FileConfig playerConfig = providePlayerConfig(playerUuid);
        amountDonated = playerConfig.get("donationAmount");
        claimedRanks = playerConfig.get("claimedRanks");

        List<Config> thresholds = CONFIG.get("thresholds");
        for (Config threshold : thresholds){
            String rankName = threshold.get("rankName");

            if ((amountDonated >= threshold.<Double>get("minThreshold")) && !claimedRanks.contains(rankName)){
                LOGGER.info("Granting " + rankName + " to " + playerUuid.toString());
                claimedRanks.add(rankName);
                List<String> commands = threshold.get("commands");

                unparsedCommands.addAll(commands);
            }
        }

        playerConfig.set("claimedRanks", claimedRanks);
        playerConfig.close();

        return unparsedCommands;
    }

    // TODO write serialiser for BigDecimal for money
    /**
     * Add an unclaimed donation to a player
     * @param playerUuid
     * @param donationAmount
     */
    public void addDonation(UUID playerUuid, String perkName, Double donationAmount) {
        LOGGER.info("providing config for " + playerUuid);

        try(FileConfig config = providePlayerConfig(playerUuid)){
            Double d = config.get("donationAmount");
            d += donationAmount;
            config.set("donationAmount", d); //this will autosave (set in provider)

            List<String> purchasedPerks = config.get("purchasedPerks");
            purchasedPerks.add(perkName);
            config.set("purchasedPerks", purchasedPerks);

            LOGGER.info("New donation amount: " + d);
            LOGGER.info("Purchased Perks: " + String.join(", ", purchasedPerks));
        }
    }

    public Double getAmountDonated(UUID uuid){
        try(FileConfig config = providePlayerConfig(uuid)){
            return config.get("donationAmount");
        }
    }

    public List<String> getPurchasedPerks(UUID uuid){
        try(FileConfig config = providePlayerConfig(uuid)){
            return config.get("purchasedPerks");
        }
    }

    public List<String> getFormattedRanks(){
        List<com.electronwill.nightconfig.core.Config> thresholds = CONFIG.get("thresholds");
        return thresholds.stream().map(x -> (String) x.get("displayName")).collect(Collectors.toList());
    }
}
