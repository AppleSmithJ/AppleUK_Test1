package net.applesmith.auk_t1.block;

import net.applesmith.auk_t1.AUKTest1;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block GOLD_P_BLOCK = registerBlock("gold_p_block.json",
            new Block(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.STONE)
                    .strength(0.4f)));

    public static final Block SILVER_P_BLOCK = registerBlock("silver_p_block",
            new Block(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.STONE)
                    .strength(0.25f)));

    public static final Block TEST_BLOCK_01 = registerBlock("test_block_01",
            new Block(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.TRIAL_SPAWNER)
                    .strength(0.25f)));


    private  static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(AUKTest1.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(AUKTest1.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        AUKTest1.LOGGER.info("Registering Mod Blocks for "+ AUKTest1.MOD_ID);
    }
}
