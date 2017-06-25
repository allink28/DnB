import { BaseEntity } from './../../shared';

export class Spell implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public level?: number,
        public type?: string,
        public range?: string,
        public castTime?: string,
        public components?: string,
        public duration?: string,
        public description?: string,
        public damage?: string,
        public character?: BaseEntity,
    ) {
    }
}
