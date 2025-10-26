# Students CLI

Sistema de gestión de estudiantes y notas mediante línea de comandos. Trabaja con archivos CSV y XML.


## Funcionalidades

La aplicación tiene dos modos de uso:

1. **Menú interactivo**: Navegar con opciones numeradas
2. **Comandos shell**: Para uso avanzado

### Comandos disponibles

- `add-students` - Añadir estudiantes con sus notas
- `list-students` - Mostrar listado de estudiantes
- `create-averages` - Generar archivo XML con medias
- `load-config --path <ruta>` - Cargar configuración
- `export-config --path <ruta>` - Exportar configuración
- `help` - Ver ayuda
- `exit` - Salir

## Configuración

Archivo `application.yml`:

```yaml
app:
  mark:
    min: 0.0
    max: 10.0
  files:
    students-csv: data/students.csv
    students-xml: data/students.xml
    students-avg-xml: data/students_with_average.xml
```

## Tecnologías

- Spring Boot 3.5.6
- Spring Shell 3.3.3
- Lombok
- Jackson (JSON/YAML)


