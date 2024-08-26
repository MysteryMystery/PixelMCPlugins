package co.pixelmc.pixelmcdonations.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public abstract class AbstractSubCommand implements CommandExecutor {

    private HashMap<String, AbstractSubCommand> subcommands = new HashMap<>();
    private AbstractSubCommand parent;

    public void setParent(AbstractSubCommand parent) {
        this.parent = parent;
    }

    public AbstractSubCommand getParent() {
        return parent;
    }

    public void addSubcommand(AbstractSubCommand command){
        command.setParent(this);
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

    public String[] getHelpText(){
        StringBuilder baseCommandBuilder = new StringBuilder();

        AbstractSubCommand parent = getParent();
        while(parent != null){
            baseCommandBuilder.append(parent.getLabel()).append(" ");
            parent = parent.getParent();
        }

        String baseCommand = baseCommandBuilder.toString();

        List<String> commands = new ArrayList<>();
        for (Map.Entry<String, AbstractSubCommand> entry : subcommands.entrySet()){
            commands.add(baseCommand + entry.getValue().getLabel());
        }

        return commands.toArray(new String[0]);
    }
}
