## Design Patterns Used

### Creational Patterns

1. **Singleton**

   - `Config` - Global configuration access
   - `GameClock` - Centralized time management

2. **Builder**

   - `ColliderBuilder` - Flexible collider construction
   - `SpriteSetBuilder` - Complex sprite set creation
   - `UiStyleBuilder` - Fluent style configuration

3. **Factory Method** (Registry)

   - `ItemRegistry.create()` - Type-safe item creation
   - `WorldObjectRegistry.create()` - Type-safe object creation

4. **Prototype**
   - Objects with `.copy()` - objects that can be cloned (e.g. colliders, position, size)

### Structural Patterns

1. **Composite**

   - `UiComponent` hierarchy - Tree of UI components. Child components inherit parent transformations

### Behavioral Patterns

1. **Observer**

   - `Observable`/`EventListener` - Event system
   - `Inventory` fires `InventoryChangeEvent`
   - `Equipment` fires `EquipmentChangeEvent`

2. **State**

   - `GameStateManager` - Stack-based state machine. Different states for title, playing, pause, game over

3. **Strategy**

   - `LayoutManager` - Pluggable layout algorithms
   - `ProgressColorStrategy` - Configurable progress bar colors

4. **Template Method**

   - `update()` and `render()` pattern
