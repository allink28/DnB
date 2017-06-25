import { BaseEntity } from './../../shared';

const enum Sex {
    'male',
    'female'
}

const enum Alignment {
    'lawful_good',
    'neutral_good',
    'chaotic_good',
    'lawful_neutral',
    'true_neutral',
    'chaotic_neutral',
    'lawful_evil',
    'neutral_evil',
    'chaotic_evil'
}

export class Character implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public exp?: number,
        public level?: number,
        public classes?: string,
        public sex?: Sex,
        public height?: string,
        public weight?: number,
        public maxHP?: number,
        public currentHP?: number,
        public strength?: number,
        public dexterity?: number,
        public wisdom?: number,
        public intelligence?: number,
        public charisma?: number,
        public alignment?: Alignment,
        public background?: string,
        public spells?: BaseEntity[],
    ) {
    }
}
