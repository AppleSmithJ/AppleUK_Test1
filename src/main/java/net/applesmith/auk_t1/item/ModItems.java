package net.applesmith.auk_t1.item;

import net.applesmith.auk_t1.AUKTest1;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

public static final Item GOLD_P_ITEM = registerItem("gold_p_item", new Item(new Item.Settings()));
public static final Item SILVER_P_ITEM = registerItem("silver_p_item", new Item(new Item.Settings()));

    private static Item registerItem (String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(AUKTest1.MOD_ID, name), item);
    }

    private static void customItems (FabricItemGroupEntries entries) {

    }

    public static void registerModItems () {
        AUKTest1.LOGGER.info("Registering Mod Items For "+AUKTest1.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::customItems);
    }


}

