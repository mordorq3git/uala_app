# UalaApp - Android Challenge

Esta es una aplicación de Android moderna que permite a los usuarios buscar ciudades, visualizarlas en un mapa y gestionar sus favoritos. El proyecto está construido siguiendo los principios de **Clean Architecture** y utilizando el patrón **MVI (Model-View-Intent)** para un manejo de estado predecible.

## 📋 Requisitos del Entorno

Para compilar y ejecutar este proyecto correctamente, asegúrate de cumplir con los siguientes requisitos:

*   **IDE**: [Android Studio Ladybug (2024.2.1)](https://developer.android.com/studio) o superior.
*   **Java**: JDK 17 (Esencial para la compatibilidad con el Gradle actual y las librerías de Compose).
*   **Android SDK**:
    *   **Compile SDK**: 36
    *   **Target SDK**: 36
    *   **Min SDK**: 24 (Android 7.0 Nougat)
*   **Kotlin**: Versión 2.2.20.
*   **Gradle**: Versión 8.7 o superior (configurado mediante el Gradle Wrapper incluido).

## 🚀 Tecnologías y Librerías (¿Por qué?)

### Core & UI
*   **Jetpack Compose**: Se utiliza para una UI declarativa moderna que reduce drásticamente el código boilerplate y acelera el desarrollo.
*   **Material 3**: Para ofrecer una experiencia de usuario consistente con los últimos estándares de diseño de Google.
*   **Navigation Compose**: Permite una navegación segura y fluida entre pantallas dentro del ecosistema Compose.

### Arquitectura & Inyección de Dependencias
*   **MVI (Model-View-Intent)**: Elegido para garantizar que el estado de la UI sea inmutable y predecible, facilitando la depuración y el testing.
*   **Dagger Hilt**: Simplifica la inyección de dependencias, permitiendo desacoplar las capas de la aplicación y facilitar la sustitución de componentes para pruebas.
*   **Coroutines & Flow**: Fundamental para el manejo de operaciones asíncronas de forma no bloqueante y la propagación de datos en tiempo real desde la base de datos a la UI.

### Datos & Networking
*   **Room**: Proporciona una capa de abstracción sobre SQLite para el almacenamiento local, actuando como "Fuente de Verdad" (SSOT) para el modo offline.
*   **Retrofit**: El estándar de la industria para peticiones de red seguras y tipadas.
*   **Kotlinx Serialization**: Una alternativa nativa de Kotlin a Gson/Moshi que es más rápida y segura en tiempos de compilación.

### Mapas
*   **Google Maps SDK & Compose Maps**: Para integrar mapas interactivos de forma nativa en los componentes de Compose.

## 🏗️ Arquitectura del Proyecto

El código está organizado siguiendo **Clean Architecture**:

1.  **Data Layer**: Contiene la lógica de acceso a datos (API y Base de datos).
2.  **Repository Layer**: Implementaciones que actúan como mediadores entre las fuentes de datos.
3.  **Presentation Layer**: 
    *   **ViewModels**: Reciben `Intents` (acciones del usuario) y emiten nuevos `States`.
    *   **Screens**: Componibles que reaccionan al estado del ViewModel.

## 🧪 Testing

### Herramientas
*   **JUnit 4**: Runner de pruebas.
*   **MockK**: Para crear dobles de prueba (mocks) de forma idiomatica en Kotlin.
*   **Turbine**: Imprescindible para testear flujos de datos (`Flow`) de forma secuencial y sin esperas arbitrarias.
*   **Robolectric**: Para ejecutar tests que dependen del framework de Android en la JVM.

### Cómo ejecutar
*   **Tests Unitarios**: Haz clic derecho en `app/src/test` -> **Run 'Tests in...'**.
*   **Tests de UI**: Haz clic derecho en `app/src/androidTest` -> **Run 'Tests in...'** (Requiere dispositivo).

## 🛠️ Configuración y Ejecución

1.  **Clonar el repositorio**.
2.  **Sincronizar Gradle**: Deja que Android Studio descargue todas las dependencias.
3.  **API Key**: La clave de Google Maps ya está configurada en `AndroidManifest.xml` para facilitar la revisión.
4.  **Ejecutar**: Selecciona un emulador (API 24+) y presiona **Run**.

---
*Desarrollado como parte del desafío técnico para Ualá.*
