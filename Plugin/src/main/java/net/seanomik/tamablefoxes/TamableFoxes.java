package net.seanomik.tamablefoxes;

import net.seanomik.tamablefoxes.util.NMSInterface;
import net.seanomik.tamablefoxes.util.Utils;
import net.seanomik.tamablefoxes.util.io.Config;
import net.seanomik.tamablefoxes.util.io.sqlite.SQLiteHelper;

import net.seanomik.tamablefoxes.util.io.LanguageConfig;

import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Level;
import net.seanomik.tamablefoxes.versions.version_26_3_R1.NMSInterface_26_3_R1;


public final class TamableFoxes extends JavaPlugin implements Listener {
    private static TamableFoxes plugin;

    private boolean versionSupported = true;

    public NMSInterface nmsInterface;
    private PlayerInteractEntityEventListener playerInteractEntityEventListener;

    private boolean equalOrBetween(double num, double min, double max) {
        return num >= min && num <= max;
    }

    @Override
    public void onLoad() {
        plugin = this;
        Utils.tamableFoxesPlugin = this;

        Config.setConfig(this.getConfig());
        LanguageConfig.getConfig(this).saveDefault();

        // Verify server version
        // FOX: 仅支持 26.3
        switch (Bukkit.getMinecraftVersion()) {
            case "26.3" -> nmsInterface = new NMSInterface_26_3_R1();

            default -> {
                Bukkit.getServer().getConsoleSender().sendMessage(Config.getPrefix() + ChatColor.RED + LanguageConfig.getUnsupportedMCVersionRegister());
                Bukkit.getServer().getConsoleSender().sendMessage(Config.getPrefix() + ChatColor.RED + "You're trying to run MC version " + Bukkit.getMinecraftVersion() + " which is not supported!");
                Bukkit.getServer().getConsoleSender().sendMessage(Config.getPrefix() + "Disabling plugin...");
                versionSupported = false;

                Bukkit.getPluginManager().disablePlugin(this);
            }
        }

        if (versionSupported) {
            // Display starting message then register entity.
            Bukkit.getServer().getConsoleSender().sendMessage(Config.getPrefix() + ChatColor.YELLOW + LanguageConfig.getMCVersionLoading(Bukkit.getMinecraftVersion()));
            nmsInterface.registerCustomFoxEntity();

            if (Config.getMaxPlayerFoxTames() != 0) {
                SQLiteHelper.getInstance(this).createTablesIfNotExist();
            }
        }
    }

    @Override
    public void onEnable() {
        if (!versionSupported) {
            Bukkit.getServer().getConsoleSender().sendMessage(Config.getPrefix() + ChatColor.RED + LanguageConfig.getUnsupportedMCVersionDisable());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        playerInteractEntityEventListener = new PlayerInteractEntityEventListener(this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(playerInteractEntityEventListener, this);
        this.getCommand("spawntamablefox").setExecutor(new CommandSpawnTamableFox(this));
        this.getCommand("tamablefoxes").setExecutor(new CommandTamableFoxes(this));
        this.getCommand("givefox").setExecutor(new CommandGiveFox(this, playerInteractEntityEventListener));

        this.saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + getFile());
        }

        File outFile = new File(getDataFolder(), resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(getDataFolder(), resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            }
            // Ignore could not save because it already exists.
            /* else {
                getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }*/
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(Config.getPrefix() + ChatColor.YELLOW + LanguageConfig.getSavingFoxMessage());
    }

    public static TamableFoxes getPlugin() {
        return plugin;
    }
}
