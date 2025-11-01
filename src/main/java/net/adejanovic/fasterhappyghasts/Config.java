package net.adejanovic.fasterhappyghasts;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// A config class to keep config organized.
public class Config {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.DoubleValue GHAST_MOUNT_SPEED;
    public static final ModConfigSpec.DoubleValue GHAST_DEFAULT_SPEED;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("ghast");
        GHAST_MOUNT_SPEED = builder
                .comment("Flying speed when mounted")
                .defineInRange("mount_speed", 0.7, 0.0, 1.0);
        GHAST_DEFAULT_SPEED = builder
                .comment("Flying speed when not mounted")
                .defineInRange("default_speed", 0.05, 0.0, 1.0);
        builder.pop();

        SPEC = builder.build();
    }
}
