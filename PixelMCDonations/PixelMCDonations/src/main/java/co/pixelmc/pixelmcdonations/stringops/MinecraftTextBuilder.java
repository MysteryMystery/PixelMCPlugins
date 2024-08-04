package co.pixelmc.pixelmcdonations.stringops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinecraftTextBuilder {
    private List<MinecraftTextBuilderLine> lines = new ArrayList<>();
    private PlaceHolderParser placeHolderParser = new PlaceHolderParser();

    public PlaceHolderParser getPlaceHolderParser() {
        return placeHolderParser;
    }

    public MinecraftTextBuilder withParser(PlaceHolderParser parser){
        placeHolderParser = parser;
        return this;
    }

    public MinecraftTextBuilder addLine(MinecraftTextBuilderLine ... entries){
        lines.addAll(Arrays.stream(entries).toList());
        return this;
    }

    public String[] getLines(){
        return lines
                .stream()
                .map(x -> placeHolderParser.parse(x.toString()))
                .toArray(String[]::new);
    }
}
