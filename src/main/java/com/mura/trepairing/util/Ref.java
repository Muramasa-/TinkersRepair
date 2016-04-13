package com.mura.trepairing.util;

public class Ref {
    public static final String MOD_ID = "TinkersRepair";
    public static final String MOD_NAME = "TinkersRepair";
    public static final String MOD_VERSION = "1.7.10-0.5";
    public static final String CLIENT_PROXY = "com.mura.trepairing.proxy.ClientProxy";
    public static final String SERVER_PROXY = "com.mura.trepairing.proxy.ServerProxy";

    public static class Values {
        public static boolean debugModifier = false;
        public static boolean iguanaSupport = false;
        public static boolean gregtechSupport = false;
        public static float repairMulti = 1.0F;
        public static int degradeChanceIguana = 3;
        public static int degradeChanceTinkers = 5;
    }

    public static class NBTKeys {
        public static String TINKERS_CONDITION = "muraCondition";
        public static String TINKERS_MANIPULATE = "muraManipulate";
        public static String TINKERS_HAS_GTENERGY = "muraHasGtEnergy";
        public static String TINKERS_GTENERGY = "muraGtEnergy";
    }
}
