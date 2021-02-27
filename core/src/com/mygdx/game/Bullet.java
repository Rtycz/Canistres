package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.units.Tank;

public class Bullet
{
    private Tank owner;
    private Vector2 position;
    private Vector2 velocity;
    private float speed;
    private boolean active;
    private int damage;
    private float currentTime;
    private float maxTime;

    public Vector2 getPosition()
    {
        return position;
    }

    public Tank getOwner() {
        return owner;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isActive()
    {
        return active;
    }

    public Bullet()
    {
        this.position = new Vector2();
        this.velocity = new Vector2();
        this.speed = 100.0f;
        this.active = false;
        this.damage = 0;
    }

    public void activate(Tank owner,float x, float y, float vx, float vy, int damage, float maxTime)
    {
        this.owner = owner;
        this.active = true;
        this.position.set(x,y);
        this.velocity.set(vx,vy);
        this.damage = damage;
        this.maxTime = maxTime;
        this.currentTime = 0.0f;
    }

    public void deactivate()
    {
        this.active = false;
    }

    public void update(float dt)
    {
        currentTime += dt;
        if (currentTime >= maxTime) {
            deactivate();
        }

        position.mulAdd(velocity, dt);
        if (position.x<0.0f || position.x> Gdx.graphics.getWidth() || position.y<0 || position.y>Gdx.graphics.getHeight()) {
            active = false;
        }
    }
}
