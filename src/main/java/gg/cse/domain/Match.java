package gg.cse.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Entity
public class Match {
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
    private List<Participant> participants;
}
