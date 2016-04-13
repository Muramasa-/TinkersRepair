package com.mura.trepairing.modifiers;

import com.mura.trepairing.util.MuraUtils;
import com.mura.trepairing.util.Ref;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.AbilityHelper;
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
        NBTTagCompound tags = input.getTagCompound().getCompoundTag("InfiTool");
        for(ItemStack stack : recipe) {
            if(stack == null) return false;
            if(tags.getBoolean("Broken")) return false;
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
        tags.setInteger("Damage", tags.getInteger("BaseDurability"));
        tags.setBoolean("Broken", true);
        MuraUtils.addConditionKey(tags);
        tags.setInteger(Ref.NBTKeys.TINKERS_CONDITION, 4);
        AbilityHelper.damageTool(tool, 0, null, true);
        tags.setBoolean(key, true);
    }
}
