# AcademiaApp

Aplicación Android en Kotlin y Jetpack Compose para estudiar interfaz, interacción, estado, consumo de API y navegación.

## Abrir el proyecto

Clona o descarga este repositorio. En Android Studio selecciona **Open** y abre la carpeta **AcademiaApp**, que contiene `settings.gradle.kts` y el proyecto completo de Gradle.

El código Kotlin está en `AcademiaApp/app/src/main/java/com/example/academiaapp/`.

## Contenido

- Pantallas de Inicio, Actividades, Productos y Detalle del producto.
- Formulario con validación, selección y mensajes de confirmación.
- Lista observable para agregar, completar y eliminar actividades.
- Retrofit, modelos, repositorio y ViewModel para consultar productos de DummyJSON.
- POST de prueba con estados de envío, confirmación y error. DummyJSON simula la creación; el producto enviado no se guarda permanentemente en el servidor.
- Navigation Compose, parámetros, barra superior y menú inferior.

## Compilar en Windows

Desde la carpeta `AcademiaApp`:

```powershell
.\gradlew.bat assembleDebug
```
