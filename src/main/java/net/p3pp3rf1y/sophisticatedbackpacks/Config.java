package net.p3pp3rf1y.sophisticatedbackpacks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.stack.StackUpgradeConfig;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("java:S1192") //don't complain about repeated config names if two upgrades happen to have the same setting
public class Config {

	private static final String REGISTRY_NAME_MATCHER = "([a-z0-9_.-]+:[a-z0-9_/.-]+)";

	private Config() {}

	public static final Server SERVER;
	public static final ForgeConfigSpec SERVER_SPEC;

	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;

	static {
		final Pair<Server, ForgeConfigSpec> serverSpec = new ForgeConfigSpec.Builder().configure(Server::new);
		SERVER_SPEC = serverSpec.getRight();
		SERVER = serverSpec.getLeft();

		final Pair<Common, ForgeConfigSpec> commonSpec = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = commonSpec.getRight();
		COMMON = commonSpec.getLeft();
	}

	public static class Server {
		public final DisallowedItems disallowedItems;
		public final NoInteractionBlocks noInteractionBlocks;
		public final NoConnectionBlocks noConnectionBlocks;
		public final BackpackConfig leatherBackpack;
		public final EntityBackpackAdditionsConfig entityBackpackAdditions;
		public final ForgeConfigSpec.BooleanValue itemFluidHandlerEnabled;
		public final ForgeConfigSpec.BooleanValue allowOpeningOtherPlayerBackpacks;
		public final ForgeConfigSpec.BooleanValue itemDisplayDisabled;
		public final ForgeConfigSpec.BooleanValue tickDedupeLogicDisabled;
		public final ForgeConfigSpec.BooleanValue canBePlacedInContainerItems;
		public final StackUpgradeConfig stackUpgrade;
		public final NerfsConfig nerfsConfig;


		@SuppressWarnings("unused") //need the Event parameter for forge reflection to understand what event this listens to
		public void onConfigReload(ModConfigEvent.Reloading event) {
			disallowedItems.initialized = false;
			stackUpgrade.clearNonStackableItems();
		}

		Server(ForgeConfigSpec.Builder builder) {
			builder.comment("Server Settings").push("server");

			disallowedItems = new DisallowedItems(builder);
			noInteractionBlocks = new NoInteractionBlocks(builder);
			noConnectionBlocks = new NoConnectionBlocks(builder);

			leatherBackpack = new BackpackConfig(builder, "Leather", 27, 1);
			
			stackUpgrade = new StackUpgradeConfig(builder);
			entityBackpackAdditions = new EntityBackpackAdditionsConfig(builder);
			nerfsConfig = new NerfsConfig(builder);

			itemFluidHandlerEnabled = builder.comment("Turns on/off item fluid handler of backpack in its item form. There are some dupe bugs caused by default fluid handling implementation that manifest when backpack is drained / filled in its item form in another mod's tank and the only way to prevent them is disallowing drain/fill in item form altogether").define("itemFluidHandlerEnabled", true);
			allowOpeningOtherPlayerBackpacks = builder.comment("Determines whether player can right click on backpack that another player is wearing to open it. If off will turn off that capability for everyone and remove related settings from backpack.").define("allowOpeningOtherPlayerBackpacks", true);
			itemDisplayDisabled = builder.comment("Allows disabling item display settings. Primarily in cases where custom backpack model doesn't support showing the item. (Requires game restart to take effect)").define("itemDisplayDisabled", false);
			tickDedupeLogicDisabled = builder.comment("Allows disabling logic that dedupes backpacks with the same UUID in players' inventory. This is here to allow turning off the logic just in case it would be causing performance issues.").define("tickDedupeLogicDisabled", false);
			canBePlacedInContainerItems = builder.comment("Determines if backpacks can be placed in container items (those that check for return value of canFitInsideContainerItems)").define("canBePlacedInContainerItems", false);

			builder.pop();
		}
		public static class NerfsConfig {
			public final ForgeConfigSpec.BooleanValue tooManyBackpacksSlowness;
			public final ForgeConfigSpec.IntValue maxNumberOfBackpacks;
			public final ForgeConfigSpec.DoubleValue slownessLevelsPerAdditionalBackpack;
			public final ForgeConfigSpec.BooleanValue onlyWornBackpackTriggersUpgrades;
			public NerfsConfig(ForgeConfigSpec.Builder builder) {
				builder.push("nerfs");
				tooManyBackpacksSlowness = builder.comment("Determines if too many backpacks in player's inventory cause slowness to the player").define("tooManyBackpacksSlowness", false);
				maxNumberOfBackpacks = builder.comment("Maximum number of backpacks in player's inventory that will not cause slowness").defineInRange("maxNumberOfBackpacks", 3, 1, 27);
				slownessLevelsPerAdditionalBackpack = builder.comment("Ratio of slowness levels per every backpack above the maximum number allowed. (number of backpacks above the max gets multiplied by this number and ceiled)").defineInRange("slownessLevelsPerAdditionalBackpack", 1, 0.1, 5);
				onlyWornBackpackTriggersUpgrades = builder.comment("Determines if active upgrades will only work in the backpack that's worn by the player. Active upgrades are for example magnet, pickup, cooking, feeding upgrades.").define("onlyWornBackpackTriggersUpgrades", false);
				builder.pop();
			}

		}
		public static class EntityBackpackAdditionsConfig {
			private static final String ENTITY_LOOT_MATCHER = "([a-z0-9_.-]+:[a-z0-9_/.-]+)\\|(null|[a-z0-9_.-]+:[a-z0-9/_.-]+)";
			public final ForgeConfigSpec.DoubleValue chance;
			public final ForgeConfigSpec.BooleanValue addLoot;
			public final ForgeConfigSpec.BooleanValue buffWithPotionEffects;
			public final ForgeConfigSpec.BooleanValue buffHealth;
			public final ForgeConfigSpec.BooleanValue equipWithArmor;
			public final ForgeConfigSpec.BooleanValue playJukebox;
			public final ForgeConfigSpec.BooleanValue dropToFakePlayers;
			public final ForgeConfigSpec.DoubleValue backpackDropChance;
			public final ForgeConfigSpec.DoubleValue lootingChanceIncreasePerLevel;
			public final ForgeConfigSpec.ConfigValue<List<? extends String>> entityLootTableList;
			public final ForgeConfigSpec.ConfigValue<List<? extends String>> discBlockList;
			@Nullable
			private Map<EntityType<?>, ResourceLocation> entityLootTables = null;

