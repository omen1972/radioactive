# RadioActive Android — Plan de producto y arquitectura

## Objetivo
Construir una app nativa Android para escuchar radios online de todo el mundo con:
- Búsqueda avanzada por país, ciudad, idioma y estilo.
- Gestión de favoritos y últimas escuchas.
- Compatibilidad con Android Auto.
- Reproducción en Chromecast y dispositivos Android TV / Google TV.
- Base técnica preparada para crecimiento (offline parcial, perfiles, métricas).

## Stack recomendado
- **Lenguaje:** Kotlin.
- **UI móvil:** Jetpack Compose + Material 3.
- **Arquitectura:** Clean Architecture + MVVM.
- **Reproductor:** Media3 ExoPlayer.
- **Android Auto:** MediaLibraryService + Car App templates (media).
- **Cast:** Google Cast SDK (CAF).
- **TV:** Módulo Android TV con Compose for TV.
- **Persistencia local:** Room (favoritos, historial, caché de búsquedas).
- **Red:** Retrofit + OkHttp + Kotlinx Serialization.
- **Inyección de dependencias:** Hilt.
- **Background / resiliencia:** WorkManager.
- **Observabilidad:** Firebase Crashlytics + Analytics (opt-in).

## Fuentes de datos de emisoras
### Opción inicial (MVP)
- **Radio Browser API** para catálogo global y metadatos.

### Evolución
- Back-end propio para:
  - Normalizar metadatos (logos, géneros, región).
  - Deduplicar emisoras.
  - Curación editorial por países/ciudades.

## Módulos funcionales
1. **Explorar**
   - Listados de tendencias y recomendadas.
   - Filtros: país, ciudad, idioma, tags/estilos, bitrate.
2. **Buscar**
   - Búsqueda por texto con sugerencias.
   - Facetas dinámicas (chips) por país/estilo.
3. **Reproductor**
   - Play/pause, reconexión automática, volumen.
   - Metadata en notificación y lockscreen.
4. **Favoritos**
   - Añadir/quitar rápido.
   - Ordenación manual y por uso.
5. **Historial**
   - Últimas emisoras reproducidas.
6. **Android Auto**
   - Navegación segura con catálogos y favoritos.
7. **Cast y TV**
   - Selector de dispositivo Cast.
   - Experiencia de salón en Android TV/Google TV.

## Requisitos de UX/UI
- Aplicar el branding **RadioActive** usando tu logo e icono `.ico` (convertido a tamaños Android con Asset Studio).
- Tema oscuro por defecto + acentos neón.
- Navegación principal con pestañas: Explorar, Buscar, Favoritos, Historial.
- Acciones de una sola mano para conducción (botones grandes y alto contraste).

## Modelo de datos (resumen)
```text
Station(
  stationId,
  name,
  streamUrl,
  faviconUrl,
  country,
  countryCode,
  state,
  city,
  language,
  tags,
  codec,
  bitrate,
  votes,
  lastCheckOk
)

Favorite(stationId, createdAt)
History(stationId, playedAt)
SearchPreset(id, name, country, city, tags, language)
```

## Integraciones clave
- **Android Auto:** exponer árbol de contenido por país/estilo/favoritos usando `MediaLibrarySession`.
- **Chromecast:**
  - envío de stream con metadata (nombre/logo).
  - controles de transporte sincronizados en móvil.
- **Smart TV:**
  - módulo específico TV con focus navigation (DPAD).

## Seguridad y cumplimiento
- Solo reproducir URLs públicas de audio.
- Política de privacidad clara (analíticas opcionales).
- Gestión de errores de red con mensajes amigables.
- Cumplimiento de normas de distracción mínima para Android Auto.

## Plan por fases
### Fase 1 — MVP (4-6 semanas)
- Catálogo global + búsqueda + filtros básicos (país/tag).
- Reproducción estable con Media3.
- Favoritos + historial local.
- Branding con logo/icono.

### Fase 2 — Ecosistema (3-4 semanas)
- Android Auto (catálogo + favoritos + reproducción).
- Chromecast (cast session completa).
- Primera versión Android TV.

### Fase 3 — Escalado (4+ semanas)
- Backend de curación.
- Recomendaciones personalizadas.
- Sincronización en la nube (cuenta opcional).

## Backlog técnico inicial
- Configurar monorepo Android (app-mobile, app-tv, core, data, player).
- Definir contrato API de estaciones.
- Diseñar cache policy (stale-while-revalidate).
- Test:
  - Unit tests de repositorios/use cases.
  - Instrumentados de reproducción.
  - Tests en emulador Android Auto.

## Definición de éxito (KPIs)
- Tiempo al primer audio < 2.5 s en red estable.
- Crash-free sessions > 99.5%.
- Tasa de reconexión exitosa > 95%.
- Uso de favoritos en semana 1 > 30% de usuarios activos.

## Siguiente paso recomendado
Crear un proyecto Android base en Kotlin con 2 módulos (`app-mobile` y `core-player`) e implementar vertical slice: **Buscar → Reproducir → Guardar en favoritos**.
