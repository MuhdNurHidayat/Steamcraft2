package steamcraft.common.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import steamcraft.common.tiles.TileSawmill;

public class BlockSawmill extends BaseContainerBlock
{
	/*
	 * TODO - Only Works on X Axis - Does not support dark oak/acacia wood. - Model - Should take time to saw - Plank blocks share number of planks & meta.
	 */
	public BlockSawmill(Material mat)
	{
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileSawmill();
	}
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
	{
		int l = MathHelper.floor_double(((living.rotationYaw * 4.0F) / 360.0F) + 0.5D) & 3;

		if(l == 0)
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);

		if(l == 1)
			world.setBlockMetadataWithNotify(x, y, z, 1, 2);

		if(l == 2)
			world.setBlockMetadataWithNotify(x, y, z, 1, 2);

		if(l == 3)
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);

		super.onBlockPlacedBy(world, x, y, z, living, stack);
	}

}
