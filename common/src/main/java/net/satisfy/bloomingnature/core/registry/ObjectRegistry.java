package net.satisfy.bloomingnature.core.registry;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.fuel.FuelRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.satisfy.bloomingnature.BloomingNature;
import net.satisfy.bloomingnature.core.block.*;
import net.satisfy.bloomingnature.core.entity.ModBoatEntity;
import net.satisfy.bloomingnature.core.item.ModBoatItem;
import net.satisfy.bloomingnature.core.util.BloomingNatureIdentifier;
import net.satisfy.bloomingnature.core.util.BloomingNatureUtil;
import net.satisfy.bloomingnature.core.util.BloomingNatureWoodType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ObjectRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BloomingNature.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BloomingNature.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();

    public static final RegistrySupplier<Item> WANDERING_GARDENER_SPAWN_EGG = registerItem("wandering_gardener_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityTypeRegistry.WANDERING_GARDENER, -1, -1, getSettings()));
    public static final RegistrySupplier<Item> TERMITE_SPAWN_EGG = registerItem("termite_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityTypeRegistry.TERMITE, -1, -1, getSettings()));
    public static final RegistrySupplier<Block> LARCH_LOG = registerWithItem("larch_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> LARCH_WOOD = registerWithItem("larch_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_LARCH_WOOD = registerWithItem("stripped_larch_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_LARCH_LOG = registerWithItem("stripped_larch_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> LARCH_PLANKS = registerWithItem("larch_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistrySupplier<Block> LARCH_STAIRS = registerWithItem("larch_stairs", () -> new StairBlock(LARCH_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> LARCH_PRESSURE_PLATE = registerWithItem("larch_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(LARCH_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> LARCH_DOOR = registerWithItem("larch_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(LARCH_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> LARCH_FENCE_GATE = registerWithItem("larch_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(LARCH_PLANKS.get().defaultMapColor()), WoodType.OAK));
    public static final RegistrySupplier<Block> LARCH_SLAB = registerWithItem("larch_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> LARCH_BUTTON = registerWithItem("larch_button", () -> woodenButton(FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> LARCH_TRAPDOOR = registerWithItem("larch_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistrySupplier<Block> LARCH_FENCE = registerWithItem("larch_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> LARCH_LEAVES = registerWithItem("larch_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS)));
    public static final RegistrySupplier<Block> LARCH_WINDOW = registerWithItem("larch_window", () -> new WindowBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GLASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> LARCH_SAPLING = registerWithItem("larch_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {
        @Override
        protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
            return configuredFeatureKey("larch");
        }
    }, BlockBehaviour.Properties.copy(Blocks.SPRUCE_SAPLING)));
    public static final RegistrySupplier<Block> POTTED_LARCH_SAPLING = registerWithoutItem("potted_larch_sapling", () -> new FlowerPotBlock(LARCH_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> BAOBAB_LOG = registerWithItem("baobab_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> BAOBAB_WOOD = registerWithItem("baobab_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_BAOBAB_WOOD = registerWithItem("stripped_baobab_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_BAOBAB_LOG = registerWithItem("stripped_baobab_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> BAOBAB_PLANKS = registerWithItem("baobab_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistrySupplier<Block> BAOBAB_STAIRS = registerWithItem("baobab_stairs", () -> new StairBlock(BAOBAB_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> BAOBAB_PRESSURE_PLATE = registerWithItem("baobab_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(BAOBAB_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> BAOBAB_DOOR = registerWithItem("baobab_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(BAOBAB_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> BAOBAB_FENCE_GATE = registerWithItem("baobab_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(BAOBAB_PLANKS.get().defaultMapColor()), WoodType.OAK));
    public static final RegistrySupplier<Block> BAOBAB_SLAB = registerWithItem("baobab_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> BAOBAB_BUTTON = registerWithItem("baobab_button", () -> woodenButton(FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> BAOBAB_TRAPDOOR = registerWithItem("baobab_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistrySupplier<Block> BAOBAB_FENCE = registerWithItem("baobab_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BAOBAB_LEAVES = registerWithItem("baobab_leaves", () -> new ExtendedLeavesBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS)));
    public static final RegistrySupplier<Block> BAOBAB_WINDOW = registerWithItem("baobab_window", () -> new WindowBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GLASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> BAOBAB_SAPLING = registerWithItem("baobab_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {
        @Override
        protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
            return configuredFeatureKey("savanna_baobab");
        }
    }, BlockBehaviour.Properties.copy(Blocks.SPRUCE_SAPLING)));
    public static final RegistrySupplier<Block> POTTED_BAOBAB_SAPLING = registerWithoutItem("potted_baobab_sapling", () -> new FlowerPotBlock(BAOBAB_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> SWAMP_OAK_LOG = registerWithItem("swamp_oak_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> SWAMP_OAK_WOOD = registerWithItem("swamp_oak_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_SWAMP_OAK_WOOD = registerWithItem("stripped_swamp_oak_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_SWAMP_OAK_LOG = registerWithItem("stripped_swamp_oak_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> SWAMP_OAK_PLANKS = registerWithItem("swamp_oak_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistrySupplier<Block> SWAMP_OAK_STAIRS = registerWithItem("swamp_oak_stairs", () -> new StairBlock(SWAMP_OAK_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> SWAMP_OAK_PRESSURE_PLATE = registerWithItem("swamp_oak_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(SWAMP_OAK_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> SWAMP_OAK_DOOR = registerWithItem("swamp_oak_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(SWAMP_OAK_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> SWAMP_OAK_FENCE_GATE = registerWithItem("swamp_oak_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(SWAMP_OAK_PLANKS.get().defaultMapColor()), WoodType.OAK));
    public static final RegistrySupplier<Block> SWAMP_OAK_SLAB = registerWithItem("swamp_oak_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> SWAMP_OAK_BUTTON = registerWithItem("swamp_oak_button", () -> woodenButton(FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> SWAMP_OAK_TRAPDOOR = registerWithItem("swamp_oak_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistrySupplier<Block> SWAMP_OAK_FENCE = registerWithItem("swamp_oak_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SWAMP_OAK_WINDOW = registerWithItem("swamp_oak_window", () -> new WindowBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GLASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> ORANGE_LEAVES = registerWithItem("orange_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.COLOR_ORANGE)));
    public static final RegistrySupplier<Block> SWAMP_OAK_LEAVES = registerWithItem("swamp_oak_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS)));
    public static final RegistrySupplier<Block> SWAMP_OAK_SAPLING = registerWithItem("swamp_oak_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {
        @Override
        protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
            return configuredFeatureKey("forest_oak_straight");
        }
    }, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> POTTED_SWAMP_OAK_SAPLING = registerWithoutItem("potted_swamp_oak_sapling", () -> new FlowerPotBlock(SWAMP_OAK_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> ASPEN_LOG = registerWithItem("aspen_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> ASPEN_WOOD = registerWithItem("aspen_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_ASPEN_WOOD = registerWithItem("stripped_aspen_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_ASPEN_LOG = registerWithItem("stripped_aspen_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> ASPEN_PLANKS = registerWithItem("aspen_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistrySupplier<Block> ASPEN_STAIRS = registerWithItem("aspen_stairs", () -> new StairBlock(ASPEN_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> ASPEN_PRESSURE_PLATE = registerWithItem("aspen_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(ASPEN_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> ASPEN_DOOR = registerWithItem("aspen_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(ASPEN_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> ASPEN_FENCE_GATE = registerWithItem("aspen_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(ASPEN_PLANKS.get().defaultMapColor()), WoodType.OAK));
    public static final RegistrySupplier<Block> ASPEN_SLAB = registerWithItem("aspen_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> ASPEN_BUTTON = registerWithItem("aspen_button", () -> woodenButton(FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> ASPEN_TRAPDOOR = registerWithItem("aspen_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistrySupplier<Block> ASPEN_FENCE = registerWithItem("aspen_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> ASPEN_WINDOW = registerWithItem("aspen_window", () -> new WindowBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GLASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> ASPEN_LEAVES = registerWithItem("aspen_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS)));
    public static final RegistrySupplier<Block> ASPEN_SAPLING = registerWithItem("aspen_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {
        @Override
        protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
            return configuredFeatureKey("aspen_tree_branched");
        }
    }, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> POTTED_ASPEN_SAPLING = registerWithoutItem("potted_aspen_sapling", () -> new FlowerPotBlock(ASPEN_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> FAN_PALM_LOG = registerWithItem("fan_palm_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> FAN_PALM_WOOD = registerWithItem("fan_palm_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_FAN_PALM_WOOD = registerWithItem("stripped_fan_palm_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_FAN_PALM_LOG = registerWithItem("stripped_fan_palm_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> FAN_PALM_PLANKS = registerWithItem("fan_palm_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistrySupplier<Block> FAN_PALM_STAIRS = registerWithItem("fan_palm_stairs", () -> new StairBlock(FAN_PALM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> FAN_PALM_PRESSURE_PLATE = registerWithItem("fan_palm_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(FAN_PALM_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> FAN_PALM_DOOR = registerWithItem("fan_palm_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(FAN_PALM_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> FAN_PALM_FENCE_GATE = registerWithItem("fan_palm_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(FAN_PALM_PLANKS.get().defaultMapColor()), WoodType.OAK));
    public static final RegistrySupplier<Block> FAN_PALM_SLAB = registerWithItem("fan_palm_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> FAN_PALM_BUTTON = registerWithItem("fan_palm_button", () -> woodenButton(FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> FAN_PALM_TRAPDOOR = registerWithItem("fan_palm_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistrySupplier<Block> FAN_PALM_FENCE = registerWithItem("fan_palm_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> FAN_PALM_WINDOW = registerWithItem("fan_palm_window", () -> new WindowBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GLASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> FAN_PALM_LEAVES = registerWithItem("fan_palm_leaves", () -> new ExtendedLeavesBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS)));
    public static final Supplier<SaplingBlock> FAN_PALM_SPROUT = registerWithItem("fan_palm_sprout", FanPalmSproutBlock::new);
    public static final RegistrySupplier<Block> POTTED_FAN_PALM_SPROUT = registerWithoutItem("potted_fan_palm_sprout", () -> new FlowerPotBlock(FAN_PALM_SPROUT.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> FIR_LOG = registerWithItem("fir_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> FIR_WOOD = registerWithItem("fir_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_FIR_WOOD = registerWithItem("stripped_fir_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_FIR_LOG = registerWithItem("stripped_fir_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> FIR_PLANKS = registerWithItem("fir_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistrySupplier<Block> FIR_STAIRS = registerWithItem("fir_stairs", () -> new StairBlock(FIR_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> FIR_PRESSURE_PLATE = registerWithItem("fir_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(FIR_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> FIR_DOOR = registerWithItem("fir_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(FIR_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> FIR_FENCE_GATE = registerWithItem("fir_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(FIR_PLANKS.get().defaultMapColor()), WoodType.OAK));
    public static final RegistrySupplier<Block> FIR_SLAB = registerWithItem("fir_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> FIR_BUTTON = registerWithItem("fir_button", () -> woodenButton(FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> FIR_TRAPDOOR = registerWithItem("fir_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistrySupplier<Block> FIR_FENCE = registerWithItem("fir_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> FIR_WINDOW = registerWithItem("fir_window", () -> new WindowBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GLASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> FIR_LEAVES = registerWithItem("fir_leaves", () -> new SnowyFirLeavesBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS)));
    public static final RegistrySupplier<Block> FIR_SAPLING = registerWithItem("fir_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {
        @Override
        protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
            return configuredFeatureKey("snowy_taiga_fir");
        }
    }, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> POTTED_FIR_SAPLING = registerWithoutItem("potted_fir_sapling", () -> new FlowerPotBlock(FIR_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_LOG = registerWithItem("swamp_cypress_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_WOOD = registerWithItem("swamp_cypress_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_SWAMP_CYPRESS_WOOD = registerWithItem("stripped_swamp_cypress_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_SWAMP_CYPRESS_LOG = registerWithItem("stripped_swamp_cypress_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_PLANKS = registerWithItem("swamp_cypress_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_STAIRS = registerWithItem("swamp_cypress_stairs", () -> new StairBlock(SWAMP_CYPRESS_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_PRESSURE_PLATE = registerWithItem("swamp_cypress_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(SWAMP_CYPRESS_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_DOOR = registerWithItem("swamp_cypress_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(SWAMP_CYPRESS_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_FENCE_GATE = registerWithItem("swamp_cypress_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(SWAMP_CYPRESS_PLANKS.get().defaultMapColor()), WoodType.OAK));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_SLAB = registerWithItem("swamp_cypress_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_BUTTON = registerWithItem("swamp_cypress_button", () -> woodenButton(FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_TRAPDOOR = registerWithItem("swamp_cypress_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_FENCE = registerWithItem("swamp_cypress_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_WINDOW = registerWithItem("swamp_cypress_window", () -> new WindowBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GLASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_LEAVES = registerWithItem("swamp_cypress_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS)));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_SAPLING = registerWithItem("swamp_cypress_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {
        @Override
        protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
            return configuredFeatureKey("swamp_cypress");
        }
    }, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> POTTED_SWAMP_CYPRESS_SAPLING = registerWithoutItem("potted_swamp_cypress_sapling", () -> new FlowerPotBlock(SWAMP_CYPRESS_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> EBONY_LOG = registerWithItem("ebony_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> EBONY_WOOD = registerWithItem("ebony_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_EBONY_WOOD = registerWithItem("stripped_ebony_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_EBONY_LOG = registerWithItem("stripped_ebony_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> EBONY_PLANKS = registerWithItem("ebony_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistrySupplier<Block> EBONY_STAIRS = registerWithItem("ebony_stairs", () -> new StairBlock(EBONY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> EBONY_PRESSURE_PLATE = registerWithItem("ebony_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(EBONY_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> EBONY_DOOR = registerWithItem("ebony_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(EBONY_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> EBONY_FENCE_GATE = registerWithItem("ebony_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(EBONY_PLANKS.get().defaultMapColor()), WoodType.OAK));
    public static final RegistrySupplier<Block> EBONY_SLAB = registerWithItem("ebony_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> EBONY_BUTTON = registerWithItem("ebony_button", () -> woodenButton(FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> EBONY_TRAPDOOR = registerWithItem("ebony_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistrySupplier<Block> EBONY_FENCE = registerWithItem("ebony_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> EBONY_WINDOW = registerWithItem("ebony_window", () -> new WindowBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GLASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> EBONY_LEAVES = registerWithItem("ebony_leaves", () -> new ExtendedLeavesBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS)));
    public static final RegistrySupplier<Block> EBONY_SAPLING = registerWithItem("ebony_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {
        @Override
        protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
            return configuredFeatureKey("sparse_jungle_ebony_tree");
        }
    }, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> POTTED_EBONY_SAPLING = registerWithoutItem("potted_ebony_sapling", () -> new FlowerPotBlock(EBONY_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> CHESTNUT_LOG = registerWithItem("chestnut_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> CHESTNUT_WOOD = registerWithItem("chestnut_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_CHESTNUT_WOOD = registerWithItem("stripped_chestnut_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_CHESTNUT_LOG = registerWithItem("stripped_chestnut_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> CHESTNUT_PLANKS = registerWithItem("chestnut_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistrySupplier<Block> CHESTNUT_STAIRS = registerWithItem("chestnut_stairs", () -> new StairBlock(CHESTNUT_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> CHESTNUT_PRESSURE_PLATE = registerWithItem("chestnut_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(CHESTNUT_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> CHESTNUT_DOOR = registerWithItem("chestnut_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(CHESTNUT_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> CHESTNUT_FENCE_GATE = registerWithItem("chestnut_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(CHESTNUT_PLANKS.get().defaultMapColor()), WoodType.OAK));
    public static final RegistrySupplier<Block> CHESTNUT_SLAB = registerWithItem("chestnut_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> CHESTNUT_BUTTON = registerWithItem("chestnut_button", () -> woodenButton(FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> CHESTNUT_TRAPDOOR = registerWithItem("chestnut_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistrySupplier<Block> CHESTNUT_FENCE = registerWithItem("chestnut_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CHESTNUT_WINDOW = registerWithItem("chestnut_window", () -> new WindowBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GLASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> CHESTNUT_LEAVES = registerWithItem("chestnut_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS)));
    public static final RegistrySupplier<Block> CHESTNUT_SAPLING = registerWithItem("chestnut_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {
        @Override
        protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
            return configuredFeatureKey("plains_chestnut_tree");
        }
    }, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> POTTED_CHESTNUT_SAPLING = registerWithoutItem("potted_chestnut_sapling", () -> new FlowerPotBlock(CHESTNUT_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> RED_BRICKS = registerWithItem("red_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
    public static final RegistrySupplier<Block> RED_BRICK_STAIRS = registerWithItem("red_brick_stairs", () -> new StairBlock(RED_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> RED_BRICK_SLAB = registerWithItem("red_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> RED_BRICK_WALL = registerWithItem("red_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> CHISELED_RED_BRICKS= registerWithItem("chiseled_red_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
    public static final RegistrySupplier<Block> TRAVERTIN = registerWithItem("travertin", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> TRAVERTIN_STAIRS = registerWithItem("travertin_stairs", () -> new StairBlock(TRAVERTIN.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> TRAVERTIN_SLAB = registerWithItem("travertin_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(TRAVERTIN.get())));
    public static final RegistrySupplier<Block> TRAVERTIN_WALL = registerWithItem("travertin_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(TRAVERTIN.get())));
    public static final RegistrySupplier<Block> COBBLED_TRAVERTIN = registerWithItem("cobbled_travertin", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> COBBLED_TRAVERTIN_STAIRS = registerWithItem("cobbled_travertin_stairs", () -> new StairBlock(COBBLED_TRAVERTIN.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> COBBLED_TRAVERTIN_SLAB = registerWithItem("cobbled_travertin_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(COBBLED_TRAVERTIN.get())));
    public static final RegistrySupplier<Block> COBBLED_TRAVERTIN_WALL = registerWithItem("cobbled_travertin_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(COBBLED_TRAVERTIN.get())));
    public static final RegistrySupplier<Block> CHISELED_TRAVERTIN = registerWithItem("chiseled_travertin", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> TRAVERTIN_BRICKS = registerWithItem("travertin_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> TRAVERTIN_BRICK_STAIRS = registerWithItem("travertin_brick_stairs", () -> new StairBlock(TRAVERTIN_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> TRAVERTIN_BRICK_SLAB = registerWithItem("travertin_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(TRAVERTIN_BRICKS.get())));
    public static final RegistrySupplier<Block> TRAVERTIN_BRICK_WALL = registerWithItem("travertin_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(TRAVERTIN_BRICKS.get())));
    public static final RegistrySupplier<Block> CRACKED_TRAVERTIN_BRICKS = registerWithItem("cracked_travertin_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_TRAVERTIN_BRICKS = registerWithItem("mossy_travertin_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_TRAVERTIN_BRICK_STAIRS = registerWithItem("mossy_travertin_brick_stairs", () -> new StairBlock(MOSSY_TRAVERTIN_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_TRAVERTIN_BRICK_SLAB = registerWithItem("mossy_travertin_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_TRAVERTIN_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_TRAVERTIN_BRICK_WALL = registerWithItem("mossy_travertin_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_TRAVERTIN_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_TRAVERTIN = registerWithItem("mossy_cobbled_travertin", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_TRAVERTIN_STAIRS = registerWithItem("mossy_cobbled_travertin_stairs", () -> new StairBlock(MOSSY_TRAVERTIN_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_TRAVERTIN_SLAB = registerWithItem("mossy_cobbled_travertin_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_TRAVERTIN_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_TRAVERTIN_WALL = registerWithItem("mossy_cobbled_travertin_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_TRAVERTIN_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_CHISELED_TRAVERTIN = registerWithItem("mossy_chiseled_travertin", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_LATERIT = registerWithItem("mossy_laterit", () -> new LateritGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).randomTicks().strength(0.9F).sound(SoundType.GRASS)));
    public static final RegistrySupplier<Block> LATERIT = registerWithItem("laterit", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> LATERIT_STAIRS = registerWithItem("laterit_stairs", () -> new StairBlock(LATERIT.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> LATERIT_SLAB = registerWithItem("laterit_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(LATERIT.get())));
    public static final RegistrySupplier<Block> LATERIT_WALL = registerWithItem("laterit_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(LATERIT.get())));
    public static final RegistrySupplier<Block> COBBLED_LATERIT = registerWithItem("cobbled_laterit", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> COBBLED_LATERIT_STAIRS = registerWithItem("cobbled_laterit_stairs", () -> new StairBlock(COBBLED_LATERIT.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> COBBLED_LATERIT_SLAB = registerWithItem("cobbled_laterit_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(COBBLED_LATERIT.get())));
    public static final RegistrySupplier<Block> COBBLED_LATERIT_WALL = registerWithItem("cobbled_laterit_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(COBBLED_LATERIT.get())));
    public static final RegistrySupplier<Block> CHISELED_LATERIT = registerWithItem("chiseled_laterit", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> LATERIT_BRICKS = registerWithItem("laterit_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> LATERIT_BRICK_STAIRS = registerWithItem("laterit_brick_stairs", () -> new StairBlock(LATERIT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> LATERIT_BRICK_SLAB = registerWithItem("laterit_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(LATERIT_BRICKS.get())));
    public static final RegistrySupplier<Block> LATERIT_BRICK_WALL = registerWithItem("laterit_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(LATERIT_BRICKS.get())));
    public static final RegistrySupplier<Block> CRACKED_LATERIT_BRICKS = registerWithItem("cracked_laterit_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_LATERIT_BRICKS = registerWithItem("mossy_laterit_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_LATERIT_BRICK_STAIRS = registerWithItem("mossy_laterit_brick_stairs", () -> new StairBlock(MOSSY_LATERIT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_LATERIT_BRICK_SLAB = registerWithItem("mossy_laterit_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_LATERIT_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_LATERIT_BRICK_WALL = registerWithItem("mossy_laterit_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_LATERIT_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_LATERIT = registerWithItem("mossy_cobbled_laterit", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_LATERIT_STAIRS = registerWithItem("mossy_cobbled_laterit_stairs", () -> new StairBlock(MOSSY_LATERIT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_LATERIT_SLAB = registerWithItem("mossy_cobbled_laterit_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_LATERIT_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_LATERIT_WALL = registerWithItem("mossy_cobbled_laterit_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_LATERIT_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_CHISELED_LATERIT = registerWithItem("mossy_chiseled_laterit", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> SLATE = registerWithItem("slate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> SLATE_STAIRS = registerWithItem("slate_stairs", () -> new StairBlock(SLATE.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> SLATE_SLAB = registerWithItem("slate_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(SLATE.get())));
    public static final RegistrySupplier<Block> SLATE_WALL = registerWithItem("slate_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(SLATE.get())));
    public static final RegistrySupplier<Block> COBBLED_SLATE = registerWithItem("cobbled_slate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> COBBLED_SLATE_STAIRS = registerWithItem("cobbled_slate_stairs", () -> new StairBlock(COBBLED_SLATE.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> COBBLED_SLATE_SLAB = registerWithItem("cobbled_slate_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(COBBLED_SLATE.get())));
    public static final RegistrySupplier<Block> COBBLED_SLATE_WALL = registerWithItem("cobbled_slate_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(COBBLED_SLATE.get())));
    public static final RegistrySupplier<Block> CHISELED_SLATE = registerWithItem("chiseled_slate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> SLATE_BRICKS = registerWithItem("slate_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> SLATE_BRICK_STAIRS = registerWithItem("slate_brick_stairs", () -> new StairBlock(SLATE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> SLATE_BRICK_SLAB = registerWithItem("slate_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(SLATE_BRICKS.get())));
    public static final RegistrySupplier<Block> SLATE_BRICK_WALL = registerWithItem("slate_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(SLATE_BRICKS.get())));
    public static final RegistrySupplier<Block> CRACKED_SLATE_BRICKS = registerWithItem("cracked_slate_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_SLATE_BRICKS = registerWithItem("mossy_slate_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_SLATE_BRICK_STAIRS = registerWithItem("mossy_slate_brick_stairs", () -> new StairBlock(MOSSY_SLATE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_SLATE_BRICK_SLAB = registerWithItem("mossy_slate_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_SLATE_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_SLATE_BRICK_WALL = registerWithItem("mossy_slate_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_SLATE_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_SLATE = registerWithItem("mossy_cobbled_slate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_SLATE_STAIRS = registerWithItem("mossy_cobbled_slate_stairs", () -> new StairBlock(MOSSY_SLATE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(RED_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_SLATE_SLAB = registerWithItem("mossy_cobbled_slate_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_SLATE_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_SLATE_WALL = registerWithItem("mossy_cobbled_slate_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_SLATE_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_CHISELED_SLATE = registerWithItem("mossy_chiseled_slate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_SLATE = registerWithItem("mossy_slate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_SLATE_STAIRS = registerWithItem("mossy_slate_stairs", () -> new StairBlock(MOSSY_SLATE.get().defaultBlockState(), BlockBehaviour.Properties.copy(MOSSY_SLATE.get())));
    public static final RegistrySupplier<Block> MOSSY_SLATE_SLAB = registerWithItem("mossy_slate_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_SLATE.get())));
    public static final RegistrySupplier<Block> MOSSY_SLATE_WALL = registerWithItem("mossy_slate_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_SLATE.get())));
    public static final RegistrySupplier<Block> MOSSY_TRAVERTIN = registerWithItem("mossy_travertin", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_TRAVERTIN_STAIRS = registerWithItem("mossy_travertin_stairs", () -> new StairBlock(MOSSY_TRAVERTIN.get().defaultBlockState(), BlockBehaviour.Properties.copy(MOSSY_TRAVERTIN.get())));
    public static final RegistrySupplier<Block> MOSSY_TRAVERTIN_SLAB = registerWithItem("mossy_travertin_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_TRAVERTIN.get())));
    public static final RegistrySupplier<Block> MOSSY_TRAVERTIN_WALL = registerWithItem("mossy_travertin_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_TRAVERTIN.get())));
    public static final RegistrySupplier<Block> MOSSY_LATERIT_STONE = registerWithItem("mossy_laterit_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_LATERIT_STAIRS = registerWithItem("mossy_laterit_stairs", () -> new StairBlock(MOSSY_LATERIT.get().defaultBlockState(), BlockBehaviour.Properties.copy(MOSSY_LATERIT.get())));
    public static final RegistrySupplier<Block> MOSSY_LATERIT_SLAB = registerWithItem("mossy_laterit_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_LATERIT.get())));
    public static final RegistrySupplier<Block> MOSSY_LATERIT_WALL = registerWithItem("mossy_laterit_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_LATERIT.get())));
    public static final RegistrySupplier<Block> MUSHROOM_BRICKS = registerWithItem("mushroom_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.MUSHROOM_STEM)));
    public static final RegistrySupplier<Block> MUSHROOM_BRICK_STAIRS = registerWithItem("mushroom_brick_stairs", () -> new StairBlock(MUSHROOM_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(MUSHROOM_BRICKS.get())));
    public static final RegistrySupplier<Block> MUSHROOM_BRICK_SLAB = registerWithItem("mushroom_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MUSHROOM_BRICKS.get())));
    public static final RegistrySupplier<Block> MUSHROOM_BRICK_WALL = registerWithItem("mushroom_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MUSHROOM_BRICKS.get())));
    public static final RegistrySupplier<Block> BROWN_MUSHROOM_BRICK_STAIRS = registerWithItem("brown_mushroom_brick_stairs", () -> new StairBlock(MUSHROOM_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(MUSHROOM_BRICKS.get())));
    public static final RegistrySupplier<Block> BROWN_MUSHROOM_BRICK_SLAB = registerWithItem("brown_mushroom_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MUSHROOM_BRICKS.get())));
    public static final RegistrySupplier<Block> BROWN_MUSHROOM_BRICK_WALL = registerWithItem("brown_mushroom_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MUSHROOM_BRICKS.get())));
    public static final RegistrySupplier<Block> BROWN_MUSHROOM_BRICKS = registerWithItem("brown_mushroom_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.MUSHROOM_STEM)));
    public static final RegistrySupplier<Block> FOREST_MOSS = registerWithItem("forest_moss", () -> new ForestMossBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).speedFactor(0.75F)));
    public static final RegistrySupplier<Block> FOREST_MOSS_CARPET = registerWithItem("forest_moss_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.copy(Blocks.MOSS_CARPET).speedFactor(0.75F)));
    public static final RegistrySupplier<Block> MARSH_BLOCK = registerWithItem("marsh_block", () -> new SinkInBlock(BlockBehaviour.Properties.copy(Blocks.MUD)));
    public static final RegistrySupplier<Block> TERMITE_MOUND = registerWithItem("termite_mound", () -> new TermiteBlock(Blocks.ROOTED_DIRT, BlockBehaviour.Properties.of().mapColor(MapColor.CLAY)));
    public static final RegistrySupplier<Block> QUICKSAND = registerWithItem("quicksand", () -> new SinkInSandBlock(BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistrySupplier<Block> JOE_PYE = registerWithItem("joe_pye", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_JOE_PYE = registerWithoutItem("potted_joe_pye", () -> new FlowerPotBlock(JOE_PYE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> HYSSOP = registerWithItem("hyssop", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_HYSSOP = registerWithoutItem("potted_hyssop", () -> new FlowerPotBlock(HYSSOP.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> MOUNTAIN_SNOWBELL = registerWithItem("mountain_snowbell", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_MOUNTAIN_SNOWBELL = registerWithoutItem("potted_mountain_snowbell", () -> new FlowerPotBlock(MOUNTAIN_SNOWBELL.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> MOUNTAIN_LAUREL = registerWithItem("mountain_laurel", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_MOUNTAIN_LAUREL = registerWithoutItem("potted_mountain_laurel", () -> new FlowerPotBlock(MOUNTAIN_LAUREL.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> GOLDEN_ROD = registerWithItem("golden_rod", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_GOLDEN_ROD = registerWithoutItem("potted_golden_rod", () -> new FlowerPotBlock(GOLDEN_ROD.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> BIRD_OF_PARADISE = registerWithItem("bird_of_paradise", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_BIRD_OF_PARADISE = registerWithoutItem("potted_bird_of_paradise", () -> new FlowerPotBlock(BIRD_OF_PARADISE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> WHITE_ORCHID = registerWithItem("white_orchid", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_WHITE_ORCHID = registerWithoutItem("potted_white_orchid", () -> new FlowerPotBlock(WHITE_ORCHID.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> DAPHNE = registerWithItem("daphne", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_DAPHNE = registerWithoutItem("potted_daphne", () -> new FlowerPotBlock(DAPHNE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> BOTTLEBRUSHES = registerWithItem("bottlebrushes", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_BOTTLEBRUSHES = registerWithoutItem("potted_bottlebrushes", () -> new FlowerPotBlock(BOTTLEBRUSHES.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> BLUEBELL = registerWithItem("bluebell", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_BLUEBELL = registerWithoutItem("potted_bluebell", () -> new FlowerPotBlock(BLUEBELL.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> BEGONIE = registerWithItem("begonie", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_BEGONIE = registerWithoutItem("potted_begonie", () -> new FlowerPotBlock(BEGONIE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> GOATSBEARD = registerWithItem("goatsbeard", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_GOATSBEARD = registerWithoutItem("potted_goatsbeard", () -> new FlowerPotBlock(GOATSBEARD.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> GENISTEAE = registerWithItem("genisteae", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_GENISTEAE = registerWithoutItem("potted_genisteae", () -> new FlowerPotBlock(GENISTEAE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> FORSYTHIA = registerWithItem("forsythia", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_FORSYTHIA = registerWithoutItem("potted_forsythia", () -> new FlowerPotBlock(FORSYTHIA.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> FOXGLOVE_WHITE = registerWithItem("foxglove_white", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_FOXGLOVE_WHITE = registerWithoutItem("potted_foxglove_white", () -> new FlowerPotBlock(FOXGLOVE_WHITE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> AMARYLLIS = registerWithItem("amaryllis", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_AMARYLLIS = registerWithoutItem("potted_amaryllis", () -> new FlowerPotBlock(AMARYLLIS.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> ANEMONE = registerWithItem("anemone", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_ANEMONE = registerWithoutItem("potted_anemone", () -> new FlowerPotBlock(ANEMONE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> FOXGLOVE_PINK = registerWithItem("foxglove_pink", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_FOXGLOVE_PINK = registerWithoutItem("potted_foxglove_pink", () -> new FlowerPotBlock(FOXGLOVE_PINK.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> GLADIOLUS = registerWithItem("gladiolus", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_GLADIOLUS = registerWithoutItem("potted_gladiolus", () -> new FlowerPotBlock(GLADIOLUS.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> FREESIA_YELLOW = registerWithItem("freesia_yellow", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_FREESIA_YELLOW = registerWithoutItem("potted_freesia_yellow", () -> new FlowerPotBlock(FREESIA_YELLOW.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> FREESIA_PINK = registerWithItem("freesia_pink", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_FREESIA_PINK = registerWithoutItem("potted_freesia_pink", () -> new FlowerPotBlock(FREESIA_PINK.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> LUPINE_PURPLE = registerWithItem("lupine_purple", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_LUPINE_PURPLE = registerWithoutItem("potted_lupine_purple", () -> new FlowerPotBlock(LUPINE_PURPLE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> LUPINE_BLUE = registerWithItem("lupine_blue", () -> new FlowerBlock(Objects.requireNonNull(MobEffect.byId(6)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_LUPINE_BLUE = registerWithoutItem("potted_lupine_blue", () -> new FlowerPotBlock(LUPINE_BLUE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> TALL_LUPINE_BLUE = registerWithItem("tall_lupine_blue", () -> new TallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.ROSE_BUSH)));
    public static final RegistrySupplier<Block> TALL_LUPINE_PURPLE = registerWithItem("tall_lupine_purple", () -> new TallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.ROSE_BUSH)));
    public static final RegistrySupplier<Block> BEACH_GRASS = registerWithItem("beach_grass", () -> new DeadBushBlock(BlockBehaviour.Properties.copy(Blocks.ALLIUM)));
    public static final RegistrySupplier<Block> BEACH_BUSH = registerWithItem("beach_bush", () -> new DeadBushBlock(BlockBehaviour.Properties.copy(Blocks.DANDELION)));
    public static final RegistrySupplier<Block> POTTED_BEACH_BUSH = registerWithoutItem("potted_beach_bush", () -> new FlowerPotBlock(BEACH_BUSH.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> BEACH_BUSH_TALL = registerWithItem("beach_bush_tall", () -> new DeadBushTallBlock(BlockBehaviour.Properties.copy(Blocks.ROSE_BUSH)));
    public static final RegistrySupplier<Block> CATTAIL = registerWithItem("cattail", () -> new CattailBlock(BlockBehaviour.Properties.copy(Blocks.TALL_SEAGRASS)));
    public static final RegistrySupplier<Block> REED = registerWithItem("reed", () -> new CattailBlock(BlockBehaviour.Properties.copy(Blocks.TALL_SEAGRASS)));
    public static final RegistrySupplier<Block> CARDINAL = registerWithItem("cardinal", () -> new TallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.ROSE_BUSH)));
    public static final RegistrySupplier<Block> TALL_MOUNTAIN_LAUREL = registerWithItem("tall_mountain_laurel", () -> new TallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.ROSE_BUSH)));
    public static final RegistrySupplier<Block> WILD_SUNFLOWER = registerWithItem("wild_sunflower", () -> new TallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.ROSE_BUSH)));
    public static final RegistrySupplier<Block> FLOWER_POT_BIG = registerWithItem("flower_pot_big", () -> new FlowerPotBigBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> FLOWER_BOX = registerWithItem("flower_box", () -> new FlowerBoxBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> FLOATING_LEAVES = registerWithItem("floating_leaves", () -> new FloatingLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().sound(SoundType.LILY_PAD).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final RegistrySupplier<Block> SUNGRASS = registerWithItem("sungrass", () -> new BonemealableTallGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS), () -> ObjectRegistry.TALL_SUNGRASS.get()));
    public static final RegistrySupplier<Block> RED_OAT_GRASS = registerWithItem("red_oat_grass", () -> new BonemealableTallGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS), () -> ObjectRegistry.TALL_RED_OAT_GRASS.get()));
    public static final RegistrySupplier<Block> TALL_SUNGRASS = registerWithItem("tall_sungrass", () -> new DoublePlantBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)));
    public static final RegistrySupplier<Block> TALL_RED_OAT_GRASS = registerWithItem("tall_red_oat_grass", () -> new DoublePlantBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)));
    public static final RegistrySupplier<Block> SILKGRASS = registerWithItem("silkgrass",() -> new BonemealableTallGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS), () -> ObjectRegistry.TALL_SILKGRASS.get()));
    public static final RegistrySupplier<Block> TALL_SILKGRASS = registerWithItem("tall_silkgrass", () -> new DoublePlantBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)));
    public static final RegistrySupplier<Block> MOSSGRASS = registerWithItem("mossgrass", () -> new BonemealableTallGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS), () -> ObjectRegistry.MOSSGRASS.get()));
    public static final RegistrySupplier<Block> SMALL_CACTUS = registerWithItem("small_cactus", () -> new SmallCactusBlock(BlockBehaviour.Properties.copy(Blocks.CACTUS)));
    public static final RegistrySupplier<Block> BARREL_CACTUS = registerWithItem("barrel_cactus", () -> new DeadBushBlock(BlockBehaviour.Properties.copy(Blocks.GRASS)));
    public static final RegistrySupplier<Block> PRICKLY_PEAR_CACTUS = registerWithItem("prickly_pear_cactus", () -> new DeadBushBlock(BlockBehaviour.Properties.copy(Blocks.GRASS)));
    public static final RegistrySupplier<Block> POTTED_BARREL_CACTUS = registerWithoutItem("potted_barrel_cactus", () -> new FlowerPotBlock(BARREL_CACTUS.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> POTTED_PRICKLY_PEAR_CACTUS = registerWithoutItem("potted_prickly_pear_cactus", () -> new FlowerPotBlock(PRICKLY_PEAR_CACTUS.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> CACTUS_PLANKS = registerWithItem("cactus_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistrySupplier<Block> CACTUS_STAIRS = registerWithItem("cactus_stairs", () -> new StairBlock(CACTUS_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> CACTUS_PRESSURE_PLATE = registerWithItem("cactus_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(CACTUS_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> CACTUS_DOOR = registerWithItem("cactus_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(CACTUS_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> CACTUS_FENCE_GATE = registerWithItem("cactus_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(CACTUS_PLANKS.get().defaultMapColor()), WoodType.OAK));
    public static final RegistrySupplier<Block> CACTUS_SLAB = registerWithItem("cactus_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> CACTUS_BUTTON = registerWithItem("cactus_button", () -> woodenButton(FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> CACTUS_TRAPDOOR = registerWithItem("cactus_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistrySupplier<Block> CACTUS_FENCE = registerWithItem("cactus_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CACTUS_WINDOW = registerWithItem("cactus_window", () -> new WindowBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GLASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> SAND = registerWithItem("sand", () -> new SandLayerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).replaceable().forceSolidOff().randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SAND).isViewBlocking((blockStatex, blockGetter, blockPos) -> blockStatex.getValue(SnowLayerBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY)));
    public static final RegistrySupplier<Block> BLOOMINGNATURE_BANNER = registerWithItem("bloomingnature_banner", () -> new CompletionistBannerBlock(BlockBehaviour.Properties.of().strength(1F).instrument(NoteBlockInstrument.BASS).noCollission().sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BLOOMINGNATURE_WALL_BANNER = registerWithoutItem("bloomingnature_wall_banner", () -> new CompletionistWallBannerBlock(BlockBehaviour.Properties.of().strength(1F).instrument(NoteBlockInstrument.BASS).noCollission().sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> ASPEN_SIGN = registerWithoutItem("aspen_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), BloomingNatureWoodType.ASPEN));
    public static final RegistrySupplier<Block> ASPEN_WALL_SIGN = registerWithoutItem("aspen_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), BloomingNatureWoodType.ASPEN));
    public static final RegistrySupplier<Block> ASPEN_HANGING_SIGN = registerWithoutItem("aspen_hanging_sign", () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), BloomingNatureWoodType.ASPEN));
    public static final RegistrySupplier<Block> ASPEN_WALL_HANGING_SIGN = registerWithoutItem("aspen_wall_hanging_sign", () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), BloomingNatureWoodType.ASPEN));
    public static final RegistrySupplier<Item> ASPEN_SIGN_ITEM = ITEMS.register("aspen_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ObjectRegistry.ASPEN_SIGN.get(), ObjectRegistry.ASPEN_WALL_SIGN.get()));
    public static final RegistrySupplier<Item> ASPEN_HANGING_SIGN_ITEM = ITEMS.register("aspen_hanging_sign", () -> new HangingSignItem(ObjectRegistry.ASPEN_HANGING_SIGN.get(), ObjectRegistry.ASPEN_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Block> BAOBAB_SIGN = registerWithoutItem("baobab_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), BloomingNatureWoodType.BAOBAB));
    public static final RegistrySupplier<Block> BAOBAB_WALL_SIGN = registerWithoutItem("baobab_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), BloomingNatureWoodType.BAOBAB));
    public static final RegistrySupplier<Block> BAOBAB_HANGING_SIGN = registerWithoutItem("baobab_hanging_sign", () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), BloomingNatureWoodType.BAOBAB));
    public static final RegistrySupplier<Block> BAOBAB_WALL_HANGING_SIGN = registerWithoutItem("baobab_wall_hanging_sign", () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), BloomingNatureWoodType.BAOBAB));
    public static final RegistrySupplier<Item> BAOBAB_SIGN_ITEM = ITEMS.register("baobab_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ObjectRegistry.BAOBAB_SIGN.get(), ObjectRegistry.BAOBAB_WALL_SIGN.get()));
    public static final RegistrySupplier<Item> BAOBAB_HANGING_SIGN_ITEM = ITEMS.register("baobab_hanging_sign", () -> new HangingSignItem(ObjectRegistry.BAOBAB_HANGING_SIGN.get(), ObjectRegistry.BAOBAB_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Block> LARCH_SIGN = registerWithoutItem("larch_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), BloomingNatureWoodType.LARCH));
    public static final RegistrySupplier<Block> LARCH_WALL_SIGN = registerWithoutItem("larch_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), BloomingNatureWoodType.LARCH));
    public static final RegistrySupplier<Block> LARCH_HANGING_SIGN = registerWithoutItem("larch_hanging_sign", () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), BloomingNatureWoodType.LARCH));
    public static final RegistrySupplier<Block> LARCH_WALL_HANGING_SIGN = registerWithoutItem("larch_wall_hanging_sign", () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), BloomingNatureWoodType.LARCH));
    public static final RegistrySupplier<Item> LARCH_SIGN_ITEM = ITEMS.register("larch_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ObjectRegistry.LARCH_SIGN.get(), ObjectRegistry.LARCH_WALL_SIGN.get()));
    public static final RegistrySupplier<Item> LARCH_HANGING_SIGN_ITEM = ITEMS.register("larch_hanging_sign", () -> new HangingSignItem(ObjectRegistry.LARCH_HANGING_SIGN.get(), ObjectRegistry.LARCH_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Block> EBONY_SIGN = registerWithoutItem("ebony_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), BloomingNatureWoodType.EBONY));
    public static final RegistrySupplier<Block> EBONY_WALL_SIGN = registerWithoutItem("ebony_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), BloomingNatureWoodType.EBONY));
    public static final RegistrySupplier<Block> EBONY_HANGING_SIGN = registerWithoutItem("ebony_hanging_sign", () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), BloomingNatureWoodType.EBONY));
    public static final RegistrySupplier<Block> EBONY_WALL_HANGING_SIGN = registerWithoutItem("ebony_wall_hanging_sign", () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), BloomingNatureWoodType.EBONY));
    public static final RegistrySupplier<Item> EBONY_SIGN_ITEM = ITEMS.register("ebony_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ObjectRegistry.EBONY_SIGN.get(), ObjectRegistry.EBONY_WALL_SIGN.get()));
    public static final RegistrySupplier<Item> EBONY_HANGING_SIGN_ITEM = ITEMS.register("ebony_hanging_sign", () -> new HangingSignItem(ObjectRegistry.EBONY_HANGING_SIGN.get(), ObjectRegistry.EBONY_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Block> CHESTNUT_SIGN = registerWithoutItem("chestnut_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), BloomingNatureWoodType.CHESTNUT));
    public static final RegistrySupplier<Block> CHESTNUT_WALL_SIGN = registerWithoutItem("chestnut_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), BloomingNatureWoodType.CHESTNUT));
    public static final RegistrySupplier<Block> CHESTNUT_HANGING_SIGN = registerWithoutItem("chestnut_hanging_sign", () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), BloomingNatureWoodType.CHESTNUT));
    public static final RegistrySupplier<Block> CHESTNUT_WALL_HANGING_SIGN = registerWithoutItem("chestnut_wall_hanging_sign", () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), BloomingNatureWoodType.CHESTNUT));
    public static final RegistrySupplier<Item> CHESTNUT_SIGN_ITEM = ITEMS.register("chestnut_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ObjectRegistry.CHESTNUT_SIGN.get(), ObjectRegistry.CHESTNUT_WALL_SIGN.get()));
    public static final RegistrySupplier<Item> CHESTNUT_HANGING_SIGN_ITEM = ITEMS.register("chestnut_hanging_sign", () -> new HangingSignItem(ObjectRegistry.CHESTNUT_HANGING_SIGN.get(), ObjectRegistry.CHESTNUT_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Block> SWAMP_OAK_SIGN = registerWithoutItem("swamp_oak_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), BloomingNatureWoodType.SWAMP_OAK));
    public static final RegistrySupplier<Block> SWAMP_OAK_WALL_SIGN = registerWithoutItem("swamp_oak_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), BloomingNatureWoodType.SWAMP_OAK));
    public static final RegistrySupplier<Block> SWAMP_OAK_HANGING_SIGN = registerWithoutItem("swamp_oak_hanging_sign", () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), BloomingNatureWoodType.SWAMP_OAK));
    public static final RegistrySupplier<Block> SWAMP_OAK_WALL_HANGING_SIGN = registerWithoutItem("swamp_oak_wall_hanging_sign", () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), BloomingNatureWoodType.SWAMP_OAK));
    public static final RegistrySupplier<Item> SWAMP_OAK_SIGN_ITEM = ITEMS.register("swamp_oak_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ObjectRegistry.SWAMP_OAK_SIGN.get(), ObjectRegistry.SWAMP_OAK_WALL_SIGN.get()));
    public static final RegistrySupplier<Item> SWAMP_OAK_HANGING_SIGN_ITEM = ITEMS.register("swamp_oak_hanging_sign", () -> new HangingSignItem(ObjectRegistry.SWAMP_OAK_HANGING_SIGN.get(), ObjectRegistry.SWAMP_OAK_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_SIGN = registerWithoutItem("swamp_cypress_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), BloomingNatureWoodType.SWAMP_CYPRESS));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_WALL_SIGN = registerWithoutItem("swamp_cypress_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), BloomingNatureWoodType.SWAMP_CYPRESS));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_HANGING_SIGN = registerWithoutItem("swamp_cypress_hanging_sign", () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), BloomingNatureWoodType.SWAMP_CYPRESS));
    public static final RegistrySupplier<Block> SWAMP_CYPRESS_WALL_HANGING_SIGN = registerWithoutItem("swamp_cypress_wall_hanging_sign", () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), BloomingNatureWoodType.SWAMP_CYPRESS));
    public static final RegistrySupplier<Item> SWAMP_CYPRESS_SIGN_ITEM = ITEMS.register("swamp_cypress_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ObjectRegistry.SWAMP_CYPRESS_SIGN.get(), ObjectRegistry.SWAMP_CYPRESS_WALL_SIGN.get()));
    public static final RegistrySupplier<Item> SWAMP_CYPRESS_HANGING_SIGN_ITEM = ITEMS.register("swamp_cypress_hanging_sign", () -> new HangingSignItem(ObjectRegistry.SWAMP_CYPRESS_HANGING_SIGN.get(), ObjectRegistry.SWAMP_CYPRESS_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Block> FAN_PALM_SIGN = registerWithoutItem("fan_palm_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), BloomingNatureWoodType.FAN_PALM));
    public static final RegistrySupplier<Block> FAN_PALM_WALL_SIGN = registerWithoutItem("fan_palm_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), BloomingNatureWoodType.FAN_PALM));
    public static final RegistrySupplier<Block> FAN_PALM_HANGING_SIGN = registerWithoutItem("fan_palm_hanging_sign", () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), BloomingNatureWoodType.FAN_PALM));
    public static final RegistrySupplier<Block> FAN_PALM_WALL_HANGING_SIGN = registerWithoutItem("fan_palm_wall_hanging_sign", () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), BloomingNatureWoodType.FAN_PALM));
    public static final RegistrySupplier<Item> FAN_PALM_SIGN_ITEM = ITEMS.register("fan_palm_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ObjectRegistry.FAN_PALM_SIGN.get(), ObjectRegistry.FAN_PALM_WALL_SIGN.get()));
    public static final RegistrySupplier<Item> FAN_PALM_HANGING_SIGN_ITEM = ITEMS.register("fan_palm_hanging_sign", () -> new HangingSignItem(ObjectRegistry.FAN_PALM_HANGING_SIGN.get(), ObjectRegistry.FAN_PALM_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Block> FIR_SIGN = registerWithoutItem("fir_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), BloomingNatureWoodType.FIR));
    public static final RegistrySupplier<Block> FIR_WALL_SIGN = registerWithoutItem("fir_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), BloomingNatureWoodType.FIR));
    public static final RegistrySupplier<Block> FIR_HANGING_SIGN = registerWithoutItem("fir_hanging_sign", () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), BloomingNatureWoodType.FIR));
    public static final RegistrySupplier<Block> FIR_WALL_HANGING_SIGN = registerWithoutItem("fir_wall_hanging_sign", () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), BloomingNatureWoodType.FIR));
    public static final RegistrySupplier<Item> FIR_SIGN_ITEM = ITEMS.register("fir_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ObjectRegistry.FIR_SIGN.get(), ObjectRegistry.FIR_WALL_SIGN.get()));
    public static final RegistrySupplier<Item> FIR_HANGING_SIGN_ITEM = ITEMS.register("fir_hanging_sign", () -> new HangingSignItem(ObjectRegistry.FIR_HANGING_SIGN.get(), ObjectRegistry.FIR_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Block> CACTUS_SIGN = registerWithoutItem("cactus_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), BloomingNatureWoodType.CACTUS));
    public static final RegistrySupplier<Block> CACTUS_WALL_SIGN = registerWithoutItem("cactus_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), BloomingNatureWoodType.CACTUS));
    public static final RegistrySupplier<Block> CACTUS_HANGING_SIGN = registerWithoutItem("cactus_hanging_sign", () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), BloomingNatureWoodType.CACTUS));
    public static final RegistrySupplier<Block> CACTUS_WALL_HANGING_SIGN = registerWithoutItem("cactus_wall_hanging_sign", () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), BloomingNatureWoodType.CACTUS));
    public static final RegistrySupplier<Item> CACTUS_SIGN_ITEM = ITEMS.register("cactus_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ObjectRegistry.CACTUS_SIGN.get(), ObjectRegistry.CACTUS_WALL_SIGN.get()));
    public static final RegistrySupplier<Item> CACTUS_HANGING_SIGN_ITEM = ITEMS.register("cactus_hanging_sign", () -> new HangingSignItem(ObjectRegistry.CACTUS_HANGING_SIGN.get(), ObjectRegistry.CACTUS_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Item> ASPEN_BOAT = ITEMS.register("aspen_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.ASPEN, new Item.Properties()));
    public static final RegistrySupplier<Item> ASPEN_CHEST_BOAT = ITEMS.register("aspen_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.ASPEN, new Item.Properties()));
    public static final RegistrySupplier<Item> BAOBAB_BOAT = ITEMS.register("baobab_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.BAOBAB, new Item.Properties()));
    public static final RegistrySupplier<Item> BAOBAB_CHEST_BOAT = ITEMS.register("baobab_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.BAOBAB, new Item.Properties()));
    public static final RegistrySupplier<Item> LARCH_BOAT = ITEMS.register("larch_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.LARCH, new Item.Properties()));
    public static final RegistrySupplier<Item> LARCH_CHEST_BOAT = ITEMS.register("larch_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.LARCH, new Item.Properties()));
    public static final RegistrySupplier<Item> EBONY_BOAT = ITEMS.register("ebony_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.EBONY, new Item.Properties()));
    public static final RegistrySupplier<Item> EBONY_CHEST_BOAT = ITEMS.register("ebony_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.EBONY, new Item.Properties()));
    public static final RegistrySupplier<Item> CHESTNUT_BOAT = ITEMS.register("chestnut_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.CHESTNUT, new Item.Properties()));
    public static final RegistrySupplier<Item> CHESTNUT_CHEST_BOAT = ITEMS.register("chestnut_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.CHESTNUT, new Item.Properties()));
    public static final RegistrySupplier<Item> SWAMP_OAK_BOAT = ITEMS.register("swamp_oak_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.SWAMP_OAK, new Item.Properties()));
    public static final RegistrySupplier<Item> SWAMP_OAK_CHEST_BOAT = ITEMS.register("swamp_oak_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.SWAMP_OAK, new Item.Properties()));
    public static final RegistrySupplier<Item> SWAMP_CYPRESS_BOAT = ITEMS.register("swamp_cypress_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.SWAMP_CYPRESS, new Item.Properties()));
    public static final RegistrySupplier<Item> SWAMP_CYPRESS_CHEST_BOAT = ITEMS.register("swamp_cypress_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.SWAMP_CYPRESS, new Item.Properties()));
    public static final RegistrySupplier<Item> FAN_PALM_BOAT = ITEMS.register("fan_palm_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.FAN_PALM, new Item.Properties()));
    public static final RegistrySupplier<Item> FAN_PALM_CHEST_BOAT = ITEMS.register("fan_palm_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.FAN_PALM, new Item.Properties()));
    public static final RegistrySupplier<Item> FIR_BOAT = ITEMS.register("fir_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.FIR, new Item.Properties()));
    public static final RegistrySupplier<Item> FIR_CHEST_BOAT = ITEMS.register("fir_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.FIR, new Item.Properties()));
    public static final RegistrySupplier<Item> CACTUS_BOAT = ITEMS.register("cactus_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.CACTUS, new Item.Properties()));
    public static final RegistrySupplier<Item> CACTUS_CHEST_BOAT = ITEMS.register("cactus_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.CACTUS, new Item.Properties()));

    public static void init() {
        ITEMS.register();
        BLOCKS.register();
    }

    private static RegistrySupplier<Block> registerLog(String path) {
        return registerWithItem(path, () -> new RotatedPillarBlock(getLogBlockSettings()));
    }

    private static BlockBehaviour.Properties getLogBlockSettings() {
        return BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F).sound(SoundType.WOOD);
    }

    private static BlockBehaviour.Properties getSlabSettings() {
        return getLogBlockSettings().explosionResistance(3.0F);
    }

    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    static Item.Properties getSettings() {
        return getSettings(settings -> {
        });
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(BloomingNature.MOD_ID, name));
    }

    public static void commonInit() {
        FuelRegistry.register(300, FAN_PALM_FENCE.get(), FAN_PALM_FENCE_GATE.get(), FAN_PALM_PLANKS.get(), FAN_PALM_LOG.get(), FAN_PALM_WOOD.get(),
                STRIPPED_FAN_PALM_LOG.get(), STRIPPED_FAN_PALM_WOOD.get(), BAOBAB_PLANKS.get(), BAOBAB_SLAB.get(), BAOBAB_STAIRS.get(), BAOBAB_FENCE.get(),
                SWAMP_OAK_PLANKS.get(), SWAMP_OAK_SLAB.get(), SWAMP_OAK_STAIRS.get(), SWAMP_OAK_FENCE.get(), SWAMP_OAK_FENCE_GATE.get(),
                SWAMP_CYPRESS_PLANKS.get(), SWAMP_CYPRESS_SLAB.get(), SWAMP_CYPRESS_STAIRS.get(), SWAMP_CYPRESS_FENCE.get(), SWAMP_CYPRESS_FENCE_GATE.get(),
                LARCH_PLANKS.get(), LARCH_SLAB.get(), LARCH_STAIRS.get(), LARCH_FENCE.get(), LARCH_FENCE_GATE.get(), BAOBAB_FENCE_GATE.get(),
                FIR_PLANKS.get(), FIR_SLAB.get(), FIR_STAIRS.get(), FIR_FENCE.get(), FIR_FENCE_GATE.get(),
                CHESTNUT_PLANKS.get(), CHESTNUT_SLAB.get(), CHESTNUT_STAIRS.get(), CHESTNUT_FENCE.get(), CHESTNUT_FENCE_GATE.get(),
                FAN_PALM_PLANKS.get(), FAN_PALM_SLAB.get(), FAN_PALM_STAIRS.get(), FAN_PALM_FENCE.get(), FAN_PALM_FENCE_GATE.get(),
                ASPEN_PLANKS.get(), ASPEN_SLAB.get(), ASPEN_STAIRS.get(), ASPEN_FENCE.get(), ASPEN_FENCE_GATE.get(),
                EBONY_PLANKS.get(), EBONY_SLAB.get(), EBONY_STAIRS.get(), EBONY_FENCE.get(), EBONY_FENCE_GATE.get(),
                SWAMP_OAK_LOG.get(), SWAMP_OAK_WOOD.get(), STRIPPED_SWAMP_OAK_LOG.get(), STRIPPED_SWAMP_OAK_WOOD.get(),
                SWAMP_CYPRESS_LOG.get(), SWAMP_CYPRESS_WOOD.get(), STRIPPED_SWAMP_CYPRESS_LOG.get(), STRIPPED_SWAMP_CYPRESS_WOOD.get(),
                LARCH_LOG.get(), LARCH_WOOD.get(), STRIPPED_LARCH_LOG.get(), STRIPPED_LARCH_WOOD.get(),
                FIR_LOG.get(), FIR_WOOD.get(), STRIPPED_FIR_LOG.get(), STRIPPED_FIR_WOOD.get(),
                CHESTNUT_LOG.get(), CHESTNUT_WOOD.get(), STRIPPED_CHESTNUT_LOG.get(), STRIPPED_CHESTNUT_WOOD.get(),
                FAN_PALM_LOG.get(), FAN_PALM_WOOD.get(), STRIPPED_FAN_PALM_LOG.get(), STRIPPED_FAN_PALM_WOOD.get(),
                ASPEN_LOG.get(), ASPEN_WOOD.get(), STRIPPED_ASPEN_LOG.get(), STRIPPED_ASPEN_WOOD.get(),
                EBONY_LOG.get(), EBONY_WOOD.get(), STRIPPED_EBONY_LOG.get(), STRIPPED_EBONY_WOOD.get(),
                BAOBAB_LOG.get(), BAOBAB_WOOD.get(), STRIPPED_BAOBAB_LOG.get(), STRIPPED_BAOBAB_WOOD.get());
    }

    private static ButtonBlock woodenButton(FeatureFlag... featureFlags) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            properties = properties.requiredFeatures(featureFlags);
        }

        return new ButtonBlock(properties, BlockSetType.OAK, 30, true);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block) {
        return BloomingNatureUtil.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, new BloomingNatureIdentifier(name), block);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return BloomingNatureUtil.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, new BloomingNatureIdentifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return BloomingNatureUtil.registerItem(ITEMS, ITEM_REGISTRAR, new BloomingNatureIdentifier(path), itemSupplier);
    }
}
