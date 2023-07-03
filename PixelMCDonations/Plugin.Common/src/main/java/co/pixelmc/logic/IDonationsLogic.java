package co.pixelmc.logic;

import java.util.List;
import java.util.UUID;

public interface IDonationsLogic {
    List<String> claimDonation(UUID playerUuid);

    void addDonation(UUID playerUuid, String perkName, Double donationAmount);

    Double getAmountDonated(UUID uuid);

    List<String> getPurchasedPerks(UUID uuid);

    List<String> getFormattedRanks();
}
