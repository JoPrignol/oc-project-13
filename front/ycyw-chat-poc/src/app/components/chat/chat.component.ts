import { Component, OnInit } from '@angular/core';
import { WebSocketService } from '../../services/web-socket.service';
import { ChatHistoryService } from '../../services/chat-history.service';
import { Chat } from '../../models/chat.model';
import { UserService } from '../../services/user.service';
@Component({
  selector: 'app-chat',
  standalone: false,
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {
  message: string = '';
  messages: Chat[] = [];
  username: any = '';

  constructor(
    private webSocketService: WebSocketService,
    private chatHistoryService: ChatHistoryService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    // Récupérer le username connecté
    this.userService.getUsername().subscribe({
      next: username => {
        this.username = username;
        console.log('Username récupéré:', this.username);
      },
      error: err => {
        console.error('Impossible de récupérer le username', err);
      }
    });

    // Charger l’historique
    this.chatHistoryService.getChats().subscribe({
      next: (chats: Chat[]) => {
        this.messages = [...chats]; // Copie
        console.log('Historique des chats chargé:', this.messages);
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
        authorId: 1,
        sentAt: new Date()
      };

      this.webSocketService.sendMessage(chatMessage);
      this.message = '';

      // Ne pas appeler saveChat ici si le backend l'enregistre déjà
    }
  }
}
