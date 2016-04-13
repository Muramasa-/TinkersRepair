package com.mura.trepairing.handler;

import com.mura.trepairing.util.MuraUtils;
import com.mura.trepairing.util.Ref;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.event.ModifyEvent;
import tconstruct.library.event.ToolCraftedEvent;
import tconstruct.library.tools.AbilityHelper;
import tconstruct.library.tools.ToolCore;

import java.util.Random;

public class ToolHandler {
    @SubscribeEvent
    public void onToolCraft(ToolCraftedEvent event) {
        if(event.player.worldObj.isRemote) return;
        if(event.tool == null || !(event.tool.getItem() instanceof ToolCore)) return;
        NBTTagCompound tags = event.tool.getTagCompound().getCompoundTag("InfiTool");
        if(!tags.hasKey(Ref.NBTKeys.TINKERS_CONDITION)) {
            System.out.println("Does not have condition key, adding it.");
            MuraUtils.addConditionKey(tags);
        }
        if(tags.getBoolean(Ref.NBTKeys.TINKERS_MANIPULATE)) {
            manipulateCondition(tags, event.tool, event.player);
            tags.setBoolean(Ref.NBTKeys.TINKERS_MANIPULATE, false);
        }
    }

    @SubscribeEvent
    public void onModify(ModifyEvent event) {
        if(event.itemStack.getItem() instanceof ToolCore && event.itemStack.hasTagCompound()) {
            NBTTagCompound tags = event.itemStack.getTagCompound().getCompoundTag("InfiTool");
            System.out.println("#" + event.modifier.key + "#");
            if(tags.getInteger(Ref.NBTKeys.TINKERS_CONDITION) >= 4 && !event.modifier.key.equals("ToolpartRepair")) {
                event.setCanceled(true);
            } else if(MuraUtils.isValidModKey(event.modifier.key)){
                tags.setBoolean(Ref.NBTKeys.TINKERS_MANIPULATE, true);
            }
        }
    }

    public static void manipulateCondition(NBTTagCompound tags, ItemStack stack, EntityPlayer player) {
        System.out.println("MANIPULATED CONDITION");
        Random rand = new Random();
        if(tags.getInteger(Ref.NBTKeys.TINKERS_CONDITION) < 4) {
            if (Ref.Values.iguanaSupport) {
                if (rand.nextInt(Ref.Values.degradeChanceIguana + tags.getInteger("ToolLevel")) == 1) {
                    tags.setInteger(Ref.NBTKeys.TINKERS_CONDITION, tags.getInteger(Ref.NBTKeys.TINKERS_CONDITION) + 1);
                }
            } else {
                if (rand.nextInt(Ref.Values.degradeChanceTinkers) == 1) {
                    tags.setInteger(Ref.NBTKeys.TINKERS_CONDITION, tags.getInteger(Ref.NBTKeys.TINKERS_CONDITION) + 1);
                }
            }
            if(tags.getInteger(Ref.NBTKeys.TINKERS_CONDITION) >= 4) {
                tags.setInteger("Damage", tags.getInteger("BaseDurability"));
                tags.setBoolean("Broken", true);
                AbilityHelper.damageTool(stack, 0, null, true);
                player.playSound("random.break", 1.0F, 1.0F);
            }
        }
    }
}
