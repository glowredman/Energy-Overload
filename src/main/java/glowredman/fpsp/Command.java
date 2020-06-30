package glowredman.fpsp;

import ic2.api.energy.EnergyNet;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class Command extends CommandBase {
	
	public static final String USAGE = "/fpsp <getPower|getTier> <value>";

	@Override
	public String getName() {
		return "fpsp";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return USAGE;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length > 0) {
			switch (args[0]) {
			case "getPower":
				if(args.length == 2) {
					try {
						sender.sendMessage(new TextComponentString(EnergyNet.instance.getPowerFromTier(Integer.parseInt(args[1])) + " EU/t"));
					} catch (Exception e) {
						sender.sendMessage(new TextComponentString(TextFormatting.RED + "'" + args[1] + "' is not a valid number!"));
					}
					break;
				} else {
					sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: /fpsp getPower <tier>"));
					break;
				}
			case "getTier":
				if(args.length == 2) {
					try {
						sender.sendMessage(new TextComponentString("Tier: " + EnergyNet.instance.getTierFromPower(Double.parseDouble(args[1]))));
					} catch (Exception e) {
						sender.sendMessage(new TextComponentString(TextFormatting.RED + "'" + args[1] + "' is not a valid number!"));
					}
					break;
				} else {
					sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: /fpsp getTier <power>"));
					break;
				}
			default:
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: " + USAGE));
				break;
			}
		} else {
			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: " + USAGE));
		}
	}

}
