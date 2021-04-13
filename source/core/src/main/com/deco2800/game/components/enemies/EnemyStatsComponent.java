package com.deco2800.game.components.enemies;

import com.deco2800.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnemyStatsComponent extends Component {
  private static final Logger logger = LoggerFactory.getLogger(EnemyStatsComponent.class);
  private int health;
  private int baseAttack;
  private int specialAttack;

  public EnemyStatsComponent(int health, int baseAttack, int specialAttack) {
    setHealth(health);
    setBaseAttack(baseAttack);
    setSpecialAttack(specialAttack);
  }

  public int getHealth() {
    return this.health;
  }

  public void setHealth(int health) {
    if (health >= 0) {
      this.health = health;
    } else {
      this.health = 0;
    }
  }

  public void addHealth(int health) {
    setHealth(this.health + health);
  }

  public int getBaseAttack() {
    return this.baseAttack;
  }

  public void setBaseAttack(int attack) {
    if (attack >= 0) {
      this.baseAttack = attack;
    } else {
      logger.error("Can not set base attack to a negative attack value");
    }
  }

  public int getSpecialAttack() {
    return this.specialAttack;
  }

  public void setSpecialAttack(int attack) {
    if (attack >= 0) {
      this.specialAttack = attack;
    } else {
      logger.error("Can not set special attack to a negative attack value");
    }
  }
}
