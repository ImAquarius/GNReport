package io.github.imaquarius.gnreport.commands;

import io.github.imaquarius.gnreport.Report;
import io.github.imaquarius.gnreport.ReportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportCommand implements CommandExecutor, TabExecutor {

    private final ReportManager reportManager;

    public ReportCommand(ReportManager reportManager) {
        this.reportManager = reportManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");

            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            return false;
        }

        String reportedPlayerName = args[0];
        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        Player reportedPlayer = Bukkit.getPlayerExact(reportedPlayerName);
        if (reportedPlayer == null) {
            player.sendMessage("Player not found.");
            return true;
        }

        Report report = new Report(player.getName(), reportedPlayer.getName(), reason, System.currentTimeMillis());
        reportManager.saveReport(report);

        player.sendMessage("Your report has been filed.");

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                //if (!player.getName().equals(sender.getName())) {  <----------Commented for testing purposes
                    suggestions.add(player.getName());
               // }
            }

            return StringUtil.copyPartialMatches(args[0], suggestions, new ArrayList<>());
        }

        return new ArrayList<>();
    }
}