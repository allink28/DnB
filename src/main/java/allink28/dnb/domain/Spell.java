package allink28.dnb.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Spell.
 */
@Entity
@Table(name = "spell")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "spell")
public class Spell implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "jhi_level")
    private Integer level;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "jhi_range")
    private String range;

    @Column(name = "cast_time")
    private String castTime;

    @Column(name = "components")
    private String components;

    @Column(name = "duration")
    private String duration;

    @Column(name = "description")
    private String description;

    @Column(name = "damage")
    private String damage;

    @ManyToOne
    private Character character;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Spell name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public Spell level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public Spell type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRange() {
        return range;
    }

    public Spell range(String range) {
        this.range = range;
        return this;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getCastTime() {
        return castTime;
    }

    public Spell castTime(String castTime) {
        this.castTime = castTime;
        return this;
    }

    public void setCastTime(String castTime) {
        this.castTime = castTime;
    }

    public String getComponents() {
        return components;
    }

    public Spell components(String components) {
        this.components = components;
        return this;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public String getDuration() {
        return duration;
    }

    public Spell duration(String duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public Spell description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDamage() {
        return damage;
    }

    public Spell damage(String damage) {
        this.damage = damage;
        return this;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public Character getCharacter() {
        return character;
    }

    public Spell character(Character character) {
        this.character = character;
        return this;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Spell spell = (Spell) o;
        if (spell.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), spell.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Spell{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", level='" + getLevel() + "'" +
            ", type='" + getType() + "'" +
            ", range='" + getRange() + "'" +
            ", castTime='" + getCastTime() + "'" +
            ", components='" + getComponents() + "'" +
            ", duration='" + getDuration() + "'" +
            ", description='" + getDescription() + "'" +
            ", damage='" + getDamage() + "'" +
            "}";
    }
}