			public EntityBackpackAdditionsConfig(ForgeConfigSpec.Builder builder) {
				builder.comment("Settings for Spawning Entities with Backpack").push("entityBackpackAdditions");
				chance = builder.comment("Chance of an entity spawning with Backpack").defineInRange("chance", 0.01, 0, 1);
				addLoot = builder.comment("Turns on/off addition of loot into backpacks").define("addLoot", true);
				buffWithPotionEffects = builder.comment("Turns on/off buffing the entity that wears backpack with potion effects. These are scaled based on how much loot is added.")
						.define("buffWithPotionEffects", true);
				buffHealth = builder.comment("Turns on/off buffing the entity that wears backpack with additional health. Health is scaled based on backpack tier the mob wears.")
						.define("buffHealth", true);
				equipWithArmor = builder.comment("Turns on/off equiping the entity that wears backpack with armor. What armor material and how enchanted is scaled based on backpack tier the mob wears.")
						.define("equipWithArmor", true);
				entityLootTableList = builder.comment("Map of entities that can spawn with backpack and related loot tables (if adding a loot is enabled) in format of \"EntityRegistryName|LootTableName\"")
						.defineList("entityLootTableList", this::getDefaultEntityLootTableList, mapping -> ((String) mapping).matches(ENTITY_LOOT_MATCHER));
				discBlockList = builder.comment("List of music discs that are not supposed to be played by entities")
						.defineList("discBlockList", this::getDefaultDiscBlockList, mapping -> ((String) mapping).matches(REGISTRY_NAME_MATCHER));
				playJukebox = builder.comment("Turns on/off a chance that the entity that wears backpack gets jukebox upgrade and plays a music disc.").define("playJukebox", true);
				dropToFakePlayers = builder.comment("Determines whether backpack drops to fake players if killed by them in addition to real ones that it always drops to").define("dropToFakePlayers", false);
				backpackDropChance = builder.comment("Chance of mob dropping backpack when killed by player").defineInRange("backpackDropChance", 0.5, 0, 1);
				lootingChanceIncreasePerLevel = builder.comment("Chance increase per looting level of mob dropping backpack").defineInRange("lootingChanceIncreasePerLevel", 0.15, 0, 0.3);
				builder.pop();
			}

