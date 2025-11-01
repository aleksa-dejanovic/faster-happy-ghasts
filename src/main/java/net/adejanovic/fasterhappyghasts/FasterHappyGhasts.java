package net.adejanovic.fasterhappyghasts;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.HappyGhast;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FasterHappyGhasts.MODID)
public class FasterHappyGhasts {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "fasterhappyghasts";
    // Directly reference a slf4j logger
    // public static final Logger LOGGER = LogUtils.getLogger();


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public FasterHappyGhasts(IEventBus modEventBus, ModContainer modContainer) {

        // Register ourselves for server and other game events we are interested in.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void onMount(EntityMountEvent event) {
        if (event.isMounting()
                && event.getEntityMounting() instanceof Player player
                && event.getEntityBeingMounted() instanceof HappyGhast ghast) {
            Objects.requireNonNull(ghast.getAttribute(Attributes.FLYING_SPEED)).setBaseValue(Config.GHAST_MOUNT_SPEED.get());
        }
    }

    @SubscribeEvent
    public void onDismount(EntityMountEvent event) {
        if (event.isDismounting()
                && event.getEntityBeingMounted() instanceof HappyGhast ghast
                && ghast.getPassengers().stream().noneMatch(e -> e instanceof Player)) {
            Objects.requireNonNull(ghast.getAttribute(Attributes.FLYING_SPEED)).setBaseValue(Config.GHAST_DEFAULT_SPEED.get());
        }
    }
}
