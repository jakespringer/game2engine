package edu.catlin.springerj.explore.rory;

import components.PositionComponent;
import components.SpriteComponent;
import java.io.File;
import edu.catlin.springerj.explore.graphics.GreyStripedBackground;
import edu.catlin.springerj.explore.collisions.CollisionManager;
import edu.catlin.springerj.explore.enemy.Enemy;
import edu.catlin.springerj.explore.graphics.TitleScreenButtons;
import edu.catlin.springerj.explore.player.PlayerEntity;
import edu.catlin.springerj.explore.planets.Planet;
import edu.catlin.springerj.explore.planets.PlanetGravityManager;
import engine.Main;
import edu.catlin.springerj.g2e.event.EventManager;
import edu.catlin.springerj.g2e.lwjgl.LWJGLManager;
import edu.catlin.springerj.g2e.lwjgl.draw.StaticImageRenderSystem;
import edu.catlin.springerj.g2e.lwjgl.view.InstantView;
import edu.catlin.springerj.g2e.tiled.TiledObject;
import edu.catlin.springerj.g2e.tiled.TiledXMLParser;
import edu.catlin.springerj.g2e.web.WebManager;
import engine.AbstractEntity;
import engine.Keys;
import engine.MouseInput;
import engine.Vector2;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.text.View;

public class Rory {

    public static void main(String[] args) {
        Core.initialize(new LWJGLManager());

        final Runnable tutorial = new Runnable() {

            @Override
            public void run() {
                Core.getRootManager().add(new EventManager());
                Core.getRootManager().add(new MouseInput());
                Core.getRootManager().add(new Keys());
                Core.getRootManager().add(new WebManager());
                Core.getRootManager().add(new PlanetGravityManager());
                Core.getRootManager().add(new CollisionManager());

                PlayerEntity p = new PlayerEntity(new Vector2(100, 0));

                Core.getRootManager().add(new View(p));
                Core.getRootManager().add(new GreyStripedBackground());

                TiledXMLParser tmx = new TiledXMLParser(new File(Core.getResourceFolder() + "map\\tutorial-00.tmx"));
                tmx.parse();

                TiledObject object;
                while ((object = tmx.nextObject()) != null) {
                    switch (object.type) {
                        case "planet":
                            Core.getRootManager().add(new Planet(new Vector2(object.x + object.width / 2, -object.y - object.width / 2), object.width / 2));
                            break;
                        case "player":
                            p.getComponent(PositionComponent.class).position = new Vector2(object.x + 32 / 2, -object.y - 32 / 2);
                            break;
                        case "slimeenemy":
                            Core.getRootManager().add(new Enemy(new Vector2(object.x + 32 / 2, -object.y - 32 / 2)));
                            break;
                        default:
                            break;
                    }
                }
                Core.getRootManager().add(p);

                try {
                    File file = new File("H:\\8bit_32.aif");
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
                    Clip clip = AudioSystem.getClip();
                    clip.open(inputStream);
                    clip.start();
                    //Audio aifEffect = AudioLoader.getAudio("AIF", ResourceLoader.getResourceAsStream("H:\\8bit_32.aif"));
                    //aifEffect.playAsMusic(1, 1, true);
                } catch (Exception ex) {
                }
            }
        };

        Runnable titlescreen = new Runnable() {

            @Override
            public void run() {
                Core.getRootManager().add(new EventManager());
                Core.getRootManager().add(new WebManager());

                AbstractEntity ae = null;
                Core.getRootManager().add(new InstantView(ae = new AbstractEntity() {
                    final AbstractEntity thus = this;
                    Object constructor = new Object() {
                        {
                            thus.add(new PositionComponent());
                        }
                    };

                    @Override
                    public void initialize() {

                    }

                    @Override
                    public void update() {
                        thus.get(PositionComponent.class).position = thus.get(PositionComponent.class).position.add(new Vector2(Main.stepSize * 50.0d, Main.stepSize * 10.0d));
                        thus.get(PositionComponent.class).position = new Vector2(thus.get(PositionComponent.class).position.x % 256, thus.get(PositionComponent.class).position.y % 256);
                    }
                }));

                Core.getRootManager().add(ae);
                Core.getRootManager().add(new GreyStripedBackground(8, 8));
                Core.getRootManager().add(new AbstractEntity() {

                    @Override
                    public void initialize() {
                        add(new PositionComponent(new Vector2()));
                        add(new SpriteComponent("titlescreen"));

                        add(new StaticImageRenderSystem());
                    }

                    @Override
                    public void update() {

                    }

                });

                Core.getRootManager().add(new TitleScreenButtons(tutorial));

                Core.getRootManager().add(new Keys());
                Core.getRootManager().add(new MouseInput());
            }
        };

        ((LWJGLManager) Core.getRootManager()).addRoom("tutorial00", tutorial);
        ((LWJGLManager) Core.getRootManager()).addRoom("titlescreen", titlescreen);
        ((LWJGLManager) Core.getRootManager()).setRoom("tutorial00");
        Core.run();
    }
}
