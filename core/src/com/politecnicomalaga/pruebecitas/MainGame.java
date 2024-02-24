package com.politecnicomalaga.pruebecitas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

/*
* Pantalla donde se desarrolla el juego
* */
public class MainGame implements Screen {
	MainScreen screen;
	Texture img;

	public MainGame(MainScreen screen){
		this.screen = screen;
	}

	@Override
	public void show() {
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		screen.batch.begin();
		screen.batch.draw(img, 100, 100);

		//Si se presiona el botón de escape se abre el menú de pausa del juego
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			screen.setScreen(new PauseMenuScreen(screen, this));
		}
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
		img.dispose();
	}
}
