package jp.ponkichi.bbgreen.entity;

import org.hibernate.annotations.SoftDelete;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "players")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDelete(columnName = "is_deleted")
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 255)
  private String name;

  private Player(String name) {
    this.name = name;
  }

  public static Player of(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("name is required");
    }
    return new Player(name);
  }

  public void changeName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("name is required");
    }
    this.name = name;
  }
}
