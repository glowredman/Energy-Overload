package glowredman.fpsp.block;

import java.util.List;

import glowredman.fpsp.Reference;
import ic2.core.IC2;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStraight extends Block implements ITileEntityProvider {
	
	public static final PropertyEnum<Axis> AXIS = PropertyEnum.create("axis", Axis.class);

	public BlockStraight() {
		super(Material.GLASS);
		this.setCreativeTab(IC2.tabIC2);
		this.setDefaultState(getDefaultState().withProperty(AXIS, Axis.X));
		this.setHardness(5);
		this.setHarvestLevel("pickaxe", 2);
		this.setRegistryName("superconductor_straight");
		this.setResistance(6);
		this.setUnlocalizedName(Reference.MODID + ".superconductor.straight");
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(AXIS, getFacingFromEntity(pos, placer).getAxis()));
	}
	
	public static EnumFacing getFacingFromEntity(BlockPos pos, EntityLivingBase placer) {
		return EnumFacing.getFacingFromVector((float) (placer.posX - pos.getX()), (float) (placer.posY - pos.getY()), (float) (placer.posZ - pos.getZ()));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = state.getActualState(source, pos);
		switch (state.getValue(AXIS)) {
		case X:
			return new AxisAlignedBB(0, 0.25, 0.25, 1, 0.75, 0.75);
		case Y:
			return new AxisAlignedBB(0.25, 0, 0.25, 0.75, 1, 0.75);
		case Z:
			return new AxisAlignedBB(0.25, 0.25, 0, 0.75, 0.75, 1);
		}
		return new AxisAlignedBB(0, 0, 0, 1, 1, 1);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		switch (meta) {
		case 0:
			return getDefaultState().withProperty(AXIS, Axis.X);
		case 1:
			return getDefaultState().withProperty(AXIS, Axis.Y);
		case 2:
			return getDefaultState().withProperty(AXIS, Axis.Z);
		default:
			return getDefaultState();
		}
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		switch (state.getValue(AXIS)) {
		case X:
			return 0;
		case Y:
			return 1;
		case Z:
			return 2;
		default:
			return 0;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileStraight();
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AXIS);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("Using Electron Acceleration Technology");
		tooltip.add("\u00a79Can transfer over 179 Uncentillion (1.79\u221910\u00b3\u2070\u2078) EU per Tick!");
	}

}
