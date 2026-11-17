## [1.2.0]

### Added
- `EventDispatcher` class, which will contain common and multiversion friendly interpretations of Fabric and NeoForge events.
    - Creative mode tab placement event.
    - Recipe sync event.
- Transitive access wideners on `SimpleParticleType` and `HeartType`.

## [1.1.4]

### Added
- Support for 26.4-snapshot-8.

### Fixes
- 1.20.1 Fabric build now works with Java 17.

## [1.1.3]

### Added
- Deferred holders can now accept callbacks that will be run when their value is registered.

## [1.1.2]

### Added
- 1.21.2+ registration helpers to deferred registry implementation.
- `Identifiable` implementation on `EntityType`.
- Updated icon.
- Item tag provider can now copy multiple `BlockItemTagId`s at once.