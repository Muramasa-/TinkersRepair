package com.mura.trepairing.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import tconstruct.library.tools.AbilityHelper;

public class MuraUtils {
    private static String[] keys = new String[]{"", "TGregRepair"};

    public static String getConditionString(int condition) {
        String output = "Condition: ";
        switch (condition) {
            case 0: output = output + EnumChatFormatting.GREEN + "Newly Forged";
                break;
            case 1: output = output + EnumChatFormatting.YELLOW + "Good";
                break;
            case 2: output = output + EnumChatFormatting.GOLD + "Okay";
                break;
            case 3: output = output + EnumChatFormatting.RED + "Brittle";
                break;
            case 4: output = output + EnumChatFormatting.DARK_RED + "Unworkable";
                break;
        }
        return output;
    }

    public static void changeDamage(NBTTagCompound tags, ItemStack tool, int damage, int condition) {
        MuraUtils.checkKeys(tags);
        tags.setInteger("Damage", damage);
        if(!(condition == -1))
            tags.setInteger(Ref.NBTKeys.TINKERS_CONDITION, condition);
        AbilityHelper.damageTool(tool, 0, null, true);
    }

    public static void breakTool(NBTTagCompound tags, ItemStack tool, boolean broken) {
        MuraUtils.checkKeys(tags);
        tags.setBoolean("Broken", broken);
        AbilityHelper.damageTool(tool, 0, null, true);
    }

    public static void checkKeys(NBTTagCompound tags) {
        if(!tags.hasKey(Ref.NBTKeys.TINKERS_CONDITION)) {
            tags.setInteger(Ref.NBTKeys.TINKERS_CONDITION, 0);
        }
        if(!tags.hasKey(Ref.NBTKeys.TINKERS_MANIPULATE)) {
            tags.setBoolean(Ref.NBTKeys.TINKERS_MANIPULATE, false);
        }
    }

    public static boolean isValidModKey(String str) {
        for(int i=0; i < keys.length; i++) {
            if(str.equals(keys[i]))
                return true;
        }
        return false;
    }
}
