package alloffabric.caveman.world.decorator;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CountFloorDecorator extends Decorator<CountDecoratorConfig> {
    public CountFloorDecorator(Function<Dynamic<?>, ? extends CountDecoratorConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, CountDecoratorConfig config, BlockPos pos) {
        return IntStream.range(0, config.count).mapToObj((i) -> {
            //randomize x and z
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();

            List<BlockPos> positions = new ArrayList<>();

            //iterate upwards and grab all of the positions with a solid block below.
            for (int y = 30; y < 127; y++) {
                if (world.getBlockState(new BlockPos(x, y - 1, z)).isOpaque()) {

                    if (world.getBlockState(new BlockPos(x, y, z)).isAir()) {
                        positions.add(new BlockPos(x, y, z));
                    }
                }
            }

            if (positions.size() == 0) {
                return null;
            }

            return positions.get(random.nextInt(positions.size()));
        }).filter(Objects::nonNull);
    }
}