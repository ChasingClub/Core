package core.itdragclick.Command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import static core.itdragclick.Core.PLname;

public class discord extends Command{
    public discord(){
        super("discord","","dsc","dis","dc");
    }
    @Override
    public void execute(CommandSender sender, String[] args){
        TextComponent message = new TextComponent(PLname);
        message.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        ComponentBuilder cb = new ComponentBuilder("Discord Link: ").bold(false).color(net.md_5.bungee.api.ChatColor.GRAY).append("dsc.gg/chasingclub").color(net.md_5.bungee.api.ChatColor.BLUE).underlined(true).bold(false);
        TextComponent discord = new TextComponent("Click Here!");
        discord.setColor(ChatColor.AQUA);
        discord.setBold(true);
        discord.setItalic(true);
        discord.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cb.create()));
        discord.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://dsc.gg/chasingclub"));

        message.addExtra(discord);
        sender.sendMessage(message);
    }
}
