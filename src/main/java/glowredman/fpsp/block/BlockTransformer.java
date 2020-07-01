package glowredman.fpsp.block;

import java.util.List;

import glowredman.fpsp.Reference;
import glowredman.fpsp.tile.TileTransformer;
import glowredman.fpsp.tile.transformer.*;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
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
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		worldIn.setBlockState(pos, state.withProperty(INPUT, BlockStraight.getFacingFromEntity(pos, placer)));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof TileTransformer) {
			playerIn.sendMessage(new TextComponentString("Current Energy stored: " + ((TileTransformer) te).buffer + " EU"));
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.translateToLocal(Reference.MODID + ".transformer." + outputTier + ".desc"));
		tooltip.add("§6Input: " + in + " EU/t");
		tooltip.add("§6Output: " + out + " EU/t");
		tooltip.add("§a§oCan convert from/to FE");
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
		switch (outputTier) {
		case 5:
			return new TileTransformerT5();
		case 6:
			return new TileTransformerT6();
		case 7:
			return new TileTransformerT7();
		case 8:
			return new TileTransformerT8();
		case 9:
			return new TileTransformerT9();
		case 10:
			return new TileTransformerT10();
		case 11:
			return new TileTransformerT11();
		case 12:
			return new TileTransformerT12();
		case 13:
			return new TileTransformerT13();
		case 14:
			return new TileTransformerT14();
		case 15:
			return new TileTransformerT15();
		case 16:
			return new TileTransformerT16();
		case 17:
			return new TileTransformerT17();
		case 18:
			return new TileTransformerT18();
		default:
			return null;
		}
	}

}
