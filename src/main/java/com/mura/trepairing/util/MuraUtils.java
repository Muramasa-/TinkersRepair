package com.mura.trepairing.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.nbt.NBTTagCompound;

public class MuraUtils {
    private static String[] keys = new String[]{"", "TGregRepair"};

    public static String getConditionString(int condition) {
        String output = "Condition: ";
        switch (condition) {
            case 0: output = output + ChatFormatting.GREEN + "Excellent";
                break;
            case 1: output = output + ChatFormatting.YELLOW + "Good";
                break;
            case 2: output = output + ChatFormatting.GOLD + "Okay";
                break;
            case 3: output = output + ChatFormatting.RED + "Poor";
                break;
            case 4: output = output + ChatFormatting.DARK_RED + "Unworkable";
                break;
        }
        return output;
    }

    public static void addConditionKey(NBTTagCompound tags) {
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
