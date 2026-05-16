package jp.ponkichi.bbgreen.entity;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;
import jp.ponkichi.bbgreen.entity.json.eventresult.Situation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "game_id", nullable = false)
  private Game game;

  @Column(name = "sequence_number", nullable = false)
  private Long sequenceNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Event parentEvent;

  @OneToMany(mappedBy = "parentEvent", fetch = FetchType.LAZY)
  private List<Event> childEvents;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false, length = 63)
  private ActionType actionType;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "detail")
  private EventDetail detail;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "result")
  private Situation result;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
