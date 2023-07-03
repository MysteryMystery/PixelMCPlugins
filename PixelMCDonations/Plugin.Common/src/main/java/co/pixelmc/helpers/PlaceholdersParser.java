package co.pixelmc.helpers;

import java.util.HashMap;
import java.util.Map;

public class PlaceholdersParser {
    public enum Keys {
        PLAYER_NAME("{{playerName}}"),
        UUID("{{uuid}}");

        private String placeholder;

        Keys(String placeholder){
            this.placeholder = placeholder;
        }

        public String getPlaceholder() {
            return placeholder;
        }
    }

    private Map<String, String> placeholders = new HashMap<>();

    public PlaceholdersParser put(Keys k, String v){
        placeholders.put(k.getPlaceholder(), v);
        return this;
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }

    public String parse(String toParse){
        String parsed = toParse;
        for (Map.Entry<String, String> entry: getPlaceholders().entrySet()) {
            parsed = parsed.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return parsed;
    }
}
