# Maps App

Aplicación móvil desarrollada en **Kotlin con Jetpack Compose** que permite gestionar marcadores en un mapa mediante operaciones CRUD (Create, Read, Update, Delete). La aplicación utiliza **Google Maps** para mostrar ubicaciones geográficas y **Supabase** como backend para almacenar los datos y las imágenes asociadas a cada marcador.

## Descripción

Maps App permite al usuario crear, visualizar, editar y eliminar marcadores sobre un mapa interactivo. Cada marcador contiene información como título, descripción y una imagen tomada desde la cámara del dispositivo o seleccionada desde la galería.

Los datos de los marcadores se almacenan en una base de datos de **Supabase**, mientras que las imágenes se guardan en **Supabase Storage**. La aplicación sigue la arquitectura **MVVM (Model - View - ViewModel)** para mantener una estructura de código limpia y escalable.

La navegación entre las distintas pantallas de la aplicación se realiza mediante un **Drawer Menu**, permitiendo acceder fácilmente al mapa y al listado de marcadores.

## Funcionalidades principales

- Visualización de un **mapa interactivo con Google Maps**
- Visualización de marcadores almacenados en la base de datos
- Creación de nuevos marcadores mediante **long press en el mapa**
- Listado de marcadores mediante **LazyColumn**
- Edición de información de marcadores
- Eliminación de marcadores mediante **gestos de swipe**
- Captura de imágenes desde la **cámara del dispositivo**
- Selección de imágenes desde la **galería**
- Almacenamiento de datos en **Supabase Database**
- Almacenamiento de imágenes en **Supabase Storage**
- Gestión de sesión de usuario mediante **SharedPreferences**

## Tecnologías utilizadas

- Kotlin
- Jetpack Compose
- Google Maps SDK
- Supabase
- Supabase Storage
- MVVM Architecture
- Coroutines
- Drawer Navigation
- LazyColumn

## Arquitectura

La aplicación sigue el patrón **MVVM**, separando la lógica de negocio de la interfaz de usuario para mejorar la mantenibilidad del proyecto.

### Estructura general del proyecto

data
├─ repository
└─ network

domain
└─ model

presentation
├─ screens
└─ viewmodel


## Objetivo del proyecto

Este proyecto forma parte del módulo de **Desarrollo de Aplicaciones Multiplataforma (DAM)** y tiene como objetivo integrar múltiples tecnologías de desarrollo móvil, incluyendo mapas, almacenamiento en la nube, gestión de imágenes y arquitectura moderna de aplicaciones Android.
