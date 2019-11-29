import { BaseEntity } from './../../shared';

export class Campaign implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public summary?: string,
        public completed?: boolean,
        public players?: BaseEntity[],
    ) {
        this.completed = false;
    }
}
