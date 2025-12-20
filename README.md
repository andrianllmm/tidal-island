<div align="center">

![Java](https://img.shields.io/badge/Java-17+-orange.svg)
[![License](https://img.shields.io/github/license/andrianllmm/tidal-island?style=flat)](https://github.com/andrianllmm/tidal-island/blob/main/LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/andrianllmm/tidal-island?style=flat)](https://github.com/andrianllmm/tidal-island/stargazers)

# Tidal Island

**A survival game where players escape an island before it submerges!**

</div>

<div align="center">
    <img src="./docs/images/title-screen.png" alt="Title Screen" width="400" />
    <img src="./docs/images/playing-on-raft.gif" alt="Gameplay on Raft" width="400" />
    <img src="./docs/images/playing.gif" alt="Gameplay with Panels" width="400" />
    <img src="./docs/images/playing.png" alt="Gameplay" width="400" />
</div>

## About

_Tidal Island_ is a survival game where you're stranded on a deserted island and must gather resources and craft items to escape. You must progress toward building a raft to escape before the tides get too high and submerge the island!

## Features

- **Resource Gathering**: Interact with trees, rocks, and bushes to collect materials
- **Crafting System**: Combine resources to create tools and ultimately build your escape raft
- **Tool System**: Craft and equip tools like axes for more efficient resource gathering
- **Survival Mechanics**: Manage health and hunger as you race against time
- **Dynamic Tidal System**: Watch as the ocean gradually floods the island, creating urgency and challenge
- **Custom Map Editor**: Build your own island layouts with the included World Builder tool

## How to Play

### Objective

1. **Gather Resources**: Approach trees, rocks, and bushes and press `E` to collect materials
2. **Craft Items**: Open the crafting menu (`C`) to create items like plnaks and axes (see recipe book in [recipes.json](/src/main/resources/recipes/recipebook.json))
3. **Build a Raft**: Collect enough wood and planks to craft a raft
4. **Escape**: Place the raft on water and board it before the island is fully flooded

### Controls

| Action     | Keys       |
| ---------- | ---------- |
| Move Up    | `W` or `↑` |
| Move Down  | `S` or `↓` |
| Move Left  | `A` or `←` |
| Move Right | `D` or `→` |
| Interact   | `E`        |
| Inventory  | `I`        |
| Crafting   | `C`        |
| Pause      | `ESC`      |
| Fullscreen | `F`        |

Configure controls in [keybindings.json](/src/main/resources/keybindings/keybindings.json)

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven 3.6+

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourusername/tidal-island.git
   cd tidal-island
   ```

2. **Build the project**

   ```bash
   mvn clean package
   ```

3. **Run the game**
   ```bash
   java -jar target/tidal-island-1.0-SNAPSHOT.jar
   ```

### Configuration

Game settings can be modified in [`config.json`](/src/main/resources/config.json):

```json
{
  "pixelScale": 4,
  "baseTileSize": 16,
  "colTiles": 20,
  "rowTiles": 15,
  "mapWidth": 50,
  "mapHeight": 50,
  "fps": 60
}
```

## Development

### Built With

- **Java 17** - Programming language
- **Maven** - Dependency management and build tool
- **Java AWT/Swing** - Graphics and UI
- **Jackson** - JSON parsing
- **JUnit 5** - Testing framework
- **AssertJ** - Fluent assertions for tests

### Project Structure

```
tidal-island/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── io/tidalisland/
│   │   │       ├── collision/      # Collision detection system
│   │   │       ├── config/         # Configuration management
│   │   │       ├── crafting/       # Crafting system
│   │   │       ├── entities/       # Player and living entities
│   │   │       ├── engine/         # Core game engine
│   │   │       ├── graphics/       # Rendering and sprites
│   │   │       ├── input/          # Input handling
│   │   │       ├── inventory/      # Inventory system
│   │   │       ├── items/          # Item definitions (food, tools, etc.)
│   │   │       ├── states/         # Game state management
│   │   │       ├── tide/           # Tidal flooding system
│   │   │       ├── tiles/          # Tile and map system
│   │   │       ├── ui/             # User interface
│   │   │       ├── worldobjects/   # World objects (trees, rocks, etc.)
│   │   │       └── worldbuilder/   # Map editor
│   │   └── resources/
│   │       ├── maps/               # Map data
│   │       ├── recipes/            # Crafting recipes
│   │       ├── sprites/            # Game sprites
│   │       └── tilesets/           # Tile definitions
│   └── test/                       # Unit tests
└── pom.xml                         # Maven configuration
```

### Architecture

The game follows object-oriented design principles with clear separation of concerns:

See [System Design Documentation](docs/system/system.md) for detailed architecture information.

### Testing

Run the tests with

```bash
mvn test
```

## Contributing

Contributions are welcome! To get started:

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a pull request

## Issues

Found a bug or issue? Report it on the
[issues page](https://github.com/andrianllmm/renux/issues).
