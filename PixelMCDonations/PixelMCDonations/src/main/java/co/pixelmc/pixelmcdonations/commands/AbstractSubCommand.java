package co.pixelmc.pixelmcdonations.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;

public abstract class AbstractSubCommand implements CommandExecutor {

    private HashMap<String, AbstractSubCommand> subcommands = new HashMap<>();

    public void addSubcommand(AbstractSubCommand command){
        subcommands.put(command.getLabel(), command);
    }

    protected String getPermission(){
        return null;
    }

    protected abstract String getLabel();

    protected boolean validate(CommandSender sender){
        return getPermission() == null || sender.hasPermission(getPermission());
    }

    public abstract boolean execute(CommandSender sender, Command command, String label, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(args.length > 0 && subcommands.containsKey(args[0])){
            AbstractSubCommand subCommand = subcommands.get(args[0]);

            return subCommand.validate(sender) &&
                    subCommand.onCommand(
                        sender,
                        command,
                        args[0],
                        Arrays.stream(args).skip(1).toArray(String[]::new)
                );
        }

        return execute(sender, command, label, args);
    }
}
