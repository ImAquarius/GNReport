package io.github.imaquarius.gnreport;

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
                plugin.getLogger().log(Level.SEVERE, "Could not save reports.yml", e);
            }
        }
        reportConfig = YamlConfiguration.loadConfiguration(reportFile);
    }

    public void saveReport(Report report){
        List<String> reports = reportConfig.getStringList("reports");
        reports.add("Reporter: " + report.getReporter() + ";" + report.getReported() + ";" + report.getReason() + ";" + report.getTimestamp());
        reportConfig.set("reports", reports);
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
        List<String> reportsStr = reportConfig.getStringList("reports");
        List<Report> reports = new ArrayList<>();
        for (String reportStr : reportsStr) {
            String[] parts = reportStr.split(";");
            if (parts.length < 4) continue;
            try {
                long timestamp = Long.parseLong(parts[3]);
                reports.add(new Report(parts[0], parts[1], parts[2], Long.parseLong(parts[3])));
            }catch (NumberFormatException e) {
                plugin.getLogger().log(Level.WARNING, "Failed to parse report timestamp: " + parts[3], e);
            }
        }
        return reports;
    }
}