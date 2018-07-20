package com.gmail.certifieddev33.ShopGUI;

import java.math.BigDecimal;

import java.util.ArrayList;
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
			/*NPC blacksmith = CitizensAPI.getNPCRegistry().getById(85);
			NPC banker = CitizensAPI.getNPCRegistry().getById(75);
			NPC mage = CitizensAPI.getNPCRegistry().getById(230);
			if(event.getRightClicked().equals(blacksmith.getEntity())) {
				createSmithInventory(event.getPlayer());
			}else if(event.getRightClicked().equals(banker.getEntity())) {
				createBankerInventory(event.getPlayer());
			}else if(event.getRightClicked().equals(mage.getEntity())) {
				createMageInventory(event.getPlayer());
			}*/
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
		PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
		potionMeta.addCustomEffect(PotionEffectType.HEAL.createEffect(1, 5), true);
		potionMeta.setDisplayName(ChatColor.GREEN + "Small Health Potion");
		potion.setItemMeta(potionMeta);
		ItemStack potion2 = new ItemStack(Material.POTION);
		PotionMeta potionMeta2 = (PotionMeta) potion2.getItemMeta();
		potionMeta2.addCustomEffect(PotionEffectType.HEAL.createEffect(1, 10), true);
		potionMeta2.setDisplayName(ChatColor.GREEN + "Medium Health Potion");
		potion2.setItemMeta(potionMeta2);
		mageShop.setItem(13, potion);
		mageShop.setItem(22, potion2);
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
		smithShop.setItem(12, splintBow);
		smithShop.setItem(14, splintStaff);
		smithShop.setItem(16, chippedDagger);
		player.openInventory(smithShop);
	}
	public void createBankerInventory(Player player) {
		Inventory bank = Bukkit.createInventory(player, 27, "Bank"); 
		ItemStack crystal = new ItemStack(Material.PRISMARINE_CRYSTALS);
		ItemMeta crystalMeta = crystal.getItemMeta();
		crystalMeta.setDisplayName(ChatColor.BLUE + "Alteria");
		crystal.setItemMeta(crystalMeta);
		bank.setItem(13, crystal);
		player.openInventory(bank);
	}
	@EventHandler
	public void onClick(InventoryClickEvent event) throws MaxMoneyException {
		Player player = (Player) event.getWhoClicked();
		if(event.getClickedInventory()==null) {
			return;
		}
		if(event.getClickedInventory().equals(player.getInventory())) {
			
		}
		if(event.getClickedInventory().getTitle().equals("SmithShop")) {
			if(event.getSlot()!=10 && event.getSlot()!=12 && event.getSlot()!=14 && event.getSlot()!=16) {
				return;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Chipped Sword") ||
					event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Splintered Bow") ||
					event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Splintered Staff") ||
					event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Chipped Dagger")) {
					if(ess.getUser(player).getMoney().compareTo(new BigDecimal(25))>-1){
						ess.getUser(player).takeMoney(new BigDecimal(25));
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You just bought a " + ChatColor.GRAY + event.getCurrentItem().getItemMeta().getDisplayName() + "!");
						player.getInventory().addItem(event.getCurrentItem());
						event.setCancelled(true);
					}else {
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have insufficient funds.");
						event.setCancelled(true);
					}
			}
		}else if(event.getClickedInventory().getTitle().equals("Bank")) {
			if(event.getSlot()!=13) {
				return;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Alteria")) {
				for(int i = 1; i<=64;) {
					if(player.getInventory().containsAtLeast(new ItemStack(Material.PRISMARINE_CRYSTALS), i)) {
						player.getInventory().removeItem(new ItemStack(Material.PRISMARINE_CRYSTALS, i));
						ess.getUser(player).setMoney(ess.getUser(player).getMoney().add(new BigDecimal(5)));
						player.sendMessage(ChatColor.GREEN + "$5 has been added to your inventory.");
						event.setCancelled(true);
						break;
						}else {
							player.sendMessage(ChatColor.RED + "You do not have any crystals to exchange.");
							event.setCancelled(true);
							break;
						}
				}
			}
		}else if(event.getClickedInventory().getTitle().equals("MageShop")) {
			if(event.getSlot()!=13 && event.getSlot()!=22) {
				return;
			}
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Small Health Potion")) {
				if(ess.getUser(player).getMoney().compareTo(new BigDecimal(25))>-1){
					ess.getUser(player).takeMoney(new BigDecimal(25));
					player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You just bought a " + ChatColor.GRAY + event.getCurrentItem().getItemMeta().getDisplayName() + "!");
					player.getInventory().addItem(event.getCurrentItem());
					event.setCancelled(true);
				}else {
					player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have insufficient funds.");
					event.setCancelled(true);
				}
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Medium Health Potion")) {
				if(ess.getUser(player).getMoney().compareTo(new BigDecimal(50))>-1){
					ess.getUser(player).takeMoney(new BigDecimal(50));
					player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You just bought a " + ChatColor.GRAY + event.getCurrentItem().getItemMeta().getDisplayName() + "!");
					player.getInventory().addItem(event.getCurrentItem());
					event.setCancelled(true);
				}else {
					player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have insufficient funds.");
					event.setCancelled(true);
				}
			}
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
			}else {
				return;
			}
		}
	}
}
