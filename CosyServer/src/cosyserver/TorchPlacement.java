package cosyserver;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

//ability to place torch by shift + right-clicking with pickaxe in hand
public class TorchPlacement implements Listener 
{	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void PickAxeRightClick(PlayerInteractEvent event)
	{
		Block block = event.getClickedBlock();
		BlockFace blockface = event.getBlockFace();
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack iteminhand = player.getItemInHand();
		ItemStack item1 = new ItemStack(Material.WOOD_PICKAXE);
		ItemStack item2 = new ItemStack(Material.STONE_PICKAXE);
		ItemStack item3 = new ItemStack(Material.IRON_PICKAXE);
		ItemStack item4 = new ItemStack(Material.GOLD_PICKAXE);
		ItemStack item5 = new ItemStack(Material.DIAMOND_PICKAXE);
		Inventory inv = player.getInventory();
		if( action.equals(Action.RIGHT_CLICK_BLOCK) &&
			(item5.equals(iteminhand) || item4.equals(iteminhand) || item3.equals(iteminhand) || 
			item2.equals(iteminhand) ||	item1.equals(iteminhand)) &&
			inv.contains(Material.TORCH, 1) && !block.isLiquid() &&	player.isSneaking()
			)
		{
			if(blockface.equals(BlockFace.UP))
			{
				Location loc = new Location(player.getWorld(), block.getX(), block.getY()+1, block.getZ());
				player.getWorld().spawnFallingBlock(loc, Material.TORCH, (byte) 0);
				RemoveOne(inv, Material.TORCH);
			} else if(blockface.equals(BlockFace.EAST))
			{
				Location loc = new Location(player.getWorld(), block.getX()+1, block.getY(), block.getZ());
				player.getWorld().spawnFallingBlock(loc, Material.TORCH, (byte) 0);
				RemoveOne(inv, Material.TORCH);
			} else if(blockface.equals(BlockFace.NORTH))
			{
				Location loc = new Location(player.getWorld(), block.getX(), block.getY(), block.getZ()-1);
				player.getWorld().spawnFallingBlock(loc, Material.TORCH, (byte) 0);
				RemoveOne(inv, Material.TORCH);
			} else if(blockface.equals(BlockFace.WEST))
			{
				Location loc = new Location(player.getWorld(), block.getX()-1, block.getY(), block.getZ());
				player.getWorld().spawnFallingBlock(loc, Material.TORCH, (byte) 0);
				RemoveOne(inv, Material.TORCH);
			}  else if(blockface.equals(BlockFace.SOUTH))
			{
				Location loc = new Location(player.getWorld(), block.getX(), block.getY(), block.getZ()+1);
				player.getWorld().spawnFallingBlock(loc, Material.TORCH, (byte) 0);
				RemoveOne(inv, Material.TORCH);
			}  
		}
	}
	
	public void RemoveOne(Inventory inv, Material material)
	{
		int slot = inv.first(material);
		int amount = inv.getItem(slot).getAmount();
		if(amount == 1)
			inv.setItem(slot, new ItemStack(Material.AIR));
		else
			inv.setItem(slot, new ItemStack(Material.TORCH,amount-1));		
	}
	
}
