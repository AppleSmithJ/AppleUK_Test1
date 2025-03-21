package net.applesmith.auk_t1.item;

import net.applesmith.auk_t1.AUKTest1;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static ItemGroup MISC_ITEMS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(AUKTest1.MOD_ID, "misc_items"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.misc_items"))
                    .icon(() -> new ItemStack(ModItems.GOLD_P_ITEM))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.GOLD_P_ITEM);
                        entries.add(ModItems.SILVER_P_ITEM);


                    }).build());

    public static void registerItemGroups() {
        AUKTest1.LOGGER.info("Registering Custom Item Groups For " + AUKTest1.MOD_ID);
    }


}
