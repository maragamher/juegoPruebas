package com.politecnicomalaga.pruebecitas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*
* Esta es la clase que se ejecute al iniciar la aplicación, por lo tanto lo único que va a tener
* que mostrar es el menú principal por ahora.
* */
public class MainScreen extends Game {
    SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
