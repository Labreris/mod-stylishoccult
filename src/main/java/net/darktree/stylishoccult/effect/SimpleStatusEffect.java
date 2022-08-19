package net.darktree.stylishoccult.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.jetbrains.annotations.Nullable;

public abstract class SimpleStatusEffect extends StatusEffect {

	private final boolean instant;

	protected SimpleStatusEffect(StatusEffectCategory type, boolean instant, int color) {
		super(type, color);
		this.instant = instant;
	}

	/**
	 * Called every tick for non-instant effect
	 */
	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		onUpdate(entity, amplifier);
	}

	/**
	 * Called only for instant effects
	 */
	@Override
	public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
		onUpdate(target, amplifier);
	}

	/**
	 * Called at least once for every effect
	 */
	public void onUpdate(LivingEntity entity, int amplifier) {

	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean isInstant() {
		return instant;
	}

	@Override
	public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onRemoved(entity, attributes, amplifier);
	}

	@Override
	public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onApplied(entity, attributes, amplifier);
	}

}
