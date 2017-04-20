package socketing;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;

public class Menu implements Listener
{
	@EventHandler
	public void onClick(PlayerInteractEvent event)
	{
		if(event.getPlayer().getItemInHand().getType().equals(Material.DIAMOND))
		{
			EntityPlayer eplayer = ((CraftPlayer) event.getPlayer()).getHandle();
			PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(eplayer.activeContainer.windowId, "minecraft:chest", new ChatMessage("Helloworld"), event.getPlayer().getOpenInventory().getTopInventory().getSize());
			//InventorySubcontainer menu = new InventorySubcontainer("Добавление нового слота", true, 27, event.getPlayer());
			eplayer.playerConnection.sendPacket(packet);
			eplayer.updateInventory(eplayer.activeContainer);
		}
	}
}
