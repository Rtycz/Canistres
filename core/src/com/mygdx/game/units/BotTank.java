package com.mygdx.game.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameScreen;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Utils.Direction;
import com.mygdx.game.Utils.TankOwner;
import com.mygdx.game.Weapon;

public class BotTank extends Tank
{
    Direction preferredDirection;
    float aiTimer;
    float aiTimerTo;
    boolean active;
    float pursuitRadius;        //радиус реагирования
    Vector3 lastPosition;

    public boolean isActive() {
        return active;
    }

    public BotTank(GameScreen game, TextureAtlas atlas)
    {
        super(game);
        this.weapon = new Weapon(atlas);
        this.texture = atlas.findRegion("botTankBase");
        this.textureHp = atlas.findRegion("bar");
        this.position = new Vector2(500.0f, 500.0f);                                        //респавн танка
        this.lastPosition = new Vector3(0.0f, 0.0f, 0.0f);
        this.speed = 40.0f;                                                             //скорость танка в пх/сек
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.hpMax = 3;
        this.hp = this.hpMax;
        this.aiTimerTo = 3.0f;
        this.pursuitRadius = 300.0f;
        this.preferredDirection = Direction.UP;
        this.circle = new Circle(position.x, position.y, (width + height)/2);
        this.ownerType = TankOwner.AI;
    }

    public void activate(float x, float y)
    {
        hpMax = 3;
        hp = hpMax;
        angle = preferredDirection.getAngle();
        preferredDirection = Direction.values() [MathUtils.random(0, Direction.values().length - 1)];
        position.set(x,y);
        aiTimer = 0.0f;
        active = true;
    }

    @Override
    public void destroy() {
        active = false;
    }

    public void update(float dt)
    {
        aiTimer += dt;

        if (aiTimer >= aiTimerTo)
        {
            aiTimer = 0;
            aiTimerTo = MathUtils.random(3.5f, 5.0f);
            preferredDirection = Direction.values() [MathUtils.random(0, Direction.values().length - 1)];
            angle = preferredDirection.getAngle();
        }
        move(preferredDirection, dt);

        float dst = this.position.dst(gameScreen.getPlayer().getPosition());

        if (dst < pursuitRadius)
        {
            rotateTurretToPoint(gameScreen.getPlayer().getPosition().x, gameScreen.getPlayer().getPosition().y,dt);
            fire();
        }
        if (Math.abs(position.x - lastPosition.x) < 0.5f && Math.abs(position.y - lastPosition.y) < 0.5f) {
            lastPosition.z += dt;
            if (lastPosition.z > 0.25f) {
                aiTimer += 10.0f;
            }
        } else {
            lastPosition.x = position.x;
            lastPosition.y = position.y;
            lastPosition.z = 0.0f;
        }

        super.update(dt);
    }
}
