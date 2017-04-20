package firstplugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class PlayerListeners implements Listener
{
	@EventHandler
	public void SmallFBRC(PlayerInteractEvent event)
	{
		Action action = event.getAction();
		Player player = event.getPlayer();
		ItemStack item = new ItemStack(Material.BLAZE_ROD,1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Посох огня");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "Огненный шар I");
		im.setLore(lorelist);
		item.setItemMeta(im);
		if((action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) &&
				item.equals(player.getItemInHand()))
				player.launchProjectile(SmallFireball.class);
	}
	
	@EventHandler
	public void FBRC(PlayerInteractEvent event)
	{
		Action action = event.getAction();
		Player player = event.getPlayer();
		ItemStack item = new ItemStack(Material.BLAZE_ROD,1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Посох огня");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "Огненный шар II");
		im.setLore(lorelist);
		item.setItemMeta(im);
		if((action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) &&
				item.equals(player.getItemInHand()))
				player.launchProjectile(Fireball.class);
	}
	@EventHandler
	public void LargeFBRC(PlayerInteractEvent event)
	{
		Action action = event.getAction();
		Player player = event.getPlayer();
		ItemStack item = new ItemStack(Material.BLAZE_ROD,1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Посох огня");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "Огненный шар III");
		im.setLore(lorelist);
		item.setItemMeta(im);
		if((action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) &&
				item.equals(player.getItemInHand()))
				player.launchProjectile(LargeFireball.class);
	}

	public Arrow FindArrow(Player player)
	{
		Object[] entarray;
		entarray = player.getNearbyEntities(5, 5, 5).toArray();
		int i=0;
		for(i=0; i<entarray.length; i++)
		{
			if( !((Arrow)entarray[i]).equals(null) && 
				((Player)((Arrow)entarray[i]).getShooter()).equals(player) &&
				!((Arrow)entarray[i]).isOnGround())
				{
					return (Arrow)entarray[i];
				}
		}
		return null;
	}
	
	@EventHandler
	public void IronBowShoot(PlayerStatisticIncrementEvent event)
	{
		Player player = event.getPlayer();
		Statistic stat = event.getStatistic();
		ItemStack item = new ItemStack(Material.BOW,1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("Железный лук");
		item.setItemMeta(im);
		if(stat.equals(Statistic.USE_ITEM) && item.equals(player.getItemInHand()))
		{
			Arrow oldarrow = FindArrow(player);
			Arrow newarrow = player.launchProjectile(Arrow.class, oldarrow.getVelocity());
			newarrow.setKnockbackStrength(oldarrow.getKnockbackStrength());
			newarrow.setCritical(oldarrow.isCritical());
		}
	}

	@EventHandler
	public void GoldBowShoot(PlayerStatisticIncrementEvent event)
	{
		Player player = event.getPlayer();
		Statistic stat = event.getStatistic();
		ItemStack item = new ItemStack(Material.BOW,1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.ALL_CODES + "Золотой лук");
		item.setItemMeta(im);
		if(stat.equals(Statistic.USE_ITEM) && item.equals(player.getItemInHand()))
		{
			Arrow oldarrow = FindArrow(player);
			Vector v = oldarrow.getVelocity();
			Vector n = v.normalize();
			Arrow leftarrow = player.launchProjectile(Arrow.class, v.add(n));
			Arrow rightarrow = player.launchProjectile(Arrow.class, v.add(n.multiply(-1)));
			leftarrow.setKnockbackStrength(oldarrow.getKnockbackStrength());
			leftarrow.setCritical(oldarrow.isCritical());
			rightarrow.setKnockbackStrength(oldarrow.getKnockbackStrength());
			rightarrow.setCritical(oldarrow.isCritical());
		}
	}
	
	@EventHandler
	public void EmeraldStringBowShoot(PlayerStatisticIncrementEvent event)
	{
		Player player = event.getPlayer();
		Statistic stat = event.getStatistic();
		ItemStack item = new ItemStack(Material.BOW,1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("Лук с изумрудной струной");
		item.setItemMeta(im);
		if(stat.equals(Statistic.USE_ITEM) && item.equals(player.getItemInHand()))
		{
			Arrow arrow = FindArrow(player);
			arrow.setCritical(true);
			arrow.setVelocity(arrow.getVelocity().multiply(2));
		}
	}
	
	/*@SuppressWarnings("deprecation")
	@EventHandler
	public void TorchPlacement(PlayerInteractEvent event)
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
			item2.equals(iteminhand) ||	item1.equals(iteminhand)) && blockface.equals(BlockFace.UP) &&
			inv.contains(Material.TORCH, 1) && !block.isLiquid() &&	player.isSneaking()
			)
		{
			Location loc = new Location(player.getWorld(), block.getX(), block.getY()+1, block.getZ());
			player.getWorld().spawnFallingBlock(loc, Material.TORCH, (byte) 0);
			RemoveOne(inv, Material.TORCH);
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
		
	}*/
	
	@EventHandler
	public void DoubleJump(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Action action = event.getAction();
		Inventory inv = player.getInventory();
		ItemStack item = new ItemStack(Material.GLASS_BOTTLE);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Облако в пузырьке");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "При падении нажмите правую кнопку мыши,");
		lorelist.add(ChatColor.GRAY + "чтобы подпрыгнуть снова.");
		im.setLore(lorelist);
		item.setItemMeta(im);
		if
		( (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))
				&& inv.contains(item) && player.getFallDistance()!=0)
		{
			Vector velocity = player.getVelocity();
			velocity.setY(0.491);
			player.setVelocity(velocity);
			player.setFallDistance(0);
		}
	}
	
	/*@EventHandler
	public void ShowVelocity(PlayerStatisticIncrementEvent event)
	{
		Player player = event.getPlayer();
		Statistic stat = event.getStatistic();
		Inventory inv = player.getInventory();
		if(stat.equals(Statistic.JUMP) && inv.contains(Material.SUGAR))
		{
			Vector v = player.getVelocity();
			player.sendMessage(Double.toString(v.getY()));
		}
	}*/
	
	@EventHandler
	public void Ench(PrepareItemEnchantEvent event)
	{
		Bukkit.getLogger().info(event.getItem().getType().toString());
	}

}
