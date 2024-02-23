package com.politecnicomalaga.pruebecitas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {

    private final int PLAY_BUTTON_WIDTH = 150;
    private final int PLAY_BUTTON_HEIGHT = 75;
    private final int PLAY_BUTTON_Y = 200;
    private final int EXIT_BUTTON_WIDTH = 100;
    private final int EXIT_BUTTON_HEIGHT = 50;
    private final int EXIT_BUTTON_Y = 75;

    private Texture play_button_active,play_button_inactive,exit_button_active,exit_button_inactive;
    MainScreen screen;
    public MainMenuScreen(MainScreen screen){
        this.screen = screen;
        this.play_button_active = new Texture("play-button-active.png");
        this.play_button_inactive = new Texture("play-button-inactive.png");
        this.exit_button_active = new Texture("exit-button-active.png");
        this.exit_button_inactive = new Texture("exit-button-inactive.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        screen.batch.begin();

        drawPlayButton();
        drawExitButton();


        screen.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void drawPlayButton(){
        int x = Gdx.graphics.getWidth()/2 - PLAY_BUTTON_WIDTH/2;
        if(Gdx.input.getX() > x && Gdx.input.getX() < x+PLAY_BUTTON_WIDTH && Gdx.graphics.getHeight()-Gdx.input.getY() > PLAY_BUTTON_Y &&  Gdx.graphics.getHeight()-Gdx.input.getY() < PLAY_BUTTON_Y+PLAY_BUTTON_HEIGHT){
            screen.batch.draw(play_button_active, Gdx.graphics.getWidth()/2 - PLAY_BUTTON_WIDTH/2, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);

            if(Gdx.input.justTouched()){
                this.dispose();
                screen.setScreen(new MainGame(screen));
            }
        }else{
            screen.batch.draw(play_button_inactive, Gdx.graphics.getWidth()/2 - PLAY_BUTTON_WIDTH/2, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }
    }
    public void drawExitButton(){
        int x = Gdx.graphics.getWidth()/2 - EXIT_BUTTON_WIDTH/2;
        if(Gdx.input.getX() > x && Gdx.input.getX() < x+EXIT_BUTTON_WIDTH && Gdx.graphics.getHeight()-Gdx.input.getY() > EXIT_BUTTON_Y &&  Gdx.graphics.getHeight()-Gdx.input.getY() < EXIT_BUTTON_Y+EXIT_BUTTON_HEIGHT){
            screen.batch.draw(exit_button_active, Gdx.graphics.getWidth()/2 - EXIT_BUTTON_WIDTH/2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

            if(Gdx.input.justTouched()){
                Gdx.app.exit();
            }
        }else{
            screen.batch.draw(exit_button_inactive, Gdx.graphics.getWidth()/2 - EXIT_BUTTON_WIDTH/2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }
    }
}
