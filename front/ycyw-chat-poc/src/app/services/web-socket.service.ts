import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: Client | undefined;
  private messageSubject: Subject<any> = new Subject<any>();

  constructor() {
    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection() {
    const serverUrl = 'http://localhost:8080/ws';
    const ws = new SockJS(serverUrl);
    this.stompClient = new Client({
      webSocketFactory: () => ws,
      reconnectDelay: 5000,
      onConnect: () => {
        this.stompClient?.subscribe('/support/chat', (message) => {
          if (message.body) {
            this.messageSubject.next(JSON.parse(message.body));
          }
        });
      },
      onStompError: (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
      }
    });

    this.stompClient.activate();
  }

  sendMessage(message: any) {
    this.stompClient?.publish({ destination: '/app/chat.sendMessage', body: JSON.stringify(message) });
  }

  getMessage(): Observable<any> {
    return this.messageSubject.asObservable();
  }
}
