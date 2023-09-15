package net.p3pp3rf1y.sophisticatedbackpacks.init;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.api.CapabilityBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.BackpackScreen;
import net.p3pp3rf1y.sophisticatedbackpacks.common.gui.BackpackContainer;
import net.p3pp3rf1y.sophisticatedbackpacks.crafting.BackpackDyeRecipe;
import net.p3pp3rf1y.sophisticatedbackpacks.crafting.BackpackUpgradeRecipe;
import net.p3pp3rf1y.sophisticatedbackpacks.crafting.BasicBackpackRecipe;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
//import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;

public class ModItems {
	private ModItems() {}

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SophisticatedBackpacks.MOD_ID);
	private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, SophisticatedBackpacks.MOD_ID);
	private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SophisticatedBackpacks.MOD_ID);

	public static final RegistryObject<BackpackItem> BACKPACK = ITEMS.register("backpack",
			() -> new BackpackItem(Config.SERVER.leatherBackpack.inventorySlotCount::get, Config.SERVER.leatherBackpack.upgradeSlotCount::get, ModBlocks.BACKPACK));
	
	public static final RegistryObject<MenuType<BackpackContainer>> BACKPACK_CONTAINER_TYPE = MENU_TYPES.register("backpack",
			() -> IForgeMenuType.create(BackpackContainer::fromBuffer));

	/*
	public static final RegistryObject<MenuType<BackpackSettingsContainerMenu>> SETTINGS_CONTAINER_TYPE = MENU_TYPES.register("settings",
			() -> IForgeMenuType.create(BackpackSettingsContainerMenu::fromBuffer));

	public static final RegistryObject<EntityType<EverlastingBackpackItemEntity>> EVERLASTING_BACKPACK_ITEM_ENTITY = ENTITY_TYPES.register(
			"everlasting_backpack_item", () -> EntityType.Builder.of(EverlastingBackpackItemEntity::new, MobCategory.MISC)
					.sized(0.25F, 0.25F).clientTrackingRange(6).updateInterval(20).build("")
	);
	*/

	private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SophisticatedBackpacks.MOD_ID);

	public static final RegistryObject<SimpleRecipeSerializer<?>> BACKPACK_DYE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("backpack_dye", () -> new SimpleRecipeSerializer<>(BackpackDyeRecipe::new));
	public static final RegistryObject<RecipeSerializer<?>> BACKPACK_UPGRADE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("backpack_upgrade", BackpackUpgradeRecipe.Serializer::new);
	//public static final RegistryObject<RecipeSerializer<?>> SMITHING_BACKPACK_UPGRADE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("smithing_backpack_upgrade", SmithingBackpackUpgradeRecipe.Serializer::new);
	public static final RegistryObject<RecipeSerializer<?>> BASIC_BACKPACK_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("basic_backpack", BasicBackpackRecipe.Serializer::new);

	public static void registerHandlers(IEventBus modBus) {
		ITEMS.register(modBus);
		MENU_TYPES.register(modBus);
		ENTITY_TYPES.register(modBus);
		RECIPE_SERIALIZERS.register(modBus);
		modBus.addListener(ModItems::registerContainers);
		
		MinecraftForge.EVENT_BUS.addListener(ModItems::onResourceReload);
	}

	private static void onResourceReload(AddReloadListenerEvent event) {
		BackpackUpgradeRecipe.REGISTERED_RECIPES.clear();
	}

	public static void registerContainers(RegisterEvent event) {
		if (!event.getRegistryKey().equals(ForgeRegistries.Keys.MENU_TYPES)) {
			return;
		}

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			MenuScreens.register(BACKPACK_CONTAINER_TYPE.get(), BackpackScreen::constructScreen);
		});
	}

	public static void registerDispenseBehavior() {
		DispenserBlock.registerBehavior(BACKPACK.get(), new BackpackDispenseBehavior());
	}

	public static void registerCauldronInteractions() {
		CauldronInteraction.WATER.put(BACKPACK.get(), new BackpackCauldronInteraction());
	}

	private static boolean hasDefaultColor(IStorageWrapper wrapper) {
		return wrapper.getAccentColor() == BackpackWrapper.DEFAULT_BORDER_COLOR && wrapper.getMainColor() == BackpackWrapper.DEFAULT_CLOTH_COLOR;
	}

	private static class BackpackCauldronInteraction implements CauldronInteraction {
		@Override
		public InteractionResult interact(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
			LazyOptional<IBackpackWrapper> backpackWrapperCapability = stack.getCapability(CapabilityBackpackWrapper.getCapabilityInstance());
			if (backpackWrapperCapability.map(ModItems::hasDefaultColor).orElse(true)) {
				return InteractionResult.PASS;
			}

			if (!level.isClientSide) {
				backpackWrapperCapability.ifPresent(w -> w.setColors(BackpackWrapper.DEFAULT_CLOTH_COLOR, BackpackWrapper.DEFAULT_BORDER_COLOR));
			}

			return InteractionResult.sidedSuccess(level.isClientSide);
		}
	}

	private static class BackpackDispenseBehavior extends OptionalDispenseItemBehavior {
		@Override
		protected ItemStack execute(BlockSource source, ItemStack stack) {
			setSuccess(false);
			Item item = stack.getItem();
			if (item instanceof BackpackItem backpackItem) {
				Direction dispenserDirection = source.getBlockState().getValue(DispenserBlock.FACING);
				BlockPos blockpos = source.getPos().relative(dispenserDirection);
				Direction against = source.getLevel().isEmptyBlock(blockpos.below()) ? dispenserDirection.getOpposite() : Direction.UP;

				setSuccess(backpackItem.tryPlace(null, dispenserDirection.getAxis() == Direction.Axis.Y ? Direction.NORTH : dispenserDirection.getOpposite(), new DirectionalPlaceContext(source.getLevel(), blockpos, dispenserDirection, stack, against)).consumesAction());
			}

			return stack;
		}
	}
}
