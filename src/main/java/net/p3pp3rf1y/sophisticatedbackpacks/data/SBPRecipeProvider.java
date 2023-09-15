package net.p3pp3rf1y.sophisticatedbackpacks.data;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.crafting.ShapeBasedRecipeBuilder;
import net.p3pp3rf1y.sophisticatedcore.init.ModRecipes;
import net.p3pp3rf1y.sophisticatedcore.util.RegistryHelper;

import java.util.function.Consumer;

public class SBPRecipeProvider extends RecipeProvider {
	private static final String HAS_UPGRADE_BASE = "has_upgrade_base";
	private static final String HAS_SMELTING_UPGRADE = "has_smelting_upgrade";

	public SBPRecipeProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		ShapeBasedRecipeBuilder.shaped(ModItems.BACKPACK.get(), ModItems.BASIC_BACKPACK_RECIPE_SERIALIZER.get())
				.pattern("SLS")
				.pattern("SCS")
				.pattern("LLL")
				.define('L', Tags.Items.LEATHER)
				.define('C', Tags.Items.CHESTS_WOODEN)
				.define('S', Tags.Items.STRING)
				.unlockedBy("has_leather", hasLeather())
				.save(consumer);

		SpecialRecipeBuilder.special(ModItems.BACKPACK_DYE_RECIPE_SERIALIZER.get()).save(consumer, SophisticatedBackpacks.getRegistryName("backpack_dye"));
	}

	private static InventoryChangeTrigger.TriggerInstance hasLeather() {
		return inventoryTrigger(ItemPredicate.Builder.item().of(Tags.Items.LEATHER).build());
	}
}
