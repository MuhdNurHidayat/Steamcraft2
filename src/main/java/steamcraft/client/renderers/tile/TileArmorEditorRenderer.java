
package steamcraft.client.renderers.tile;

import org.lwjgl.opengl.GL11;

import boilerplate.client.ClientHelper;
import boilerplate.client.renderers.RenderFloatingItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import steamcraft.client.renderers.models.ModelArmorEditor;
import steamcraft.common.lib.ModInfo;
import steamcraft.common.tiles.TileArmorEditor;

/**
 * @author Surseance
 *
 */
public class TileArmorEditorRenderer extends TileEntitySpecialRenderer
{
	private static final ResourceLocation crystal = new ResourceLocation(ModInfo.PREFIX.replace(":", ""), "textures/models/armoreditor.png");
	private final ModelArmorEditor model;

	public TileArmorEditorRenderer()
	{
		this.model = new ModelArmorEditor();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double dx, double dy, double dz, float scale)
	{
		TileArmorEditor tile = (TileArmorEditor) te;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) dx + 0.5F, (float) dy + 1.5F, (float) dz + 0.5F);
		ClientHelper.textureManager().bindTexture(crystal);
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glScalef(1, 0.9F, 1);
		GL11.glTranslatef(0, 0.2F, 0);
		this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		if (tile.inventory[0] != null)
		{
			GL11.glPushMatrix();
			RenderFloatingItem.render(dx + 0.5F, dy + 0.8F, dz + 0.15F, 90, 0, 0, 2F, 2F, 2F, tile.inventory[0]);
			GL11.glPopMatrix();
		}
	}
}
