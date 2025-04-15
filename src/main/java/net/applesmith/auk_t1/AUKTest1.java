package net.applesmith.auk_t1;

import net.applesmith.auk_t1.block.ModBlocks;
import net.applesmith.auk_t1.item.ModItemGroups;
import net.applesmith.auk_t1.item.ModItems;
import net.applesmith.auk_t1.other.ScheduledAction;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class AUKTest1 implements ModInitializer {
	public static final String MOD_ID = "auk_t1";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Map<UUID, ScheduledAction> ATTACKS = new HashMap<>();

	@Override
	public void onInitialize() {

		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();

		// ScheduledEvent Registration
		{
			ServerTickEvents.END_WORLD_TICK.register(world -> {
				Iterator<Map.Entry<UUID, ScheduledAction>> iter = ATTACKS.entrySet().iterator();

				while (iter.hasNext()) {
					ScheduledAction task = iter.next().getValue();
					boolean finished = task.tick(world);
					if (finished) iter.remove();
				}
			});
		}

	}
}