package gg.cse.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Table(name = "match_")
public class Match extends BaseTimeEntity {
    @Id
    private String matchId;

    private String dataVersion;
    private long gameCreation;
    private long gameDuration;
    private long gameId;
    private String gameMode;
    private String gameName;
    private long gameStartTimeStamp;
    private String gameType;
    private String gameVersion;
    private int mapId;

    @ElementCollection
    @CollectionTable(name="match_participants", joinColumns = @JoinColumn(name = "match_id"))
    private Set<Participant> participants;
}
