# Functional and Non-Functional Requirements

## Actors

First, we need to define the actors of the web application, these are the following

| Role                   | Description                                             |
|------------------------|---------------------------------------------------------|
| Student                | Registers as a player and can be a captain              |
| Graduate               | Registers as a player and can be a captain              |
| Professor              | Registers as a player and can be a captain              |
| Administrative Staff   | Registers as a player and can be a captain              |
| Family Members         | Registers as a player and can be a captain              |
| Captain                | Creates and manages a team                              |
| Organizer              | Manages the tournament                                  |
| Referee                | Views information about the matches to officiate        |
| Administrator          | Full control of the system                              |

## Functional Requirements (quick-view)

- [Student Registration](#student-registration): the page must include a log in and sign up section so that students can register/create an account

- **creacion y administracion de equipos**: la pagina debe permitir a diferentes actores (_estudiantes, graduados,
profesores, personal administrativo y/o familiares) crear y gestionar equipos

- **buscador de jugadores**: la pagina debe tener un buscador para jugadores, por defecto el filtro para la
busqueda sera su posicion pero en orden de hacerlo mas facil, tambien se podra buscar por otros valores como
el nombre.

- **comprobante de pagos**: la pagina debe tener un espacio reservado para los comprobantes de pago

- **calendario**: la pagina debe ser capaz de renderizar informacion coo el calendario, reglamentos y
fechas importantes sobre el torneo

- **logs**: la pagina debe ser capaz de guardar informacion sobre los partidos y sus resultados, incluso
si el partido ocurrio fechas atras o en otros torneos

- **posiciones**: cada torneo debe tener una tabla de posiciones asi como que equipos han avanzado
a determinadas fases de la competencia (octavos, cuartos, semis, final)

- **alineaciones**: la pagina debe ser capaz de renderizar como es la alineacion de un equipo (estilo fifa)

### student registration
