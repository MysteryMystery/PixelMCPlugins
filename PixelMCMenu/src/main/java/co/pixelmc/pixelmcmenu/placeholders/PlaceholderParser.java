package co.pixelmc.pixelmcmenu.placeholders;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlaceholderParser {
    private Map<String, String> placeHolderRules;

    private PlaceholderParser(){

    }

    public static PlaceholderParserBuilder builder(){
        return new PlaceholderParserBuilder();
    }

    public String parse(final String string){
        String result = string;
        for (Map.Entry<String, String> entry: placeHolderRules.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static class PlaceholderParserBuilder {
        private final Map<String, String> placeholderData = new HashMap<>();

        private PlaceholderParserBuilder() {
        }

        public PlaceholderParserBuilder withPlayerName(String s){
            placeholderData.put("{playerName}", s);
            return this;
        }

        public PlaceholderParserBuilder withUUID(UUID u){
            placeholderData.put("{uuid}", u.toString());
            return this;
        }

        public PlaceholderParserBuilder withPlayer(Player p){
            placeholderData.put("{playerName}", p.getName());
            placeholderData.put("{uuid}", p.getUniqueId().toString());
            return this;
        }

        public PlaceholderParserBuilder with(String placeholder, String data){
            placeholderData.put(placeholder, data);
            return this;
        }

        public PlaceholderParser build() {
            PlaceholderParser placeholderParser = new PlaceholderParser();
            placeholderParser.placeHolderRules = placeholderData;
            return placeholderParser;
        }
    }
}
