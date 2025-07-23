import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Observable, Subject } from 'rxjs';
import { Chat } from '../models/chat.model';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: Client | undefined; // Client STOMP pour la communication WebSocket
  private messageSubject: Subject<Chat> = new Subject<Chat>(); // Permet d'émettre les messages reçus

  constructor() {
    this.initializeWebSocketConnection(); // Initialisation de la connexion WebSocket
  }

  initializeWebSocketConnection() {
    const serverUrl = 'http://localhost:8080/ws'; // Endpoint du serveur WebSocket
    const ws = new SockJS(serverUrl); // Création d'une instance SockJS pour la connexion WebSocket

    this.stompClient = new Client({
      webSocketFactory: () => ws,
      reconnectDelay: 5000, // Reconnexion automatique après 5 secondes en cas de coupure
      connectHeaders: {
        'Cookie': document.cookie // Envoi des cookies pour l'authentification
      },
      onConnect: () => {
        // Abonnement pour recevoir les messages envoyés à '/support/chat'
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

  // Envoyer des messages au serveur WebSocket
  sendMessage(message: Chat) {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.publish({
        destination: '/app/chat.sendMessage',
        body: JSON.stringify(message)
      });
    } else {
      console.error('STOMP client is not connected.');
    }
  }

  // Permet aux composants de s'abonner aux messages
  getMessage(): Observable<Chat> {
    return this.messageSubject.asObservable();
  }
}
