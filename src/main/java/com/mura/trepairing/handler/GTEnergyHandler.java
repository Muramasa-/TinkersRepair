package com.mura.trepairing.handler;

import com.mura.trepairing.util.Ref;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.world.BlockEvent;
import tconstruct.library.tools.ToolCore;

public class GTEnergyHandler {
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if(!event.world.isRemote) {
            EntityPlayer player = event.getPlayer();
            if(player == null) return;
            ItemStack stack = player.getCurrentEquippedItem();
            if(stack == null) return;
            if(stack.getItem() instanceof ToolCore && stack.hasTagCompound()) {
                NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");
                if(tags.hasKey(Ref.NBTKeys.TINKERS_HAS_GTENERGY)) {
                    //Is Tinkers Tool With GT Energy Modifier.
                    if(Ref.Values.debugText) System.out.println("Is Tinkers Tool With GT Energy Modifier");

                }
            }
        }
    }
}
