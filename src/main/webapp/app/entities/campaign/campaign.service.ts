import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Campaign } from './campaign.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CampaignService {

    private resourceUrl = 'api/campaigns';
    private resourceSearchUrl = 'api/_search/campaigns';

    constructor(private http: Http) { }

    create(campaign: Campaign): Observable<Campaign> {
        const copy = this.convert(campaign);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(campaign: Campaign): Observable<Campaign> {
        const copy = this.convert(campaign);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Campaign> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(campaign: Campaign): Campaign {
        const copy: Campaign = Object.assign({}, campaign);
        return copy;
    }
}
