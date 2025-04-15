package net.applesmith.auk_t1.other;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;

import java.lang.invoke.TypeDescriptor;

public class ScheduledAction {
    public final PlayerEntity user;
    public final LivingEntity target;
    public int ticksRemaining;
    public final int originalDelay;
    public ItemUsageContext context;


    public ScheduledAction(PlayerEntity user, LivingEntity target, ItemUsageContext context, int delayTicks) {
        this.user = user;
        this.target = target;
        this.ticksRemaining = delayTicks;
        this.originalDelay = delayTicks;
        this.context = context;
    }
    

    public boolean tick(ServerWorld world) {
        if (!user.isAlive() || !target.isAlive()) return true;

        ticksRemaining--;
        if (ticksRemaining <= 0) {
            target.damage(target.getRecentDamageSource(), 7);
            ticksRemaining = originalDelay;



            if (!target.isAlive()) {
                user.setVelocity(0, -5, 0); // Fall if entity dies
                return true; // remove task
            }
        }

        return false; // keep task
    }
}
