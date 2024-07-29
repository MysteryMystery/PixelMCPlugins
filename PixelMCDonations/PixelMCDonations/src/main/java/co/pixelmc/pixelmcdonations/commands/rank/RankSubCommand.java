package co.pixelmc.pixelmcdonations.commands.rank;

import co.pixelmc.pixelmcdonations.commands.AbstractSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RankSubCommand extends AbstractSubCommand {

    public RankSubCommand(){
        addSubcommand(new RankAddSubCommand());
        addSubcommand(new RankListSubCommand());
    }

    @Override
    protected String getLabel() {
        return "rank";
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
