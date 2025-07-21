import { Injectable } from '@angular/core';
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ChatService {
  private stompClient!: Stomp.Client;
  public messages$ = new BehaviorSubject<any[]>([]);

  connect() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = new Stomp.Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        this.stompClient.subscribe('/topic/chat', (message) => {
          if (message.body) {
            const msg = JSON.parse(message.body);
            this.messages$.next([...this.messages$.getValue(), msg]);
          }
        });
      },
    });
    this.stompClient.activate();
  }

  sendMessage(msg: any) {
    this.stompClient.publish({
      destination: '/app/chat.sendMessage',
      body: JSON.stringify(msg),
    });
  }
}
