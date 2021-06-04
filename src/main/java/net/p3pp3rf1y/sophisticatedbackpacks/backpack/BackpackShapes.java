package net.p3pp3rf1y.sophisticatedbackpacks.backpack;

import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.stream.Stream;

public class BackpackShapes {
	private BackpackShapes() {}

	static final VoxelShape NORTH_SHAPE = Stream.of(
			Block.makeCuboidShape(5, 3, 2.75, 6, 5, 3),
			Block.makeCuboidShape(10, 3, 2.75, 11, 5, 3),
			Block.makeCuboidShape(4, 0, 3, 12, 1, 5),
			Block.makeCuboidShape(4, 1, 3, 12, 2, 5),
			Block.makeCuboidShape(4, 2, 3, 12, 6, 5),
			Block.makeCuboidShape(15, 3, 7.5, 15.25, 5, 8.5),
			Block.makeCuboidShape(13, 0, 5.5, 15, 1, 10.5),
			Block.makeCuboidShape(13, 1, 5.5, 15, 2, 10.5),
			Block.makeCuboidShape(13, 2, 5.5, 15, 6, 10.5),
			Block.makeCuboidShape(14, 8, 7.5, 14.25, 10, 8.5),
			Block.makeCuboidShape(13, 7, 5.5, 14, 11, 10.5),
			Block.makeCuboidShape(1.75, 8, 7.5, 2, 10, 8.5),
			Block.makeCuboidShape(2, 7, 5.5, 3, 11, 10.5),
			Block.makeCuboidShape(0.75, 3, 7.5, 1, 5, 8.5),
			Block.makeCuboidShape(1, 0, 5.5, 3, 1, 10.5),
			Block.makeCuboidShape(1, 1, 5.5, 3, 2, 10.5),
			Block.makeCuboidShape(1, 2, 5.5, 3, 6, 10.5),
			Block.makeCuboidShape(11.75, 0, 11, 12.25, 12.5, 12),
			Block.makeCuboidShape(10.75, 0, 11, 11.75, 12.5, 12),
			Block.makeCuboidShape(10.25, 0, 11, 10.75, 12.5, 12),
			Block.makeCuboidShape(3.75, 0, 11, 4.25, 12.5, 12),
			Block.makeCuboidShape(4.25, 0, 11, 5.25, 12.5, 12),
			Block.makeCuboidShape(5.25, 0, 11, 5.75, 12.5, 12),
			Block.makeCuboidShape(11.75, 8.25, 4.75, 12.75, 13.25, 10.75),
			Block.makeCuboidShape(4.25, 9.25, 4.75, 11.75, 13.25, 10.75),
			Block.makeCuboidShape(3.25, 8.25, 4.75, 4.25, 13.25, 10.75),
			Block.makeCuboidShape(4.25, 8.25, 4.75, 11.75, 9.25, 5),
			Block.makeCuboidShape(5.75, 13.25, 7.75, 6.5, 14, 8.75),
			Block.makeCuboidShape(5.75, 14, 7.75, 10.25, 14.5, 8.75),
			Block.makeCuboidShape(9.5, 13.25, 7.75, 10.25, 14, 8.75),
			Block.makeCuboidShape(4.5, 7, 4.5, 5.5, 9, 5.25),
			Block.makeCuboidShape(4.5, 9, 4.5, 5.5, 13.5, 11.25),
			Block.makeCuboidShape(10.5, 7, 4.5, 11.5, 9, 5.25),
			Block.makeCuboidShape(10.5, 9, 4.5, 11.5, 13.5, 11.25),
			Block.makeCuboidShape(4, 1, 11, 12, 12, 11.5),
			Block.makeCuboidShape(3, 0, 5, 13, 13, 11)
	).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
	static final VoxelShape SOUTH_SHAPE = Stream.of(
			Block.makeCuboidShape(10, 3, 13, 11, 5, 13.25),
			Block.makeCuboidShape(5, 3, 13, 6, 5, 13.25),
			Block.makeCuboidShape(4, 0, 11, 12, 1, 13),
			Block.makeCuboidShape(4, 1, 11, 12, 2, 13),
			Block.makeCuboidShape(4, 2, 11, 12, 6, 13),
			Block.makeCuboidShape(0.75, 3, 7.5, 1, 5, 8.5),
			Block.makeCuboidShape(1, 0, 5.5, 3, 1, 10.5),
			Block.makeCuboidShape(1, 1, 5.5, 3, 2, 10.5),
			Block.makeCuboidShape(1, 2, 5.5, 3, 6, 10.5),
			Block.makeCuboidShape(1.75, 8, 7.5, 2, 10, 8.5),
			Block.makeCuboidShape(2, 7, 5.5, 3, 11, 10.5),
			Block.makeCuboidShape(14, 8, 7.5, 14.25, 10, 8.5),
			Block.makeCuboidShape(13, 7, 5.5, 14, 11, 10.5),
			Block.makeCuboidShape(15, 3, 7.5, 15.25, 5, 8.5),
			Block.makeCuboidShape(13, 0, 5.5, 15, 1, 10.5),
			Block.makeCuboidShape(13, 1, 5.5, 15, 2, 10.5),
			Block.makeCuboidShape(13, 2, 5.5, 15, 6, 10.5),
			Block.makeCuboidShape(3.75, 0, 4, 4.25, 12.5, 5),
			Block.makeCuboidShape(4.25, 0, 4, 5.25, 12.5, 5),
			Block.makeCuboidShape(5.25, 0, 4, 5.75, 12.5, 5),
			Block.makeCuboidShape(11.75, 0, 4, 12.25, 12.5, 5),
			Block.makeCuboidShape(10.75, 0, 4, 11.75, 12.5, 5),
			Block.makeCuboidShape(10.25, 0, 4, 10.75, 12.5, 5),
			Block.makeCuboidShape(3.25, 8.25, 5.25, 4.25, 13.25, 11.25),
			Block.makeCuboidShape(4.25, 9.25, 5.25, 11.75, 13.25, 11.25),
			Block.makeCuboidShape(11.75, 8.25, 5.25, 12.75, 13.25, 11.25),
			Block.makeCuboidShape(4.25, 8.25, 11, 11.75, 9.25, 11.25),
			Block.makeCuboidShape(9.5, 13.25, 7.25, 10.25, 14, 8.25),
			Block.makeCuboidShape(5.75, 14, 7.25, 10.25, 14.5, 8.25),
			Block.makeCuboidShape(5.75, 13.25, 7.25, 6.5, 14, 8.25),
			Block.makeCuboidShape(10.5, 7, 10.75, 11.5, 9, 11.5),
			Block.makeCuboidShape(10.5, 9, 4.75, 11.5, 13.5, 11.5),
			Block.makeCuboidShape(4.5, 7, 10.75, 5.5, 9, 11.5),
			Block.makeCuboidShape(4.5, 9, 4.75, 5.5, 13.5, 11.5),
			Block.makeCuboidShape(4, 1, 4.5, 12, 12, 5),
			Block.makeCuboidShape(3, 0, 5, 13, 13, 11)
	).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
	static final VoxelShape WEST_SHAPE = Stream.of(
			Block.makeCuboidShape(2.75, 3, 10, 3, 5, 11),
			Block.makeCuboidShape(2.75, 3, 5, 3, 5, 6),
			Block.makeCuboidShape(3, 0, 4, 5, 1, 12),
			Block.makeCuboidShape(3, 1, 4, 5, 2, 12),
			Block.makeCuboidShape(3, 2, 4, 5, 6, 12),
			Block.makeCuboidShape(7.5, 3, 0.75, 8.5, 5, 1),
			Block.makeCuboidShape(5.5, 0, 1, 10.5, 1, 3),
			Block.makeCuboidShape(5.5, 1, 1, 10.5, 2, 3),
			Block.makeCuboidShape(5.5, 2, 1, 10.5, 6, 3),
			Block.makeCuboidShape(7.5, 8, 1.75, 8.5, 10, 2),
			Block.makeCuboidShape(5.5, 7, 2, 10.5, 11, 3),
			Block.makeCuboidShape(7.5, 8, 14, 8.5, 10, 14.25),
			Block.makeCuboidShape(5.5, 7, 13, 10.5, 11, 14),
			Block.makeCuboidShape(7.5, 3, 15, 8.5, 5, 15.25),
			Block.makeCuboidShape(5.5, 0, 13, 10.5, 1, 15),
			Block.makeCuboidShape(5.5, 1, 13, 10.5, 2, 15),
			Block.makeCuboidShape(5.5, 2, 13, 10.5, 6, 15),
			Block.makeCuboidShape(11, 0, 3.75, 12, 12.5, 4.25),
			Block.makeCuboidShape(11, 0, 4.25, 12, 12.5, 5.25),
			Block.makeCuboidShape(11, 0, 5.25, 12, 12.5, 5.75),
			Block.makeCuboidShape(11, 0, 11.75, 12, 12.5, 12.25),
			Block.makeCuboidShape(11, 0, 10.75, 12, 12.5, 11.75),
			Block.makeCuboidShape(11, 0, 10.25, 12, 12.5, 10.75),
			Block.makeCuboidShape(4.75, 8.25, 3.25, 10.75, 13.25, 4.25),
			Block.makeCuboidShape(4.75, 9.25, 4.25, 10.75, 13.25, 11.75),
			Block.makeCuboidShape(4.75, 8.25, 11.75, 10.75, 13.25, 12.75),
			Block.makeCuboidShape(4.75, 8.25, 4.25, 5, 9.25, 11.75),
			Block.makeCuboidShape(7.75, 13.25, 9.5, 8.75, 14, 10.25),
			Block.makeCuboidShape(7.75, 14, 5.75, 8.75, 14.5, 10.25),
			Block.makeCuboidShape(7.75, 13.25, 5.75, 8.75, 14, 6.5),
			Block.makeCuboidShape(4.5, 7, 10.5, 5.25, 9, 11.5),
			Block.makeCuboidShape(4.5, 9, 10.5, 11.25, 13.5, 11.5),
			Block.makeCuboidShape(4.5, 7, 4.5, 5.25, 9, 5.5),
			Block.makeCuboidShape(4.5, 9, 4.5, 11.25, 13.5, 5.5),
			Block.makeCuboidShape(11, 1, 4, 11.5, 12, 12),
			Block.makeCuboidShape(5, 0, 3, 11, 13, 13)
	).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
	static final VoxelShape EAST_SHAPE = Stream.of(
			Block.makeCuboidShape(13, 3, 5, 13.25, 5, 6),
			Block.makeCuboidShape(13, 3, 10, 13.25, 5, 11),
			Block.makeCuboidShape(11, 0, 4, 13, 1, 12),
			Block.makeCuboidShape(11, 1, 4, 13, 2, 12),
			Block.makeCuboidShape(11, 2, 4, 13, 6, 12),
			Block.makeCuboidShape(7.5, 3, 15, 8.5, 5, 15.25),
			Block.makeCuboidShape(5.5, 0, 13, 10.5, 1, 15),
			Block.makeCuboidShape(5.5, 1, 13, 10.5, 2, 15),
			Block.makeCuboidShape(5.5, 2, 13, 10.5, 6, 15),
			Block.makeCuboidShape(7.5, 8, 14, 8.5, 10, 14.25),
			Block.makeCuboidShape(5.5, 7, 13, 10.5, 11, 14),
			Block.makeCuboidShape(7.5, 8, 1.75, 8.5, 10, 2),
			Block.makeCuboidShape(5.5, 7, 2, 10.5, 11, 3),
			Block.makeCuboidShape(7.5, 3, 0.75, 8.5, 5, 1),
			Block.makeCuboidShape(5.5, 0, 1, 10.5, 1, 3),
			Block.makeCuboidShape(5.5, 1, 1, 10.5, 2, 3),
			Block.makeCuboidShape(5.5, 2, 1, 10.5, 6, 3),
			Block.makeCuboidShape(4, 0, 11.75, 5, 12.5, 12.25),
			Block.makeCuboidShape(4, 0, 10.75, 5, 12.5, 11.75),
			Block.makeCuboidShape(4, 0, 10.25, 5, 12.5, 10.75),
			Block.makeCuboidShape(4, 0, 3.75, 5, 12.5, 4.25),
			Block.makeCuboidShape(4, 0, 4.25, 5, 12.5, 5.25),
			Block.makeCuboidShape(4, 0, 5.25, 5, 12.5, 5.75),
			Block.makeCuboidShape(5.25, 8.25, 11.75, 11.25, 13.25, 12.75),
			Block.makeCuboidShape(5.25, 9.25, 4.25, 11.25, 13.25, 11.75),
			Block.makeCuboidShape(5.25, 8.25, 3.25, 11.25, 13.25, 4.25),
			Block.makeCuboidShape(11, 8.25, 4.25, 11.25, 9.25, 11.75),
			Block.makeCuboidShape(7.25, 13.25, 5.75, 8.25, 14, 6.5),
			Block.makeCuboidShape(7.25, 14, 5.75, 8.25, 14.5, 10.25),
			Block.makeCuboidShape(7.25, 13.25, 9.5, 8.25, 14, 10.25),
			Block.makeCuboidShape(10.75, 7, 4.5, 11.5, 9, 5.5),
			Block.makeCuboidShape(4.75, 9, 4.5, 11.5, 13.5, 5.5),
			Block.makeCuboidShape(10.75, 7, 10.5, 11.5, 9, 11.5),
			Block.makeCuboidShape(4.75, 9, 10.5, 11.5, 13.5, 11.5),
			Block.makeCuboidShape(4.5, 1, 4, 5, 12, 12),
			Block.makeCuboidShape(5, 0, 3, 11, 13, 13)
	).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
}