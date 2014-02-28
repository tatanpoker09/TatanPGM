package cl.eilers.tatanpoker09.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;

import cl.eilers.tatanpoker09.map.MapLoader;

public class Handler_serverstop extends Thread {
 
    public void run() {
        for(World world : Bukkit.getServer().getWorlds()){
        		MapLoader.deleteWorld(world.getName());
        }
    }
}