package net.applesmith.auk_t1.item.custom;

import it.unimi.dsi.fastutil.ints.IntList;
import net.applesmith.auk_t1.other.ScheduledAction;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.FireworkExplosionComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.*;

public class ChainsawItem extends Item {
    public ChainsawItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity user = context.getPlayer();

        // Server-side logic: break logs
        if (!world.isClient()) {
            if (world.getBlockState(context.getBlockPos()).isIn(BlockTags.LOGS)) {
                world.breakBlock(context.getBlockPos(), true, context.getPlayer());

                context.getStack().damage(1, (ServerWorld) world, (ServerPlayerEntity) user,
                        item -> Objects.requireNonNull(user).sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));
            }
        }

        // Client-side logic: firework when clicking iron block
        if (world.isClient) {
            if (world.getBlockState(context.getBlockPos()).isOf(Blocks.IRON_BLOCK)) {

                {
                    world.addFireworkParticle(
                            context.getBlockPos().getX() + 0.5,
                            context.getBlockPos().getY() + 1.0,
                            context.getBlockPos().getZ() + 0.5,
                            0, 0.5, 0,
                            List.of(new FireworkExplosionComponent(
                                    FireworkExplosionComponent.Type.STAR,
                                    IntList.of(0xFF0000),
                                    IntList.of(0xFFFF00),
                                    true,
                                    true
                            ))
                    );

                    List<SoundEvent> sounds = Arrays.asList(new SoundEvent[]{
                            SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH,
                            SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE
                    });

                    SoundEvent randomSound = sounds.get(world.getRandom().nextInt(sounds.size()));

                    world.playSound(
                            context.getPlayer(),
                            context.getBlockPos(),
                            randomSound,
                            SoundCategory.PLAYERS,
                            1.0f,
                            0.8f + world.getRandom().nextFloat() * 0.6f
                    );
                }
                if (user != null) {
                    user.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.NAUSEA, // Effect type
                            300, // Duration in ticks (100 ticks = 5 seconds)
                            2  // Amplifier (1 = normal nausea level)
                    ));
                }
            }
        }

        return super.useOnBlock(context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        user.setVelocity(0,5,0);
        entity.setVelocity(0,5,0);
        entity.damage(entity.getRecentDamageSource(), 7.5f);
        entity.hasPassenger(user);



        if (!entity.isAlive()) {
            user.setVelocity(0,-5,0);
        }


        return ActionResult.success(user.getWorld().isClient);

    }

}
