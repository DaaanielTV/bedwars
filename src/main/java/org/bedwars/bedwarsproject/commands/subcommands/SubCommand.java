package org.bedwars.bedwarsproject.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bedwars.bedwarsproject.commands.parentcommands.ParentCommand;

import java.util.List;

public abstract class SubCommand {
    
    private final ParentCommand parent;
    private final String subCommandName;
    private String permission;
    private String displayInfo;
    private int priority = 0;
    private boolean showInList = true;

    public SubCommand(ParentCommand parent, String name) {
        this.parent = parent;
        this.subCommandName = name;
        this.permission = "bedwars." + name.toLowerCase();
    }

    public abstract boolean execute(String[] args, CommandSender sender);

    public abstract List<String> getTabComplete();

    public boolean canSee(CommandSender sender) {
        return hasPermission(sender);
    }

    public boolean hasPermission(CommandSender sender) {
        return permission == null || sender.hasPermission(permission);
    }

    public String getSubCommandName() {
        return subCommandName;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setDisplayInfo(String displayInfo) {
        this.displayInfo = displayInfo;
    }

    public String getDisplayInfo() {
        return displayInfo;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void showInList(boolean show) {
        this.showInList = show;
    }

    public boolean isShowInList() {
        return showInList;
    }

    public ParentCommand getParent() {
        return parent;
    }
}