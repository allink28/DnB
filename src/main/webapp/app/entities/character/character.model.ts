import { BaseEntity } from './../../shared';

const enum Sex {
    'male',
    'female'
}

export class Character implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public level?: number,
        public classes?: string,
        public sex?: Sex,
        public height?: string,
        public weight?: number,
        public spells?: BaseEntity[],
    ) {
    }
}