			public Optional<ResourceLocation> getLootTableName(EntityType<?> entityType) {
				if (entityLootTables == null) {
					initEntityLootTables();
				}
				return Optional.ofNullable(entityLootTables.get(entityType));
			}

			public boolean canWearBackpack(EntityType<?> entityType) {
				if (entityLootTables == null) {
					initEntityLootTables();
				}
				return entityLootTables.containsKey(entityType);
			}

			private void initEntityLootTables() {
				entityLootTables = new HashMap<>();
				for (String mapping : entityLootTableList.get()) {
					String[] entityLoot = mapping.split("\\|");
					if (entityLoot.length < 2) {
						continue;
					}
					String entityRegistryName = entityLoot[0];
					String lootTableName = entityLoot[1];

					EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(entityRegistryName));
					if (entityType != null) {
						entityLootTables.put(entityType, lootTableName.equals("null") ? null : new ResourceLocation(lootTableName));
					}
				}
			}

			private List<String> getDefaultDiscBlockList() {
				List<String> ret = new ArrayList<>();
				ret.add("botania:record_gaia_1");
				ret.add("botania:record_gaia_2");
				return ret;
			}

			private List<String> getDefaultEntityLootTableList() {
				return getDefaultEntityLootMapping().entrySet().stream().map(e -> ForgeRegistries.ENTITY_TYPES.getKey(e.getKey()) + "|" + e.getValue()).collect(Collectors.toList());
			}

			private Map<EntityType<?>, ResourceLocation> getDefaultEntityLootMapping() {
				Map<EntityType<?>, ResourceLocation> mapping = new LinkedHashMap<>();
				mapping.put(EntityType.CREEPER, BuiltInLootTables.DESERT_PYRAMID);
				mapping.put(EntityType.DROWNED, BuiltInLootTables.SHIPWRECK_TREASURE);
				mapping.put(EntityType.ENDERMAN, BuiltInLootTables.END_CITY_TREASURE);
				mapping.put(EntityType.EVOKER, BuiltInLootTables.WOODLAND_MANSION);
				mapping.put(EntityType.HUSK, BuiltInLootTables.DESERT_PYRAMID);
				mapping.put(EntityType.PIGLIN, BuiltInLootTables.BASTION_BRIDGE);
				mapping.put(EntityType.PIGLIN_BRUTE, BuiltInLootTables.BASTION_TREASURE);
				mapping.put(EntityType.PILLAGER, BuiltInLootTables.PILLAGER_OUTPOST);
				mapping.put(EntityType.SKELETON, BuiltInLootTables.SIMPLE_DUNGEON);
				mapping.put(EntityType.STRAY, BuiltInLootTables.IGLOO_CHEST);
				mapping.put(EntityType.VEX, BuiltInLootTables.WOODLAND_MANSION);
				mapping.put(EntityType.VINDICATOR, BuiltInLootTables.WOODLAND_MANSION);
				mapping.put(EntityType.WITCH, BuiltInLootTables.BURIED_TREASURE);
				mapping.put(EntityType.WITHER_SKELETON, BuiltInLootTables.NETHER_BRIDGE);
				mapping.put(EntityType.ZOMBIE, BuiltInLootTables.SIMPLE_DUNGEON);
				mapping.put(EntityType.ZOMBIE_VILLAGER, BuiltInLootTables.VILLAGE_ARMORER);
				mapping.put(EntityType.ZOMBIFIED_PIGLIN, BuiltInLootTables.BASTION_OTHER);
				return mapping;
			}
		}

		public static class BackpackConfig {
			public final ForgeConfigSpec.IntValue inventorySlotCount;
			public final ForgeConfigSpec.IntValue upgradeSlotCount;

			public BackpackConfig(ForgeConfigSpec.Builder builder, String backpackPrefix, int inventorySlotCountDefault, int upgradeSlotCountDefault) {
				builder.comment(backpackPrefix + " Backpack Settings").push(backpackPrefix.toLowerCase(Locale.ENGLISH) + "Backpack");
				inventorySlotCount = builder.comment("Number of inventory slots in the backpack").defineInRange("inventorySlotCount", inventorySlotCountDefault, 1, 144);
				upgradeSlotCount = builder.comment("Number of upgrade slots in the backpack").defineInRange("upgradeSlotCount", upgradeSlotCountDefault, 0, 10);
				builder.pop();
			}
		}

