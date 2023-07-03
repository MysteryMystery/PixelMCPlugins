package co.pixelmc.pixelmcmenu.models;

import co.pixelmc.pixelmcmenu.inventory.InventoryAction;

import java.util.List;

public class CommandMenuSlot {
    public String title;
    public String displayItem;
    public List<String> lore;
    public int index;
    public String command;
    public InventoryAction inventoryAction = InventoryAction.COMMAND;
}
