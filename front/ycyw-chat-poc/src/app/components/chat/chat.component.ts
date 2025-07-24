import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
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
export class ChatComponent implements OnInit, AfterViewInit {
  // Référence à la div scrollable contenant les messages (utilisé pour le scroll automatique)
  @ViewChild('messageContainer') private messageContainer!: ElementRef;
  message: string = '';
  messages: Chat[] = [];
  username: any = '';

  constructor(
    private webSocketService: WebSocketService,
    private chatHistoryService: ChatHistoryService,
    private userService: UserService
  ) {}

  // Après l'initialisation de la vue, on scroll vers le bas pour afficher les derniers messages
  ngAfterViewInit(): void {
    this.scrollToBottom();
  }

  ngOnInit(): void {
    // Récupérer le username connecté
    this.userService.getUsername().subscribe({
      next: username => {
        this.username = username;
      },
      error: err => {
        console.error('Impossible de récupérer le username', err);
      }
    });

    // Charger l’historique
    this.chatHistoryService.getChats().subscribe({
      next: (chats: Chat[]) => {
        this.messages = [...chats]; // Copier les anciens messages dans le tableau
        this.scrollToBottom(); // Scroll automatique au dernier message lors du chargement des messages de l'historique
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
        this.scrollToBottom(); // Scroll automatique vers le bas à chaque nouveau message
      },
      error: (err) => {
        console.error('Erreur lors de la réception du message:', err);
      }
    });
  }

  // Envoi d'un message au backend via websocket
  sendMessage() {
    if (this.message.trim()) {
      const chatMessage: Chat = {
        content: this.message,
        // authorId: 1, // ID en dur pour le POC
        sentAt: new Date()
      };

      this.webSocketService.sendMessage(chatMessage);
      this.message = '';

      // Ne pas appeler saveChat ici car le backend l'enregistre déjà
    }
  }

  // Fonction pour faire défiler la liste des messages vers le bas
  private scrollToBottom(): void {
    setTimeout(() => {
      try {
        this.messageContainer.nativeElement.scrollTop =
          this.messageContainer.nativeElement.scrollHeight;
      } catch (err) {
        console.error('Erreur scroll:', err);
      }
    }, 0);
  }
}
