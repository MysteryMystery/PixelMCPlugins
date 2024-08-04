package co.pixelmc.pixelmcdonations.stringops;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/***
 * A rule builder to replace placeholders, applied after a fully built string is supplied.
 */
public class PlaceHolderStringHandler {
    private List<Function<String, String>> rules = new ArrayList<>();

    public PlaceHolderStringHandler replaceUUID(UUID uuid){
        rules.add(s -> s.replaceAll("\\{uuid}", uuid.toString()));
        return this;
    }

    public PlaceHolderStringHandler replacePlayerName(String playerName) {
        rules.add(s -> s.replaceAll("\\{playerName}", playerName));
        return this;
    }

    public PlaceHolderStringHandler replaceServerName(String serverName) {
        rules.add(s -> s.replaceAll("\\{serverName}", serverName));
        return this;
    }

    public PlaceHolderStringHandler replaceRankName(String rankName) {
        rules.add(s -> s.replaceAll("\\{rankName}", rankName));
        return this;
    }


    public PlaceHolderStringHandler replaceDonationAmount(double donationAmount) {
        rules.add(s -> s.replaceAll("\\{donationAmount}", String.valueOf(donationAmount)));
        return this;
    }


    public PlaceHolderStringHandler replaceString(String placeholder, String replacement) {
        rules.add(s -> s.replaceAll("\\{" + placeholder + "}", replacement));
        return this;
    }

    public String getString(String string){
        String str = string;
        for (Function<String, String> rule : rules) {
            str = rule.apply(str);
        }
        return str;
    }
}
