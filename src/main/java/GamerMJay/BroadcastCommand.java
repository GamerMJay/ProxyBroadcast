package GamerMJay;

import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;

public class BroadcastCommand extends Command {

    private final Main plugin;
    private final String prefix;
    private final String noPermissionMessage;
    private final String usageMessage;
    private final String successMessage;

    public BroadcastCommand(Main plugin) {
        super("pbc", CommandSettings.builder()
                .setDescription("Send a broadcast message to all servers")
                .setPermission("broadcast.command")
                .setAliases("pbroadcast")
                .build());
        this.plugin = plugin;
        this.prefix = translateColorCodes(plugin.getBroadcastConfig().getProperty("prefix", "&b&lProxyBroadcast &7"));
        this.noPermissionMessage = translateColorCodes(plugin.getBroadcastConfig().getProperty("no-permission", "&cYou do not have permission."));
        this.usageMessage = translateColorCodes(plugin.getBroadcastConfig().getProperty("usage", "&7Use &b/pbc <message>"));
        this.successMessage = translateColorCodes(plugin.getBroadcastConfig().getProperty("success", "&7The broadcast message was &asuccessfully &7sent to all servers&7."));
    }

    @Override
    public boolean onExecute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission(getPermission())) {
            sender.sendMessage(prefix + noPermissionMessage);
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(prefix + usageMessage);
            return false;
        }

        String subCommand = args[0].toLowerCase();
        if (subCommand.equals("version") || subCommand.equals("about") || subCommand.equals("ver")) {
            String versionMessage = prefix + "§7Plugin by §bGamerMJay\n" +
                    prefix + "§7Website: §bhttps://github.com/GamerMJay";
            sender.sendMessage(versionMessage);
            return true;
        }

        String message = String.join(" ", args);
        ProxyServer.getInstance().getPlayers().values().forEach(player ->
                player.sendMessage(prefix + translateColorCodes(message))
        );
        sender.sendMessage(prefix + successMessage);
        return true;
    }

    private String translateColorCodes(String input) {
        return input.replace("&", "§");
    }
}
