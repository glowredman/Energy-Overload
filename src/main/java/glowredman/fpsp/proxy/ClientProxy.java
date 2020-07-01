package glowredman.fpsp.proxy;

import glowredman.fpsp.Reference;
import glowredman.fpsp.block.BlockTransformer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit() {
		super.preInit();
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Reference.CORNER), 0, new ModelResourceLocation(Reference.MODID + ":superconductor_corner", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Reference.STRAIGHT), 0, new ModelResourceLocation(Reference.MODID + ":superconductor_straight", "inventory"));
		
		for(BlockTransformer block : Reference.transformerBlocks) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
		}
	}

}
