package artificemc.artifice.api.builders.server;

import artificemc.artifice.api.builders.JsonObjectBuilder;
import artificemc.artifice.api.builders.TypedJsonBuilder;
import artificemc.artifice.api.core.resource.JsonResource;
import artificemc.artifice.api.core.util.Processor;
import artificemc.artifice.impl.core.util.IdUtils;
import com.google.gson.JsonObject;

/**
 * Builder for loot table files ({@code namespace:loot_tables/type/lootid.json}).
 * @see <a href="https://minecraft.gamepedia.com/Loot_table" target="_blank">Minecraft Wiki</a>
 */
public final class LootTableBuilder extends TypedJsonBuilder<JsonResource, LootTableBuilder> {
    public LootTableBuilder() { super(JsonResource::new); }

    /**
     * Set the type of this loot table.
     * @param id The type ID.
     * @return this
     */
    public LootTableBuilder type(String id) {
        IdUtils.validateIdentifier(id);
        this.set("type", id);
        return this;
    }

    /**
     * Add a pool to this loot table.
     * @param settings A callback which will be passed a {@link Pool}.
     * @return this
     */
    public LootTableBuilder pool(Processor<Pool> settings) {
        withArray("pools", pools -> pools.add(settings.process(new Pool()).build()));
        return this;
    }

    /**
     * Builder for loot table pools.
     * @see LootTableBuilder
     */
    public static final class Pool extends TypedJsonBuilder<JsonObject, Pool> {
        private Pool() { super(j->j); }

        /**
         * Add an entry to this pool.
         * @param settings A callback which will be passed an {@link Entry}.
         * @return this
         */
        public Pool entry(Processor<Entry> settings) {
            withArray("entries", entries -> entries.add(settings.process(new Entry()).build()));
            return this;
        }

        /**
         * Add a condition to this pool. All conditions must pass for the pool to be used.
         * The specific properties of this vary by condition, so this falls through to direct JSON building.
         *
         * @param id The condition ID.
         * @param settings A callback which will be passed a {@link JsonObjectBuilder}.
         * @return this
         * @see <a href="https://minecraft.gamepedia.com/Loot_table#Conditions" target="_blank">Minecraft Wiki</a>
         */
        public Pool condition(String id, Processor<JsonObjectBuilder> settings) {
            IdUtils.validateIdentifier(id);
            withArray("conditions", conditions ->
                conditions.add(settings.process(new JsonObjectBuilder().set("condition", id)).build()));
            return this;
        }

        /**
         * Set the number of rolls to apply this pool for.
         * @param rolls The number of rolls.
         * @return this
         */
        public Pool rolls(int rolls) {
            this.set("rolls", rolls);
            return this;
        }

        /**
         * Set the number of rolls to apply this pool for as a range from which to randomly select a number.
         * @param min The minimum number of rolls (inclusive).
         * @param max The maximum number of rolls (inclusive).
         * @return this
         */
        public Pool rolls(int min, int max) {
            this.set("rolls", new JsonObjectBuilder().set("min", min).set("max", max).build());
            return this;
        }

        /**
         * Set the number of bonus rolls to apply this pool for per point of luck.
         * @param rolls The number of rolls.
         * @return this
         */
        public Pool bonusRolls(float rolls) {
            this.set("bonus_rolls", rolls);
            return this;
        }

        /**
         * Set the number of bonus rolls to apply this pool for per point of luck as a range from which to randomly select a number.
         * @param min The minimum number of rolls (inclusive).
         * @param max The maximum number of rolls (inclusive).
         * @return this
         */
        public Pool bonusRolls(float min, float max) {
            this.set("bonus_rolls", new JsonObjectBuilder().set("min", min).set("max", max).build());
            return this;
        }

        /**
         * Builder for a loot table pool entry.
         * @see Pool
         */
        public static final class Entry extends TypedJsonBuilder<JsonObject, Entry> {
            private Entry() { super(j->j); }

            /**
             * Set the type of this entry.
             * @param id The type ID.
             * @return this
             */
            public Entry type(String id) {
                IdUtils.validateIdentifier(id);
                this.set("type", id);
                return this;
            }

            /**
             * Set the name of this entry's value. Expected value varies by type.
             * @param id The name of the value as an ID.
             * @return this
             */
            public Entry name(String id) {
                IdUtils.validateIdentifier(id);
                this.set("name", id);
                return this;
            }

            /**
             * Add a child to this entry.
             * @param settings A callback which will be passed an {@link Entry}.
             * @return this
             */
            public Entry child(Processor<Entry> settings) {
                withArray("children", children -> children.add(settings.process(new Entry()).build()));
                return this;
            }

            /**
             * For type {@code tag}, set whether to use the given tag as a list of equally weighted options or to use all tag entries.
             * @param expand Whether to expand.
             * @return this
             */
            public Entry expand(boolean expand) {
                this.set("expand", expand);
                return this;
            }

            /**
             * Set the relative weight of this entry.
             * @param weight The weight.
             * @return this
             */
            public Entry weight(int weight) {
                this.set("weight", weight);
                return this;
            }

            /**
             * Set the quality of this entry (modifies the weight based on the player's luck attribute).
             * @param quality The quality.
             * @return this
             */
            public Entry quality(int quality) {
                this.set("quality", quality);
                return this;
            }

            /**
             * Add a function to be applied to this entry.
             * @param id The function ID.
             * @param settings A callback which will be passed a {@link Function}.
             * @return this
             * @see <a href="https://minecraft.gamepedia.com/Loot_table#Functions" target="_blank">Minecraft Wiki</a>
             */
            public Entry function(String id, Processor<Function> settings) {
                IdUtils.validateIdentifier(id);
                withArray("functions", functions ->
                    functions.add(settings.process(new Function(new JsonObjectBuilder().set("function", id).build())).build()));
                return this;
            }

            /**
             * Builder for loot table entry functions.
             * @see Entry
             * @see <a href="https://minecraft.gamepedia.com/Loot_table#Functions" target="_blank">Minecraft Wiki</a>
             */
            public static final class Function extends JsonObjectBuilder {
                private Function(JsonObject func) { super(func); }

                /**
                 * Add a condition to this function. All conditions must pass for the function to be applied.
                 * The specific properties of this vary by condition, so this falls through to direct JSON building.
                 *
                 * @param id The condition ID.
                 * @param settings A callback which will be passed a {@link JsonObjectBuilder}.
                 * @return this
                 * @see <a href="https://minecraft.gamepedia.com/Loot_table#Conditions" target="_blank">Minecraft Wiki</a>
                 */
                public Function condition(String id, Processor<JsonObjectBuilder> settings) {
                    IdUtils.validateIdentifier(id);
                    withArray("conditions", conditions ->
                        conditions.add(settings.process(new JsonObjectBuilder().set("condition", id)).build()));
                    return this;
                }
            }
        }
    }
}
