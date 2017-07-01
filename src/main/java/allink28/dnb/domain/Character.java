package allink28.dnb.domain;

import allink28.dnb.domain.enumeration.Alignment;
import allink28.dnb.domain.enumeration.Race;
import allink28.dnb.domain.enumeration.Sex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Character.
 */
@Entity
@Table(name = "jhi_character")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "character")
public class Character implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "race")
    private Race race;

    @Column(name = "classes")
    private String classes;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "alignment")
    private Alignment alignment;

    @Column(name = "height")
    private String height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "max_hp")
    private Integer maxHP;

    @Column(name = "current_hp")
    private Integer currentHP;

    @Column(name = "strength")
    private Integer strength;

    @Column(name = "dexterity")
    private Integer dexterity;

    @Column(name = "constitution")
    private Integer constitution;

    @Column(name = "wisdom")
    private Integer wisdom;

    @Column(name = "intelligence")
    private Integer intelligence;

    @Column(name = "charisma")
    private Integer charisma;

    @Column(name = "background")
    private String background;

    @Column(name = "exp")
    private Long exp;

    @NotNull
    @Min(value = 0)
    @Column(name = "jhi_level", nullable = false)
    private Integer level;

    @OneToMany(mappedBy = "character")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Spell> spells = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Character name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    public Character race(Race race) {
        this.race = race;
        return this;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public String getClasses() {
        return classes;
    }

    public Character classes(String classes) {
        this.classes = classes;
        return this;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public Sex getSex() {
        return sex;
    }

    public Character sex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public Character alignment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public String getHeight() {
        return height;
    }

    public Character height(String height) {
        this.height = height;
        return this;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public Character weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getMaxHP() {
        return maxHP;
    }

    public Character maxHP(Integer maxHP) {
        this.maxHP = maxHP;
        return this;
    }

    public void setMaxHP(Integer maxHP) {
        this.maxHP = maxHP;
    }

    public Integer getCurrentHP() {
        return currentHP;
    }

    public Character currentHP(Integer currentHP) {
        this.currentHP = currentHP;
        return this;
    }

    public void setCurrentHP(Integer currentHP) {
        this.currentHP = currentHP;
    }

    public Integer getStrength() {
        return strength;
    }

    public Character strength(Integer strength) {
        this.strength = strength;
        return this;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getDexterity() {
        return dexterity;
    }

    public Character dexterity(Integer dexterity) {
        this.dexterity = dexterity;
        return this;
    }

    public void setDexterity(Integer dexterity) {
        this.dexterity = dexterity;
    }

    public Integer getConstitution() {
        return constitution;
    }

    public Character constitution(Integer constitution) {
        this.constitution = constitution;
        return this;
    }

    public void setConstitution(Integer constitution) {
        this.constitution = constitution;
    }

    public Integer getWisdom() {
        return wisdom;
    }

    public Character wisdom(Integer wisdom) {
        this.wisdom = wisdom;
        return this;
    }

    public void setWisdom(Integer wisdom) {
        this.wisdom = wisdom;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public Character intelligence(Integer intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getCharisma() {
        return charisma;
    }

    public Character charisma(Integer charisma) {
        this.charisma = charisma;
        return this;
    }

    public void setCharisma(Integer charisma) {
        this.charisma = charisma;
    }

    public String getBackground() {
        return background;
    }

    public Character background(String background) {
        this.background = background;
        return this;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Long getExp() {
        return exp;
    }

    public Character exp(Long exp) {
        this.exp = exp;
        return this;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public Integer getLevel() {
        return level;
    }

    public Character level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Set<Spell> getSpells() {
        return spells;
    }

    public Character spells(Set<Spell> spells) {
        this.spells = spells;
        return this;
    }

    public Character addSpell(Spell spell) {
        this.spells.add(spell);
        spell.setCharacter(this);
        return this;
    }

    public Character removeSpell(Spell spell) {
        this.spells.remove(spell);
        spell.setCharacter(null);
        return this;
    }

    public void setSpells(Set<Spell> spells) {
        this.spells = spells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Character character = (Character) o;
        if (character.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), character.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Character{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", race='" + getRace() + "'" +
            ", classes='" + getClasses() + "'" +
            ", sex='" + getSex() + "'" +
            ", alignment='" + getAlignment() + "'" +
            ", height='" + getHeight() + "'" +
            ", weight='" + getWeight() + "'" +
            ", maxHP='" + getMaxHP() + "'" +
            ", currentHP='" + getCurrentHP() + "'" +
            ", strength='" + getStrength() + "'" +
            ", dexterity='" + getDexterity() + "'" +
            ", constitution='" + getConstitution() + "'" +
            ", wisdom='" + getWisdom() + "'" +
            ", intelligence='" + getIntelligence() + "'" +
            ", charisma='" + getCharisma() + "'" +
            ", background='" + getBackground() + "'" +
            ", exp='" + getExp() + "'" +
            ", level='" + getLevel() + "'" +
            "}";
    }
}
