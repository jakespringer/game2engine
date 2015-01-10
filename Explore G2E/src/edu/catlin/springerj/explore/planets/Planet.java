package edu.catlin.springerj.explore.planets;

import edu.catlin.springerj.explore.collisions.CircleCollisionComponent;
import edu.catlin.springerj.explore.collisions.CircleCollisionSystem;
import edu.catlin.springerj.g2e.core.AbstractEntity;
import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.g2e.physics.PositionComponent;
import edu.catlin.springerj.g2e.physics.VelocityComponent;
import edu.catlin.springerj.g2e.physics.VelocitySystem;

public class Planet extends AbstractEntity {

    public Planet(Vector2 pos, double size) {
        //Components
        add(new PositionComponent(pos));
        add(new VelocityComponent());
        add(new CircleCollisionComponent(size, true));
        add(new PlanetComponent());
        //Systems
        add(new VelocitySystem());
        add(new CircleCollisionSystem());
        add(new PlanetRenderSystem());
        add(new PlanetGravitySystem());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void update() {
    }

}
