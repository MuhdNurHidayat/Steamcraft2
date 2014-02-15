package common.steamcraft.mod.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import common.steamcraft.mod.common.block.tile.TileEntityGenerator;
import common.steamcraft.mod.common.block.tile.container.ContainerGenerator;
import common.steamcraft.mod.common.lib.LibInfo;

public class GuiGenerator extends GuiContainer
{
	private static final ResourceLocation guitexture = new ResourceLocation(LibInfo.SC2_PREFIX + "textures/gui/coalgenerator.png");
	private TileEntityGenerator furnaceInventory;

	public GuiGenerator(InventoryPlayer player, TileEntityGenerator tile)
	{
		super(new ContainerGenerator(player, tile));
		furnaceInventory = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRenderer.drawString("Inventory", 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		mc.renderEngine.bindTexture(guitexture);
		int var5 = (width - xSize) / 2;
		int var6 = (height - ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, xSize, ySize);

		if (furnaceInventory.isBurning())
		{
			int burnTime = furnaceInventory.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(var5 + 80, var6 + 50 - burnTime, 176, 12 - burnTime, 14, burnTime + 2);
		}
		if(furnaceInventory.hasEnergy())
		{
			int var8 = furnaceInventory.getEnergyScaled(310);
			this.drawTexturedModalRect(var5 + 32, var6 + 49 - var8, 176, 54 - var8, 16, var8 + 1);
		}
		
	}

}
