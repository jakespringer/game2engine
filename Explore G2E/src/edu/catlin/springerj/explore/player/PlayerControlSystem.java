package edu.catlin.springerj.explore.player;

import edu.catlin.springerj.explore.grapple.Grapple;
import edu.catlin.springerj.explore.grapple.GrappleComponent;
import edu.catlin.springerj.explore.jake.Jake;
import edu.catlin.springerj.explore.collisions.CircleCollisionComponent;
import edu.catlin.springerj.explore.enemy.HealthComponent;
import edu.catlin.springerj.explore.planets.PlanetGravityComponent;
import edu.catlin.springerj.g2e.core.AbstractEntity;
import edu.catlin.springerj.g2e.core.AbstractSystem;
import edu.catlin.springerj.g2e.core.Core;
import edu.catlin.springerj.g2e.event.MouseEvent;
import edu.catlin.springerj.g2e.lwjgl.SpriteComponent;
import edu.catlin.springerj.g2e.lwjgl.draw.Graphics;
import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.g2e.physics.PositionComponent;
import edu.catlin.springerj.g2e.physics.RotationComponent;
import edu.catlin.springerj.g2e.physics.VelocityComponent;
import edu.catlin.springerj.g2e.thread.Task;

import org.lwjgl.input.Keyboard;

public class PlayerControlSystem extends AbstractSystem {

	private PositionComponent pos;
	private VelocityComponent vel;
	private RotationComponent rot;
	private SpriteComponent spr;
	private CircleCollisionComponent ccc;
	private PlanetGravityComponent pg;
	private GrappleComponent gc;
	private HealthComponent hc;
	private PlayerEntity player;

	@Override
	public void initialize(AbstractEntity e) {
		pos = e.get(PositionComponent.class);
		vel = e.get(VelocityComponent.class);
		rot = e.get(RotationComponent.class);
		spr = e.get(SpriteComponent.class);
		ccc = e.get(CircleCollisionComponent.class);
		pg = e.get(PlanetGravityComponent.class);
		gc = e.get(GrappleComponent.class);
		hc = e.get(HealthComponent.class);
		player = (PlayerEntity) e;
	}

	@Override
	public void update() {
		try {
			Vector2 toPlanet = pg.planetPos.position.subtract(pos.position)
					.normalize();
			Vector2 relativeVel = vel.velocity.subtract(pg.planetVel.velocity);
			// Rotate to face planet
			rot.rotation = toPlanet.direction() + Math.PI / 2;
			// If you're on a planet
			if (ccc.placeSolid(pos.position.add(toPlanet))) {
				// Left-right movement
//				boolean left = Core.getRootManager().getManager(Keys.class)
//						.isDown(Keyboard.KEY_A);
//				boolean right = Core.getRootManager().getManager(Keys.class)
//						.isDown(Keyboard.KEY_D);
//				boolean red = spr.name.equals("character_idle_left_red");
//				if (left && !right && !red) {
//					vel.velocity = vel.velocity.add(toPlanet.normal().multiply(
//							-.4));
//					spr.setSprite("character_walking_left", 8);
//				} else if (right && !left && !red) {
//					vel.velocity = vel.velocity.add(toPlanet.normal().multiply(
//							.4));
//					spr.setSprite("character_walking_right", 8);
//				} else {
//					if (relativeVel.dot(toPlanet.normal()) < 0 && !red) {
//						spr.setSprite("character_idle_left", 8);
//					} else if (relativeVel.dot(toPlanet.normal()) > 0 && !red) {
//						spr.setSprite("character_idle_right", 8);
//					}
//				}
				// Friction
				Vector2 sideVel = toPlanet.normal().multiply(
						relativeVel.dot(toPlanet.normal()));
				vel.velocity = vel.velocity.add(sideVel.multiply(-.004));
				// Jumping
//				boolean jump = Core.getRootManager().getManager(Keys.class)
//						.isDown(Keyboard.KEY_W)
//						|| Core.getRootManager().getManager(Keys.class)
//								.isDown(Keyboard.KEY_SPACE);
//				if (jump) {
//					vel.velocity = toPlanet.normal()
//							.multiply(relativeVel.dot(toPlanet.normal()))
//							.add(toPlanet.multiply(-100));
//				} else {
//					// Stick to planet
//					vel.velocity = vel.velocity.add(toPlanet);
//				}
//				// Possible destroy grapple
				if (gc.planet != null && pg.planetPos == gc.planet.pc) {
					gc.destroyGrapple();
					// gc.grapple.get(SpriteComponent.class).alpha = .5;
				}
			}
			// Slight friction
			vel.velocity = vel.velocity.multiply(.9999);
			// Grapple
//			if (Core.getRootManager().getManager(MouseInput.class)
//					.isPressed(MouseEvent.BUTTON_MB2)) {
//				if (gc.grapple == null) {
//					Vector2 velocity = Core.getRootManager().getManager(
//							MouseInput.class).mousePos.subtract(pos.position)
//							.setLength(100);
//					gc.grapple = new Grapple(player, velocity);
//					Core.getRootManager().add(gc.grapple);
//				}
//			}
			// Health
			if (hc.currentHealth == 0) {
				for (int i = 0; i < Core.getRootManager().getEntities().size(); i++) {
					Core.getRootManager().remove(
							Core.getRootManager().getEntities().get(i));
				}

//				Core.task(new Task() {
//
//					@Override
//					public void run() {
//						Jake.tutorial.run();
//					}
//				});
				
				Core.init();
				Jake.main(new String[1]);
			}
		} catch (Exception e) {
			return;
		}
	}
}
