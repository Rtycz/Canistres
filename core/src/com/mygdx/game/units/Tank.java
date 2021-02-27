package com.mygdx.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Utils.Direction;
import com.mygdx.game.Utils.TankOwner;
import com.mygdx.game.Utils.Utils;
import com.mygdx.game.Weapon;

public abstract class Tank {
    //такие поля видимы только в пакете
    GameScreen gameScreen;
    TankOwner ownerType;
    Weapon weapon;
    TextureRegion texture;
    TextureRegion textureHp;
    Vector2 position;
    Vector2 tmp;
    Circle circle;

    int hp;
    int hpMax;

    float speed;
    float angle;

    float turretAngle;
    float fireTimer;

    int width;
    int height;
    //int halfWidth;
    //int halfHeight;


    public Circle getCircle() {
        return circle;
    }

    public Tank(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
        this.tmp = new Vector2(0.0f, 0.0f);
    }

    public TankOwner getOwnerType() {
        return ownerType;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void render (SpriteBatch batch)
    {
        batch.draw(texture, position.x-width/2, position.y-height/2,width/2, height/2,width,height,1 ,1,angle);
        batch.draw(weapon.getTexture(), position.x-width/2, position.y-height/2,width/2, height/2,width,height,1 ,1,turretAngle);

        if (hp<hpMax)
        {
            batch.setColor(0,0,0,0.8f); //цвет батча перемножается на цвет текстуры
            batch.draw(textureHp, position.x - width / 2 - 2, position.y + height / 2 - 4 -2, 44, 12 );
            batch.setColor(0,1,0,0.8f);
            batch.draw(textureHp, position.x - width / 2, position.y + height / 2 - 4, ((float)hp/ hpMax) * 40, 8 );
            batch.setColor(1,1,1,1);
        }
    }

    public void takeDamage(int damage)
    {
        hp -= damage;
        if (hp<=0)
        {
            destroy();
        }
    }

    public abstract void destroy();

    public void update(float dt)
    {
        fireTimer += dt;
        if (position.x < 0.0f) {position.x = 0.0f;}
        if (position.x > Gdx.graphics.getWidth()) {position.x = Gdx.graphics.getWidth();}
        if (position.y < 0.0f) {position.y = 0.0f;}
        if (position.y > Gdx.graphics.getHeight()) {position.y = Gdx.graphics.getHeight();}

        circle.setPosition(position);
    }

    public void rotateTurretToPoint(float pointX, float pointY, float dt)
    {
        float angleTo = Utils.getAngle(position.x, position.y, pointX, pointY);
        turretAngle = Utils.makeRotation(turretAngle, angleTo, 270.0f, dt);                         //скорость поворота башни, углы/сек
        turretAngle = Utils.angleToFromNegPiToPosPi(turretAngle);
    }

    public void move(Direction direction, float dt) {
        tmp.set(position);
        tmp.add(speed * direction.getVx() * dt, speed * direction.getVy() * dt);
        if (gameScreen.getMap().isAreaClear(tmp.x, tmp.y, width / 2)) {
            angle = direction.getAngle();
            position.set(tmp);
        }
    }

    public void fire()
    {
        if(fireTimer >= weapon.getFirePeriod())
        {
            fireTimer = 0.0f;
            float angleRad = (float) Math.toRadians(turretAngle);
            gameScreen.getBulletEmitter().activate(this,position.x,position.y,weapon.getProjectileSpeed() * (float)Math.cos(angleRad), weapon.getProjectileSpeed() * (float)Math.sin(angleRad), weapon.getDamage(), weapon.getProjectileLifetime());            //скорость пульки, пх/сек

        }
    }

}
