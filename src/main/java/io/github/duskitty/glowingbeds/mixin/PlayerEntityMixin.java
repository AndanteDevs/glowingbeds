package io.github.duskitty.glowingbeds.mixin;

import com.mojang.datafixers.util.Either;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow public abstract boolean isCreative();

    public PlayerEntityMixin() {
        super();
    }

    @Inject(method = "trySleep", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;isCreative()Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void getEntity(BlockPos pos, CallbackInfoReturnable<Either> cir) {
        if (this.isCreative() == false){
        List<HostileEntity> list2 = ((PlayerEntity)(Object) this).world.getEntities(HostileEntity.class, new Box((double)pos.getX() - 8.0D, (double)pos.getY() - 5.0D, (double)pos.getZ() - 8.0D, (double)pos.getX() + 8.0D, (double)pos.getY() + 5.0D, (double)pos.getZ() + 8.0D), (hostileEntity) -> {
           hostileEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING,60,1) );
           return hostileEntity.isAngryAt(((PlayerEntity)(Object) this));
        });}
    }


}
