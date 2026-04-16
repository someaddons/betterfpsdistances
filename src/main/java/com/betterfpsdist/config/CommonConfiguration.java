package com.betterfpsdist.config;

import com.betterfpsdist.BetterfpsdistMod;
import com.cupboard.config.ICommonConfig;
import com.google.gson.JsonObject;

public class CommonConfiguration implements ICommonConfig
{
    public double  verticalScaling   = 2.0;
    public double  horizontalScaling = 1.1;
    public boolean debugMode         = false;
    public boolean affectEntities    = true;
    public boolean repositionSodiumOptions = true;

    public JsonObject serialize()
    {
        final JsonObject root = new JsonObject();

        final JsonObject entry = new JsonObject();
        entry.addProperty("desc:", "The amount by which the chunk render distance sphere is stretched vertically."
                                     + " default:2.0, min 0.5, max 10");
        entry.addProperty("verticalScaling", verticalScaling);
        root.add("verticalScaling", entry);


        final JsonObject entry2 = new JsonObject();
        entry2.addProperty("desc:", "The amount by which the chunk render distance sphere is stretched horizontally."
                                      + " default:1.0, min 0.05, max 2");
        entry2.addProperty("horizontalScaling", horizontalScaling);
        root.add("horizontalScaling", entry2);

        final JsonObject entry4 = new JsonObject();
        entry4.addProperty("desc:", "Enables the distance stretch to also affect entity rendering, default = true");
        entry4.addProperty("affectEntities", affectEntities);
        root.add("affectEntities", entry4);

        final JsonObject entry3 = new JsonObject();
        entry3.addProperty("desc:", "Enables debug mode, which displays how many sections are being hidden(A section is an area of 16x16x16 blocks)"
                                      + " default:false");
        entry3.addProperty("debugMode", debugMode);
        root.add("debugMode", entry3);

        final JsonObject entry5 = new JsonObject();
        entry5.addProperty("desc:",
            "Repositions the sodium video settings to a sub button of the original video settings. Disabling this also hides the horizontal/vertical stretch options in the original screen."
                + " default:true");
        entry5.addProperty("repositionSodiumOptions", repositionSodiumOptions);
        root.add("repositionSodiumOptions", entry5);

        return root;
    }

    public void deserialize(JsonObject data)
    {
        if (data == null)
        {
            BetterfpsdistMod.LOGGER.error("Config file was empty!");
            return;
        }

        verticalScaling = data.get("verticalScaling").getAsJsonObject().get("verticalScaling").getAsDouble();
        horizontalScaling = data.get("horizontalScaling").getAsJsonObject().get("horizontalScaling").getAsDouble();
        debugMode = data.get("debugMode").getAsJsonObject().get("debugMode").getAsBoolean();
        affectEntities = data.get("affectEntities").getAsJsonObject().get("affectEntities").getAsBoolean();
        repositionSodiumOptions = data.get("repositionSodiumOptions").getAsJsonObject().get("repositionSodiumOptions").getAsBoolean();
    }
}
