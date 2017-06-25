import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DnBTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SpellDetailComponent } from '../../../../../../main/webapp/app/entities/spell/spell-detail.component';
import { SpellService } from '../../../../../../main/webapp/app/entities/spell/spell.service';
import { Spell } from '../../../../../../main/webapp/app/entities/spell/spell.model';

describe('Component Tests', () => {

    describe('Spell Management Detail Component', () => {
        let comp: SpellDetailComponent;
        let fixture: ComponentFixture<SpellDetailComponent>;
        let service: SpellService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DnBTestModule],
                declarations: [SpellDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SpellService,
                    JhiEventManager
                ]
            }).overrideTemplate(SpellDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpellDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpellService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Spell(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.spell).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
