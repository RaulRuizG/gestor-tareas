# Gestor de Tareas

Aplicación de consola en Java para gestionar tareas, con persistencia real en SQLite, tests unitarios, containerización con Docker y un pipeline de CI/CD con GitHub Actions.

Proyecto personal desarrollado para practicar un flujo de trabajo completo de desarrollo de software: desde el diseño de clases con buenas prácticas hasta la automatización de compilación, pruebas y análisis de seguridad.

## Funcionalidades

- Añadir, listar, marcar como completada y borrar tareas desde un menú por consola
- Persistencia real de datos en una base de datos SQLite (las tareas sobreviven entre ejecuciones)
- Suite de tests unitarios con JUnit 5
- Escaneo automático de vulnerabilidades en las dependencias con OWASP Dependency-Check

## Tecnologías

- **Java 21** (Eclipse Temurin)
- **Maven** para gestión de dependencias y build
- **JUnit 5** para tests unitarios
- **SQLite** (JDBC) para persistencia
- **Docker** (multi-stage build) para containerización
- **GitHub Actions** para integración continua
- **OWASP Dependency-Check** para análisis de vulnerabilidades en dependencias

## Arquitectura

El proyecto sigue el patrón de inversión de dependencias: la lógica de negocio (`GestorTareas`) no depende de una implementación concreta de persistencia, sino de una interfaz (`Repositorio`). Esto permite:

- Testear la lógica de negocio con una implementación en memoria (`RepositorioEnMemoria`), sin necesidad de una base de datos real
- Cambiar la tecnología de persistencia (`TareaRepositorio` con SQLite) sin modificar el resto del código

```
Main → GestorTareas → Repositorio (interfaz)
                            ├── TareaRepositorio (SQLite, producción)
                            └── RepositorioEnMemoria (tests)
```

## Cómo ejecutarlo

### Con Docker (recomendado)

```bash
docker build -t gestor-tareas .
docker run -it -v gestor-tareas-datos:/app gestor-tareas
```

El volumen (`-v`) es necesario para que las tareas persistan entre distintas ejecuciones del contenedor.

### Con Maven, en local

Requiere JDK 21 y Maven instalados.

```bash
mvn package
java -jar target/Gestor-tareas-1.0-SNAPSHOT.jar
```

### Ejecutar los tests

```bash
mvn test
```

## CI/CD

Cada push a la rama principal dispara un workflow de GitHub Actions que:

1. Compila el proyecto y ejecuta la suite de tests
2. Escanea las dependencias en busca de vulnerabilidades conocidas (CVE) con OWASP Dependency-Check

## Estructura del proyecto

```
src/main/java/org/example/
├── Main.java                 # Punto de entrada y menú de consola
├── Tarea.java                 # Modelo de datos
├── GestorTareas.java           # Lógica de negocio
├── Repositorio.java            # Interfaz de persistencia
└── TareaRepositorio.java       # Implementación con SQLite

src/test/java/org/example/
├── GestorTareasTest.java
└── RepositorioEnMemoria.java   # Implementación en memoria para tests
```
