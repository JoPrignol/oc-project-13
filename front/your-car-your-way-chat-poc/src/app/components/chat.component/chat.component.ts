import { Component, OnInit } from '@angular/core';
import { ChatService } from '../../services/chat.service';

@Component({
  standalone: false,
  selector: 'app-chat',
  templateUrl: './chat.component.html',
})
export class ChatComponent implements OnInit {
  message = '';
  sender = 'Anonymous';
  messages: any[] = [];

  constructor(public chatService: ChatService) {}

  ngOnInit() {
    this.chatService.connect();
    this.chatService.messages$.subscribe((msgs) => (this.messages = msgs));
  }

  send() {
    if (this.message.trim()) {
      this.chatService.sendMessage({ sender: this.sender, content: this.message });
      this.message = '';
    }
  }
}
