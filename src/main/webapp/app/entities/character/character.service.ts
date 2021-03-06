import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Character } from './character.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CharacterService {

    private resourceUrl = 'api/characters';
    private resourceSearchUrl = 'api/_search/characters';

    constructor(private http: Http) { }

    create(character: Character): Observable<Character> {
        const copy = this.convert(character);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    generate(character: Character): Observable<Character> {
        const copy = this.convert(character);
        return this.http.post(this.resourceUrl + '/generate', copy).map((res: Response) => {
            return res.json();
        });
    }

//    generate(name: String): Observable<String> {
//        return this.http.post(this.resourceUrl + '/generate', name).map((res: Response) => {
//            return res.json();
//        });
//    }

    update(character: Character): Observable<Character> {
        const copy = this.convert(character);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Character> {
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

    private convert(character: Character): Character {
        const copy: Character = Object.assign({}, character);
        return copy;
    }
}
