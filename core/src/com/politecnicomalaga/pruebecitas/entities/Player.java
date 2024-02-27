package com.politecnicomalaga.pruebecitas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player extends Sprite implements InputProcessor {
    private final float speed = 60 * 2;
    private final float gravity = 60 * 2f;
    private Vector2 vel;    //Movement velocity
    private boolean canJump;
    private TiledMapTileLayer collisionLayer;
    private String groundKey = "ground";
    //Animacion
    private TextureRegion playerStand;
    public enum State{FALLING, JUMPING, STANDING, RUNNING}//Enumeracion de los estados del jugador(parado, corriendo, saltando...)
    public State currentState;      //Guarga el estado actual en el que se encuentra el jugador
    public State previosState;  //Guarga el próximo estado del jugador
    private Animation playerRun;
    private Animation playerJump;
    private boolean runningRight;   //Indica a qué direccion va corriendo el jugador
    private float stateTimer;

    public Player(Sprite sprite, TiledMapTileLayer collisionLayer){
        super(sprite);
        this.vel = new Vector2();
        this.collisionLayer = collisionLayer;
        setSize(collisionLayer.getTileWidth(), collisionLayer.getTileHeight()*1.5f);

        currentState = State.STANDING;
        previosState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        float frameDuration = 0.3f;
        int frameWidth = 39, frameHeigth = 56;

        //Para crear una animacion lo primero que tenemos que hacer es crear un array de texturas para pasarle al constructor la textura de la animacion
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i<3; i++){       //i<4: dentro de nuestro player.png, la animacion de correr dura hasta la imagen 3
            frames.add(new TextureRegion(getTexture(), i*frameWidth,0 , frameWidth, frameHeigth));
        }
        //Se inicializa la animacion de correr
        playerRun = new Animation(frameDuration,frames);
        frames.clear();

        //Ajusta array para animacion de correr
        for(int i = 3; i< 4; i++){
            frames.add(new TextureRegion(getTexture(), i*frameWidth, 0, frameWidth, frameHeigth));
        }

        //Se inicializa la animacion de saltar
        playerJump = new Animation(frameDuration, frames);

        playerStand = new TextureRegion(getTexture(), 0,0,frameWidth,frameHeigth);
        setRegion(playerStand);
    }
    public void draw(SpriteBatch batch){
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void update(float delta){
        vel.y -= gravity*delta;

        if(vel.y > speed)
            vel.y = speed;
        else if(vel.y < -speed)
            vel.y = -speed;

        //Colisiones

        //Guadar las posiciones antiguas
        float oldX = getX(), oldY =getY();

        boolean collisionX, collisionY;

        //Movimiento en x
        setX(getX() + vel.x * delta);

        if(vel.x < 0)  //Si se mueve a las izquierda, queremos comprobar si colisiona el lado izquierdo del sprite
            collisionX = collidesLeft();
        else  //Si se mueve a la derecha, queremos comprobar si colisiona el lado derecho del sprite
            collisionX = collidesRigth();

        /*Si choca con un muro o algún elemento (en el eje x),
            no queremos que continue por lo que le asignamos la posición que tenía anteriormente y
            su velocidad se vuelve 0 para que no pueda moverse y sobrepasar el objeto
         */

        if(collisionX){
            setX(oldX);
            vel.x = 0;
        }

        //Movimiento en y
        setY(getY() + vel.y *delta);

        if(vel.y < 0){  //Si está cayendo, queremos comprobar que toca el suelo
            canJump= collisionY = collidesBottom();     //Hasta que no toque el suelo no puede volver a saltar
        }else{  //Si está saltando, queremos comprobar si se choca con algo
            collisionY = collidesTop();
        }

        /*Si choca con un suelo o con un techo (en el eje y),
            no queremos que pueda sobrepasarlo por lo que le asignamos la posición que tenía
            anteriormente y su velocidad en y se vuelve 0
         */

        if(collisionY){
            setY(oldY);
            vel.y = 0;
        }

        setRegion(getFrame(delta));

    }

    private boolean isCellBlocked(float x, float y){
        Cell cell = collisionLayer.getCell((int) (x/collisionLayer.getTileWidth()), (int) (y/collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(groundKey);
    }

    private boolean collidesRigth(){
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2) {
            if (isCellBlocked(getX() + getWidth(), getY() + step))
                return true;
        }
        return false;
    }
    private boolean collidesLeft(){
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2) {
            if (isCellBlocked(getX(), getY() + step))
                return true;
        }
        return false;
    }
    private boolean collidesTop(){
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2) {
            if (isCellBlocked(getX() + step, getY() + getHeight()))
                return true;
        }
        return false;
    }
    private boolean collidesBottom(){
        for(int step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2){
            if(isCellBlocked(getX() + step, getY()))
                return true;
        }
        return false;
    }

    public TextureRegion getFrame(float dt){
        //Lo primero que saber en que estado el jugador
        currentState = getState();

        TextureRegion region;
        //Vamos a hacer diferentes cosas dependiendo del estado en el que se encuntre el jugador
        switch (currentState){
            case JUMPING:
                region = (TextureRegion) playerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) playerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:   //Estan seguidos porque los tres son iguales
            case STANDING:
            default:
                region = playerStand;
                break;
        }

        //Vamos a establecer la dirección a la que mira el jugador cuando se mueve
        //Para establecer la direccion a la que mira el jugador cuando esta parado debemos recordar el estado previo para saber a que dieccion miraba antes
        if((vel.x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);   //Se gira a la izquierda
            runningRight = false;
        }else if((vel.x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previosState ? stateTimer + dt : 0;
        previosState = currentState;
        return region;
    }

    public State getState(){
        if(vel.y > 0 || vel.y <0 && previosState == State.JUMPING)
            //Esta animacion se ejecutara cuando Mario salte y cuando caiga después de saltar, pero no cuando se caiga por el edge del mundo
            return State.JUMPING;
        else if(vel.y < 0)
            return State.FALLING;
        else if (vel.x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (canJump) {
                vel.y = speed / 1.5f;
                canJump = false;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            vel.x = -speed;
        }else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            vel.x = speed;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
            case Input.Keys.D:
                vel.x = 0;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    //Getters y setters
    public Vector2 getVel() {
        return vel;
    }

    public void setVel(Vector2 vel) {
        this.vel = vel;
    }

    public float getSpeed() {
        return speed;
    }

    public float getGravity() {
        return gravity;
    }


    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }
}
