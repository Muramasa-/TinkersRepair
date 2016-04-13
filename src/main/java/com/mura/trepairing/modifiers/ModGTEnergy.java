package com.mura.trepairing.modifiers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.modifier.ItemModifier;

public class ModGTEnergy extends ItemModifier {

    public ModGTEnergy() {
        super(new ItemStack[0], 0, "GTEnergyMod");
    }

    @Override
    public boolean matches(ItemStack[] input, ItemStack tool) {
        return canModify(tool, input);
    }

    @Override
    protected boolean canModify(ItemStack input, ItemStack[] recipe) {
        NBTTagCompound tags = input.getTagCompound().getCompoundTag("InfiTool");
       return false;
    }

    @Override
    public void addMatchingEffect(ItemStack tool) {
        //NOOP
    }

    @Override
    public void modify(ItemStack[] itemStacks, ItemStack tool) {

    }
}
