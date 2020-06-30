package glowredman.fpsp;

import static glowredman.fpsp.Reference.*;

import glowredman.fpsp.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(acceptedMinecraftVersions = MCVERSION, dependencies = DEPENDENCIES, modid = MODID, name = MODNAME, version = MODVERSION)
public class Main {
	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = CLIENT, modId = MODID, serverSide = COMMON)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
	}
	
	@EventHandler
	public static void serverStart(FMLServerStartingEvent event) {
		event.registerServerCommand(new Command());
	}

}
