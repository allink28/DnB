import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CharacterComponent } from './character.component';
import { CharacterDetailComponent } from './character-detail.component';
import { CharacterPopupComponent } from './character-dialog.component';
import { CharacterDeletePopupComponent } from './character-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CharacterResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const characterRoute: Routes = [
    {
        path: 'character',
        component: CharacterComponent,
        resolve: {
            'pagingParams': CharacterResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Characters'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'character/:id',
        component: CharacterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Characters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const characterPopupRoute: Routes = [
    {
        path: 'character-new',
        component: CharacterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Characters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'character/:id/edit',
        component: CharacterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Characters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'character/:id/delete',
        component: CharacterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Characters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
