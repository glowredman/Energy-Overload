package glowredman.fpsp.tile;

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

public class TileTransformer extends TileEntity implements ITickable, IEnergySink, IEnergySource, IEnergyStorage {
	
	private boolean loaded;
	public int outputTier;
	public double maxEnergy;
	public double buffer;
	
	public TileTransformer(int outputTier) {
		maxEnergy = EnergyNet.instance.getPowerFromTier(outputTier);
		this.outputTier = outputTier;
	}

	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing side) {
		return side == world.getBlockState(pos).getValue(BlockTransformer.INPUT);
	}

	@Override
	public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
		return side != world.getBlockState(pos).getValue(BlockTransformer.INPUT);
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int delta = (int) Math.min(maxReceive, (maxEnergy - buffer) * 4);
		if(!simulate) buffer += delta / 4;
		return delta;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int delta = (int) Math.min(maxExtract, buffer * 4);
		if(!simulate) buffer -= delta / 4;
		return delta;
	}

	@Override
	public int getEnergyStored() {
		return (int) Math.min(Integer.MAX_VALUE, buffer);
	}

	@Override
	public int getMaxEnergyStored() {
		return (int) Math.min(Integer.MAX_VALUE, maxEnergy);
	}

	@Override
	public boolean canExtract() {
		return buffer > 0;
	}

	@Override
	public boolean canReceive() {
		return maxEnergy - buffer > 0;
	}

	@Override
	public double getOfferedEnergy() {
		return buffer;
	}

	@Override
	public void drawEnergy(double amount) {
		buffer -= amount;
	}

	@Override
	public int getSourceTier() {
		return outputTier;
	}

	@Override
	public double getDemandedEnergy() {
		return maxEnergy - buffer;
	}

	@Override
	public int getSinkTier() {
		return outputTier + 1;
	}

	@Override
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
		double delta = Math.min(amount, maxEnergy - buffer);
		buffer += delta;
		return amount - delta;
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
