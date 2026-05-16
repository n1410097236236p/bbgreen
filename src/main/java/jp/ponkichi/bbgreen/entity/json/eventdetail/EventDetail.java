package jp.ponkichi.bbgreen.entity.json.eventdetail;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.atbat.BallsInPlayOutDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.atbat.DoubleDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.atbat.FourBallsDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.atbat.HitByPitchDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.atbat.HomeRunDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.atbat.SacrificeBuntDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.atbat.SacrificeFlyDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.atbat.SingleDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.atbat.StrikeOutDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.atbat.TripleDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.atbat.UncaughtThirdStrikeDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.defence.AssistDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.defence.ErrorDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.defence.PassedBallDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.defence.PickoffDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.defence.PutOutDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.defence.WildPitchDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.other.ChangePlayerDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.other.ChangePositionDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.other.GameEndDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.other.PlayBallDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.other.TurnOverDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.pitch.BallDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.pitch.BuntFoulBallDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.pitch.FoulBallDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.pitch.InPlayDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.pitch.StrikeDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.pitch.SwingAndMissDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.running.CaughtStealingDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.running.PickedOffDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.running.StolenBaseDetail;
import jp.ponkichi.bbgreen.entity.json.eventdetail.running.TagUpDetail;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ //
    // --- 投球結果系 ---
    @JsonSubTypes.Type(value = StrikeDetail.class, name = "STRIKE"),
    @JsonSubTypes.Type(value = SwingAndMissDetail.class, name = "SWING_AND_MISS"),
    @JsonSubTypes.Type(value = BallDetail.class, name = "BALL"),
    @JsonSubTypes.Type(value = FoulBallDetail.class, name = "FAUL_BALL"),
    @JsonSubTypes.Type(value = BuntFoulBallDetail.class, name = "BUNT_FAUL_BALL"),
    @JsonSubTypes.Type(value = InPlayDetail.class, name = "IN_PLAY"),
    // --- 打撃結果系 ---
    @JsonSubTypes.Type(value = SingleDetail.class, name = "SINGLE"),
    @JsonSubTypes.Type(value = DoubleDetail.class, name = "DOUBLE"),
    @JsonSubTypes.Type(value = TripleDetail.class, name = "TRIPLE"),
    @JsonSubTypes.Type(value = HomeRunDetail.class, name = "HOME_RUN"),
    @JsonSubTypes.Type(value = FourBallsDetail.class, name = "FOUR_BALLS"),
    @JsonSubTypes.Type(value = HitByPitchDetail.class, name = "HIT_BY_PITCH"),
    @JsonSubTypes.Type(value = BallsInPlayOutDetail.class, name = "BALLS_IN_PLAY_OUT"),
    @JsonSubTypes.Type(value = StrikeOutDetail.class, name = "STRIKE_OUT"),
    @JsonSubTypes.Type(value = UncaughtThirdStrikeDetail.class, name = "UNCAUGHT_THIRD_STRIKE"),
    @JsonSubTypes.Type(value = SacrificeBuntDetail.class, name = "SACRIFICE_BUNT"),
    @JsonSubTypes.Type(value = SacrificeFlyDetail.class, name = "SACRIFICE_FLY"),
    // --- 守備系 ---
    @JsonSubTypes.Type(value = AssistDetail.class, name = "ASSIST"),
    @JsonSubTypes.Type(value = PutOutDetail.class, name = "PUT_OUT"),
    @JsonSubTypes.Type(value = PickoffDetail.class, name = "PICKOFF"),
    @JsonSubTypes.Type(value = ErrorDetail.class, name = "ERROR"),
    @JsonSubTypes.Type(value = PassedBallDetail.class, name = "PASSED_BALL"),
    @JsonSubTypes.Type(value = WildPitchDetail.class, name = "WILD_PITCH"),
    // --- 走塁系 ---
    @JsonSubTypes.Type(value = StolenBaseDetail.class, name = "STOLEN_BASE"),
    @JsonSubTypes.Type(value = CaughtStealingDetail.class, name = "CAUGHT_STEALING"),
    @JsonSubTypes.Type(value = PickedOffDetail.class, name = "PICKED_OFF"),
    @JsonSubTypes.Type(value = TagUpDetail.class, name = "TAG_UP"),
    // --- その他 ---
    @JsonSubTypes.Type(value = PlayBallDetail.class, name = "PLAY_BALL"),
    @JsonSubTypes.Type(value = TurnOverDetail.class, name = "TURN_OVER"),
    @JsonSubTypes.Type(value = GameEndDetail.class, name = "GAME_END"),
    @JsonSubTypes.Type(value = ChangePlayerDetail.class, name = "CHANGE_PLAYER"),
    @JsonSubTypes.Type(value = ChangePositionDetail.class, name = "CHANGE_POSITION")})
public interface EventDetail {
  EventType getEventType();
}
