package com.milkski.createbopbridge.fluids;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

import java.util.function.Supplier;

public class SulfuricAcidBlock extends LiquidBlock {
    public SulfuricAcidBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        // Only emit from the top surface of source blocks, occasionally
        if (random.nextInt(4) != 1) return;

        // Check the block above is air so we only bubble at the surface
        if (!level.getBlockState(pos.above()).isAir()) return;

        double x = pos.getX() + random.nextDouble();
        double y = pos.getY() + 0.9;  // just at the surface
        double z = pos.getZ() + random.nextDouble();

        // Bubble rising + occasional "smoke" for an acrid look
        if (random.nextInt(4) == 1) {
            level.addParticle(ParticleTypes.WHITE_SMOKE, x, y + 0.1, z, 0, 0.02, 0);
        }
    }
}