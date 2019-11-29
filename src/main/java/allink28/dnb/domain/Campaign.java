package allink28.dnb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Campaign.
 */
@Entity
@Table(name = "campaign")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "campaign")
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "completed")
    private Boolean completed;

    @OneToMany(mappedBy = "campaign")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Character> players = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Campaign title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public Campaign summary(String summary) {
        this.summary = summary;
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public Campaign completed(Boolean completed) {
        this.completed = completed;
        return this;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Set<Character> getPlayers() {
        return players;
    }

    public Campaign players(Set<Character> characters) {
        this.players = characters;
        return this;
    }

    public Campaign addPlayers(Character character) {
        this.players.add(character);
//        character.setCampaign(this);
        return this;
    }

    public Campaign removePlayers(Character character) {
        this.players.remove(character);
//        character.setCampaign(null);
        return this;
    }

    public void setPlayers(Set<Character> characters) {
        this.players = characters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Campaign campaign = (Campaign) o;
        if (campaign.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), campaign.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Campaign{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", summary='" + getSummary() + "'" +
            ", completed='" + isCompleted() + "'" +
            "}";
    }
}
