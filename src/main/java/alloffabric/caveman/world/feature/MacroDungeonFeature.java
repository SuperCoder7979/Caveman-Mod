package alloffabric.caveman.world.feature;

import alloffabric.caveman.Caveman;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import robosky.structurehelpers.structure.ExtendedStructures;
import robosky.structurehelpers.structure.pool.ExtendedSinglePoolElement;

import java.util.List;
import java.util.function.Function;

import static alloffabric.caveman.util.IdentifierUtil.id;
import static alloffabric.caveman.util.IdentifierUtil.idString;

public class MacroDungeonFeature extends AbstractTempleFeature<DefaultFeatureConfig> {
    static {
        pool(
            id("macro_dungeon/lobby"),
            ImmutableList.of(
                element(id("macro_dungeon/x_intersection/lit"), 1)
            )
        );

        pool(
            id("macro_dungeon/hallways"),
            ImmutableList.of(
                element(id("macro_dungeon/hallway/default"), 6),
                element(id("macro_dungeon/hallway/lit"), 1),
                element(id("macro_dungeon/hallway/ominous"), 1)
            )
        );

        pool(
            id("macro_dungeon/stairways/down"),
            ImmutableList.of(
                // Hallways
                element(id("macro_dungeon/hallway/stairway/up/default"), 6),
                element(id("macro_dungeon/hallway/stairway/up/lit"), 1),
                element(id("macro_dungeon/hallway/stairway/up/ominous"), 1),
                // T-Intersections
                element(id("macro_dungeon/t_intersection/stairway/up/default"), 6),
                element(id("macro_dungeon/t_intersection/stairway/up/lit"), 1),
                element(id("macro_dungeon/t_intersection/stairway/up/ominous"), 1),
                // X-Intersections
                element(id("macro_dungeon/x_intersection/stairway/up/default"), 6),
                element(id("macro_dungeon/x_intersection/stairway/up/lit"), 1),
                element(id("macro_dungeon/x_intersection/stairway/up/ominous"), 1)
            )
        );

        pool(
            id("macro_dungeon/stairways/up"),
            ImmutableList.of(
                // Hallways
                element(id("macro_dungeon/hallway/stairway/down/default"), 6),
                element(id("macro_dungeon/hallway/stairway/down/lit"), 1),
                element(id("macro_dungeon/hallway/stairway/down/ominous"), 1),
                // T-Intersections
                element(id("macro_dungeon/t_intersection/stairway/down/default"), 6),
                element(id("macro_dungeon/t_intersection/stairway/down/lit"), 1),
                element(id("macro_dungeon/t_intersection/stairway/down/ominous"), 1),
                // X-Intersections
                element(id("macro_dungeon/x_intersection/stairway/down/default"), 6),
                element(id("macro_dungeon/x_intersection/stairway/down/lit"), 1),
                element(id("macro_dungeon/x_intersection/stairway/down/ominous"), 1)
            )
        );

        pool(
            id("macro_dungeon/premises"),
            ImmutableList.of(
                /* HALLWAYS */
                // Hallways - Weight x8
                element(id("macro_dungeon/hallway/default"), 48),
                element(id("macro_dungeon/hallway/lit"), 8),
                element(id("macro_dungeon/hallway/ominous"), 8),
                // Stairways - Weight x1
                element(id("macro_dungeon/hallway/stairway/down/default"), 6),
                element(id("macro_dungeon/hallway/stairway/down/lit"), 1),
                element(id("macro_dungeon/hallway/stairway/down/ominous"), 1),
                element(id("macro_dungeon/hallway/stairway/up/default"), 6),
                element(id("macro_dungeon/hallway/stairway/up/lit"), 1),
                element(id("macro_dungeon/hallway/stairway/up/ominous"), 1),

                /* T-INTERSECTIONS */
                // Hallways - Weight x4
                element(id("macro_dungeon/t_intersection/default"), 24),
                element(id("macro_dungeon/t_intersection/lit"), 4),
                element(id("macro_dungeon/t_intersection/ominous"), 4),
                // Stairways - Weight x1
                element(id("macro_dungeon/t_intersection/stairway/down/default"), 6),
                element(id("macro_dungeon/t_intersection/stairway/down/lit"), 1),
                element(id("macro_dungeon/t_intersection/stairway/down/ominous"), 1),
                element(id("macro_dungeon/t_intersection/stairway/up/default"), 6),
                element(id("macro_dungeon/t_intersection/stairway/up/lit"), 1),
                element(id("macro_dungeon/t_intersection/stairway/up/ominous"), 1),

                /* X-INTERSECTIONS */
                // Hallways - Weight x4
                element(id("macro_dungeon/x_intersection/default"), 24),
                element(id("macro_dungeon/x_intersection/lit"), 4),
                element(id("macro_dungeon/x_intersection/ominous"), 4),
                // Stairways - Weight x1
                element(id("macro_dungeon/x_intersection/stairway/down/default"), 6),
                element(id("macro_dungeon/x_intersection/stairway/down/lit"), 1),
                element(id("macro_dungeon/x_intersection/stairway/down/ominous"), 1),
                element(id("macro_dungeon/x_intersection/stairway/up/default"), 6),
                element(id("macro_dungeon/x_intersection/stairway/up/lit"), 1),
                element(id("macro_dungeon/x_intersection/stairway/up/ominous"), 1)
            )
        );
    }

    public MacroDungeonFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory) {
        super(configFactory);
    }

    private static Pair<StructurePoolElement, Integer> element(Identifier id, Integer weight) {
        return Pair.of(new ExtendedSinglePoolElement(id, false, ImmutableList.of()), weight);
    }

    private static void pool(Identifier id, List<Pair<StructurePoolElement, Integer>> elements) {
        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(id, new Identifier("empty"), elements, StructurePool.Projection.RIGID));
    }

    private static void pool(Identifier id, Identifier terminatorId, List<Pair<StructurePoolElement, Integer>> elements) {
        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(id, terminatorId, elements, StructurePool.Projection.RIGID));
    }

    @Override
    protected int getSeedModifier(ChunkGeneratorConfig config) {
        return 0;
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return Start::new;
    }

    @Override
    public String getName() {
        return idString("macro_dungeon");
    }

    @Override
    public int getRadius() {
        return 16;
    }

    public static class Start extends StructureStart {
        public Start(StructureFeature<?> structureFeature, int chunkX, int chunkZ, BlockBox blockBox, int i, long l) {
            super(structureFeature, chunkX, chunkZ, blockBox, i, l);
        }

        public void init(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome) {
            List<PoolStructurePiece> pieces = ExtendedStructures.addPieces(
                ImmutableList.of(),
                80,
                80,
                id("macro_dungeon/lobby"),
                10,
                Caveman.MACRO_DUNGEON_PIECE,
                chunkGenerator,
                structureManager,
                new BlockPos(x * 16, chunkGenerator.getMaxY(), z * 16),
                this.random,
                true, // Unknown shit
                false
            );
            this.children.addAll(pieces);
            this.setBoundingBoxFromChildren();
            this.method_14978(chunkGenerator.getMaxY(), this.random, 0);
        }
    }
}
