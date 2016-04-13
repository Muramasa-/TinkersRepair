package com.mura.trepairing.handler;

import com.mura.trepairing.util.MuraUtils;
import com.mura.trepairing.util.Ref;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import tconstruct.library.tools.ToolCore;

public class TooltipHandler {
    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if(event.itemStack == null) return;
        if(event.itemStack.getItem() instanceof ToolCore) {
            if(event.itemStack.hasTagCompound()) {
                NBTTagCompound tags = event.itemStack.getTagCompound().getCompoundTag("InfiTool");
                if(tags.hasKey(Ref.NBTKeys.TINKERS_CONDITION)) {
                    event.toolTip.add(MuraUtils.getConditionString(tags.getInteger(Ref.NBTKeys.TINKERS_CONDITION)));
                }
            }
        }
    }
}
