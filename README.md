# CoffeePod

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
An app to find reviews for local coffee shops and submit reviews based on your experiences at a location.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='CoffeePod Week1 Progress.gif' />

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Lifestyle
- **Mobile:** This app would be primarily developed for mobile as it is aimed for use at any point in the day, where someone may not have quick access to a desktop or laptop. The camera will also be used to allow the user to upload photos of the location they visit.
- **Story:** This app will allow people to streamline their coffee purchase experience by seeing review information provided by others, and contribute reviews based on the experiences they have.
- **Market:** Any individual who drinks coffee could use this app, which is most people in the USA.
- **Habit:** This app could be used as often as one wants to find a place to get coffee, and many people drink coffee multiple times throughout the day.
- **Scope:** We plan to start with the bare review functionality. After this, the app can evolve to include more social features, such as chatting in a forum about different locations or setting up events with other local users.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User is able to login, signup, and logout of the app. The user login is persisted with app restarts.
* User can set up a personal profile with a home location.
* User can see a quick summary of photo, address, rating, pricing, tags, and popular items.
* User can see detailed information about a coffee location when they click on its summary.
* User can submit reviews with information about their visit to a location (what they ordered, rating, tags, location photo, etc).

**Optional Nice-to-have Stories**

* User can navigate on a map to see pinpoints for coffee locations.
* User can share coffee locations with others.
* User can see foot traffic and busy times for coffee locations.

### 2. Screen Archetypes

* Login/Register
   * User is able to login, signup, and logout of the app. The user login is persisted with app restarts.
* Stream
   * User can see a quick summary of photo, address, rating, pricing, tags, and popular items.
* Detail
   * User can see detailed information about a coffee location when they click on its summary.
* Creation
   * User can submit reviews with information about their visit to a location (what they ordered, rating, tags, location photo, etc).
* Settings
   * User can set up a personal profile with a home location.

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Reviews summary stream screen
* Review creation screen
* Settings screen

**Flow Navigation** (Screen to Screen)

* Login / Register screen
   * Reviews summary stream screen
* Reviews summary stream screen
   * Review detail screen
* Review detail screen
   * Review summary stream screen
* Review creation screen
   * Review summary stream screen

## Wireframes
<img src="https://github.com/Codepath-Pod15/CoffeePod/blob/main/wireframe.png" width=600>

## Schema 

### Models

#### User

   | Property       | Type            | Description |
   | -------------- | --------------- | ----------- |
   | objectId       | String          | unique id for the user (default field) |
   | username       | String          | username for the user |
   | password       | String          | password for the user |
   | name           | String          | users first and last name |
   | profilePicture | File            | profilePicture for the user (optional) |
   | location       | Number          | zip code for the user (optional) |
   | createdAt      | DateTime        | date when post is created (default field) |
   | updatedAt      | DateTime        | date when post is last updated (default field)

#### Location

   | Property      | Type            | Description  |
   | ------------- | --------------- | ------------ |
   | objectId      | String          | unique id for the review (default field) |
   | address       | String          | location address |
   | createdAt     | DateTime        | date when review is created (default field) |
   | updatedAt     | DateTime        | date when review is last updated (default field) |

#### Review

   | Property      | Type                | Description  |
   | ------------- | ------------------- | ------------ |
   | objectId      | String              | unique id for the review (default field) |
   | user          | Pointer to User     | review author |
   | location      | Pointer to Location | lcoation being reviewed |
   | image         | File                | image that user uploads with review |
   | order         | String              | what was ordered during the visit |
   | reviewText    | String              | review text posted by the user |
   | rating        | Number              | review rating out of 5 stars |
   | tags          | Array               | tags for the location (vibe, cost, etc) |
   | createdAt     | DateTime            | date when review is created (default field) |
   | updatedAt     | DateTime            | date when review is last updated (default field) |

### Networking
   - Login/Register screen
      - (Create/POST) Register a new user
      - (Read/GET) Authenticate user login
   - Review stream screen
      - (Read/GET) Query all locations by zip code
      - (Read/GET) Query review data by location
   - Review detail screen
      - (Read/GET) Query all reviews by location
   - Review creation Screen
      - (Create/POST) Create a new review object
   - Settings Screen
      - (Read/GET) Query logged in user object
      - (Update/PUT) Update user profile picture and location
