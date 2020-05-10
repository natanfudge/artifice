package com.swordglowsblue.artifice.api.builder.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;
import net.minecraft.util.Identifier;

/**
 * Builder for loot table files ({@code namespace:loot_tables/type/lootid.json}).
 * @see <a href="https://minecraft.gamepedia.com/Loot_table" target="_blank">Minecraft Wiki</a>
 */
public final class LootTableBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public LootTableBuilder() { super(new JsonObject(), JsonResource::new); }

    /**
     * Set the type of this loot table.
     * @param id The type ID.
     * @return this
     */
    public LootTableBuilder type(Identifier id) {
        root.addProperty("type", id.toString());
        return this;
    }

    /**
     * Add a pool to this loot table.
     * @param settings A callback which will be passed a {@link Pool}.
     * @return this
     */
    public LootTableBuilder pool(Processor<Pool> settings) {
        with("pools", JsonArray::new, pools -> pools.add(settings.process(new Pool()).build()));
        return this;
    }

    /**
     * Builder for loot table pools.
     * @see LootTableBuilder
     */
    public static final class Pool extends TypedJsonBuilder<JsonObject> {
        private Pool() { super(new JsonObject(), j->j); }

        /**
         * Add an entry to this pool.
         * @param settings A callback which will be passed an {@link Entry}.
         * @return this
         */
        public Pool entry(Processor<Entry> settings) {
            with("entries", JsonArray::new, entries ->
                entries.add(settings.process(new Entry()).build()));
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
        public Pool condition(Identifier id, Processor<JsonObjectBuilder> settings) {
            with("conditions", JsonArray::new, conditions ->
                conditions.add(settings.process(new JsonObjectBuilder().add("condition", id.toString())).build()));
            return this;
        }

        /**
         * Set the number of rolls to apply this pool for.
         * @param rolls The number of rolls.
         * @return this
         */
        public Pool rolls(int rolls) {
            root.addProperty("rolls", rolls);
            return this;
        }

        /**
         * Set the number of rolls to apply this pool for as a range from which to randomly select a number.
         * @param min The minimum number of rolls (inclusive).
         * @param max The maximum number of rolls (inclusive).
         * @return this
         */
        public Pool rolls(int min, int max) {
            root.add("rolls", new JsonObjectBuilder().add("min", min).add("max", max).build());
            return this;
        }

        /**
         * Set the number of bonus rolls to apply this pool for per point of luck.
         * @param rolls The number of rolls.
         * @return this
         */
        public Pool bonusRolls(float rolls) {
            root.addProperty("bonus_rolls", rolls);
            return this;
        }

        /**
         * Set the number of bonus rolls to apply this pool for per point of luck as a range from which to randomly select a number.
         * @param min The minimum number of rolls (inclusive).
         * @param max The maximum number of rolls (inclusive).
         * @return this
         */
        public Pool bonusRolls(float min, float max) {
            root.add("bonus_rolls", new JsonObjectBuilder().add("min", min).add("max", max).build());
            return this;
        }

        /**
         * Builder for a loot table pool entry.
         * @see Pool
         */
        public static final class Entry extends TypedJsonBuilder<JsonObject> {
            private Entry() { super(new JsonObject(), j->j); }

            /**
             * Set the type of this entry.
             * @param id The type ID.
             * @return this
             */
            public Entry type(Identifier id) {
                root.addProperty("type", id.toString());
                return this;
            }

            /**
             * Set the name of this entry's value. Expected value varies by type.
             * @param id The name of the value as an ID.
             * @return this
             */
            public Entry name(Identifier id) {
                root.addProperty("name", id.toString());
                return this;
            }

            /**
             * Add a child to this entry.
             * @param settings A callback which will be passed an {@link Entry}.
             * @return this
             */
            public Entry child(Processor<Entry> settings) {
                with("children", JsonArray::new, children -> children.add(settings.process(new Entry()).build()));
                return this;
            }

            /**
             * For type {@code tag}, set whether to use the given tag as a list of equally weighted options or to use all tag entries.
             * @param expand Whether to expand.
             * @return this
             */
            public Entry expand(boolean expand) {
                root.addProperty("expand", expand);
                return this;
            }

            /**
             * Set the relative weight of this entry.
             * @param weight The weight.
             * @return this
             */
            public Entry weight(int weight) {
                root.addProperty("weight", weight);
                return this;
            }

            /**
             * Set the quality of this entry (modifies the weight based on the player's luck attribute).
             * @param quality The quality.
             * @return this
             */
            public Entry quality(int quality) {
                root.addProperty("quality", quality);
                return this;
            }

            /**
             * Add a function to be applied to this entry.
             * @param id The function ID.
             * @param settings A callback which will be passed a {@link Function}.
             * @return this
             * @see <a href="https://minecraft.gamepedia.com/Loot_table#Functions" target="_blank">Minecraft Wiki</a>
             */
            public Entry function(Identifier id, Processor<Function> settings) {
                with("functions", JsonArray::new, functions ->
                    functions.add(settings.process(new Function(new JsonObjectBuilder().add("function", id.toString()).build())).build()));
                return this;
            }

            /**
             * Add a condition to this entry. All conditions must pass for the entry to be used.
             * The specific properties of this vary by condition, so this falls through to direct JSON building.
             *
             * @param id The condition ID.
             * @param settings A callback which will be passed a {@link JsonObjectBuilder}.
             * @return this
             * @see <a href="https://minecraft.gamepedia.com/Loot_table#Conditions" target="_blank">Minecraft Wiki</a>
             */
            public Entry condition(Identifier id, Processor<JsonObjectBuilder> settings) {
                with("conditions", JsonArray::new, conditions ->
                        conditions.add(settings.process(new JsonObjectBuilder().add("condition", id.toString())).build()));
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
                public Function condition(Identifier id, Processor<JsonObjectBuilder> settings) {
                    with("conditions", JsonArray::new, conditions ->
                        conditions.add(settings.process(new JsonObjectBuilder().add("condition", id.toString())).build()));
                    return this;
                }
            }
        }
    }
}
