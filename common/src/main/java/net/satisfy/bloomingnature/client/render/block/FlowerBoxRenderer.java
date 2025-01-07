package net.satisfy.bloomingnature.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.satisfy.bloomingnature.client.util.ClientUtil;
import net.satisfy.bloomingnature.core.block.entity.StorageBlockEntity;


@Environment(EnvType.CLIENT)
public class FlowerBoxRenderer implements StorageTypeRenderer {

    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        matrices.translate(-0.25f, 0.25f, 0.75f);
        matrices.mulPose(Axis.YP.rotationDegrees(90));

        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
                matrices.pushPose();
                matrices.translate(0f, 0f, -0.5f * i);
                ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
                matrices.popPose();
            }
        }
    }
}