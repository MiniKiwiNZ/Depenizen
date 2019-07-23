package com.denizenscript.depenizen.bukkit.events.essentials;

import com.denizenscript.denizen.BukkitScriptEntryData;
import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizen.objects.PlayerTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import com.denizenscript.denizencore.scripts.containers.ScriptContainer;
import com.denizenscript.denizencore.utilities.CoreUtilities;
import net.ess3.api.events.JailStatusChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJailStatusScriptEvent extends BukkitScriptEvent implements Listener {

    // <--[event]
    // @Events
    // player jailed
    // player unjailed
    // player un-jailed
    // player jail status changes
    //
    // @Regex ^on player (jailed|unjailed|un-jailed|jail status changes)$
    //
    // @Cancellable true
    //
    // @Triggers when a player's jail status changes.
    //
    // @Context
    // <context.status> Returns the player's jail status.
    //
    // @Plugin Depenizen, Essentials
    //
    // -->

    public static PlayerJailStatusScriptEvent instance;
    public JailStatusChangeEvent event;
    public ElementTag jailed;

    public PlayerJailStatusScriptEvent() {
        instance = this;
    }

    @Override
    public boolean couldMatch(ScriptContainer scriptContainer, String s) {
        String lower = CoreUtilities.toLowerCase(s);
        return lower.startsWith("player jail")
                || lower.startsWith("player unjailed")
                || lower.startsWith("player un-jailed");
    }

    @Override
    public boolean matches(ScriptPath path) {
        String status = path.eventArgLowerAt(1);
        if (status.equals("jailed") && jailed.asBoolean()) {
            return true;
        }
        else if ((status.equals("unjailed") || status.equals("un-jailed")) && !jailed.asBoolean()) {
            return true;
        }
        else {
            return status.equals("status");
        }
    }

    @Override
    public String getName() {
        return "PlayerJailStatus";
    }

    @Override
    public ScriptEntryData getScriptEntryData() {
        return new BukkitScriptEntryData(PlayerTag.mirrorBukkitPlayer(event.getAffected().getBase()), null);
    }

    @Override
    public ObjectTag getContext(String name) {
        if (name.equals("status")) {
            return jailed;
        }
        return super.getContext(name);
    }

    @EventHandler
    public void onPlayerAFKStatus(JailStatusChangeEvent event) {
        jailed = new ElementTag(event.getValue());
        this.event = event;
        fire(event);
    }
}
