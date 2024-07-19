## Accounting - Client Management and Invoice Tracking System

## Overview

This project is a Client Management and Invoice Tracking System built using Spring Boot and Thymeleaf. It utilizes Hibernate for ORM (Object-Relational Mapping) and follows the MVC (Model-View-Controller) design pattern. The system allows you to create clients, add products for clients, generate purchase and sales invoices, and track profit and loss ratios. Additionally, it integrates with third-party APIs using FeignClients to fetch country information.

### Features

	•	Client Management: Create and manage clients.
	•	Product Management: Add and manage products for each client.
	•	Invoice Management: Generate and manage purchase and sales invoices.
	•	Profit and Loss Tracking: Track profit and loss for each invoice.
	•	Country Information: Fetch country information using FeignClients.
	•	MVC Architecture: Follows MVC design pattern for a clean separation of concerns.
	•	ORM with Hibernate: Utilizes Hibernate for efficient database interactions.
	•	Database: Uses PostgreSQL as the database.
	•	ModelMapper: Used for mapping between DTOs and entities.

### Technologies Used

	•	Spring Boot
	•	Thymeleaf
	•	Hibernate
	•	JPA (Java Persistence API)
	•	PostgreSQL
	•	FeignClients
	•	ModelMapper
	•	Maven

### Prerequisites

	•	JDK 11 or higher
	•	Maven 3.6 or higher
	•	PostgreSQL database

### Project Structure

	•	Controller: Handles HTTP requests and responses.
	•	Service: Contains business logic.
	•	Repository: Interfaces for database access.
	•	Model: Entity classes representing database tables.
	•	DTO: Data Transfer Objects for transferring data between layers.
	•	FeignClient: Interfaces for consuming third-party APIs.
