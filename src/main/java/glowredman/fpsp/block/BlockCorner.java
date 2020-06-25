package glowredman.fpsp.block;

import java.util.List;

import glowredman.fpsp.Reference;
import ic2.core.IC2;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCorner extends Block implements ITileEntityProvider {

	public BlockCorner() {
		super(Material.IRON);
		this.setCreativeTab(IC2.tabIC2);
		this.setHardness(5);
		this.setHarvestLevel("pickaxe", 2);
		this.setRegistryName("superconductor_corner");
		this.setResistance(6);
		this.setUnlocalizedName(Reference.MODID + ".superconductor.corner");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCorner();
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("§9 Can transfer over 179 Uncentillion (1.79\u221910³\u2070\u2078) EU per Tick!");
	}

}
