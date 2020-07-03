package glowredman.fpsp.tile;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import glowredman.fpsp.block.BlockTransformer;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.energy.IEnergyStorage;

public class TileTransformer extends TileEntity implements ITickable, IEnergySink, IEnergySource, IEnergyStorage, IEnergyProvider, IEnergyReceiver {
	
	private boolean loaded;
	public int outputTier;
	public double maxEnergy;
	public double buffer;
	
	public TileTransformer(int outputTier) {
		maxEnergy = EnergyNet.instance.getPowerFromTier(outputTier);
		this.outputTier = outputTier;
	}
	
	public EnumFacing getFacing() {
		return world.getBlockState(pos).getValue(BlockTransformer.INPUT);
	}

	//Forge
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int delta = (int) Math.min(maxReceive, (maxEnergy - buffer) * 4);
		if(!simulate) buffer += delta / 4;
		return delta;
	}

	//Forge
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int delta = (int) Math.min(maxExtract, buffer * 4);
		if(!simulate) buffer -= delta / 4;
		return delta;
	}

	//Forge
	@Override
	public int getEnergyStored() {
		return (int) Math.min(Integer.MAX_VALUE, buffer * 4);
	}

	//Forge
	@Override
	public int getMaxEnergyStored() {
		return (int) Math.min(Integer.MAX_VALUE, maxEnergy * 4);
	}

	//Forge
	@Override
	public boolean canExtract() {
		return buffer > 0;
	}

	//Forge
	@Override
	public boolean canReceive() {
		return maxEnergy - buffer > 0;
	}

	//IC2
	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing side) {
		return side == world.getBlockState(pos).getValue(BlockTransformer.INPUT);
	}

	//IC2
	@Override
	public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
		return side != world.getBlockState(pos).getValue(BlockTransformer.INPUT);
	}

	//IC2
	@Override
	public double getOfferedEnergy() {
		return buffer;
	}

	//IC2
	@Override
	public void drawEnergy(double amount) {
		buffer -= amount;
	}

	//IC2
	@Override
	public int getSourceTier() {
		return outputTier;
	}

	//IC2
	@Override
	public double getDemandedEnergy() {
		return maxEnergy - buffer;
	}

	//IC2
	@Override
	public int getSinkTier() {
		return outputTier + 1;
	}

	//IC2
	@Override
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
		double delta = Math.min(amount, maxEnergy - buffer);
		buffer += delta;
		return amount - delta;
	}

	//RF
	@Override
	public int getEnergyStored(EnumFacing arg0) {
		return getFacing() == arg0 ? 0 : getEnergyStored();
	}

	//RF
	@Override
	public int getMaxEnergyStored(EnumFacing arg0) {
		return getFacing() == arg0 ? 0 : getMaxEnergyStored();
	}

	//RF
	@Override
	public boolean canConnectEnergy(EnumFacing arg0) {
		return true;
	}

	//RF
	@Override
	public int receiveEnergy(EnumFacing arg0, int arg1, boolean arg2) {
		return getFacing() == arg0 ? receiveEnergy(arg1, arg2) : 0;
	}

	//RF
	@Override
	public int extractEnergy(EnumFacing arg0, int arg1, boolean arg2) {
		return getFacing() == arg0 ? 0 : extractEnergy(arg1, arg2);
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		onChunkUnload();
	}
	
	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		if(loaded) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			loaded = false;
		}
	}

	@Override
	public void update() {
		if(!loaded && !world.isRemote) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			loaded = true;
		}
	}

}
