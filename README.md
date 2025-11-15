# 🚀 Backend – Gestion des Interventions

### Microservices Spring Boot – Stage OCP (Juillet 2025 – Août 2025)

## 📌 **Description du Projet**

Ce backend fait partie d’une application web full-stack développée dans le cadre d’un stage au **Groupe OCP (Khouribga)**.
L’objectif est de gérer les **interventions techniques** (planification, suivi, techniciens, équipements…) via une architecture **microservices** sécurisée, scalable et intégrée à un écosystème messaging + monitoring.

---

## 🏗️ **Architecture**

Le backend repose sur une architecture microservices basée sur **Spring Boot** :

* **Service Intervention** : gestion des interventions (CRUD, planning…)
* **Service Technicien** : gestion des techniciens
* **Service Notification** : envoi des notifications (via RabbitMQ)
* **Service Logs** : stockage et consultation des logs (MongoDB)
* **API Gateway** (Spring Cloud Gateway)
* **Service Registry** (Eureka)

Un schéma UML a été réalisé (cas d’utilisation / classes / séquences / activités) pour structurer l’analyse des besoins.

---

## 🔐 **Sécurité**

La sécurisation de l’ensemble du backend repose sur :

* **Spring Security**
* **JWT (JSON Web Token)**
  → Authentification stateless + autorisations par rôles
  → Filtre JWT personnalisé pour valider chaque requête
  → Protection des microservices via Gateway

---

## 📮 **Communication inter-microservices**

* **RabbitMQ** est utilisé pour la messagerie asynchrone.
* Exemple : lorsqu’une intervention est créée, un message est envoyé au service Notification.

RabbitMQ Management UI permet de suivre :

* les exchanges
* les queues
* les consommateurs

---

## 💾 **Base de Données**

Le backend utilise un modèle **polyglotte** :

### 🔸 MySQL

Pour toutes les données relationnelles :

* interventions
* techniciens
* utilisateurs
* affectations

### 🔸 MongoDB

Pour les logs applicatifs :

* actions utilisateur
* événements système
* messages RabbitMQ
  → Permet un audit complet et une meilleure traçabilité.

---

## ⚙️ **Tests**

Les tests couvrent :

* **JUnit** : tests unitaires (services, repositories)
* **MockMvc** : tests des endpoints REST
* **Postman** : tests d’intégration & scénarios complets

---


## 🤖 **Chatbot Intelligent (Assistance Utilisateur)**

Le backend expose un microservice supplémentaire dédié au chatbot :

* Gestion des requêtes utilisateur
* Analyse de texte (NLP)
* Interaction avec les microservices métier
* Envoi de réponses contextualisées à l’interface Angular

---



## 👤 **Auteur**

**Ismail – Stagiaire Ingénieur Génie Informatique**
**Groupe OCP, Khouribga – Stage Juillet/Août 2025**

---
