# UalaApp - Android Challenge

Esta es una aplicación de Android moderna que permite a los usuarios buscar ciudades, visualizarlas en un mapa y gestionar sus favoritos. El proyecto está construido siguiendo los principios de **Clean Architecture** y utilizando el patrón **MVI (Model-View-Intent)**.

## 🚀 Tecnologías y Librerías

El proyecto utiliza las últimas tecnologías recomendadas para el desarrollo de Android:

### Core & UI
*   **Kotlin**: Lenguaje de programación principal.
*   **Jetpack Compose**: Toolkit moderno para construir interfaces de usuario nativas de forma declarativa.
*   **Material 3**: La última evolución de Material Design para componentes de UI consistentes y modernos.
*   **Navigation Compose**: Gestión de la navegación entre pantallas de forma integrada con Compose.

### Arquitectura & Inyección de Dependencias
*   **MVI**: Separación clara de la lógica de UI y la lógica de negocio.
*   **Dagger Hilt**: Solución estándar para la inyección de dependencias, facilitando el desacoplamiento y la testeabilidad.
*   **Coroutines & Flow**: Manejo de operaciones asíncronas y flujos de datos reactivos en toda la aplicación.

### Datos & Networking
*   **Room**: Base de datos local (SQLite) para el almacenamiento persistente de ciudades y favoritos.
*   **Retrofit**: Cliente HTTP para el consumo de APIs REST.
*   **Kotlinx Serialization**: Serialización de JSON de forma segura y eficiente.
*   **SharedPreferences**: Almacenamiento simple para la gestión de la sesión del usuario.

### Mapas
*   **Google Maps SDK for Android**: Integración de mapas interactivos.
*   **Compose Maps**: Librería de utilidades para usar Google Maps dentro de Jetpack Compose.

## 🏗️ Arquitectura del Proyecto

El código está organizado por capas para asegurar la escalabilidad:

1.  **Data Layer**:
    *   `api/`: Definiciones de Retrofit para obtener datos remotos.
    *   `database/`: Entidades de Room, DAOs y configuración de la base de datos local.
    *   `entities/`: Modelos de datos específicos de la capa de persistencia.
2.  **Repository Layer**: Implementaciones que deciden si obtener datos de la red o de la base de datos local (Source of Truth).
3.  **Presentation Layer**:
    *   `ViewModels`: Gestión del estado de la UI y procesamiento de eventos (`Intents`).
    *   `UI Screens`: Componibles de Compose que representan las pantallas (Listado y Mapa).

## 🧪 Testing

El proyecto tiene un fuerte enfoque en la calidad del código mediante pruebas:

### Librerías de Test
*   **JUnit 4**: Framework base para pruebas unitarias.
*   **MockK**: Librería potente para crear mocks en Kotlin.
*   **Turbine**: Utilidad especializada para testear flujos (`Flow`) de corrutinas de forma sencilla.
*   **Coroutines Test**: Para el control de dispatchers en pruebas asíncronas.
*   **Compose UI Test**: Pruebas de instrumentación para verificar la interfaz de usuario.

### Cómo ejecutar los tests
1.  **Unit Tests**: Haz clic derecho en la carpeta `app/src/test` y selecciona **"Run 'Tests in...'"**. Estos tests verifican la lógica de los ViewModels y Repositorios sin necesidad de un dispositivo.
2.  **UI Tests**: Haz clic derecho en la carpeta `app/src/androidTest` y selecciona **"Run 'Tests in...'"**. Requieren un emulador o dispositivo físico conectado.

## 🛠️ Configuración y Ejecución

1.  **Clonar el repositorio**.
2.  **Google Maps API Key**: La aplicación utiliza Google Maps. Asegúrate de que la clave en `AndroidManifest.xml` sea válida o reemplázala por una propia con los permisos necesarios.
3.  **Sincronizar Gradle**: Abre el proyecto en Android Studio y deja que descargue las dependencias.
4.  **Ejecutar**: Selecciona el módulo `app` y presiona **Run**.

---
*Desarrollado como parte del desafío técnico para Ualá.*