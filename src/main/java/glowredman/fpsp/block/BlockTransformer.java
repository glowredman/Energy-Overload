package glowredman.fpsp.block;

import java.util.List;

import glowredman.fpsp.Reference;
import glowredman.fpsp.tile.TileTransformer;
import ic2.api.energy.EnergyNet;
import ic2.core.IC2;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockTransformer extends Block implements ITileEntityProvider {
	
	public int outputTier;
	public final long in;
	public final long out;
	public static final PropertyEnum<EnumFacing> INPUT = PropertyEnum.create("input", EnumFacing.class);

	public BlockTransformer(int outputTier) {
		super(Material.IRON);
		this.outputTier = outputTier;
		this.in = (long) EnergyNet.instance.getPowerFromTier(outputTier + 1);
		this.out = (long) EnergyNet.instance.getPowerFromTier(outputTier);
		this.setCreativeTab(IC2.tabIC2);
		this.setHardness(5);
		this.setHarvestLevel("pickaxe", 2);
		this.setRegistryName("transformer." + outputTier);
		this.setResistance(6);
		this.setUnlocalizedName(Reference.MODID + ".transformer." + outputTier);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(INPUT, BlockStraight.getFacingFromEntity(pos, placer)));
	}
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		super.onBlockClicked(worldIn, pos, playerIn);
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof TileTransformer) {
			playerIn.sendMessage(new TextComponentString("Current Energy stored: " + ((TileTransformer) te).buffer + " EU"));
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("Input: " + in + " EU/t");
		tooltip.add("Output: " + out + " EU/t");
		tooltip.add(TextFormatting.ITALIC + "Can convert from/to FE");
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, INPUT);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		switch (state.getValue(INPUT)) {
		case NORTH:
			return 0;
		case EAST:
			return 1;
		case SOUTH:
			return 2;
		case WEST:
			return 3;
		case UP:
			return 4;
		case DOWN:
			return 5;
		default:
			return 0;
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		switch (meta) {
		case 0:
			return this.getDefaultState().withProperty(INPUT, EnumFacing.NORTH);
		case 1:
			return this.getDefaultState().withProperty(INPUT, EnumFacing.EAST);
		case 2:
			return this.getDefaultState().withProperty(INPUT, EnumFacing.SOUTH);
		case 3:
			return this.getDefaultState().withProperty(INPUT, EnumFacing.WEST);
		case 4:
			return this.getDefaultState().withProperty(INPUT, EnumFacing.UP);
		case 5:
			return this.getDefaultState().withProperty(INPUT, EnumFacing.DOWN);
		default:
			return this.getDefaultState().withProperty(INPUT, EnumFacing.NORTH);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileTransformer(outputTier);
	}

}
