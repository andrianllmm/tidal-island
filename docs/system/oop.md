# OOP Principles Applied

## Pillars

- **Encapsulation:** Almost all classes encapsulate their own data and provide getters/setters.

- **Inheritance:** `Entity` → `Player, Item` → `Wood,Stone`, `WorldObject` → `Tree`, `Rock`, …

- **Polymorphism:** `WorldObject.interact(Player)` behaves differently depending on the specific object type, etc.

- **Abstraction:** Abstract classes define common behavior without implementation (`Entity`, `Item`, `WorldObject`). Interfaces define shared behaviors (`Placeable`, `Interactable`, `Edible`). …

## SOLID

- **Single Responsibility (SRP):** Each class has one clear purpose. For example, a common pattern is to have a separate loader class.

- **Open/Closed (OCP):** New `Item` or `WorldObject` types can be added without modifying existing code. (See [extension points](./extension-points.md))

- **Liskov Substitution (LSP):** `Player` can be used wherever `Entity` is expected. Any concrete item and world object can also be used interchangeably in `Inventory` and `WorldObjectManager`.

- **Interface Segregation (ISP):** `Placeable`, `Edible`, and `Interactable` interfaces separate functionality so classes like `Item` and `WorldObject` implement only what they need (i.e. not all are placeable, edible nor interactable). For example, an `Apple` is edible, `Raft` is placeable, but `Wood` is neither.

- **Dependency Inversion (DIP):** High-level modules (e.g. `Player`) depend on abstractions (e.g. `Item`, `WorldObject`) rather than concrete classes (e.g. `Wood`, `Tree`).
