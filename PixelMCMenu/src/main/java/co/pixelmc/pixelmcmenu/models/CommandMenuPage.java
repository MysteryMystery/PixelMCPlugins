package co.pixelmc.pixelmcmenu.models;

import java.util.List;

public class CommandMenuPage {
    public String title;
    public Integer size;
    public List<CommandMenuSlot> items;

    public void addItem(CommandMenuSlot slot){
        items.add(slot);
    }
}
