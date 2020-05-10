package com.swordglowsblue.artifice.test;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.api.builder.data.AdvancementBuilder;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArtificeDataTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "artifice:advancements/test_advancement.json",
            "artifice:loot_tables/test_loottable.json",
            "artifice:tags/items/test_item_tag.json",
            "artifice:tags/blocks/test_block_tag.json",
            "artifice:tags/entity_types/test_entity_tag.json",
            "artifice:tags/fluids/test_fluid_tag.json",
            "artifice:tags/functions/test_function_tag.json"
    })
    void testDataResources(String path) throws IOException {
        assertEquals(Util.readFile("data_ref/data/" + path.replace(":", "/")), Util.getResource(data, path));
    }

    @Test
    void testDataMeta() throws IOException {
        assertEquals(data.getType(), ResourceType.SERVER_DATA);
        assertEquals(data.getName(), "Artifice Test");
        assertEquals(Util.readFile("data_ref/pack.mcmeta"), Util.getRootResource(data, "pack.mcmeta"));
    }

    @Test
    void testDataFiles() throws IOException {
        data.dumpResources(Util.ROOT + "data_dump");
        Util.compareDirectoryToDump(Paths.get(Util.ROOT + "data_ref"), "_ref", "_dump");
    }

    private ArtificeResourcePack data = ArtificeResourcePack.ofData(pack -> {
        pack.setDisplayName("Artifice Test");
        pack.setDescription("Artifice test pack");

        pack.addAdvancement(new Identifier("artifice:test_advancement"), advancement -> advancement
                .parent(new Identifier("artifice:parent"))
                .criteria("criteria0", criteria -> criteria
                        .trigger(new Identifier("artifice:trigger0"))
                        .conditions(cond -> cond.add("test", 0)))
                .criteria("criteria1", criteria -> criteria
                        .trigger(new Identifier("artifice:trigger1"))
                        .conditions(cond -> cond.add("test", 1)))
                .display(display -> display
                        .title("title")
                        .description("desc")
                        .icon(new Identifier("artifice:icon"), "nbt")
                        .background(new Identifier("artifice:background"))
                        .frame(AdvancementBuilder.Display.Frame.CHALLENGE)
                        .hidden(true)
                        .announceToChat(true)
                        .showToast(true))
                .requirement("criteria0", "criteria1"));

        pack.addLootTable(new Identifier("artifice:test_loottable"), loot -> loot
                .type(new Identifier("artifice:type"))
                .pool(pool -> pool.rolls(0).bonusRolls(0))
                .pool(pool -> pool
                        .rolls(0, 5)
                        .bonusRolls(0, 5)
                        .condition(new Identifier("artifice:condition0"), cond -> cond.add("test", 0))
                        .condition(new Identifier("artifice:condition1"), cond -> cond.add("test", 1))
                        .entry(entry -> entry.name(new Identifier("artifice:entry0")))
                        .entry(entry -> entry
                                .type(new Identifier("artifice:type"))
                                .name(new Identifier("artifice:entry1"))
                                .expand(false)
                                .condition(new Identifier("artifice:condition0"), cond -> cond.add("test", 0))
                                .condition(new Identifier("artifice:condition1"), cond -> cond.add("test", 1))
                                .weight(1)
                                .quality(9)
                                .child(entry1 -> entry1.name(new Identifier("artifice:child0")))
                                .child(entry1 -> entry1.name(new Identifier("artifice:child1")))
                                .function(new Identifier("artifice:function0"), function -> function
                                        .condition(new Identifier("artifice:condition0"), cond -> cond.add("test", 0))
                                        .condition(new Identifier("artifice:condition1"), cond -> cond.add("test", 1)))
                                .function(new Identifier("artifice:function1"), function -> function
                                        .condition(new Identifier("artifice:condition0"), cond -> cond.add("test", 0))
                                        .condition(new Identifier("artifice:condition1"), cond -> cond.add("test", 1))))));

        pack.addItemTag(new Identifier("artifice:test_item_tag"), tag -> tag
                .replace(false)
                .include(new Identifier("artifice:tag"))
                .value(new Identifier("artifice:value0"))
                .values(
                        new Identifier("artifice:value1"),
                        new Identifier("artifice:value2")
                ));
        pack.addBlockTag(new Identifier("artifice:test_block_tag"), tag -> tag
                .replace(false)
                .include(new Identifier("artifice:tag"))
                .value(new Identifier("artifice:value0"))
                .values(
                        new Identifier("artifice:value1"),
                        new Identifier("artifice:value2")
                ));
        pack.addEntityTypeTag(new Identifier("artifice:test_entity_tag"), tag -> tag
                .replace(false)
                .include(new Identifier("artifice:tag"))
                .value(new Identifier("artifice:value0"))
                .values(
                        new Identifier("artifice:value1"),
                        new Identifier("artifice:value2")
                ));
        pack.addFluidTag(new Identifier("artifice:test_fluid_tag"), tag -> tag
                .replace(false)
                .include(new Identifier("artifice:tag"))
                .value(new Identifier("artifice:value0"))
                .values(
                        new Identifier("artifice:value1"),
                        new Identifier("artifice:value2")
                ));
        pack.addFunctionTag(new Identifier("artifice:test_function_tag"), tag -> tag
                .replace(false)
                .include(new Identifier("artifice:tag"))
                .value(new Identifier("artifice:value0"))
                .values(
                        new Identifier("artifice:value1"),
                        new Identifier("artifice:value2")
                ));
    });
}
