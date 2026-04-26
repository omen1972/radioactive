# 📻 RadioActive

**RadioActive** es una aplicación web minimalista y moderna diseñada para la sintonización de emisoras de radio de todo el mundo. Con una interfaz estética de estilo "Neón-Futurista", ofrece una experiencia de usuario fluida, permitiendo gestionar favoritos, explorar nuevas frecuencias y mantener un historial de escucha reciente.

![Licencia](https://img.shields.io/badge/license-MIT-blue.svg)
![Estado](https://img.shields.io/badge/status-active-brightgreen.svg)

## ✨ Características principaless

- **Sintonización Global:** Acceso a miles de emisoras a través de la API de Radio Browser.
- **Interfaz Neón Custom:** Diseño optimizado con colores oscuros, efectos neón y tipografía técnica (`Orbitron` y `Rajdhani`).
- **Persistencia de Datos:** Recordatorio automático de la última emisora reproducida y guardado de listas de favoritos e historial mediante `localStorage`.
- **Reloj Integrado:** Visualización de fecha y hora en tiempo real en la cabecera.
- **Control de Volumen:** Slider integrado para una gestión rápida del audio.
- **Diseño Responsivo:** Adaptado para dispositivos móviles y visualización en pantalla completa (apta para modos de conducción).

## 🚀 Instalación y Uso

Al ser una aplicación basada exclusivamente en tecnologías web (HTML5, CSS3 y JavaScript), no requiere de un servidor complejo.

1. **Clona el repositorio:**
   ```bash
   git clone [https://github.com/tu-usuario/radioactive.git](https://github.com/tu-usuario/radioactive.git)

## 📱 Roadmap Android (nuevo)
Si quieres convertir RadioActive en una app Android completa (móvil, Android Auto, TV y Chromecast), ya tienes una propuesta técnica y de producto en:

- [`ROADMAP_ANDROID.md`](./ROADMAP_ANDROID.md)

Incluye stack recomendado en Kotlin/Compose/Media3, plan por fases y arquitectura para escalar.

## 🤖 Prototipo Android (bootstrap)
Se ha añadido una base de proyecto Android en `./android` para empezar la app nativa con Kotlin + Jetpack Compose.

### Qué incluye
- Proyecto Gradle multi-módulo inicial (`:app`).
- Pantalla prototipo con:
  - Búsqueda por texto.
  - Filtros por país, ciudad y estilo.
  - Gestión local de favoritos (en memoria).

### Cómo ejecutarlo
1. Abre la carpeta `android/` en Android Studio (Hedgehog o superior).
2. Sincroniza Gradle.
3. Ejecuta `app` en un emulador Android 8+.
