package net.applesmith.auk_t1.item.custom;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FireworkExplosionComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.command.EffectCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.ceil;
import static java.lang.Math.random;

public class ChainsawItem extends Item {
    public ChainsawItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        ClientWorld clientWorld = (ClientWorld) context.getWorld();
        PlayerEntity user = context.getPlayer();

        if (!world.isClient()) {
            if (world.getBlockState(context.getBlockPos()).isIn(BlockTags.LOGS)) {
                world.breakBlock(context.getBlockPos(), true, context.getPlayer());

                context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()), item ->
                        Objects.requireNonNull(context.getPlayer()).sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));
            }
        }

        if (clientWorld.isClient) {
            if (clientWorld.getBlockState(context.getBlockPos()).isIn((RegistryEntryList<Block>) Blocks.IRON_BLOCK)) {
                clientWorld.addFireworkParticle(context.getBlockPos().getX(), context.getBlockPos().getY(), context.getBlockPos().getZ(), 0, 1, 0,
                        List.of(new FireworkExplosionComponent(
                                FireworkExplosionComponent.Type.STAR,
                                IntList.of(0xFF0000),        // Red explosion color
                                IntList.of(0xFFFF00),        // Yellow fade color
                                true,                        // hasTrail
                                true                         // hasTwinkle
                        ))
                );
            }
        }


        return super.useOnBlock(context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        var userVel = user.getVelocity().multiply(5, 6, 9);

//        user.setHealth(200);
  user.setVelocity(0,3,0);


        if (!(entity instanceof PlayerEntity)) {
            if (!user.getWorld().isClient && entity.isAlive()) {
                entity.damage(entity.getRecentDamageSource(), 7.5f);
                entity.setVelocity(0,3,0);
            }
            return ActionResult.success(user.getWorld().isClient);
        } else {
            return ActionResult.PASS;
        }


    }

}
