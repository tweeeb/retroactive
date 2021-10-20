package com.deco2800.game.entities.components.object;

import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.components.InteractionComponent;
import com.deco2800.game.entities.components.player.PlayerActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatActions extends InteractionComponent {
    private static final Logger logger = LoggerFactory.getLogger(CatActions.class);
    private static final String updateAnimation = "update_animation";


    @Override
    public void create() {
        super.create();
        logger.debug("created Cat actions. ");

    }

    @Override
    public void onCollisionStart(Entity target) {
        if (target.getComponent(PlayerActions.class) != null) {
            entity.getEvents().trigger(updateAnimation, "licking");
        }
    }
}