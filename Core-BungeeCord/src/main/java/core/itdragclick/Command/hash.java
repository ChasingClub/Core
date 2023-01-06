package core.itdragclick.Command;

import core.itdragclick.Utils.Utils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import static core.itdragclick.Core.PLname;
import static core.itdragclick.Utils.Utils.noperm;

public class hash extends Command {
    public hash(){
        super("hash","");
    }
    @Override
    public void execute(CommandSender sender, String[] args){
        if(sender.hasPermission("rank.owner")){
            if(args.length == 0){
                // Helps
                sender.sendMessage(PLname + "/hash <message> - Hash the message By SHA-512");
            }else if(args.length == 1){
                // Put all after
                String hash = Utils.HashByItDragClick(args[0]);
                sender.sendMessage(hash);
                String code = Utils.HashByItDragClick("password");
                if (hash.equals(code)) {
                    sender.sendMessage(PLname + "Correct!");
                }
            }
        }else{
            sender.sendMessage(noperm);
        }
    }
}
