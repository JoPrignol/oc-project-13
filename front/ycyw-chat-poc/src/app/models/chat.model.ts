export interface Chat {
  id?: number; // L'identifiant est optionnel car il est généré par la base de données
  content: string;
  authorId: number;
  sentAt?: Date; // La date est optionnelle car elle est générée automatiquement par le backend
}
