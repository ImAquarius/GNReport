package io.github.imaquarius.gnreport;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ReportManager {
    private final JavaPlugin plugin;
    private final File reportFile;
    private final FileConfiguration reportConfig;

    public ReportManager(JavaPlugin plugin){
        this.plugin = plugin;
        reportFile = new File(plugin.getDataFolder(), "reports.yml");
        if(!reportFile.exists()) {
            try{
                boolean fileCreated = reportFile.createNewFile();
                if (!fileCreated){
                    plugin.getLogger().warning("Failed to create new file, it may already exist: " + reportFile.getPath());
                }
            } catch(IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to create new file", e);
            }
        }
        reportConfig = YamlConfiguration.loadConfiguration(reportFile);
    }

    public void saveReport(Report report){
        reportConfig.set("reports." + report.getId() + ".reporter", report.getReporter());
        reportConfig.set("reports." + report.getId() + ".reported", report.getReported());
        reportConfig.set("reports." + report.getId() + ".reason", report.getReason());
        reportConfig.set("reports." + report.getId() + ".timestamp", report.getTimestamp());

        save();
    }

    private void save() {
        try {
            reportConfig.save(reportFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save reports.yml", e);
        }
    }

    public void clearAllReports() {
        reportConfig.set("reports", new ArrayList<>()); // Set reports to an empty list
        save(); // Method that saves the config to file
    }

    public List<Report> getReports() {
        List<Report> reportsList = new ArrayList<>();
        ConfigurationSection reportsSection = reportConfig.getConfigurationSection("reports");
        if (reportsSection != null) {
            for (String id : reportsSection.getKeys(false)) {
                String reporter = reportsSection.getString(id + ".reporter");
                String reported = reportsSection.getString(id + ".reported");
                String reason = reportsSection.getString(id + ".reason");
                long timestamp = reportsSection.getLong(id + ".timestamp");
                Report report = new Report(reporter, reported, reason, timestamp, id);
                reportsList.add(report);
            }
        }
        return reportsList;
    }
}