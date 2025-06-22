package io.github.mcengine.papermc.particle.engine;

import io.github.mcengine.api.mcengine.MCEngineApi;
import io.github.mcengine.api.mcengine.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class MCEngineParticlePaperMC extends JavaPlugin {

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

        // Load extensions
        MCEngineApi.loadExtensions(
            this,
            "io.github.mcengine.api.particle.addon.IMCEngineParticleAddOn",
            "addons",
            "AddOn"
            );
        MCEngineApi.loadExtensions(
            this,
            "io.github.mcengine.api.particle.dlc.IMCEngineParticleDLC",
            "dlcs",
            "DLC"
            );

        MCEngineApi.checkUpdate(this, getLogger(), "github", "MCEngine", "particle-engine", getConfig().getString("github.token", "null"));
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {}
}
