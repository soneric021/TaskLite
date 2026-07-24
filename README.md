 Enunciado Funcional
Desarrollar una aplicación Android llamada TaskLite.
1. Pantalla principal – Lista de tareas
Cada tarea debe mostrar:
• Título
• Descripción
• Estado: Pendiente / Completada/ En progreso
Requisitos:
• Lista usando LazyColumn
• Si no existen tareas, mostrar un mensaje de estado vacío
• Las tareas completadas deben diferenciarse visualmente
(checkbox marcado, texto tachado o color atenuado)
2. Crear nueva tarea
• Botón flotante (FAB) para agregar tarea
• Pantalla de creación con para manejar el flujo de navegación:
o Campo Título (obligatorio)
o Campo Descripción (Obligatorio
• Botón Guardar:
o Si el título está vacío → mostrar error
o Si es válido → regresar a la lista y mostrar la tarea
3. Completar tarea
• Permitir marcar una tarea como completada desde la lista
• El estado debe reflejarse inmediatamente en la UI
Editar tarea
• Modificar Titulo y Descripción, guardar el dato.
 Requisitos Técnicos
 Lenguaje y UI
• Kotlin obligatorio
• Jetpack Compose obligatorio
 Arquitectura
• MVVM simple
o ViewModel con estado observable
o UI sin lógica de negocio
• Fuente de datos (Room) Obligatorio
• Separación clara de responsabilidades (no todo en el ViewModel)
 Pruebas Automatizadas
Implementar al menos 3 pruebas con Espresso:
1. Crear tarea correctamente
• Ingresar título
• Guardar
• Verificar que aparece en la lista
2. Validación de formulario
• Intentar guardar sin título
• Verificar mensaje de error o que el botón no permita guardar
3. Completar tarea
• Marcar una tarea como completada
• Verificar cambio en UI (checkbox / estado)
4. Editar tarea
• Modificar Titulo y Descripción
5. Eliminar tarea: se debe mostrar un pop up indicando que se va a eliminar la tarea.
6. Agregar una tarea de prueba automatizada que entienda necesaria.
Requisitos:
• Uso de testTag en Compose
• Selectores claros y estables
• Page Object Pattern Obligatorio para las pruebas.
