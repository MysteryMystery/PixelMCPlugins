package co.pixelmc.pixelmcdonations.commands;

import co.pixelmc.pixelmcdonations.commands.claim.ClaimSubCommand;
import co.pixelmc.pixelmcdonations.commands.donation.DonationSubCommand;
import co.pixelmc.pixelmcdonations.commands.rank.RankSubCommand;
import co.pixelmc.pixelmcdonations.commands.server.ServerSubCommand;
import co.pixelmc.pixelmcdonations.stringops.MinecraftColour;
import co.pixelmc.pixelmcdonations.stringops.MinecraftTextBuilder;
import co.pixelmc.pixelmcdonations.stringops.MinecraftTextBuilderLine;
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
        return "pixelmc";
    }

    private String[] getHelpOutput(){
        return new MinecraftTextBuilder()
                .addLine(
                        MinecraftTextBuilderLine.of(MinecraftColour.YELLOW, "/pixelmcdonations server add <server name>"),
                        MinecraftTextBuilderLine.of(MinecraftColour.YELLOW, "/pixelmcdonations server list"),
                        MinecraftTextBuilderLine.of(MinecraftColour.YELLOW, "/pixelmcdonations rank add <name> <server id> <threshold> <command>"),
                        MinecraftTextBuilderLine.of(MinecraftColour.YELLOW, "/pixelmcdonations rank list"),
                        MinecraftTextBuilderLine.of(MinecraftColour.YELLOW, "/pixelmcdonations donation add <uuid> <amount>"),
                        MinecraftTextBuilderLine.of(MinecraftColour.YELLOW, "/pixelmcdonations claim")
                ).getLines();
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(getHelpOutput());
        return false;
    }
}