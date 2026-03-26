<div align="center">

  <h1>📱 Primera App Kotlin</h1>

  <p>
    <img src="https://img.shields.io/badge/Kotlin-1.9.0-purple?style=for-the-badge&logo=kotlin" alt="Kotlin" />
    <img src="https://img.shields.io/badge/Android%20Studio-Hedgehog-green?style=for-the-badge&logo=android-studio" alt="Android Studio" />
    <img src="https://img.shields.io/badge/Jetpack%20Compose-Naranja?style=for-the-badge&logo=jetpack-compose" alt="Compose" />
  </p>

  <strong>Colección de mini aplicaciones prácticas construidas para el aprendizaje de desarrollo Android moderno.</strong>
</div>

---

## 🚀 Mini Proyectos

En este repositorio encontrarás diferentes ejercicios que cubren conceptos fundamentales de UI y lógica de negocio:

**🔢 Counter App**: Gestión de estados simples con controles de incremento, decremento y reset.

**✅ Todo App**: Manejo de listas interactivas, estados mutables y persistencia temporal de tareas.

**🧠 Trivia App**: Implementación de arquitectura básica con *ViewModels* y manejo de estructuras de datos para cuestionarios.

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Kotlin
* **UI Framework:** Jetpack Compose
* **Arquitectura:** Pattern MVVM (en Trivia App)

---

## 🗂️ Estructura del Proyecto

```text
app/
└── src/main/java/cl/sebastianrojo/primera_app_kotlin/
    ├── MainActivity.kt          # Menú principal de navegación
    ├── CounterAppActivity.kt    # Mini app: Contador
    ├── TodoAppActivity.kt       # Mini app: Lista de tareas
    ├── TriviaAppActivity.kt     # Mini app: Quiz / Trivia
    └── trivia/
        ├── QuizViewModel.kt     # Lógica de negocio del Quiz
        └── QuizModels.kt        # Modelos de datos del Quiz
