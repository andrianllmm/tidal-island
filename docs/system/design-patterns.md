## Design Patterns Used

### Creational Patterns

1. **Singleton**

   - `Config` - Global configuration access
   - `GameClock` - Centralized time management
   - `ItemRegistry` - Static type registration
   - `WorldObjectRegistry` - Static type registration

2. **Builder**

   - `ColliderBuilder` - Flexible collider construction
   - `UiStyleBuilder` - Fluent style configuration
   - `SpriteSetBuilder` - Complex sprite set creation

3. **Factory Method**

   - `ItemRegistry.create()` - Type-safe item creation
   - `WorldObjectRegistry.create()` - Type-safe object creation

4. **Registry**

   - `ItemRegistry` - Central item type management
   - `WorldObjectRegistry` - Central object type management

5. **Prototype**
   - `.copy()` - Copying objects (e.g. colliders, ui components)

### Structural Patterns

1. **Composite**

   - `UiComponent` hierarchy - Tree of UI components
   - Child components inherit parent transformations

2. **Facade**

   - `CollisionManager` - Simplified collision queries
   - `UiManager` - Unified UI subsystem access
   - `CraftingManager` - Simplified crafting operations

3. **Flyweight**
   - `TileSet` - Shared tile definitions
   - `SpriteAtlas` - Shared sprite texture data

### Behavioral Patterns

1. **Observer**

   - `Observable`/`EventListener` - Event system
   - `Inventory` fires `InventoryChangeEvent`
   - `Equipment` fires `EquipmentChangeEvent`

2. **State**

   - `GameStateManager` - Stack-based state machine
   - Different states for title, playing, pause, game over

3. **Strategy**

   - `LayoutManager` - Pluggable layout algorithms
   - `ProgressColorStrategy` - Configurable progress bar colors
