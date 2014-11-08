package edu.catlin.springerj.g2e.object.movement;

import edu.catlin.springerj.g2e.core.AbstractComponent;
import edu.catlin.springerj.g2e.core.AbstractEntity;
import edu.catlin.springerj.g2e.math.Vector2;

public class VelocityComponent extends AbstractComponent {
	public Vector2 velocity;
	
	@Override
	public void initialize(AbstractEntity e) {
		
	}
	
	public VelocityComponent(Vector2 vel) {
		velocity = new Vector2(vel.x(), vel.y());
	}
	
	public VelocityComponent() {
		velocity = new Vector2(0.0f, 0.0f);
	}
}