/**
 * This class was created by <Surseance> or his SC2 development team. 
 * This class is available as part of the Steamcraft 2 Mod for Minecraft.
 *
 * Steamcraft 2 is open-source and is distributed under the MMPL v1.0 License.
 * (http://www.mod-buildcraft.com/MMPL-1.0.txt)
 *
 * Steamcraft 2 is based on the original Steamcraft Mod created by Proloe.
 * Steamcraft (c) Proloe 2011
 * (http://www.minecraftforum.net/topic/251532-181-steamcraft-source-code-releasedmlv054wip/)
 *
 * File created @ [May 7, 2014, 1:26:41 PM]
 */
package steamcraft.api;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author Surseance (Johnny Eatmon)
 *
 */
public class GasUtils
{
	static World world;
	static int ix, iy, iz;

	/**
	 * GasUtils
	 * 
	 * @param world - the TileGas worldObj
	 * @param x - TileGas xCoord
	 * @param y - TileGas yCoord
	 * @param z - TileGas zCoord
	 */
	public GasUtils(World world, int x, int y, int z)
	{
		this.world = world;

		ix = x;
		iy = y;
		iz = z;
	}

	/**
	 * Determines how high the gas will float before
	 * dissipating.
	 * 
	 * @param height - the height before dissipating
	 * @return - when the block above != Blocks.air
	 */
	public static boolean continueRising(int height)
	{
		boolean result = true;
		int y = 0;

		while ((result) && (y < height));
		{
			result = isOffsetAir(0, 1 + y, 0);
			y++;
		}

		return result;
	}

	/**
	 * Plays the fizz sound and spawns various smoke particles
	 * around the general area. Woo.
	 * 
	 * @param particleAmount - the particle spawn amount
	 */
	public static void gasSFX(int particleAmount)
	{
		world.playSoundEffect(ix + 0.5D, iy + 0.5D, iz + 0.5D, "random.fizz", 1.0F, (world.rand.nextFloat() * 0.4F) + 0.8F);

		for (int amount = 0; amount < particleAmount; amount++)
		{
			double gaussian = world.rand.nextGaussian() - 0.5D;
			world.spawnParticle("smoke", ix - gaussian, iy - gaussian, iz - gaussian, 0, 0, 0);
		}
	}

	/**
	 * Moves the gas with the given offset. Removes previous
	 * TileEntities and Gas blocks.
	 * 
	 * @param x - xCoord offset
	 * @param y - yCoord offset
	 * @param z - zCoord offset
	 */
	public static void moveGas(int x, int y, int z)
	{
		world.setBlock(ix + x, iy + y, iz + z, TileGas.getGas().getFluid().getBlock(), TileGas.getMeta(), 4);

		TileEntity te = world.getTileEntity(ix + x, iy + y, iz + z);

		if ((te != null) && (te instanceof TileGas))
			((TileGas)te).setGasAmount(TileGas.getGasAmount());

		world.setBlockToAir(ix, iy, iz);
		world.removeTileEntity(ix, iy, iz);
	}

	/**
	 * Partially dissipates the gas and divides the volume and
	 * metadata. Removes previous TileEntities and Gas blocks.
	 * 
	 * @param x - xCoord offset
	 * @param y - yCoord offset
	 * @param z - zCoord offset
	 */
	public static void splitAndMoveGas(int x, int y, int z)
	{
		int volume, metadata;
		world.setBlock(ix + x, iy + y, iz + z, TileGas.getGas().getFluid().getBlock());
		TileEntity te = getOffsetTile(x, y, z);

		if ((te != null) && (te instanceof TileGas))
		{
			((TileGas)te).setGasAmount(TileGas.getGasAmount() / 2);
			volume = ((TileGas)te).getGasAmount();
			metadata = (volume * 15) / TileGas.VOLUME;

			world.setBlockMetadataWithNotify(ix + x, iy + y, iz + z, metadata, 4);
		}

		TileGas.getGas().amount /= 2;
		volume = TileGas.getGasAmount();
		metadata = (volume * 15) / TileGas.VOLUME;
		world.setBlockMetadataWithNotify(ix, iy, iz, metadata, 4);
	}

	/**
	 * Grabs the block at the given offset using the TileGas'
	 * coordinates.
	 * 
	 * @param x - xCoord offset
	 * @param y - yCoord offset
	 * @param z - zCoord offset
	 * @return - the block at the given offset
	 */
	public static Block getOffsetBlock(int x, int y, int z)
	{
		return world.getBlock(ix + x, iy + x, iz + z);
	}

	/**
	 * Grabs the TileEntity at the given offset using the 
	 * TileGas' coordinates.
	 * 
	 * @param x - xCoord offset
	 * @param y - yCoord offset
	 * @param z - zCoord offset
	 * @return - the TileEntity at the given offset
	 */
	public static TileEntity getOffsetTile(int x, int y, int z)
	{
		return world.getTileEntity(ix + x, iy + y, iz + z);
	}

	/**
	 * Determines whether the block at the given offset is
	 * an air block. The offset is determined by passing
	 * the TileGas' coordinates.
	 * 
	 * @param x - xCoord offset
	 * @param y - yCoord offset
	 * @param z - zCoord offset
	 * @return - true if there is an air block, false if not
	 */
	public static boolean isOffsetAir(int x, int y, int z)
	{
		return (world.getBlock(ix + x, iy + y, iz + z) == Blocks.air) ? true : false;
	}

	/**
	 * Places a block at the given offset. The offset is
	 * determined by passing the TileGas' coordinates.
	 * 
	 * @param x - xCoord offset
	 * @param y - yCoord offset
	 * @param z - zCoord offset
	 * @param block - the block to place
	 */
	private static void setOffsetBlock(int x, int y, int z, Block block)
	{
		world.setBlock(ix + x, iy + y, iz + z, block);
	}

	/**
	 * Uses the TileGas' coordinates to determine whether to
	 * start a fire or not.
	 * 
	 * @param
	 */
	public static void updateForFireCheck()
	{
		if (TileGas.count == 4)
		{
			if (Gas.isFlammable)
			{
				Gas gas = (Gas)getOffsetBlock(0, 0, 0);

				for (int x = ix - 2; x < ix + 2; x++)
				{
					for(int y = iy - 2; y < iy + 2; y++)
					{
						for(int z = iz - 2; z < iz + 2; z++)
						{
							if ((world.getBlock(x, y, z) == Blocks.fire) || (world.getBlock(x, y, z) == Blocks.torch) || (world.getBlock(x, y, z) == Blocks.lava))
								gas.setFire(world, ix, iy, iz);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Determines whether the block at the given offset can
	 * be broken/replaced by the gas.
	 * 
	 * @param x - zCoord offset
	 * @param y - yCoord offset
	 * @param z - zCoord offset
	 * @return - false if it cannot be broken, true if it can
	 */
	public static boolean canGasDestroyBlock(int x, int y, int z)
	{
		Block block = getOffsetBlock(x, y, z);
		
		if ((SteamcraftApi.gasDestructionWhiteList.contains(block)) || !(block.getMaterial().isSolid()) || !(block.getMaterial().isReplaceable()))
			return true;
		
		return false;
	}
}
