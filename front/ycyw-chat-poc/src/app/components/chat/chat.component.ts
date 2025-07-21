import { Component, OnInit } from '@angular/core';
import { WebSocketService } from '../../services/web-socket.service';

@Component({
  selector: 'app-chat',
  standalone: false,
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {
  message: string = '';
  messages: any[] = [];

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit(): void {
    // TODO: requêter les messages précédents depuis le serveur lorsque la persistence sera implémentée
    this.webSocketService.getMessage().subscribe((message: any) => {
      this.messages.push(message);
    });
  }

  sendMessage() {
    if (this.message.trim()) {
      const chatMessage = {
        sender: 'User', // TODO: Remplacer par le vrai nom d'utilisateur lorsque les comptes seront implémentés
        content: this.message,
        timestamp: new Date().toLocaleTimeString()
      };
      this.webSocketService.sendMessage(chatMessage);
      this.message = '';
    }
  }
}
