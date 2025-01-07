package net.satisfy.bloomingnature.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.satisfy.bloomingnature.core.registry.EntityTypeRegistry;
import net.satisfy.bloomingnature.core.util.BloomingNatureGeneralUtil;
import org.jetbrains.annotations.NotNull;

public class FlowerPotBigBlockEntity extends BlockEntity {
    private Item flower;

    public FlowerPotBigBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.FLOWER_POT_BIG_ENTITY.get(), pos, state);
    }

    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        this.writeFlower(nbt, this.flower);
    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.flower = this.readFlower(nbt);
    }

    public void writeFlower(CompoundTag nbt, Item flower) {
        CompoundTag nbtCompound = new CompoundTag();
        if (flower != null) {
            flower.getDefaultInstance().save(nbtCompound);
        }

        nbt.put("flower", nbtCompound);
    }

    public Item readFlower(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("flower")) {
            CompoundTag nbtCompound = nbt.getCompound("flower");
            if (!nbtCompound.isEmpty()) {
                return ItemStack.of(nbtCompound).getItem();
            }
        }

        return null;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    public void setChanged() {
        if (this.level != null && !this.level.isClientSide()) {
            Packet<ClientGamePacketListener> updatePacket = this.getUpdatePacket();

            for (ServerPlayer player : BloomingNatureGeneralUtil.tracking((ServerLevel) this.level, this.getBlockPos())) {
                assert updatePacket != null;
                player.connection.send(updatePacket);
            }
        }

        super.setChanged();
    }
}

