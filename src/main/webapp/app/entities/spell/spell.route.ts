import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SpellComponent } from './spell.component';
import { SpellDetailComponent } from './spell-detail.component';
import { SpellPopupComponent } from './spell-dialog.component';
import { SpellDeletePopupComponent } from './spell-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class SpellResolvePagingParams implements Resolve<any> {

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

export const spellRoute: Routes = [
    {
        path: 'spell',
        component: SpellComponent,
        resolve: {
            'pagingParams': SpellResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Spells'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'spell/:id',
        component: SpellDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Spells'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const spellPopupRoute: Routes = [
    {
        path: 'spell-new',
        component: SpellPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Spells'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'spell/:id/edit',
        component: SpellPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Spells'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'spell/:id/delete',
        component: SpellDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Spells'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
