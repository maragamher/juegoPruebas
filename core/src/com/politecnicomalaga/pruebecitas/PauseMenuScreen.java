package com.politecnicomalaga.pruebecitas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
/*
 * Este es el menú de pausa del juego. Debe permitir al usuario pausar el juego, poder volver al
 * mismo o salir al menu principal.
 * Opciones:
 *   - RESUME: al ser pulsada esta opción se reanuda el juego
 *   - EXIT: al ser pulsada se vuelve al menú principal del juego
 * En un futuro se implementará una opción de guardar partida para que el jugador no pierda su progreso
 * y otra de ajustes para el volumen de la música, controles, etc.
 * */

public class PauseMenuScreen implements Screen {
    private final int PAUSE_TITLE_WIDTH = 450;
    private final int PAUSE_TITLE_HEIGHT = 200;
    private final int PAUSE_TITLE_Y = 250;
    private final int RESUME_BUTTON_WIDTH = 150;
    private final int RESUME_BUTTON_HEIGHT = 75;
    private final int RESUME_BUTTON_Y = 175;
    private final int EXIT_BUTTON_WIDTH = 100;
    private final int EXIT_BUTTON_HEIGHT = 50;
    private final int EXIT_BUTTON_Y = 75;
    private Texture resume_button_active,resume_button_inactive,exit_button_active,exit_button_inactive, title_pause;
    MainScreen screen;
    MainGame game;

    public PauseMenuScreen(MainScreen screen, MainGame game){
        this.screen = screen;
        this.game = game; //Se para por parámetro la pantalla de la  partida actua para poder volver a ella al pulsar el botón de "resume"
        this.resume_button_active = new Texture("screens/button/resume-button-active.png");
        this.resume_button_inactive = new Texture("screens/button/resume-button-inactive.png");
        this.exit_button_active = new Texture("screens/button/exit-button-active.png");
        this.exit_button_inactive = new Texture("screens/button/exit-button-inactive.png");
        this.title_pause = new Texture("screens/pause-title.png");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        screen.batch.begin();

        screen.batch.draw(title_pause,Gdx.graphics.getWidth()/2 - PAUSE_TITLE_WIDTH/2, PAUSE_TITLE_Y, PAUSE_TITLE_WIDTH, PAUSE_TITLE_HEIGHT);

        drawResumeButton();
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
    public void drawResumeButton(){
        int x = Gdx.graphics.getWidth()/2 - RESUME_BUTTON_WIDTH/2;
        //Si se coloca el cursor encima de las letras estas cambian sus texturas dando sensación que de se que "activa" la opción
        if(Gdx.input.getX() > x && Gdx.input.getX() < x+RESUME_BUTTON_WIDTH && Gdx.graphics.getHeight()-Gdx.input.getY() > RESUME_BUTTON_Y &&  Gdx.graphics.getHeight()-Gdx.input.getY() < RESUME_BUTTON_Y+RESUME_BUTTON_HEIGHT){
            screen.batch.draw(resume_button_active, Gdx.graphics.getWidth()/2 - RESUME_BUTTON_WIDTH/2, RESUME_BUTTON_Y, RESUME_BUTTON_WIDTH, RESUME_BUTTON_HEIGHT);

            if(Gdx.input.justTouched()){    //Si se pulsa, esta pantalla desaparece y vuelve el juego
                this.dispose();
                screen.setScreen(game);
            }
        }else{
            screen.batch.draw(resume_button_inactive, Gdx.graphics.getWidth()/2 - RESUME_BUTTON_WIDTH/2, RESUME_BUTTON_Y, RESUME_BUTTON_WIDTH, RESUME_BUTTON_HEIGHT);
        }
    }
    public void drawExitButton(){
        int x = Gdx.graphics.getWidth()/2 - EXIT_BUTTON_WIDTH/2;
        //Si se coloca el cursor encima de las letras estas cambian sus texturas dando sensación que de se que "activa" la opción
        if(Gdx.input.getX() > x && Gdx.input.getX() < x+EXIT_BUTTON_WIDTH && Gdx.graphics.getHeight()-Gdx.input.getY() > EXIT_BUTTON_Y &&  Gdx.graphics.getHeight()-Gdx.input.getY() < EXIT_BUTTON_Y+EXIT_BUTTON_HEIGHT){
            screen.batch.draw(exit_button_active, Gdx.graphics.getWidth()/2 - EXIT_BUTTON_WIDTH/2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

            if(Gdx.input.justTouched()){    //Si se pulsa, se sale de la partida y vuelve al menú principal
                this.dispose();
                screen.setScreen(new MainMenuScreen(screen));
            }
        }else{
            screen.batch.draw(exit_button_inactive, Gdx.graphics.getWidth()/2 - EXIT_BUTTON_WIDTH/2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }
    }
}
