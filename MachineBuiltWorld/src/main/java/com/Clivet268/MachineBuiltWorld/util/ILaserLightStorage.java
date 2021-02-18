package com.Clivet268.MachineBuiltWorld.util;


/**
 * An LaserLight storage is the unit of interaction with LaserLight things.
 * <p>
 *
 * Derived from the Redstone Flux power system designed by King Lemming and originally utilized in Thermal Expansion and related mods.
 * Created with consent and permission of King Lemming and Team CoFH. Released with permission under LGPL 2.1 when bundled with Forge.
 *
 */
public interface ILaserLightStorage {

        /**
         * Adds LaserLight to the storage. Returns quantity of LaserLight that was accepted.
         *
         * @param maxReceive
         *            Maximum amount of LaserLight to be inserted.
         * @param simulate
         *            If TRUE, the insertion will only be simulated.
         * @return Amount of LaserLight that was (or would have been, if simulated) accepted by the storage.
         */
        int receiveLaserLight(int maxReceive, boolean simulate);

        /**
         * Removes LaserLight from the storage. Returns quantity of LaserLight that was removed.
         *
         * @param maxExtract
         *            Maximum amount of LaserLight to be extracted.
         * @param simulate
         *            If TRUE, the extraction will only be simulated.
         * @return Amount of LaserLight that was (or would have been, if simulated) extracted from the storage.
         */
        int extractLaserLight(int maxExtract, boolean simulate);

        /**
         * Returns the amount of LaserLight currently stored.
         */
        int getLaserLightStored();

        /**
         * Returns the maximum amount of LaserLight that can be stored.
         */
        int getMaxLaserLightStored();


        int getRedLaserLightStored();

        /**
         * Returns if this storage can have LaserLight extracted.
         * If this is false, then any calls to extractLaserLight will return 0.
         */
        boolean canExtract();

        /**
         * Used to determine if this storage can receive LaserLight.
         * If this is false, then any calls to receiveLaserLight will return 0.
         */
        boolean canReceive();

    }

