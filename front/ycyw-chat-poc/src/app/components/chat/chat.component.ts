import { Component, OnInit } from '@angular/core';
import { WebSocketService } from '../../services/web-socket.service';
import { ChatHistoryService } from '../../services/chat-history.service';
import { Chat } from '../../models/chat.model';

@Component({
  selector: 'app-chat',
  standalone: false,
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {
  message: string = '';
  messages: Chat[] = [];

  constructor(
    private webSocketService: WebSocketService,
    private chatHistoryService: ChatHistoryService
  ) {}

  ngOnInit(): void {
  // Charger l’historique
  this.chatHistoryService.getChats().subscribe({
    next: (chats: Chat[]) => {
      this.messages = [...chats]; // Copie
    },
    error: (err) => {
      console.error('Erreur lors de la récupération des chats:', err);
    }
  });

  // Abonnement aux nouveaux messages via WebSocket
  this.webSocketService.getMessage().subscribe({
    next: (message: Chat) => {
      // Ajouter à la fin sans écraser
      this.messages = [...this.messages, message];
    },
    error: (err) => {
      console.error('Erreur lors de la réception du message:', err);
    }
  });
}


  sendMessage() {
  if (this.message.trim()) {
    const chatMessage: Chat = {
      content: this.message,
      authorId: 1, //TODO: remplacer par le nom de l'utilisateur connecté une fois l'authentification implémentée
      sentAt: new Date()
    };

    this.webSocketService.sendMessage(chatMessage);
    this.message = '';

    // Ne pas appeler saveChat ici si le backend l'enregistre déjà
  }
}
}
