### Added
- Client event dispatcher, used for client-only events.
  - Range select item property registration.
  - Tooltip modification.
  - Key Mapping registration events.
  - Entity renderer events.
- Automatic config screens for mods using Kaleido Config.
  - On vanilla (26.1 and above), a basic config screen is provided.
  - With Cloth Config installed, a config screen using that API is provided.
  - With YACL installed, a config screen using that API is provided.
  - YACL is the recommended and preferred option on all versions, as it provides the most featureful and accessible view.

### Changed
- Kaleido Config is now bundled with Asphodel API.
- YACL is now recommended.
- Mod now declares minimum compatible Fabric API and NeoForge versions.
- Deferred register APIs are now available on 1.20.1 builds.
- Various methods in creative tab APIs now take `Collection`s.