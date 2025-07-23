import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Chat } from '../models/chat.model';

@Injectable({
  providedIn: 'root'
})
export class ChatHistoryService {
  private apiUrl = 'http://localhost:8080/api/chats';

  constructor(private http: HttpClient) { }

  getChats(): Observable<Chat[]> {
    return this.http.get<Chat[]>(this.apiUrl, { withCredentials: true });
  }

  saveChat(chat: Chat): Observable<Chat> {
    return this.http.post<Chat>(this.apiUrl, chat);
  }
}
