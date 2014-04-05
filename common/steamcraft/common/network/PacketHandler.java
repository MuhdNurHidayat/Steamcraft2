/**
 * This class was created by <MrArcane111> or his SC2 development team. 
 * This class is available as part of the Steamcraft 2 Mod for Minecraft.
 *
 * Steamcraft 2 is open-source and is distributed under the MMPL v1.0 License.
 * (http://www.mod-buildcraft.com/MMPL-1.0.txt)
 * 
 * Steamcraft 2 is based on the original Steamcraft created by Proloe.
 * Steamcraft (c) Proloe 2011
 * (http://www.minecraftforum.net/topic/251532-181-steamcraft-source-code-releasedmlv054wip/)
 * 
 * Some code is derived from PowerCraft created by MightyPork which is registered
 * under the MMPL v1.0.
 * PowerCraft (c) MightyPork 2012
 *
 * File created @ [Feb 15, 2014, 1:57:22 PM]
 */
package common.steamcraft.common.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import common.steamcraft.common.SC2;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * @author MrArcane111
 *
 */
public class PacketHandler implements IPacketHandler {
	/** */
	public static final String SC2_CHANNEL = "SC2_Channel";
	
	/** */
	public static final int PACKET_UPDATE = 10;
	
	/** */
	public static final int PACKET_REQUEST_UPDATE = 20;
	
	/** */
	public static final int PACKET_CLIENT_UPDATE = 30;
	
	/** */
	public static final int PACKET_GUI_INFO = 40;
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		try{
		DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			int packetType = dataStream.read();
			switch(packetType)
			{
				case 10:
					handleUpdatePacket("server", dataStream, player);
				case 20:
					handleUpdatePacket("request", dataStream, player);
				case 30:
					handleUpdatePacket("client", dataStream, player);
				case 40:
					handleGUIInfoPacket(dataStream);
				default:
					
			}
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			}
	/**
	 * @throws IOException 
	 * @param type
	 * @param dataStream
	 * @param player
	 */
	private void handleGUIInfoPacket(DataInputStream dataStream) throws IOException {
		int windowID = dataStream.readByte();
		int barID = dataStream.readShort();
		int content = dataStream.readInt();
		
		net.minecraft.inventory.Container container = Minecraft.getMinecraft().thePlayer.openContainer;
		
		if ((container != null) && (container.windowId == windowID)) {
			container.updateProgressBar(barID, content);
		}
	}
	/**
	 * @throws IOException 
	 * @param type
	 * @param dataStream
	 * @param player
	 */
	private void handleUpdatePacket(String type, DataInputStream dataStream, Player player) throws IOException {
		int x = dataStream.readInt();
		int y = dataStream.readInt();
		int z = dataStream.readInt();
		
		World world = ((EntityPlayer) player).worldObj;
		TileEntity te = world.getBlockTileEntity(x, y, z);
		
		if (te instanceof NetworkTile) {
			NetworkTile netTE = (NetworkTile)te;
			
			if (type == "server") {
				netTE.readPacket(dataStream);
			} else if (type == "request") {
				netTE.sendPacket();
			} else if (type == "client") {
				netTE.readPacketFromClient(dataStream);
			}
	}
}
}