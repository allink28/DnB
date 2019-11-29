import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CampaignComponent } from './campaign.component';
import { CampaignDetailComponent } from './campaign-detail.component';
import { CampaignPopupComponent } from './campaign-dialog.component';
import { CampaignDeletePopupComponent } from './campaign-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CampaignResolvePagingParams implements Resolve<any> {

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

export const campaignRoute: Routes = [
    {
        path: 'campaign',
        component: CampaignComponent,
        resolve: {
            'pagingParams': CampaignResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Campaigns'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'campaign/:id',
        component: CampaignDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Campaigns'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const campaignPopupRoute: Routes = [
    {
        path: 'campaign-new',
        component: CampaignPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Campaigns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'campaign/:id/edit',
        component: CampaignPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Campaigns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'campaign/:id/delete',
        component: CampaignDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Campaigns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
