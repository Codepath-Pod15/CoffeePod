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

Here's a walkthrough of all implemented user stories:

<img src='CoffeePod Week3 Progress.gif' />

### App Evaluation
- **Category:** Lifestyle
- **Mobile:** This app would be primarily developed for mobile as it is aimed for use at any point in the day, where someone may not have quick access to a desktop or laptop. The camera will also be used to allow the user to upload photos of the location they visit.
- **Story:** This app will allow people to streamline their coffee purchase experience by seeing review information provided by others, and contribute reviews based on the experiences they have.
- **Market:** Any individual who drinks coffee could use this app, which is most people in the USA.
- **Habit:** This app could be used as often as one wants to find a place to get coffee, and many people drink coffee multiple times throughout the day.
- **Scope:** We plan to start with the bare review functionality. After this, the app can evolve to include more social features, such as chatting in a forum about different locations or setting up events with other local users.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

- [x] User is able to login, signup, and logout of the app. The user login is persisted with app restarts.
- [x] User can set up a personal profile with a profile photo, name, email, and zip code.
- [x] User can see a quick summary of coffee locations with a photo, name, rating, and tags.
- [x] User can see detailed information about a coffee location when they click on its summary (name, photo, address, tags, and reviews).
- [x] User can submit reviews with information about their visit to a location (what they ordered, rating, tags, and photo).

**Optional Nice-to-have Stories**

- [ ] User can navigate on a map to see pinpoints for coffee locations.
- [ ] User can share coffee locations with others.
- [ ] User can see foot traffic and busy times for coffee locations.

### 2. Screen Archetypes

* Login/Register
   * User is able to login, signup, and logout of the app. The user login is persisted with app restarts.
* Stream
   * User can see a quick summary of coffee locations with a photo, name, rating, and tags.
* Detail
   * User can see detailed information about a coffee location when they click on its summary (name, photo, address, tags, and reviews).
* Creation
   * User can submit reviews with information about their visit to a location (what they ordered, rating, tags, and photo).
* Settings
   * User can set up a personal profile with a profile photo, name, email, and zip code.

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
   | email          | String          | email address for the user |
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
   | name          | String          | location name |
   | createdAt     | DateTime        | date when review is created (default field) |
   | updatedAt     | DateTime        | date when review is last updated (default field) |

#### Review

   | Property      | Type                | Description  |
   | ------------- | ------------------- | ------------ |
   | objectId      | String              | unique id for the review (default field) |
   | user          | Pointer to User     | review author |
   | location      | Pointer to Location | location being reviewed |
   | image         | File                | image that user uploads with review |
   | order         | String              | what was ordered during the visit |
   | reviewText    | String              | review text posted by the user |
   | rating        | Number              | review rating out of 5 stars |
   | tags          | Relation to Tag     | tags for the location (vibe, cost, etc) |
   | createdAt     | DateTime            | date when review is created (default field) |
   | updatedAt     | DateTime            | date when review is last updated (default field) |

#### Tags

   | Property      | Type            | Description  |
   | ------------- | --------------- | ------------ |
   | objectId      | String          | unique id for the review (default field) |
   | name          | String          | tag name |
   | createdAt     | DateTime        | date when review is created (default field) |
   | updatedAt     | DateTime        | date when review is last updated (default field) |

### Networking
   - Login/Register screen
      - (Create/POST) Register a new user
      - (Read/GET) Authenticate user login
   - Review stream screen
      - (Read/GET) Query all locations
   - Review detail screen
      - (Read/GET) Query all reviews by location
   - Review creation Screen
      - (Create/POST) Create a new review object
      - (Read/GET) Query all tags and locations
   - Settings Screen
      - (Read/GET) Query logged in user object
      - (Update/PUT) Update user profile picture, zip code, name, and email
