package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.data.worldgen.BlockStateProviderBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FeatureSizeBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FoliagePlacerBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.TreeDecoratorBuilder;
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.TrunkPlacerBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.world.Heightmap;

public class TreeFeatureConfigBuilder extends FeatureConfigBuilder {

    public TreeFeatureConfigBuilder() {
        super();
        this.root.add("decorators", new JsonArray());
    }

    public TreeFeatureConfigBuilder maxWaterDepth(int maxWaterDepth) {
        this.root.addProperty("max_water_depth", maxWaterDepth);
        return this;
    }

    public TreeFeatureConfigBuilder ignoreVines(boolean ignoreVines) {
        this.root.addProperty("ignore_vines", ignoreVines);
        return this;
    }

    public <P extends BlockStateProviderBuilder> TreeFeatureConfigBuilder trunkProvider(Processor<P> providerProcessor, P instance) {
        with("trunk_provider", JsonObject::new, jsonObject -> providerProcessor.process(instance).buildTo(jsonObject));
        return this;
    }

    public <P extends BlockStateProviderBuilder> TreeFeatureConfigBuilder leavesProvider(Processor<P> providerProcessor, P instance) {
        with("leaves_provider", JsonObject::new, jsonObject -> providerProcessor.process(instance).buildTo(jsonObject));
        return this;
    }

    public <P extends FoliagePlacerBuilder> TreeFeatureConfigBuilder foliagePlacer(Processor<P> providerProcessor, P instance) {
        with("foliage_placer", JsonObject::new, jsonObject -> providerProcessor.process(instance).buildTo(jsonObject));
        return this;
    }

    public <P extends TrunkPlacerBuilder> TreeFeatureConfigBuilder trunkPlacer(Processor<P> providerProcessor, P instance) {
        with("trunk_placer", JsonObject::new, jsonObject -> providerProcessor.process(instance).buildTo(jsonObject));
        return this;
    }

    public <P extends FeatureSizeBuilder> TreeFeatureConfigBuilder minimumSize(Processor<P> providerProcessor, P instance) {
        with("minimum_size", JsonObject::new, jsonObject -> providerProcessor.process(instance).buildTo(jsonObject));
        return this;
    }

    public <D extends TreeDecoratorBuilder> TreeFeatureConfigBuilder addDecorator(Processor<D> processor, D instance) {
        this.root.getAsJsonArray("decorators").add(processor.process(instance).buildTo(new JsonObject()));
        return this;
    }

    public TreeFeatureConfigBuilder heightmap(Heightmap.Type type) {
        this.root.addProperty("heightmap", type.getName());
        return this;
    }
}
