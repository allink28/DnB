import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DnBTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CharacterDetailComponent } from '../../../../../../main/webapp/app/entities/character/character-detail.component';
import { CharacterService } from '../../../../../../main/webapp/app/entities/character/character.service';
import { Character } from '../../../../../../main/webapp/app/entities/character/character.model';

describe('Component Tests', () => {

    describe('Character Management Detail Component', () => {
        let comp: CharacterDetailComponent;
        let fixture: ComponentFixture<CharacterDetailComponent>;
        let service: CharacterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DnBTestModule],
                declarations: [CharacterDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CharacterService,
                    JhiEventManager
                ]
            }).overrideTemplate(CharacterDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CharacterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CharacterService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Character(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.character).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
