# Functional and Non-Functional Requirements

## Functional Requirements

- **Tournament Management**: The organizer can create tournaments with basic info (dates,
team count, cost), manage tournament states (Draft, Active, In Progress, Finished),
and initiate or finalize the event

- **User Registration & Profiles**: Participants can register using institutional or Gmail
accounts, create sports profiles with positions and jersey numbers, upload photos, and
set availability for teams

- **Team Management**: Captains can create teams, assign names and colors, upload shields,
and invite players.

- **Player Search**: Captains can search for available players by position, semester,
age, gender, name, or identification

- **Registration & Payments**: Captains upload payment receipts to the platform for organizer
review; the system tracks the status (Pending, In Review, Approved, Rejected).

- **Tournament Configuration**: Organizers can define regulations, important dates, registration
deadlines, match schedules, fields, and sanctions.

- **Lineups**: Captains organize formations visually, selecting starters and reserves before matches

- **Match Logging**: Organizers record scores, scorers, and yellow/red cards.

- **Standings & Brackets**: The system automatically calculates standings (wins, losses,
points, goal difference, etc.) and generates random initial brackets followed by
quarterfinals, semifinals, and finals.

- **Statistics & History**: Users can consult top scorers, match history, and specific team results.

## NonFunctional Requirements

### About tech

- **Backend**: Developed in Spring Boot, following a layered architecture (controllers, adapters,
logic, and data) and exposing a REST API.

- **Frontend**: Built with React and TypeScript.

- **Database**: PostgreSQL must be used

- **Development Tools**: The project must be structured with Maven, versioned on GitHub,
planned in Jira using Scrum + Kanban, and designed in Figma
