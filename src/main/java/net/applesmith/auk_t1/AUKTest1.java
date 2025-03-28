package net.applesmith.auk_t1;

import net.applesmith.auk_t1.block.ModBlocks;
import net.applesmith.auk_t1.item.ModItemGroups;
import net.applesmith.auk_t1.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AUKTest1 implements ModInitializer {
	public static final String MOD_ID = "auk_t1";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();

	}
}