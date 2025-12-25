package ru.samsung.gamestudio;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import ru.samsung.gamestudio.objects.GameObject;

public class ContactManager {

    World world;

    public ContactManager(World world) {
        this.world = world;

        world.setContactListener(new ContactListener() {
           @Override
            public void beginContact(Contact contact) {

                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDef = fixA.getFilterData().categoryBits;
                int cDef2 = fixB.getFilterData().categoryBits;

                if (cDef == GameSetting.MYSOR_BIT && cDef2 == GameSetting.BULLET_BIT
                        || cDef2 == GameSetting.MYSOR_BIT && cDef == GameSetting.BULLET_BIT
                        || cDef == GameSetting.MYSOR_BIT && cDef2 == GameSetting.SHIP_BIT
                        || cDef2 == GameSetting.MYSOR_BIT && cDef == GameSetting.SHIP_BIT) {

                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();

                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });

    }
}
