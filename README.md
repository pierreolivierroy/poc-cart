# poc-cart

Petite preuve de concept d'un panier d'achat basé sur [spring-boot] et [Liquibase].

## Quick Start Up

Builder le projet:
```terminal
mvn clean install
 ```
 
 Partir le projet (port 8282):
 ```terminal
 java -jar -Dspring.profiles.active=local target/*.jar
 ```
 
### Quelques endpoints pratiques:

- Lister les produits de manière paginée:
```terminal
GET http://localhost:8282/products/list&page=0&size=1
```

- Créer un panier (nécessaire de passer un TTL pour l'instant et d'en créer un avant de faire tout autre opération, à revoir...)
```terminal
POST http://localhost:8282/carts body: {"timeToLive": 60}
```

- Ajouter un produit à un panier
```terminal
POST http://localhost:8282/carts/1/addItem?cartId=1&quantity=2 body: product
```

- Retirer un produit d'un panier: 
```terminal
DELETE http://localhost:8282/carts/1/removeItem?cartItemId=1
```

- Mettre à jour la quantité d'un produit:
```terminal
PATCH http://localhost:8282/carts/1?productId=3&quantity=5
```

- Obtenir le total du panier:
```terminal
GET http://localhost:8282/carts/1/total
```

[spring-boot]: <https://projects.spring.io/spring-boot/>
[Liquibase]: <http://www.liquibase.org/>
