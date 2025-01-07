package net.satisfy.bloomingnature.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.satisfy.bloomingnature.core.block.entity.StorageBlockEntity;

public interface StorageTypeRenderer {
    void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks);
}
