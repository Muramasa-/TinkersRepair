package com.mura.trepairing.modifiers;

import com.mura.trepairing.util.MuraUtils;
import com.mura.trepairing.util.Ref;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.items.tools.*;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.DynamicToolPart;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.util.IToolPart;
import vexatos.tgregworks.item.ItemTGregPart;

public class ModToolpart extends ItemModifier {
    public ModToolpart() {
        super(new ItemStack[0], 0, "ToolpartRepair");
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
            if(!tags.hasKey(Ref.NBTKeys.TINKERS_CONDITION)) {
                if(Ref.Values.debugText) System.out.println("Tinker tool does not have condition nbt key, this is an issue.");
                MuraUtils.checkKeys(tags);
                return false;
            }
            if(tags.getInteger(Ref.NBTKeys.TINKERS_CONDITION) >= 4){
                if (stack.getItem() instanceof IToolPart && input.getItem() instanceof ToolCore) {
                    int materialId = 0;
                    String partName = "";
                    //Normal Tinkers Support
                    if(stack.getItem() instanceof DynamicToolPart) {
                        DynamicToolPart part = (DynamicToolPart) stack.getItem();
                        materialId = part.getMaterialID(stack);
                        partName = part.partName;

                    }//Tinkers Gregworks Support
                    else if(stack.getItem() instanceof ItemTGregPart) {
                        ItemTGregPart part = (ItemTGregPart) stack.getItem();
                        materialId = part.getMaterialID(stack);
                        partName = part.func_77658_a().replaceAll("\\s+","");
                    }
                    if (materialId == tags.getInteger("Head")) {
                        if(Ref.Values.debugText) System.out.println("Part Name: " + partName);
                        return isValidPartType(input.getItem(), partName);
                    }
                }
            }
        }
        return false;
    }

    public boolean isValidPartType(Item input, String partName) {
        if(input instanceof Pickaxe)
            return partName.equalsIgnoreCase("PickaxeHead");
        else if(input instanceof Shovel)
            return partName.equalsIgnoreCase("ShovelHead");
        else if(input instanceof Hatchet)
            return partName.equalsIgnoreCase("AxeHead");
        else if(input instanceof Broadsword)
            return partName.equalsIgnoreCase("SwordBlade");
        else if(input instanceof Longsword)
            return partName.equalsIgnoreCase("SwordBlade");
        else if(input instanceof Rapier)
            return partName.equalsIgnoreCase("SwordBlade");
        else if(input instanceof Dagger)
            return partName.equalsIgnoreCase("KnifeBlade");
        else if(input instanceof Cutlass)
            return partName.equalsIgnoreCase("SwordBlade");
        else if(input instanceof FryingPan)
            return partName.equalsIgnoreCase("FrypanHead");
        else if(input instanceof BattleSign)
            return partName.equalsIgnoreCase("SignHead");
        else if(input instanceof Mattock)
            return partName.equalsIgnoreCase("AxeHead");
        else if(input instanceof Chisel)
            return partName.equalsIgnoreCase("ChiselHead");
        else if(input instanceof LumberAxe)
            return partName.equalsIgnoreCase("LumberAxeHead");
        else if(input instanceof Cleaver)
            return partName.equalsIgnoreCase("LargeSwordBlade");
        else if(input instanceof Scythe)
            return partName.equalsIgnoreCase("ScytheHead");
        else if(input instanceof Excavator)
            return partName.equalsIgnoreCase("ExcavatorHead");
        else if(input instanceof Hammer)
            return partName.equalsIgnoreCase("HammerHead");
        else if(input instanceof Battleaxe) {
            return partName.equalsIgnoreCase("LumberAxeHead");
        } else
            return false;
    }

    @Override
    public void addMatchingEffect(ItemStack tool) {
        //NOOP
    }

    @Override
    public void modify(ItemStack[] itemStacks, ItemStack tool) {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        int damage = (int) Math.floor(tags.getInteger("TotalDurability") * Ref.Values.repairMulti);
        MuraUtils.changeDamage(tags, tool, tags.getInteger("TotalDurability") - damage, 0);
        MuraUtils.breakTool(tags, tool, false);
        tags.setBoolean(key, true);
    }
}