		public static class NoInteractionBlocks {
			private final ForgeConfigSpec.ConfigValue<List<String>> noInteractionBlocksList;
			private boolean initialized = false;
			private Set<Block> noInteractionBlocksSet = null;

			NoInteractionBlocks(ForgeConfigSpec.Builder builder) {
				noInteractionBlocksList = builder.comment("List of blocks that inventory interaction upgrades can't interact with - e.g. \"minecraft:shulker_box\"").define("noInteractionBlocks", new ArrayList<>());
			}

			public boolean isBlockInteractionDisallowed(Block block) {
				if (!SERVER_SPEC.isLoaded()) {
					return true;
				}
				if (!initialized) {
					loadDisallowedSet();
				}
				return noInteractionBlocksSet.contains(block);
			}

			private void loadDisallowedSet() {
				initialized = true;
				noInteractionBlocksSet = new HashSet<>();

				for (String disallowedItemName : noInteractionBlocksList.get()) {
					ResourceLocation registryName = new ResourceLocation(disallowedItemName);
					if (ForgeRegistries.BLOCKS.containsKey(registryName)) {
						noInteractionBlocksSet.add(ForgeRegistries.BLOCKS.getValue(registryName));
					}
				}
			}
		}

		public static class NoConnectionBlocks {
			private final ForgeConfigSpec.ConfigValue<List<? extends String>> noConnectionBlocksList;
			private boolean initialized = false;
			private Set<Block> noConnnectionBlocksSet = null;

			NoConnectionBlocks(ForgeConfigSpec.Builder builder) {
				noConnectionBlocksList = builder.comment("List of blocks that are not allowed to connect to backpacks - e.g. \"refinedstorage:external_storage\"")
						.defineList("noConnectionBlocks", new ArrayList<>(), mapping -> ((String) mapping).matches(REGISTRY_NAME_MATCHER));
			}

			public boolean isBlockConnectionDisallowed(Block block) {
				if (!SERVER_SPEC.isLoaded()) {
					return true;
				}
				if (!initialized) {
					loadDisallowedSet();
				}
				return noConnnectionBlocksSet.contains(block);
			}

			private void loadDisallowedSet() {
				initialized = true;
				noConnnectionBlocksSet = new HashSet<>();

				for (String disallowedItemName : noConnectionBlocksList.get()) {
					ResourceLocation registryName = new ResourceLocation(disallowedItemName);
					if (ForgeRegistries.BLOCKS.containsKey(registryName)) {
						noConnnectionBlocksSet.add(ForgeRegistries.BLOCKS.getValue(registryName));
					}
				}
			}
		}

		public static class DisallowedItems {
			private final ForgeConfigSpec.BooleanValue containerItemsDisallowed;
			private final ForgeConfigSpec.ConfigValue<List<String>> disallowedItemsList;
			private boolean initialized = false;
			private Set<Item> disallowedItemsSet = null;

			DisallowedItems(ForgeConfigSpec.Builder builder) {
				disallowedItemsList = builder.comment("List of items that are not allowed to be put in backpacks - e.g. \"minecraft:shulker_box\"").define("disallowedItems", new ArrayList<>());
				containerItemsDisallowed = builder.comment("Determines if container items (those that override canFitInsideContainerItems to false) are able to fit in backpacks")
						.define("containerItemsDisallowed", false);
			}

			public boolean isItemDisallowed(Item item) {
				if (!SERVER_SPEC.isLoaded()) {
					return true;
				}

				if (!initialized) {
					loadDisallowedSet();
				}

				if (Boolean.TRUE.equals(containerItemsDisallowed.get()) && !(item instanceof BackpackItem) && !item.canFitInsideContainerItems()) {
					return true;
				}

				return disallowedItemsSet.contains(item);
			}

			private void loadDisallowedSet() {
				initialized = true;
				disallowedItemsSet = new HashSet<>();

				for (String disallowedItemName : disallowedItemsList.get()) {
					ResourceLocation registryName = new ResourceLocation(disallowedItemName);
					if (ForgeRegistries.ITEMS.containsKey(registryName)) {
						disallowedItemsSet.add(ForgeRegistries.ITEMS.getValue(registryName));
					}
				}
			}
		}
	}

	public static class Common {
		public final ForgeConfigSpec.BooleanValue chestLootEnabled;
		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Common Settings").push("common");

			chestLootEnabled = builder.comment("Turns on/off loot added to various vanilla chest loot tables").define("chestLootEnabled", true);
			builder.pop();
		}
	}
}
