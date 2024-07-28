package co.pixelmc.pixelmcdonations.commands.donation;

import co.pixelmc.pixelmcdonations.commands.AbstractSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DonationSubCommand extends AbstractSubCommand {

    public DonationSubCommand(){
        addSubcommand(new DonationAddSubCommand());
    }

    @Override
    protected String getLabel() {
        return "donation";
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
