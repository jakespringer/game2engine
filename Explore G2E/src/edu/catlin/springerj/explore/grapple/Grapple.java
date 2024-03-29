package edu.catlin.springerj.explore.grapple;

import edu.catlin.springerj.explore.collisions.CircleCollisionComponent;
import edu.catlin.springerj.explore.player.PlayerEntity;
import edu.catlin.springerj.g2e.core.AbstractEntity;
import edu.catlin.springerj.g2e.lwjgl.SpriteComponent;
import edu.catlin.springerj.g2e.lwjgl.SpriteRenderSystem;
import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.g2e.physics.PositionComponent;
import edu.catlin.springerj.g2e.physics.RotationComponent;
import edu.catlin.springerj.g2e.physics.VelocityComponent;
import edu.catlin.springerj.g2e.physics.VelocitySystem;

public class Grapple extends AbstractEntity {

    public Grapple(PlayerEntity player, Vector2 velocity) {
        //Components
        add(new PositionComponent(player.getComponent(PositionComponent.class).position));
        add(new VelocityComponent(velocity));
        add(new RotationComponent());
        add(new SpriteComponent("grapplehead"));
        add(player.get(GrappleComponent.class));
        add(new CircleCollisionComponent(8, false));
        //Systems
        add(new SpriteRenderSystem());
        add(new VelocitySystem());
        add(new GrappleSystem());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void update() {
    }
}
