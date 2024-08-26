package co.pixelmc.pixelmcdonations.stringops;

import java.util.*;
import java.util.function.Function;

/***
 * A rule builder to replace placeholders, applied after a fully built string is supplied.
 */
public class PlaceHolderParser {
    private Map<String, String> rules = new HashMap<>();

    public static PlaceHolderParserBuilder builder() {
        return new PlaceHolderParserBuilder();
    }

    public String parse(String string){
        String str = string;
        for (Map.Entry<String, String> rule : rules.entrySet()) {
            str = str.replaceAll(rule.getKey(), rule.getValue());
        }

        return str;
    }

    public static class PlaceHolderParserBuilder {
        private Map<String, String> rules = new HashMap<>();

        public PlaceHolderParserBuilder replaceUUID(UUID uuid){
            rules.put("\\{uuid}", uuid.toString());
            return this;
        }

        public PlaceHolderParserBuilder replacePlayerName(String playerName) {
            rules.put("\\{playerName}", playerName);
            return this;
        }

        public PlaceHolderParserBuilder replaceServerName(String serverName) {
            rules.put("\\{serverName}", serverName);
            return this;
        }

        public PlaceHolderParserBuilder replaceRankName(String rankName) {
            rules.put("\\{rankName}", rankName);
            return this;
        }


        public PlaceHolderParserBuilder replaceDonationAmount(double donationAmount) {
            rules.put("\\{donationAmount}", String.valueOf(donationAmount));
            return this;
        }

        public PlaceHolderParserBuilder replaceString(String placeholder, String replacement) {
            rules.put("\\{" + placeholder + "}", replacement);
            return this;
        }

        public PlaceHolderParser build(){
            PlaceHolderParser p = new PlaceHolderParser();
            p.rules = this.rules;
            return p;
        }
    }
}
