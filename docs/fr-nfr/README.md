# Functional and Non-Functional Requirements

## Topics
- [Actors](#actors)
- [Functional requirements](#functional-requirements)
- [Non Functional requirements](#non-functional-requirements)
- [Help](#help)

## Actors

The following actors interact with the web application:

| Role                 | Description                                              |
|----------------------|----------------------------------------------------------|
| Student              | Registers as a player; eligible to be a Captain          |
| Graduate             | Registers as a player; eligible to be a Captain          |
| Professor            | Registers as a player; eligible to be a Captain          |
| Administrative Staff | Registers as a player; eligible to be a Captain          |
| Family Member        | Registers as a player; eligible to be a Captain          |
| Captain              | Creates and manages a team                               |
| Organizer            | Responsible for managing the tournament                  |
| Referee              | Views match information for officiating purposes         |
| Administrator        | Exercises full control over the system                   |

## Functional Requirements

### Account Creation and Authentication
The application must provide Login and Sign-Up pages where users can provide credentials for account creation or authentication. The system must validate provided information via email

**Requirement**: Provide a dedicated interface for account creation and login, including credential validation for both processes

### Tournament Management (Organizers Only)
Users with the Organizer [role](#actors) shall be able to create and manage tournaments. The application will provide a restricted area for these actions

To create a formal tournament, the Organizer must provide:
- Start and end dates
- Minimum and maximum number of teams
- Registration cost per team
- Tournament status (Draft, Active, In Progress, or Finished)

Additionally, for tournament administration, Organizers shall be able to:
- Change tournament status (Start/End)
- Define and publish tournament rules
- Provide general descriptions and information

**Requirement**: Provide a management dashboard for Organizers to initialize and administer tournament details

### Player Registration and Profiles
The following [actors](#actors) are eligible to participate as players: Student, Graduate, Professor, Administrative Staff, and Family Member

The system must provide a section for these users to:
- Create and update a sports profile
- Specify their field position (e.g., Striker, Goalkeeper)
- Select a jersey number
- Upload a profile picture
- Set their status as "Available" so Captains can scout them
- Receive and manage invitations to join teams

**Requirement**: Provide a player profile section for customization and implement a notification system for team invitations

### Team Creation and Validation
Any Captain is authorized to:
- Create a team and assign a name
- Upload a team icon/logo
- Define team colors
- Invite players to the roster

When a Captain submits a team as "Ready," the system must validate the following constraints:
- Minimum of 7 players; maximum of 12 players
- More than 50% of the roster must be Students
- The remaining members must belong to the permitted roles (Graduate, Professor, Staff, or Family)

**Requirement**: Provide a team customization interface and an automated validation engine to ensure roster compliance before a team is finalized

### Player Search Engine
To enhance user engagement, a global search tool will allow users to filter players based on:
- Field Position
- Academic Semester (Students only)
- Age and Gender
- Name or Identification Number (CC, TI)

**Requirement**: Implement a search engine with multi-filter capabilities to list all actors satisfying specific criteria

### Inscriptions and Payments
**Rules**
- The Captain is responsible for submitting payment
- Only teams with a confirmed payment status are eligible to participate

**Process**
- The Captain pays via NEQUI or cash to the Organizer
- The Captain uploads the payment receipt (proof of purchase)
- The Organizer verifies the document and updates the status to: *Pending, Under Review, Approved,* or *Failed*

**Requirement**: Implement a subscription workflow where Captains can join tournaments and upload digital receipts, allowing Organizers to validate and update payment statuses

### Team Lineups and Formations
Prior to each match, the Captain shall be able to:
- Select the starting lineup and substitutes
- Choose and customize tactical formations (FIFA-style interface)
- View the opponent's formation
- Ensure players are not registered on multiple teams

**Requirement**: Provide a tactical engine for formation management and visualization. Formations should be searchable globally via the team's profile

### Match Registration
The Tournament Organizer is responsible for recording the following match details

* The final score of the match
* Identification of the players who scored goals
* Registration of players cautioned during the match
* Registration of players sent off during the match

**Requirement**: The system must provide a dedicated interface for Organizers to input and
save these match statistics, ensuring that all data is reflected in the historical logs and
tournament standings

### Match Inquiries (Referees Only)
Referees shall have access to a dedicated view to consult the details of their assigned matches. The system must display:
- The scheduled kickoff for the specific match
- The specific location within the campus or complex where the match will take place
- The names and details of the two teams playing the match

**Requirement**: Provide a personalized schedule for Referees to ensure they have all necessary
logistical information for their officiating duties

### Tournament Standings
The system must automatically calculate and update the following statistics for each participating team:
- Total number of games completed
- Total number of victories
- Total number of ties/draws
- Total number of defeats
- Total number of goals scored by the team
- Total number of goals conceded by the team
- The result of subtracting Goals Against from Goals For
- Total points accumulated based on match results

**Requirement**: Implement an automated standings engine that updates in real-time as match
results are registered, ensuring accurate rankings throughout the tournament

### Tournament Brackets and Knockout Stages
The system must automatically generate the tournament structure, including:
- Randomized pairing for the opening round of the tournament
- Automatic progression and seeding of the top 8 teams
- Automatic progression of winning teams from the previous stage
- Matchup generation to determine the tournament champion

**Requirement**: Implement an automated bracket engine capable of handling random seeding
and advancing teams through the knockout phases based on match results

### Tournament Statistics and History
The system shall provide a centralized dashboard where the following information can be consulted:
- A ranked list of players with the highest number of goals across the tournament
- A comprehensive record of all matches played, including dates and locations
- A detailed breakdown of performance and specific match outcomes for any selected team

**Requirement**: Implement a data visualization and query module that allows users to track
individual and collective performance throughout the competition

---

## Non Functional Requirements

### Technological Architecture (Non-Functional Requirements)

The system must adhere to the following architectural and technical constraints:

- **Backend**: Developed using **Spring Boot**, implementing a layered architecture (Controllers, Adapters, Logic/Service, and Data/Persistence)
- **API Management**: Exposure of system functionalities through a **REST API** managed by Spring Boot
- **Frontend**: A web application built with **React** using **TypeScript** for type safety and modern state management
- **Database**: Use of **PostgreSQL** as the relational database management system for persistent storage

**Requirement**: Ensure the system is scalable and maintainable by strictly following the layered pattern and the specified tech stack

---

## Help

If the `../requirements.pdf` document changes, please re open ![issue #1](https://github.com/0strezz/TECHCUP/issues/1) and fix/add
whatever is now requested.

