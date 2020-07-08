package glowredman.fpsp.proxy;

import glowredman.fpsp.Reference;
import glowredman.fpsp.block.BlockTransformer;
import glowredman.fpsp.tile.TileCorner;
import glowredman.fpsp.tile.TileStraight;
import glowredman.fpsp.tile.TileTransformer;
import glowredman.fpsp.tile.transformer.*;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void preInit() {
		//E.A.T.
		ForgeRegistries.BLOCKS.register(Reference.CORNER);
		ForgeRegistries.BLOCKS.register(Reference.STRAIGHT);
		GameRegistry.registerTileEntity(TileCorner.class, Reference.MODID + ":superconductor_corner");
		GameRegistry.registerTileEntity(TileStraight.class, Reference.MODID + ":superconductor_straight");
		ForgeRegistries.ITEMS.register(new ItemBlock(Reference.CORNER).setRegistryName(Reference.CORNER.getRegistryName()));
		ForgeRegistries.ITEMS.register(new ItemBlock(Reference.STRAIGHT).setRegistryName(Reference.STRAIGHT.getRegistryName()));
		
		//Transformer
		registerTransformer(5, TileTransformerT5.class);   //IV
		registerTransformer(6, TileTransformerT6.class);   //LuV
		registerTransformer(7, TileTransformerT7.class);   //ZPM
		registerTransformer(8, TileTransformerT8.class);   //UV
		registerTransformer(9, TileTransformerT9.class);   //UHV
		registerTransformer(10, TileTransformerT10.class); //UEV
		registerTransformer(11, TileTransformerT11.class); //UIV
		registerTransformer(12, TileTransformerT12.class); //OpV
		registerTransformer(13, TileTransformerT13.class); //MAX
	}
	
	private void registerTransformer(int tier, Class<? extends TileTransformer> clazz) {
		BlockTransformer block = new BlockTransformer(tier);
		ForgeRegistries.BLOCKS.register(block);
		GameRegistry.registerTileEntity(clazz, Reference.MODID + ":transformer_" + tier);
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		Reference.transformerBlocks.add(block);
	}

}
