import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DnBTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CampaignDetailComponent } from '../../../../../../main/webapp/app/entities/campaign/campaign-detail.component';
import { CampaignService } from '../../../../../../main/webapp/app/entities/campaign/campaign.service';
import { Campaign } from '../../../../../../main/webapp/app/entities/campaign/campaign.model';

describe('Component Tests', () => {

    describe('Campaign Management Detail Component', () => {
        let comp: CampaignDetailComponent;
        let fixture: ComponentFixture<CampaignDetailComponent>;
        let service: CampaignService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DnBTestModule],
                declarations: [CampaignDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CampaignService,
                    JhiEventManager
                ]
            }).overrideTemplate(CampaignDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CampaignDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CampaignService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Campaign(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.campaign).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
