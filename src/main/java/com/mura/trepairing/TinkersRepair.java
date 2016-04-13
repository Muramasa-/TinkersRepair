package com.mura.trepairing;

import com.mura.trepairing.handler.GTEnergyHandler;
import com.mura.trepairing.handler.ToolHandler;
import com.mura.trepairing.handler.TooltipHandler;
import com.mura.trepairing.modifiers.ModBreak;
import com.mura.trepairing.modifiers.ModGTEnergy;
import com.mura.trepairing.modifiers.ModToolpart;
import com.mura.trepairing.modifiers.ModUnwork;
import com.mura.trepairing.proxy.CommonProxy;
import com.mura.trepairing.util.Ref;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import tconstruct.library.crafting.ModifyBuilder;

@Mod(modid = Ref.MOD_ID, name = Ref.MOD_NAME, version = Ref.MOD_VERSION, dependencies = "required-after:TConstruct;")
public class TinkersRepair {
    @SidedProxy(clientSide = Ref.CLIENT_PROXY, serverSide = Ref.SERVER_PROXY)
    public static CommonProxy proxy;

    @Mod.Instance(Ref.MOD_ID)
    public static TinkersRepair instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        Ref.Values.debugText = config.getBoolean("DebugText", Configuration.CATEGORY_GENERAL, false, "This enables debugging text printed to the console during certain tool events.");
        Ref.Values.debugModifier = config.getBoolean("DebugModifiers", Configuration.CATEGORY_GENERAL, false, "This adds two modifiers for testing the condition degradation chance by breaking your tool or making it unworkable. The two items are coal and baked potato.");
        Ref.Values.iguanaSupport = config.getBoolean("IguanaTweaksSupport", Configuration.CATEGORY_GENERAL, false, "Uses your IguanaTweaks's tool level to scale the chance for your tools condition to regrade. High level = less chance");
        Ref.Values.gregtechSupport = config.getBoolean("GregTechSupport", Configuration.CATEGORY_GENERAL, false, "Enables the GregTech5 energy modifier. (WIP)");
        Ref.Values.repairMulti = config.getFloat("RepairMultiplier", Configuration.CATEGORY_GENERAL, 1.0F, 0.0F, 1.0F, "Scales the amount of tool durability returned when it is unworkable (And requires the tool head).");
        Ref.Values.degradeChanceIguana = config.getInt("DegradeChanceIguana", Configuration.CATEGORY_GENERAL, 3, 0, 100, "Between 0 and this number is the chance for your tool to degrade if iguana support is enabled.");
        Ref.Values.degradeChanceTinkers = config.getInt("DegradeChanceTinkers", Configuration.CATEGORY_GENERAL, 5, 0, 100, "Between 0 and this number is the chance for your tool to degrade. This is ignored if iguana support is enabled.");
        config.save();
        MinecraftForge.EVENT_BUS.register(new ToolHandler());
        MinecraftForge.EVENT_BUS.register(new TooltipHandler());
        MinecraftForge.EVENT_BUS.register(new GTEnergyHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        //NOOP
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ModifyBuilder.registerModifier(new ModToolpart());
        if(Ref.Values.debugModifier) {
            ModifyBuilder.registerModifier(new ModBreak());
            ModifyBuilder.registerModifier(new ModUnwork());
        }
        if(Loader.isModLoaded("gregtech") && Ref.Values.gregtechSupport) {
            ModifyBuilder.registerModifier(new ModGTEnergy());
        }
        if(Loader.isModLoaded("IguanaTweaksTConstruct") && Ref.Values.iguanaSupport) {
            Ref.Values.iguanaSupport = true;
        }
    }
}
