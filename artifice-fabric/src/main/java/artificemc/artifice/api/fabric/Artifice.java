package artificemc.artifice.api.fabric;

import artificemc.artifice.api.core.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

public final class Artifice {
    private Artifice() {}

    @Environment(EnvType.CLIENT)
    public static final SimpleRegistry<ArtificeResourcePack.Client> ASSETS =
        Registry.register(Registry.REGISTRIES, "artifice:assets_packs", new SimpleRegistry<>());
    public static final SimpleRegistry<ArtificeResourcePack.Server> DATA =
        Registry.register(Registry.REGISTRIES, "artifice:data_packs", new SimpleRegistry<>());

    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack.Client registerAssets(String id, Processor<ResourcePackBuilder.Client> register) {
        return Registry.register(ASSETS, id, new ArtificeResourcePack.Client(id, register)); }
    public static ArtificeResourcePack.Server registerData(String id, Processor<ResourcePackBuilder.Server> register) {
        return Registry.register(DATA, id, new ArtificeResourcePack.Server(id, register)); }

    @Environment(EnvType.CLIENT)
    public static ArtificeResourcePack registerAssets(String id, ArtificeResourcePack.Client pack) {
        return Registry.register(ASSETS, id, pack); }
    public static ArtificeResourcePack registerData(String id, ArtificeResourcePack.Server pack) {
        return Registry.register(DATA, id, pack); }
}
