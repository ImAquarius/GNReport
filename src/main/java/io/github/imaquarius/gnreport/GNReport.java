package io.github.imaquarius.gnreport;

import io.github.imaquarius.gnreport.commands.ReportCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class GNReport extends JavaPlugin {

    @Override
    public void onEnable() {
        ReportManager reportManager = new ReportManager(this);
        this.getCommand("report").setExecutor(new ReportCommand(reportManager));
    }

    @Override
    public void onDisable() {

    }
}
