package co.pixelmc.pixelmcdonations.commands.rank;

import co.pixelmc.pixelmcdonations.PixelMCDonations;
import co.pixelmc.pixelmcdonations.commands.AbstractSubCommand;
import co.pixelmc.pixelmcdonations.repositories.RankRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RankAddSubCommand extends AbstractSubCommand {
    @Override
    protected String getLabel() {
        return "add";
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4){
            return false;
        }

        String rankCommand = Arrays.stream(args).skip(3).collect(Collectors.joining(" "));

        RankRepository repository = new RankRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        boolean success = repository.addRank(args[0], Integer.parseInt(args[1]), Double.parseDouble(args[2]), rankCommand);

        sender.sendMessage(success ? "Success!" : "Failure :(");

        return success;
    }
}
