package subway.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 25, nullable = false)
    private String name;
    @Column(length = 25, nullable = false)
    private String color;
    @Embedded
    private final Sections sections = new Sections();
    @Builder
    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }
    public static Line of(String name, String color, Station upStation, Station downStation, Long distance){
        Line line = new Line(name, color);
        Section section = Section.of(line, distance, upStation, downStation);
        line.getSections().firstAddSection(section);
        return line;
    }
    public void updateNameAndColor(String name, String color){
        this.name = name;
        this.color = color;
    }

    public List<Station> getStations() {
        return sections.getStations();
    }
    public void firstAddSection(Section section) {
        sections.firstAddSection(section);
    }
    public void addSection(Section section) {
        sections.addSection(section);
    }

    public void deleteSection(Station station) {
        sections.deleteSection(station);
    }

}
