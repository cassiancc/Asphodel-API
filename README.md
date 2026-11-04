# Asphodel API
<a href='https://modrinth.com/mod/asphodel-api/versions?l=fabric'><img alt="Available for Fabric" height="56" src="https://raw.githubusercontent.com/intergrav/devins-badges/refs/heads/v3/assets/cozy/supported/fabric_vector.svg"></a>
<a href='https://modrinth.com/mod/asphodel-api/versions?l=neoforge&l=forge'><img alt="Available for NeoForge" height="56" src="https://raw.githubusercontent.com/cassiancc/Cassians-Badges/refs/heads/main/cozy/NeoForge.svg"></a>

Asphodel API is a multiloader/multiversion API designed by Cassian to simplify long-term maintenance of their mods.

Asphodel API does not contain any content on its own. It should only be installed if you have a mod that depends on it.

Yes, this is another moddername library. I work on over 50 mods at this point spread across a variety of versions, and every library I contribute code to keeps discontinuing versions I have to maintain or isn't available for snapshots.

## Features
- Simplified block/item registration.
- Universal interface for retrieving an object's `Identifier`.
- A lightweight deferred registration implementation for easily porting mods away from Forge.
- Multiversion-friendly block and item tag data generators.
- Common code-friendly implementation of the common tags.


## Credits
Common tag classes are based on the implementation in [Fabric API](https://github.com/FabricMC/fabric), used under Apache 2.0 License.
