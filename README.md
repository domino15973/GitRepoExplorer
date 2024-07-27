# GitRepoExplorer
GitRepoExplorer is a Spring Boot application that fetches and displays GitHub repositories and their branches for a given user. It uses WebClient for making asynchronous HTTP requests.

## Requirements
Before you begin, ensure you have the following installed on your local machine:
- Java 21
- Gradle
- Git

## Getting Started
Follow these steps to set up and run the application.

### Clone the repository
```bash
git clone https://github.com/domino15973/GitRepoExplorer.git
```
```bash
cd GitRepoExplorer
```

### Configuration
GitHub Token (Optional):
- The application can run without a [GitHub token](https://github.com/settings/tokens), but it is recommended to use one to avoid rate limits. Find a file named [application.properties](src/main/resources/application.properties) and add the following line:
  - `github.token=YOUR_GITHUB_TOKEN`
- Replace `YOUR_GITHUB_TOKEN` with your actual GitHub token. 

### Build the project
```bash
./gradlew.bat build
```

### Run the project
```bash
./gradlew.bat bootRun
```

## Access the API
The application runs on http://localhost:8080. Use the following endpoint to fetch repositories and their branches for a specific GitHub user:
- GET http://localhost:8080/api/github/repos/{username} 

Replace `{username}` with the GitHub username you want to fetch repositories for.

### Example
To fetch repositories for the user domino15973, you can use:
- GET http://localhost:8080/api/github/repos/domino15973