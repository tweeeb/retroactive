package com.deco2800.game.screens.mainmenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.generic.ServiceLocator;
import com.deco2800.game.ui.components.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.lang.Math;

/**
 * A ui component for displaying the Main menu.
 */
public class MainMenuDisplay extends UIComponent {
  private static final Logger logger = LoggerFactory.getLogger(MainMenuDisplay.class);
  private static final float Z_INDEX = 2f;
  private static TextField txtUsername;
  private Table table;
  private String playablecharcters[] = {
          "images/characters/boy_01/boy_01_menu_preview.png",
          "images/characters/girl_00/girl_00_menu_preview.png",
          "images/characters/boy_00/boy_00_menu_preview.png"

  };
  private  String playableAtlas[]={
          "images/characters/boy_01/boy_01.atlas",
          "images/characters/girl_00/girl_00.atlas",
          "images/characters/boy_00/boy_00.atlas"
  };
  int characterIndex= 0 ;

  @Override
  public void create() {
    super.create();
    addActors();
  }

  private void addActors() {
    table = new Table();
    table.setFillParent(true);
    Image title =
        new Image(
            ServiceLocator.getResourceService()
                .getAsset("images/ui/title/RETROACTIVE-large.png", Texture.class));
    writeAtlas(); //Stores copy of the first character

    TextButton startBtn = new TextButton("Start", skin);
    TextButton leaderboardBtn = new TextButton("LeaderBoard", skin);
    TextButton settingsBtn = new TextButton("Settings", skin);
    TextButton exitBtn = new TextButton("Exit", skin);
    TextButton changeCharacterBtn = new TextButton("Change Character", skin);
    this.txtUsername = new TextField("", skin);
    txtUsername.setMessageText("Username:");

    Image character = new Image(ServiceLocator.getResourceService()
            .getAsset(playablecharcters[characterIndex], Texture.class));

    // Triggers an event when the button is pressed
    startBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Start button clicked");
            writeUsername();
            entity.getEvents().trigger("start");
          }
        });

    leaderboardBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("LearderBoard button clicked");
            entity.getEvents().trigger("leaderboard");
          }
        });

    settingsBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Settings button clicked");
            entity.getEvents().trigger("settings");
          }
        });

    exitBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {

            logger.debug("Exit button clicked");
            entity.getEvents().trigger("exit");
          }
        });

    changeCharacterBtn.addListener(
            new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    characterIndex++;
                    changeCharacterDisplay();
                    writeAtlas();
                    logger.info("Change Character button clicked. ");
                    entity.getEvents().trigger("change_character");
                }
            });

    table.add(title);
    table.row();
    table.add(startBtn).padTop(15);
    table.row();
    table.add(leaderboardBtn).padTop(15f);
    table.row();
    table.add(settingsBtn).padTop(15f);
    table.row();
    table.add(exitBtn).padTop(15f);
    table.row();
    table.add(txtUsername).padTop(50f);
    table.row();
    table.add(character).padTop(20f);
    table.row();
    table.add(changeCharacterBtn).padTop(10f).padBottom(20f);
    stage.addActor(table);

  }

  @Override
  public void draw(SpriteBatch batch) {
    // draw is handled by the stage
  }

  @Override
  public float getZIndex() {
    return Z_INDEX;
  }

  @Override
  public void dispose() {
    table.clear();
    super.dispose();
  }

    /**
     * Changes the character displayed on the main menu page and cycles the index.
     */
  public void changeCharacterDisplay(){
      if (characterIndex <= playablecharcters.length-1){
          dispose();
          create();
      } else {
          characterIndex =0;
          dispose();
          create();
      }
  }
    /**
     * Updates currentCharacterAtlas.txt
     */
    public void writeAtlas(){
        try {
            FileWriter writer = new FileWriter("configs/currentCharacterAtlas.txt");
            writer.write(this.playableAtlas[this.characterIndex]);
            writer.close();
            logger.info("Writing new atlas to settings.");
        } catch (Exception e){

            logger.debug("Could not load the atlas after character change was made.");
        }
    }

    public void writeUsername(){
        try {
            FileWriter writer = new FileWriter("configs/leaderboard.txt",true);
            String username;

            if (this.txtUsername.getText().length()<2){
                username = "DirtyDefault"+ getRandomNum();
            }else{
                username = this.txtUsername.getText();
            }

            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append(username);
            sb.append(":");
            String s = sb.toString();
            writer.write(s);
            writer.close();
            logger.info("Wrote username to leaderboard.");
        } catch (Exception e){
            logger.debug("Could not write username to leaderboard.");
        }
    }

    public int getRandomNum(){
        return (int)(Math.random()*100000);
    }
}
