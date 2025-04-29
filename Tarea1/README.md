# Tareas ELO329

Repositorio para el desarrollo de tareas del curso ELO329, correspondientes al semestre 2025-1.

## Integrantes

- **Martin Rodríguez**  
  Rol: 201973620-5

- **José Campos**  
  Rol: 202173609-3

- **Bastian Ortega**  
  Rol: 202173547-K

- **Matías Elgueta**  
  Rol: 202073592-1

## Consideraciones Tarea 1

Esta tarea busca practicar la orientación a objeto en un sistema que simula la operación del patrón
publicador y suscriptor. Este patrón será usado para anunciar eventos de forma asincrónica a varios
consumidores interesados, sin necesidad de vincular directamente generadores de eventos con sus
receptores.

El archivo de configuración `.txt` usado es el mismo que dan como ejemplo en el documento de la tarea.

Cada etapa (`stage`) presenta su propio `Makefile`, donde para hacer uso del simulador es necesario encontrarse en la ruta del programa y ejecutar:

```bash
make       # Para compilar
make run   # Para ejecutar
make clean # Para limpiar los archivos .class
