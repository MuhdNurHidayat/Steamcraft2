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
package steamcraft.client.renderers.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import steamcraft.common.lib.LibInfo;
import boilerplate.client.renderers.RenderFloatingItem;

// TODO: Auto-generated Javadoc
/**
 * The Class TileCrystalRenderer.
 *
 * @author Surseance (Johnny Eatmon)
 */
public class TileChargerRenderer extends TileEntitySpecialRenderer
{

	/** The model. */
	private final ModelCharger model;
	private RenderFloatingItem itemRenderer;

	/**
	 * Instantiates a new tile crystal renderer.
	 */
	public TileChargerRenderer()
	{
		model = new ModelCharger();
		this.itemRenderer = new RenderFloatingItem(false, false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer#
	 * renderTileEntityAt(net.minecraft.tileentity.TileEntity, double, double,
	 * double, float)
	 */
	@Override
	public void renderTileEntityAt(final TileEntity te, final double dx, final double dy, final double dz, final float scale)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) dx + 0.5F, (float) dy + 1.5F, (float) dz + 0.5F);
		final ResourceLocation crystal = (new ResourceLocation(LibInfo.PREFIX.replace(":", ""), "textures/models/charger.png"));
		Minecraft.getMinecraft().renderEngine.bindTexture(crystal);
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		EntityItem itemToRender = new EntityItem(te.getWorldObj(), dx, dy + 1, dz, new ItemStack(Items.apple));
		itemRenderer.render(itemToRender,(float) dx, (float)dy+7, (float)dz);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}