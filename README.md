# Full-Stack Student Management System

A comprehensive full-stack application built with Java/Spring Boot and Angular for the frontend. This project allows users to manage students and their course enrollments through a clean, modern, and responsive user interface.

## Getting Started: How to Run This App

These instructions will get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* Java JDK 17 or newer
* Apache Maven
* Node.js (LTS version recommended)
* Angular CLI (`npm install -g @angular/cli`)

### Quick Start Commands

1.  **Clone the repository:**
    ```bash
    git clone <your-github-repository-url>
    cd FullStack-Student-Managment-System
    ```

2.  **Run the Backend (in a new terminal):**
    ```bash
    # Navigate to the backend directory
    cd backend

    # Run the Spring Boot application
    ./mvnw spring-boot:run
    ```
    * The backend will be running on `http://localhost:8080`.

3.  **Run the Frontend (in a second, separate terminal):**
    ```bash
    # Navigate to the frontend directory
    cd frontend

    # Install dependencies
    npm install

    # Run the Angular development server
    ng serve
    ```
    * The frontend will be running on `http://localhost:4200`.

Open your browser to **`http://localhost:4200`** to see the application in action! The database is automatically populated with sample data on startup.

---

## Features

* **Full CRUD Operations:** Create, Read, Update, and Delete students.
* **Course Management:** Create and view courses with rich details (course code, professor, credits).
* **Enrollment Management:** A dedicated student detail page to enroll and unenroll students from available courses, which opens in a new tab.
* **Responsive UI:** A modern, responsive interface built with Angular Material that works on both desktop and mobile.
* **Interactive Modals:** All forms for creating/editing and confirmation dialogs for deleting are handled in non-blocking modal windows.
* **Polished UX:** Includes hover effects, animations, a custom logo, and a consistent, professional layout.
* **Automatic Data Seeding:** The backend automatically pre-populates the database with sample data on startup for immediate use.

---

## Tech Stack

### Backend
* **Java 17** & **Spring Boot 3+**
* **Spring Data JPA / Hibernate**
* **H2 In-Memory Database**
* **Maven**
* **JUnit 5 & Mockito** for unit testing

### Frontend
* **Angular 18+** (with Standalone Components)
* **TypeScript**
* **RxJS** for state management
* **Angular Material** for UI components
* **SCSS** for styling

---

## Screenshots

**
**Student List Page**

**
**Student Enrollment Page**

**
**Course Management Page**
