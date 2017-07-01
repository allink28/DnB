import { BaseEntity } from './../../shared';

const enum Race {
    'Dragonborn',
    'Dwarf',
    'Elf',
    'Gnome',
    'Half_Elf',
    'Half_Orc',
    'Halfling',
    'Human',
    'Tiefling'
}

const enum Sex {
    'Male',
    'Female'
}

const enum Alignment {
    'Lawful_Good',
    'Neutral_Good',
    'Chaotic_Good',
    'Lawful_Neutral',
    'True_Neutral',
    'Chaotic_Neutral',
    'Lawful_Evil',
    'Neutral_Evil',
    'Chaotic_Evil',
    'Unaligned'
}

export class Character implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public race?: Race,
        public classes?: string,
        public sex?: Sex,
        public alignment?: Alignment,
        public height?: string,
        public weight?: number,
        public maxHP?: number,
        public currentHP?: number,
        public strength?: number,
        public dexterity?: number,
        public constitution?: number,
        public wisdom?: number,
        public intelligence?: number,
        public charisma?: number,
        public background?: string,
        public exp?: number,
        public level?: number,
        public spells?: BaseEntity[],
    ) {
    }
}
