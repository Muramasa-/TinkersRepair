package com.mura.trepairing.modifiers;

import com.mura.trepairing.util.MuraUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.ToolCore;

public class ModUnwork extends ItemModifier {

    public ModUnwork() {
        super(new ItemStack[0], 0, "ModUnworkTool");
    }

    @Override
    public boolean matches(ItemStack[] input, ItemStack tool) {
        return canModify(tool, input);
    }

    @Override
    protected boolean canModify(ItemStack input, ItemStack[] recipe) {
        for(ItemStack stack : recipe) {
            if(stack == null) return false;
            if(stack.isItemEqual(new ItemStack(Items.baked_potato)) && input.getItem() instanceof ToolCore) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addMatchingEffect(ItemStack tool) {
        //NOOP
    }

    @Override
    public void modify(ItemStack[] itemStacks, ItemStack tool) {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        MuraUtils.changeDamage(tags, tool, tags.getInteger("BaseDurability"), 4);
        tags.setBoolean(key, true);
    }
}
