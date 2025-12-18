# Data Loading Pipeline

All game data is loaded from JSON files using Jackson.

```mermaid
graph LR
    JSON[JSON Files]
    Loader[Data Loaders]
    Registry[Registries]
    Objects[Game Objects]

    JSON --> Loader
    Loader --> Registry
    Registry --> Objects
```

## Loader Classes

- TileSetLoader → TileSet
- WorldObjectLoader → List<WorldObject>
- RecipeBookLoader → RecipeBook
- SpriteSheet.load() → SpriteSheet
- KeyBindingsLoader → KeyBindings
