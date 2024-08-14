package GamerMJay;

import dev.waterdog.waterdogpe.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Main extends Plugin {

    private Properties broadcastConfig;

    @Override
    public void onEnable() {
        loadBroadcastConfig();
        this.getProxy().getCommandMap().registerCommand(new BroadcastCommand(this));
    }

    public Properties getBroadcastConfig() {
        return broadcastConfig;
    }

    private void loadBroadcastConfig() {
        broadcastConfig = new Properties();
        File configFile = new File(this.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            saveDefaultBroadcastConfig(configFile);
        }

        try (FileInputStream fis = new FileInputStream(configFile)) {
            broadcastConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDefaultBroadcastConfig(File configFile) {
        broadcastConfig.setProperty("prefix", "&b&lProxyBroadcast &7");
        broadcastConfig.setProperty("no-permission", "&cYou do not have permission.");
        broadcastConfig.setProperty("usage", "&7Use &b/pbc <message>");
        broadcastConfig.setProperty("success", "&7The broadcast message was &asuccessfully &7sent to all servers&7.");

        try {
            this.getDataFolder().mkdirs();
            try (OutputStream output = new FileOutputStream(configFile)) {
                broadcastConfig.store(output, "Broadcast Command Configuration");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
