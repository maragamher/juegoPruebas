package com.politecnicomalaga.pruebecitas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.politecnicomalaga.pruebecitas.entities.Player;

/*
* Pantalla donde se desarrolla el juego
* */
public class MainGame implements Screen {
	private MainScreen screen;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private Player player;

	public MainGame(MainScreen screen){
		this.screen = screen;
	}

	@Override
	public void show() {
		//Mapa
		TmxMapLoader mapLoader = new TmxMapLoader();
		map = mapLoader.load("maps/dungeon1.tmx");	//tmx es la extensión del archivo del mapa

		mapRenderer = new OrthogonalTiledMapRenderer(map);

		camera = new OrthographicCamera();

		//Jugador
		player = new Player(new Sprite(new Texture("player.png")),(TiledMapTileLayer) map.getLayers().get(1));
		player.setPosition(4 * player.getCollisionLayer().getTileWidth(),6 * player.getCollisionLayer().getTileHeight());

		Gdx.input.setInputProcessor(player);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		camera.update();

		mapRenderer.setView(camera);
		mapRenderer.render();

		mapRenderer.getBatch().begin();
		player.draw((SpriteBatch) mapRenderer.getBatch());
		mapRenderer.getBatch().end();

		screen.batch.begin();
		//Si se presiona el botón de escape se abre el menú de pausa del juego
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			screen.setScreen(new PauseMenuScreen(screen, this));
		}

		screen.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 2.5f;
		camera.viewportHeight = height / 2.5f;
		camera.update();
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
		map.dispose();
		mapRenderer.dispose();
	}
}
