package io.github.mcengine.spigotmc.particle.engine;

import io.github.mcengine.api.core.MCEngineCoreApi;
import io.github.mcengine.api.core.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class MCEngineParticleSpigotMC extends JavaPlugin {

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        new Metrics(this, 25756);
        saveDefaultConfig(); // Save config.yml if it doesn't exist

        boolean enabled = getConfig().getBoolean("enable", false);
        if (!enabled) {
            getLogger().warning("Plugin is disabled in config.yml (enable: false). Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        String license = getConfig().getString("licenses.license", "free"); 
        if (!license.equalsIgnoreCase("free")) { 
            getLogger().warning("Plugin is disabled in config.yml.");
            getLogger().warning("Invalid license.");
            getLogger().warning("Check license or use \"free\".");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Load extensions
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.particle.addon.IMCEngineParticleAddOn",
            "addons",
            "AddOn"
            );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.particle.dlc.IMCEngineParticleDLC",
            "dlcs",
            "DLC"
            );

        MCEngineCoreApi.checkUpdate(this, getLogger(), "github", "MCEngine-Engine", "particle", getConfig().getString("github.token", "null"));
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {}
}
