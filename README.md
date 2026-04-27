# OrionTek Clients Manager

Aplicación Android desarrollada en Kotlin para la gestión de clientes y sus direcciones, implementando arquitectura moderna basada en MVVM, Repository Pattern y Clean Architecture (parcial).

---

## 🚀 Tecnologías utilizadas

- Kotlin
- Jetpack Compose
- MVVM (Model - View - ViewModel)
- Repository Pattern
- Coroutines + StateFlow
- JUnit (Unit Testing)

---

## 🧱 Arquitectura

El proyecto está organizado en capas:

```text
data/       → Implementación del repositorio
domain/     → Casos de uso (UseCases)
ui/         → Pantallas + ViewModels
core/       → Inyección manual (AppModule)
```

---

## 📱 Funcionalidades

### Clientes
- Crear cliente
- Editar cliente
- Eliminar cliente
- Listar clientes

### Direcciones
- Crear dirección
- Editar dirección
- Eliminar dirección
- Listar direcciones por cliente

---

## 🧪 Pruebas unitarias

Se implementaron pruebas para:

- UseCases (validaciones de negocio)
- ViewModels (manejo de estado, errores y flujos)
- Repository (Fake implementation)

### Ejecutar tests

```bash
./gradlew test