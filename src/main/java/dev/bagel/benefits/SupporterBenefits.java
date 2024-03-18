package dev.bagel.benefits;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SupporterBenefits implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Supporter Benefits");


	@Override
	public void onInitialize() {
		PlayerManager.init();
	}

}