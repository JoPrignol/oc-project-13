import { TestBed } from '@angular/core/testing';

import { ChatHistoryService } from './chat-history.service';

describe('ChatHistoryServiceService', () => {
  let service: ChatHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChatHistoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
