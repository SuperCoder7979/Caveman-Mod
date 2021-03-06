package alloffabric.caveman.world.chunk;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;

public class CavemanChunkGeneratorConfig extends ChunkGeneratorConfig {
    public CavemanChunkGeneratorConfig() {
        this.strongholdDistance = 8; // Instead of 32
        this.templeDistance = 12; // Instead of 32
        this.defaultFluid = Blocks.AIR.getDefaultState();
    }

    @Override
    public int getTempleSeparation() {
        return 8; // Instead of 8
    }

    @Override
    public int getBedrockFloorY() {
        return 0;
    }

    @Override
    public int getBedrockCeilingY() {
        return 255;
    }
}
