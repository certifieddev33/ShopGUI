package com.gmail.certifieddev33.ShopGUI;

import java.math.BigDecimal;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import com.earth2me.essentials.Essentials;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.ess3.api.*;

public class ShopGUI extends JavaPlugin implements Listener {
	//TODO implement banker and mage GUI
	Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	@Override
	public void onDisable() {
		
	}
	@EventHandler
	public void onRightClick(PlayerInteractEntityEvent event) {
		boolean isCitizensNPC = event.getRightClicked().hasMetadata("NPC");
		if(isCitizensNPC) {
			NPC npc = CitizensAPI.getNPCRegistry().getNPC(event.getRightClicked());
			NPC blacksmith = CitizensAPI.getNPCRegistry().getById(85);
			NPC banker = CitizensAPI.getNPCRegistry().getById(75);
			NPC mage = CitizensAPI.getNPCRegistry().getById(230);
			if(event.getRightClicked().equals(blacksmith.getEntity())) {
				createSmithInventory(event.getPlayer());
			}else if(event.getRightClicked().equals(banker.getEntity())) {
				createBankerInventory(event.getPlayer());
			}else if(event.getRightClicked().equals(mage.getEntity())) {
				createMageInventory(event.getPlayer());
			}
			//for testing purposes
			if(npc.getName().equals("Blacksmith")) {
				createSmithInventory(event.getPlayer());
			}else if(npc.getName().equals("Banker")) {
				createBankerInventory(event.getPlayer());
			}else if(npc.getName().equals("Mage")) {
				createMageInventory(event.getPlayer());
			}
		}
	}
	public void createMageInventory(Player player) {
		Inventory mageShop = Bukkit.createInventory(player, 27, "MageShop");
		ItemStack potion = new ItemStack(Material.POTION);
		//PotionEffect potionEffect = new PotionEffect(PotionEffectType.HEAL, 0, 0);
		PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
		potionMeta.addCustomEffect(PotionEffectType.HEAL.createEffect(1, 5), true);
		potion.setItemMeta(potionMeta);
		mageShop.setItem(13, potion);
		player.openInventory(mageShop);
	}
	public void createSmithInventory(Player player) {
		Inventory smithShop = Bukkit.createInventory(player, 54, "SmithShop");
		ItemStack chippedSword = new ItemStack(Material.STONE_SWORD);
		ItemMeta meta = chippedSword.getItemMeta();
		meta.setLore(Arrays.asList(ChatColor.GRAY + "There are multiple chips taken out of this sword."));
		meta.setDisplayName(ChatColor.GRAY + "Chipped Sword");
		chippedSword.setItemMeta(meta);
		chippedSword.setDurability((short) 170);
		ItemStack splintBow = new ItemStack(Material.BOW);
		ItemMeta bowMeta = splintBow.getItemMeta();
		bowMeta.setLore(Arrays.asList(ChatColor.GRAY + "This bow is definitely second hand."));
		bowMeta.setDisplayName(ChatColor.GRAY + "Splintered Bow");
		splintBow.setDurability((short) 160);
		splintBow.setItemMeta(bowMeta);
		ItemStack splintStaff = new ItemStack(Material.STICK);
		ItemMeta staffMeta = splintStaff.getItemMeta();
		staffMeta.setLore(Arrays.asList(ChatColor.GRAY + "This has been used before, definitely been used before."));
		staffMeta.setDisplayName(ChatColor.GRAY + "Splintered Staff");
		staffMeta.setUnbreakable(true);
		splintStaff.setItemMeta(staffMeta);
		ItemStack chippedDagger = new ItemStack(Material.STONE_SWORD);
		ItemMeta dagMeta = chippedDagger.getItemMeta();
		dagMeta.setLore(Arrays.asList(ChatColor.GRAY + "This has chunks taken out of it."));
		dagMeta.setDisplayName(ChatColor.GRAY + "Chipped Dagger");
		chippedDagger.setDurability((short) 390);
		chippedDagger.setItemMeta(dagMeta);
		smithShop.setItem(10, chippedSword);
		smithShop.setItem(12, chippedSword);
		smithShop.setItem(14, chippedSword);
		smithShop.setItem(16, chippedSword);
		player.openInventory(smithShop);
	}
	public void createBankerInventory(Player player) {
		Inventory bank = Bukkit.createInventory(player, 27, "Bank"); 
		ItemStack crystal = new ItemStack(Material.PRISMARINE_CRYSTALS);
		ItemMeta crystalMeta = crystal.getItemMeta();
		crystalMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Alteria");
		crystal.setItemMeta(crystalMeta);
		bank.setItem(13, crystal);
		player.openInventory(bank);
	}
	@EventHandler
	public void onClick(InventoryClickEvent event) throws MaxMoneyException {
		Player player = (Player) event.getWhoClicked();
		if(event.getClickedInventory().getName().equals("SmithShop")) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Chipped Sword") ||
					event.getCurrentItem().getItemMeta().getDisplayName().equals("Splintered Bow") ||
					event.getCurrentItem().getItemMeta().getDisplayName().equals("Splintered Staff") ||
					event.getCurrentItem().getItemMeta().getDisplayName().equals("Chipped Dagger")) {
					if(ess.getUser(player).getMoney().compareTo(new BigDecimal(25))>-1){
						ess.getUser(player).takeMoney(new BigDecimal(25));
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You just bought a " + ChatColor.GRAY + event.getCurrentItem().getItemMeta().getDisplayName() + "!");
					}else {
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have insufficient funds.");
					}
			}
		}else if(event.getClickedInventory().getName().equals("Bank")) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Alteria")) {
				for(ItemStack item : player.getInventory().getContents()) {
					if(item.equals(new ItemStack(Material.PRISMARINE_CRYSTALS))) {
						ess.getUser(player).setMoney(ess.getUser(player).getMoney().add(new BigDecimal(5)));
						player.getInventory().remove(item);
						break;
					}
				}
			}
		}
		player.updateInventory();
	}
	@EventHandler 
	public void onDrag(InventoryDragEvent event) {
		Player player = (Player) event.getWhoClicked();
		if(event.getInventory()!=player.getInventory()) {
			event.setCancelled(true);
		}
		player.updateInventory();
	}
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if(event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) && event.getEntityType().equals(EntityType.PLAYER)) {
			Player player = (Player) event.getEntity();
			if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Chipped Sowrd")) {
				event.setDamage(5);
			}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Splintered Bow")) {
				event.setDamage(7);
			}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Chipped Sowrd")) {
				event.setDamage(0);
			}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Chipped Sowrd")) {
				event.setDamage(4);
			}
		}
	}
}
