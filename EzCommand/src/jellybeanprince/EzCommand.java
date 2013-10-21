package jellybeanprince;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EzCommand extends JavaPlugin {
	public static final Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender player, Command cmd, String label,
			String[] args) {
		Player p = (Player) player;
		if (label.equalsIgnoreCase("fly")) {
			if (args.length == 0) {
				if (p.hasPermission("ezcommand.fly")) {
					p.setAllowFlight(!p.getAllowFlight());
					if (p.getAllowFlight() == true) {
						p.sendMessage(ChatColor.YELLOW + "You are now flying!");
					}else{
						p.sendMessage(ChatColor.YELLOW + "You are no longer flying!");
					}
				} else {
					p.sendMessage("You do not have permission!");
				}
			} else if (args.length == 1) {
				if (p.hasPermission("ezcommand.fly.other")) {
					if (p.getServer().getPlayer(args[0]) != null) {
						Player t = p.getServer().getPlayer(args[0]);
						t.setAllowFlight(!p.getAllowFlight());
						if (p.getAllowFlight() == true) {
							t.sendMessage(ChatColor.YELLOW + p.getName()
									+ " has gifted you the power of "
									+ ChatColor.BOLD + ChatColor.GOLD
									+ "FLIGHT!");
						} else {
							t.sendMessage(ChatColor.GOLD
									+ "Your flight powers have been revoked!");
						}
					} else {
						p.sendMessage(ChatColor.RED
								+ "That player is not online.");
					}
				} else {
					p.sendMessage("You do not have permission!");
				}

			}
		} else if (label.equalsIgnoreCase("heal")) {
			if (args.length == 0) {
				if (p.hasPermission("ezcommand.heal")) {
					p.setHealth(20.0);
					p.setFireTicks(0);
					p.sendMessage(ChatColor.YELLOW + "You have been healed!");
				} else {
					p.sendMessage("You do not have permission!");
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("*")) {
					if (p.hasPermission("ezcommand.heal.all")) {
						List<Player> ops = Arrays.asList(Bukkit.getServer()
								.getOnlinePlayers());
						Iterator<Player> i = ops.iterator();
						Bukkit.broadcastMessage(ChatColor.GOLD + p.getName()
								+ " has healed everyone!");
						while (i.hasNext()) {
							Player op = i.next();
							op.setHealth(20.0);
						}
					}
				} else {
					if (p.hasPermission("ezcommand.heal.other")) {
						if (p.getServer().getPlayer(args[0]) != null) {
							Player t = p.getServer().getPlayer(args[0]);
							t.setHealth(20.0);
							t.setFireTicks(0);
							t.sendMessage(ChatColor.GOLD + p.getName()
									+ " has healed you!");
						} else {
							p.sendMessage(ChatColor.RED
									+ "That player is not online.");
						}
					} else {
						p.sendMessage("You do not have permission!");
					}
				}
			}
		} else if (label.equalsIgnoreCase("slay")) {
			if (args.length == 0) {
				slay(p);
				p.sendMessage(ChatColor.GOLD + "You killed... yourself?");
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("*")) {
					if (p.hasPermission("ezcommand.slay.all")) {
						List<Player> ops = Arrays.asList(Bukkit.getServer()
								.getOnlinePlayers());
						Iterator<Player> i = ops.iterator();
						Bukkit.broadcastMessage(ChatColor.RED + p.getName()
								+ " has slain everyone!");
						while (i.hasNext()) {
							Player op = i.next();
							slay(op);
						}
					}
				} else {
					if (p.hasPermission("ezcommand.slay.other")) {
						if (p.getServer().getPlayer(args[0]) != null) {
							Player t = p.getServer().getPlayer(args[0]);
							slay(t);
							t.sendMessage(ChatColor.RED + p.getName()
									+ " has slain you.");
							p.sendMessage(ChatColor.GOLD + "You have slain "
									+ t.getName() + ".");
						} else {
							p.sendMessage(ChatColor.RED
									+ "That player is not online.");
						}
					}
				}
			}
		}
		return false;
	}

	public void interact(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType().getId() == 63
					|| e.getClickedBlock().getType().getId() == 68) {
				Block b = e.getClickedBlock();
				Sign s = (Sign) b;
				if (s.getLine(0).equalsIgnoreCase("[Trash Bin]")) {
					if (p.hasPermission("ezcommand.sign.trash")) {
						p.openInventory(Bukkit.createInventory(p, 54,
								"Trash Bin"));
					} else {
						p.sendMessage(ChatColor.RED
								+ "You don't have permission!");
					}
				} else if (s.getLine(0).equalsIgnoreCase("[Heal]")) {
					p.setHealth(20.0);
					p.setFoodLevel(20);
				}
			}
		}
	}

	public void slay(Player t) {
		t.setHealth(0.0);
	}

}
