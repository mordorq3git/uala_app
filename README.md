# UalaApp 📱

UalaApp es una aplicación Android moderna desarrollada con **Kotlin** y **Jetpack Compose**. El proyecto está diseñado siguiendo principios de arquitectura limpia y utiliza las últimas herramientas de desarrollo recomendadas por Google.

## 🚀 Tecnologías y Stack Técnico

*   **Lenguaje:** [Kotlin 2.2.10](https://kotlinlang.org/)
*   **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) con Material 3
*   **Serialización:** [Kotlinx Serialization 1.9.0](https://github.com/Kotlin/kotlinx.serialization) para el manejo de datos JSON.
*   **Testing:** JUnit 4 para pruebas unitarias de lógica de negocio.
*   **Build System:** Gradle (Kotlin DSL) utilizando **Version Catalogs** (`libs.versions.toml`).
*   **Compatibilidad:**
    *   Min SDK: 24 (Android 7.0)
    *   Target/Compile SDK: 36 (Android 15+)

## 📂 Estructura del Proyecto



## 🧪 Estrategia de Testing

El proyecto separa los recursos de producción de los de prueba. Para garantizar tests rápidos y desacoplados del framework de Android, los tests unitarios utilizan la carpeta `src/test/resources`.

### Ejemplo de uso de TestUtils:
Para deserializar un objeto desde un archivo JSON en los tests:
- Ejemplo: `TestUtils.deserialize<City>("city.json")`





## 🛠️ Configuración del Entorno

1. **Android Studio:** Se recomienda la versión **Ladybug** o superior.
2. **Java:** Configurado con **Java 17** (`JavaVersion.VERSION_17`).
3. **Plugins:** El proyecto utiliza el plugin de serialización de Kotlin para generar los serializadores en tiempo de compilación.

## ⚙️ Instalación y Ejecución

1. Clona el repositorio.
2. Abre el proyecto en Android Studio.
3. Sincroniza Gradle para descargar las dependencias definidas en el Version Catalog.
4. Para ejecutar los tests:
    - Ve a la carpeta `app/src/test/java/com/example/ualaapp/`.
    - Haz clic derecho y selecciona **Run 'Tests in...'**.

---
Desarrollado como parte del ecosistema de aprendizaje de Android moderno.


Tiempos:
<img width="1349" alt="Captura de pantalla 2026-04-05 210209" src="https://github.com/user-attachments/assets/20408d3e-6d10-4baf-9eec-4932e33e599d" />



