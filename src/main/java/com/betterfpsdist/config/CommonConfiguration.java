package com.betterfpsdist.config;

import com.betterfpsdist.BetterfpsdistMod;
import com.google.gson.JsonObject;

public class CommonConfiguration
{
    public double stretch = 2.0;

    public JsonObject serialize()
    {
        final JsonObject root = new JsonObject();

        final JsonObject entry = new JsonObject();
        entry.addProperty("desc:", "The amount by which the chunk render distance sphere is stretched in horizontal direction."
                                     + " default:2.0, min 0.5, max 10");
        entry.addProperty("stretch", stretch);
        root.add("stretch", entry);

        return root;
    }

    public void deserialize(JsonObject data)
    {
        if (data == null)
        {
            BetterfpsdistMod.LOGGER.error("Config file was empty!");
            return;
        }

        try
        {
            stretch = data.get("stretch").getAsJsonObject().get("stretch").getAsDouble();
        }
        catch (Exception e)
        {
            BetterfpsdistMod.LOGGER.error("Could not parse config file", e);
        }
    }
}
