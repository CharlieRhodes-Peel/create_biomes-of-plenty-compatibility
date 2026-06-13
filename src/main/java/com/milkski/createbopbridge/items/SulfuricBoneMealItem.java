package com.milkski.createbopbridge.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class SulfuricBoneMealItem extends BoneMealItem {
    private static final int APPLICATIONS = 2;

    public SulfuricBoneMealItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        // Verify the target accepts bone meal at all before doing anything
        BlockState initialState = level.getBlockState(pos);
        if (!(initialState.getBlock() instanceof BonemealableBlock initialBonemealable)
                || !initialBonemealable.isValidBonemealTarget(level, pos, initialState)) {
            return InteractionResult.PASS;
        }

        // Server does the actual growth logic
        if (level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < APPLICATIONS; i++) {
                // Re-read the state each iteration because growth may have
                // changed the block (e.g. crop matured, sapling became tree)
                BlockState currentState = level.getBlockState(pos);
                if (!(currentState.getBlock() instanceof BonemealableBlock current)
                        || !current.isValidBonemealTarget(level, pos, currentState)) {
                    break;
                }
                if (current.isBonemealSuccess(level, level.random, pos, currentState)) {
                    current.performBonemeal(serverLevel, level.random, pos, currentState);
                    // Trigger the green-sparkle particle effect for each successful growth
                }
                level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 3);
            }

            // Consume exactly one item regardless of how many growth ticks fired
            stack.shrink(1);
            if (player != null) {
                player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
            }
        }

        // sidedSuccess animates the hand-swing and returns the correct result per side
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}