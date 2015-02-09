/**
 * This class was created by BrassGoggledCoders modding team.
 * This class is available as part of the Steamcraft 2 Mod for Minecraft.
 *
 * Steamcraft 2 is open-source and is distributed under the MMPL v1.0 License.
 * (http://www.mod-buildcraft.com/MMPL-1.0.txt)
 *
 * Steamcraft 2 is based on the original Steamcraft Mod created by Proloe.
 * Steamcraft (c) Proloe 2011
 * (http://www.minecraftforum.net/topic/251532-181-steamcraft-source-code-releasedmlv054wip/)
 *
 */
package steamcraft.common.items.modules;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import steamcraft.common.Steamcraft;
import steamcraft.common.lib.ModInfo;
import boilerplate.common.baseclasses.BaseArmorModule;
import boilerplate.steamapi.item.ModuleRegistry;

/**
 * @author warlordjones
 * 
 */
public class ItemReactivePistonPlating extends BaseArmorModule
{
	public ItemReactivePistonPlating()
	{
		super();
		ModuleRegistry.registerModule(this);
		ModuleRegistry.setModuleIncompatibilities(ModuleRegistry.getModule("pistonplating"),
				new String[] { "aqualung" });
		this.setMaxStackSize(1);
		this.setCreativeTab(Steamcraft.tabSC2);
	}

	@Override
	public int getApplicablePiece()
	{
		return 1;
	}

	@Override
	public String getName()
	{
		return "Reactive Piston Plating";
	}

	@Override
	public String getModuleId()
	{
		return "pistonplating";
	}

	@Override
	public boolean applyModuleEffect(World world, EntityPlayer player, ItemStack stack)
	{
		AxisAlignedBB axisalignedbb = null;
		axisalignedbb = player.boundingBox.expand(1.0D, 0.5D, 1.0D);
		List list = world.getEntitiesWithinAABBExcludingEntity(player, axisalignedbb);

		if(list != null)
		{
			for(int i = 0; i < list.size(); ++i)
			{
				Entity entity = (Entity) list.get(i);

				if(!entity.isDead && (entity instanceof EntityLiving))
				{
					entity.setVelocity(-entity.motionX - 0.3F, 0.3F, -entity.motionZ - 0.3F);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public EnumArmorEffectType getArmorEffectType()
	{
		return EnumArmorEffectType.ONTICK;
	}

	@Override
	public int getSteamConsumedOnEffect()
	{
		return 100;
	}

	@Override
	public int getEnergyConsumedOnEffect()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(ModInfo.PREFIX + this.getUnlocalizedName().substring(5));
	}
}
