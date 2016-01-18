package net.gnomeffinway.depenizen.support.plugins;


import com.wasteofplastic.askyblock.ASkyBlockAPI;
import net.aufdemrand.denizen.objects.dLocation;
import net.aufdemrand.denizen.objects.dPlayer;
import net.aufdemrand.denizen.objects.dWorld;
import net.aufdemrand.denizencore.events.ScriptEvent;
import net.aufdemrand.denizencore.objects.Element;
import net.aufdemrand.denizencore.tags.Attribute;
import net.gnomeffinway.depenizen.events.ASkyBlock.*;
import net.gnomeffinway.depenizen.events.Towny.PlayerExitsTownScriptEvent;
import net.gnomeffinway.depenizen.extensions.askyblock.ASkyBlockLocationExtension;
import net.gnomeffinway.depenizen.extensions.askyblock.ASkyBlockPlayerExtension;
import net.gnomeffinway.depenizen.extensions.askyblock.ASkyBlockWorldExtension;
import net.gnomeffinway.depenizen.support.Support;

public class ASkyBlockSupport extends Support {

    public ASkyBlockSupport() {
        registerProperty(ASkyBlockPlayerExtension.class, dPlayer.class);
        registerProperty(ASkyBlockLocationExtension.class, dLocation.class);
        registerProperty(ASkyBlockWorldExtension.class, dWorld.class);
        registerScriptEvents(new SkyBlockCreatedScriptEvent());
        registerScriptEvents(new SkyBlockResetScriptEvent());
        registerScriptEvents(new PlayerEntersSkyBlockScriptEvent());
        registerScriptEvents(new PlayerExitsSkyBlockScriptEvent());
        registerScriptEvents(new PlayerCompletesSkyBlockChallengeScriptEvent());
        // TODO: Skyblock Command
    }

    ASkyBlockAPI api = ASkyBlockAPI.getInstance();

    @Override
    public String additionalTags(Attribute attribute) {
        if (attribute == null) {
            return null;
        }

        if (attribute.startsWith("skyblock")) {
            attribute = attribute.fulfill(1);

            // <--[tag]
            // @attribute <skyblock.island_world>
            // @returns dWorld
            // @description
            // Returns the world that A Skyblock uses for islands.
            // @plugin Depenizen, A SkyBlock
            // -->
            if (attribute.startsWith("island_world")) {
                return new dWorld(api.getIslandWorld()).getAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <skyblock.nether_world>
            // @returns dWorld
            // @description
            // Returns the world that A Skyblock uses for the nether.
            // @plugin Depenizen, A SkyBlock
            // -->
            else if (attribute.startsWith("nether_world")) {
                return new dWorld(api.getNetherWorld()).getAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <skyblock.island_count>
            // @returns Element(Number)
            // @description
            // Returns the number of Skyblock Islands that exist.
            // @plugin Depenizen, A SkyBlock
            // -->
            else if (attribute.startsWith("island_count")) {
                return new Element(api.getIslandCount()).getAttribute(attribute.fulfill(1));
            }
        }

        return null;
    }

}
