# 💬 Chat POC — Your Car Your Way 💬

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-blue)](https://www.oracle.com/java/)
[![Angular](https://img.shields.io/badge/Angular-%5E17-red?logo=angular)](https://angular.io/)
[![H2 Database](https://img.shields.io/badge/Database-H2-lightgray?logo=databricks)](https://www.h2database.com/)
[![WebSocket](https://img.shields.io/badge/Protocol-WebSocket-orange?logo=websocket)](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API)
[![HTTP](https://img.shields.io/badge/Protocol-HTTP-blue)](https://developer.mozilla.org/fr/docs/Web/HTTP)

## Présentation

Ce projet est une **Proof of Concept** pour le système de **chat en temps réel** entre utilisateurs et support de l’application *Your Car Your Way*.

Il s'agit d'un POC avec :
- **Spring Boot** côté backend
- **Angular** côté frontend
- **WebSocket** (STOMP + SockJS) pour la communication temps réel
- Base **H2** pour le stockage des messages
- Authentification via des **utilisateurs 'in memory'**

---

## Démarrer le projet

### Prérequis

- Java 21
- Node.js >= 18
- Maven >= 3.8.x

---

### Lancer le backend

```bash
cd backend/chat-poc
./mvnw spring-boot:run
```

Serveur lancé sur : http://localhost:8080

La console H2 est disponible sur : http://localhost:8080/h2-console

---

### Lancer le frontend
``` bash
cd frontend/ycyw-chat-poc
npm install
ng serve
```

Application accessible sur : http://localhost:4200

Le frontend communique avec le backend via WebSocket et REST.

---

### Connexion à l'application
Il est possible de se connecter avec 2 utilisateurs différents :

>**Username :** user
>
>**Mot de passe :** password

ou

>**Username:** support
>
>**Mot de passe:** password

(D'autres utilisateurs peuvent être définis dans la config Spring Security.)

---

### Fonctionnalités implémentées
- Envoi de messages temps réel via WebSocket

- Affichage de l’historique des messages

- Affichage du nom de l’utilisateur connecté

- Scroll automatique vers le bas

- Stockage temporaire avec H2

- Sécurité basique avec Spring Security + utilisateurs en mémoire

- Authentification par cookie

---

### Limitations
- Pas de base de données persistante

- Authentification basique

- Pas de système de rôles et permissions

- Un seul canal de discussion
