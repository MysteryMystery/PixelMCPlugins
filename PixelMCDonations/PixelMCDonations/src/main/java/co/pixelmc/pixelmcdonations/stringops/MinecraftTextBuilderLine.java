package co.pixelmc.pixelmcdonations.stringops;

public class MinecraftTextBuilderLine {
    private StringBuilder text = new StringBuilder();

    public MinecraftTextBuilderLine ofColour(MinecraftColour colour){
        text.append(colour.getCode());
        return this;
    }

    public MinecraftTextBuilderLine addString(String str){
        text.append(str);
        return this;
    }

    public static MinecraftTextBuilderLine of(MinecraftColour colour, String str){
        return new MinecraftTextBuilderLine()
                .addString(colour, str);
    }

    public MinecraftTextBuilderLine addString(MinecraftColour colour, String str){
        ofColour(colour);
        addString(str);
        return this;
    }

    @Override
    public String toString(){
        return text.toString();
    }
}
