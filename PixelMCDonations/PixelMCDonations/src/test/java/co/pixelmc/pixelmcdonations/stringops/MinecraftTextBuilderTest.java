package co.pixelmc.pixelmcdonations.stringops;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MinecraftTextBuilderTest {
    @Test
    void minecraftTextBuilder_should_format_colour(){
        String[] s = new MinecraftTextBuilder()
                .addLine(MinecraftTextBuilderLine.of(MinecraftColour.YELLOW, "hi"))
                .addLine(MinecraftTextBuilderLine.of(MinecraftColour.YELLOW, "bye"))
                .getLines();

        assertTrue(s.length == 2);
        assertEquals(s[0], "§ehi");
        assertEquals(s[1], "§ebye");
    }

    @Test
    void minecraftTextBuilder_should_replace_placeholders(){
        MinecraftTextBuilder b = new MinecraftTextBuilder()
                .addLine(MinecraftTextBuilderLine.of(MinecraftColour.YELLOW, "hi, {playerName}"))
                .withParser(
                    PlaceHolderParser.builder()
                            .replacePlayerName("MysteryMystery")
                            .build()
                );

        String[] result = b.getLines();

        assertTrue(result.length == 1);
        assertEquals(result[0], "§ehi, MysteryMystery");
    }
}
