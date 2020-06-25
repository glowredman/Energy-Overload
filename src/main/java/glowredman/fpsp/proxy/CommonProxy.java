package glowredman.fpsp.proxy;

import glowredman.fpsp.Reference;
import glowredman.fpsp.block.TileCorner;
import glowredman.fpsp.block.TileStraight;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void preInit() {
		ForgeRegistries.BLOCKS.register(Reference.CORNER);
		ForgeRegistries.BLOCKS.register(Reference.STRAIGHT);
		GameRegistry.registerTileEntity(TileCorner.class, Reference.MODID + ":superconductor_corner");
		GameRegistry.registerTileEntity(TileStraight.class, Reference.MODID + ":superconductor_straight");
		ForgeRegistries.ITEMS.register(new ItemBlock(Reference.CORNER).setRegistryName(Reference.CORNER.getRegistryName()));
		ForgeRegistries.ITEMS.register(new ItemBlock(Reference.STRAIGHT).setRegistryName(Reference.STRAIGHT.getRegistryName()));
	}

}
