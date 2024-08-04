package co.pixelmc.pixelmcdonations.stringops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinecraftTextBuilder {
    private List<MinecraftTextBuilderLine> lines = new ArrayList<>();
    private PlaceHolderStringHandler placeHolderStringHandler = new PlaceHolderStringHandler();

    public PlaceHolderStringHandler getPlaceHolderStringHandler() {
        return placeHolderStringHandler;
    }

    public MinecraftTextBuilder addLine(MinecraftTextBuilderLine ... entries){
        lines.addAll(Arrays.stream(entries).toList());
        return this;
    }

    public String[] getLines(){
        return lines
                .stream()
                .map(x -> placeHolderStringHandler.getString(x.toString()))
                .toArray(String[]::new);
    }
}
