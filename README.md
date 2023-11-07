#Meli Challenge

El proyecto esta dividido en 3 modulos

- utils: contiene piezas de código reutilizables
- search: contiene las vistas, viewModels y repositorios de las 3 pantallas (busqueda, listado y detalles)
- app: se encarga de la inicialiacion del app

## Modulo Utils

### Algebraic Data Types o ADT


Se definen ADTs mediante sealed class para limitar los posibles valores,
proteger el código de futuros cambios y garantizar un manejo exhaustivo de las posibilidades.

##### Image

- Image.URL: Representa una imagen a partir de un recurso dibujable identificado por su ID.
- Image.DrawableRes: Representa una imagen a partir de una URL.

#### Text
- Text.StringRes: Representa una cadena de recurso con identificación y parámetros opcionales.
- Text.StringValue: Representa una string literal.


### Funciones procesadoras de ADT

Se definen funciones que usan Pattern Matching para colapsar el ADT a un valor final utilizable.

- stringText()
- painterImage()

## Modulo Search

Se divide en tres directorios siguiendo una screaming arquitecture

- domain: define los modelos clave y la intefaz de un repositorio con las funciones que necesita
- data: implementa el repositorio definido por el dominio.
- presentation: contiene las pantallas, logica de preentacion y navegacion

### Presentacion

contiene un package por pantalla ademas de un package para los componentes reusables entre pantallas.
cada pantalla cuenta con:
- Composable: vista construida con compose
- ViewModel: contiene la logica de presentacion, accede a los datos mediante repositorio.
- Navigation: logica de navegacion type-safe, define como se navega y como se accede a los parametros
- State: data class que representa el estado completo de la vista


## Tecnologia

- catalogo de versiones de gradle y gradle kotlin dsl para manejar las dependencias
y construccion del proyecto

- Retrofit y Kotlin Serialization para las llamadas al api
- Arrow para representar resultados y facilitar el control del flujo dentro del codigo
- ViewModels para retener el estado de la vista a traves de cambios de configuracion
- Coroutinas para ejecutar las llamadas a las apis sin bloquear el hilo principal
- Coil para la carga urls de imagenes
- Lottie para las animaciones
- Timber para los logs
- StateFlow para emitir actualizaciones de estado
- Compose para construir las vistas
- Navigation Compose para la navegacion entre pantallas
- Dagger Hilt para administrar las dependencias

- Truth para las validaciones en los tests
- Mock para mockear dependencias
- kotest para las validaciones con Arrow
- Robolectric para ejecutar tests instrumentados en local
- 

## Testing

Se realizan diferentes tests al api con MockWebServer

- unitarios black box, a los mappers y al api
- unitarios white box, al repositorio

Los tests instrumentados que normalmente se ejecutan en un dispositivo físico,
han sido configurados con robolectric para mayor comodidad