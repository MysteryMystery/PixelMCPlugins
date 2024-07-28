package co.pixelmc.pixelmcdonations.commands;

import co.pixelmc.pixelmcdonations.commands.claim.ClaimSubCommand;
import co.pixelmc.pixelmcdonations.commands.donation.DonationSubCommand;
import co.pixelmc.pixelmcdonations.commands.rank.RankSubCommand;
import co.pixelmc.pixelmcdonations.commands.server.ServerSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class PixelMCDonationsCommand extends AbstractSubCommand {

    public PixelMCDonationsCommand(){
        this.addSubcommand(new ServerSubCommand());
        this.addSubcommand(new RankSubCommand());
        this.addSubcommand(new DonationSubCommand());
        this.addSubcommand(new ClaimSubCommand());
    }

    @Override
    protected String getLabel() {
        return "pixelmcdonations";
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}