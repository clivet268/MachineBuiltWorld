package com.Clivet268.MachineBuiltWorld.blocks;

//import com.Clivet268.MachineBuiltWorld.init.ModTileEntityTypes;

import com.Clivet268.MachineBuiltWorld.Config;
import com.Clivet268.MachineBuiltWorld.state.MoreStateProperties;
import com.Clivet268.MachineBuiltWorld.tileentity.SmokeDetectorTile;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.Stream;

public class SmokeDetector extends Block {
    private int sampleRate = Config.SMOKE_DETECTOR_SAMPLE_RATE.get();
    public static final BooleanProperty ONOROFF = MoreStateProperties.ONOROFF;
    public static final BooleanProperty SENSITIVE = MoreStateProperties.ONOROFF;
    public static final BooleanProperty ALARM = MoreStateProperties.ONOROFF;
    private static final VoxelShape REG_SHAPE = Stream.of(
            Block.makeCuboidShape(3, 0, 4, 4, 2, 12),
            Block.makeCuboidShape(4, 0, 4, 12, 2, 12),
            Block.makeCuboidShape(10, 2, 6, 11, 3, 7),
            Block.makeCuboidShape(10, 2, 7.25, 11, 3, 8.25),
            Block.makeCuboidShape(12, 0, 4, 13, 2, 12),
            Block.makeCuboidShape(4, 0, 3, 12, 2, 4),
            Block.makeCuboidShape(4, 0, 12, 12, 2, 13)
    ).reduce((v1, v2) -> {
        return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
    }).get();

    public SmokeDetector() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return REG_SHAPE;
    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SmokeDetectorTile().getTileEntity();
    }
    //@OnlyIn()
   /*Particle p =
            boolean xcheck = (p.getBoundingBox().getCenter().x > pos.getX() - 3 || p.getBoundingBox().getCenter().x < pos.getX() + 3);
            boolean ycheck = (p.getBoundingBox().getCenter().y > pos.getY() - 3 || p.getBoundingBox().getCenter().y < pos.getY() + 3);
            boolean zcheck = (p.getBoundingBox().getCenter().z > pos.getZ() - 3 || p.getBoundingBox().getCenter().z < pos.getZ() + 3);
            boolean smokystuff = (p instanceof CampfireParticle || p instanceof FlameParticle || p instanceof HugeExplosionParticle
                    || p instanceof LargeExplosionParticle || p instanceof LargeSmokeParticle || p instanceof LavaParticle
                    || p instanceof SmokeParticle);
            if((xcheck || ycheck || zcheck) && smokystuff){
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.BLOCKS, 10000, 0, false);
            }

         */


    public boolean canSpawnInBlock() {
        return true;
    }
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        return hasSolidSideOnTop(worldIn, blockpos) || hasEnoughSolidSide(worldIn, blockpos, Direction.UP)|| hasEnoughSolidSide(worldIn, blockpos, Direction.DOWN)
                || hasEnoughSolidSide(worldIn, blockpos, Direction.EAST)|| hasEnoughSolidSide(worldIn, blockpos, Direction.WEST) || hasEnoughSolidSide(worldIn, blockpos, Direction.NORTH)
                || hasEnoughSolidSide(worldIn, blockpos, Direction.SOUTH);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        //System.out.println(sampleRate);
        if(sampleRate > 0){
            sampleRate--;
        }
        else {
            sampleRate = Config.SMOKE_DETECTOR_SAMPLE_RATE.get();
            detect(worldIn, pos, stateIn);
        }

           // worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
    }


    public void detect(World world, BlockPos pos, BlockState state){
        //System.out.println("check1");
        ResourceLocation tag = new ResourceLocation("machinebuiltworld", "smoke_sensitive");
        for(int x=-5; x <= 5; x++) {
            for (int y = -5; y <= 5; y++) {
                for (int z = -5; z <= 5; z++) {
                    Block block = world.getBlockState(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z)).getBlock();
                    //System.out.println(block);
                    //System.out.println(BlockTags.getCollection().getOrCreate(tag).contains(block));
                    if (RegistryHandler.Tags.SMOKY_SENSITIVE.contains(block)){
                        
                        world.setBlockState(pos, state.with(ALARM, Boolean.TRUE), 3);
                        world.playSound(pos.getX(), pos.getY(), pos.getZ(), RegistryHandler.SMOKE_ALARM.get(), SoundCategory.BLOCKS, 10000, 0, false);
                        //System.out.println("FIRE!!");
                    }
                }
            }
        }
    }






    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ALARM);
    }
    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }
    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }
    @Override
    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }
    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }
    @Override
    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) { return false; }
}
