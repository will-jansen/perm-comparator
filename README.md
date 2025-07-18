# Perm Comparator (Modernized)

This is a modernized version of the Salesforce Permission Comparator, now built with Spring Boot and Maven for easy deployment on Heroku.

## Features
- Compare Salesforce users, profiles, and permission sets
- OAuth2 login with Salesforce (not yet implemented)
- Modern Java 17, Spring Boot 3.x
- Deployable to Heroku with a single click

## API Endpoints (Stubbed)
- `/api/users` - List users
- `/api/permissionsets` - List permission sets
- `/api/profiles` - List profiles
- `/api/compare/user` - Compare user permissions
- `/api/compare/object` - Compare object permissions
- `/api/compare/setupentity` - Compare setup entity access

## Local Development

1. Clone the repository
2. Set Salesforce OAuth environment variables (see below)
3. Build and run:
   ```bash
   mvn clean package
   java -jar target/perm-comparator-1.0.0.jar
   ```

## Heroku Deployment

1. Push to Heroku (ensure Java 17 is set in `system.properties`)
2. Heroku will build with Maven and run the Spring Boot app
3. Set Salesforce OAuth environment variables in Heroku config

## Configuration

Set the following environment variables:
- `SALESFORCE_CLIENT_ID`
- `SALESFORCE_CLIENT_SECRET`
- `SALESFORCE_REDIRECT_URI`

## License
MIT  
