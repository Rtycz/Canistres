package com.mygdx.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Utils.Direction;
import com.mygdx.game.Utils.TankOwner;
import com.mygdx.game.Weapon;

public class PlayerTank extends Tank
{
    int lives;
    int score;

    public PlayerTank(GameScreen game, TextureAtlas atlas)
    {
        super(game);
        this.weapon = new Weapon(atlas);
        this.texture = atlas.findRegion("playerTankBase");
        this.textureHp = atlas.findRegion("bar");
        this.position = new Vector2(100.0f, 100.0f);                                        //респавн танка
        this.speed = 70.0f;                                                             //скорость танка в пх/сек
        //this.width = texture.getRegionWidth();
        //this.height = texture.getRegionHeight();
        this.width = 40;
        this.height = 40;
        this.hpMax = 10;
        this.hp = this.hpMax;
        this.circle = new Circle(position.x, position.y, (width + height)/2);
        this.lives= 5;
        this.ownerType = TankOwner.PLAYER;
    }

    public void addScore(int amount) {
        score += amount;
    }

    @Override
    public void destroy() {
        lives --;
        hp = hpMax;
    }

    public void update(float dt)
    {

        checkMovement(dt);
        float mx = Gdx.input.getX();
        float my = Gdx.graphics.getHeight() - Gdx.input.getY();

        rotateTurretToPoint(mx,my,dt);

        if(Gdx.input.isTouched())
        {
            fire();
        }
        super.update(dt);
    }

    public void renderHUD(SpriteBatch batch, BitmapFont font24) {
        //font24.draw(batch, "Score: " + score + "\nLives: " + lives, 5, 360);
        font24.draw(batch, "\nLives: " + lives, 5, 385);
    }

    public void checkMovement(float dt)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            move(Direction.LEFT, dt);
            return;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            move(Direction.RIGHT, dt);
            return;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            move(Direction.UP, dt);
            return;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            move(Direction.DOWN, dt);
            return;
        }
    }



}
