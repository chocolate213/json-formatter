# JetBrains component sources

- IDE HTML and inline SVG controls: https://www.jetbrains.com/idea/
- IDE CSS: https://www.jetbrains.com/_assets/rr/overview-BkhZE2Xc.css
- Captured 2026-09-19. `ide-original.html` preserves the extracted component before adding the JSON Formatter panel. `ide-components.css` preserves the complete original stylesheet, including its CSS layers; no rewritten icon paths.
- JSON Formatter actions: JetBrains/intellij-community, platform/icons/src/actions/{collapseall,expandall,copy,setDefault,moveDown,moveUp}_dark.svg. These are the AllIcons assets referenced by the plugin. Repository: https://github.com/JetBrains/intellij-community (Apache-2.0).
- JetBrains Mono font is referenced from the official JetBrains resource host.

The original website assets retain their respective ownership and are not relicensed under this repository's MIT license. Local screenshot pixels and local IDE content are not included. Integration, JSON operations, responsive composition and accessibility are maintained separately in the demo files.
