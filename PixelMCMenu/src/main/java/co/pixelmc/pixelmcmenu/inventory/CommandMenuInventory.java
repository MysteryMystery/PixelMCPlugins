package co.pixelmc.pixelmcmenu.inventory;

import co.pixelmc.pixelmcmenu.models.CommandMenu;
import co.pixelmc.pixelmcmenu.models.CommandMenuPage;
import co.pixelmc.pixelmcmenu.models.CommandMenuSlot;
import co.pixelmc.pixelmcmenu.placeholders.PlaceholderParser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Optional;

public class CommandMenuInventory implements Listener {
    private Inventory inventory;
    private CommandMenu menu;
    private CommandMenuPage currentPage;
    private int currentPageIndex = 0;

    private HumanEntity entity;


    public CommandMenuInventory(final CommandMenu menu, final HumanEntity entity){
        this.entity = entity;
        this.menu = menu;
        this.currentPage = menu.pages.isEmpty() ? null : menu.pages.get(currentPageIndex);

        if (currentPage != null)
            setInventory(currentPage);
    }

    protected void setInventory(CommandMenuPage page){
        inventory = Bukkit.createInventory(null, page.size, page.title);

        addPaginationButtons(page);

        for (CommandMenuSlot slot : page.items){
            ItemStack stack = newItemStack(slot);
            inventory.setItem(slot.index, stack);
        }
    }

    private void addPaginationButtons(CommandMenuPage page){
        int maxSize = (currentPage.size == null) ? menu.size : currentPage.size;

        CommandMenuSlot slot = new CommandMenuSlot();
        slot.inventoryAction = InventoryAction.PAGE_PREVIOUS;
        slot.lore = Collections.singletonList("Go to page " + Integer.toString((currentPageIndex > 0 ? currentPageIndex - 1 : 0)));
        slot.title = "Previous Page";
        slot.index = maxSize-8;
        slot.displayItem = Material.ARMOR_STAND.name();
        page.addItem(slot);

        CommandMenuSlot next = new CommandMenuSlot();
        next.inventoryAction = InventoryAction.PAGE_NEXT;
        next.lore = Collections.singletonList("Go to page " + Integer.toString((currentPageIndex < menu.pages.size() ? currentPageIndex + 1 : 0)));
        next.title = "Next Page";
        next.index = maxSize;
        next.displayItem = Material.ARMOR_STAND.name();
        page.addItem(next);
    }

    public void open(){
        entity.openInventory(inventory);
    }

    private ItemStack newItemStack(CommandMenuSlot slot){
        ItemStack stack = new ItemStack(Material.valueOf(slot.displayItem));
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(slot.title);
        meta.setLore(slot.lore);
        return stack;
    }

    private void nextPage(){
        if (currentPageIndex < menu.pages.size() - 1){
            currentPageIndex++;
            currentPage = menu.pages.get(currentPageIndex);
        }
    }

    private void previousPage(){
       if (currentPageIndex > 0){
           currentPageIndex--;
           currentPage = menu.pages.get(currentPageIndex);
       }
    }

    private void runCommand(final CommandMenuSlot slot, final Player p){
        PlaceholderParser placeholderParser = PlaceholderParser.builder()
                .withPlayer(p)
                .build();

        String command = placeholderParser.parse(slot.command);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inventory))
            return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir())
            return;

        final Player p = (Player) e.getWhoClicked();

        Optional<CommandMenuSlot> slotOpt = currentPage.items.stream()
                .filter(x -> x.index == e.getRawSlot())
                .findFirst();

        slotOpt.ifPresent(slot -> {
            switch (slot.inventoryAction){
                case COMMAND:
                    runCommand(slot, p);
                    break;
                case PAGE_NEXT:
                    nextPage();
                    open();
                    break;
                case PAGE_PREVIOUS:
                    previousPage();
                    open();
                    break;
            }
        });
    }
}
