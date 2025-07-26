# Salesforce Permission Comparator

A modernized web application for comparing Salesforce permissions across users, profiles, and permission sets. Built with Spring Boot 3.x and Java 17 for easy deployment on Heroku.

## Features
- 🔐 **User-provided Authentication** - Users enter their own Salesforce Connected App credentials
- 🏢 **Multi-Environment Support** - Production, Sandbox, and Custom Domain authentication
- 👥 **User Comparison** - Compare permissions across Salesforce users
- 🛡️ **Permission Set Analysis** - Analyze and compare permission sets
- 📋 **Profile Comparison** - Compare profile-based permissions
- 🚀 **Heroku Ready** - One-click deployment to Heroku

## Live Demo
The application is deployed at: [https://perm-comparator-reloaded-ef9f76256418.herokuapp.com](https://perm-comparator-reloaded-ef9f76256418.herokuapp.com)

## How to Use

### 1. Set Up Salesforce Connected App
Before using the application, you need to create a Connected App in Salesforce:

1. **In Salesforce Setup:**
   - Go to **Setup** → **App Manager** → **New Connected App**
   - Fill in basic information (Connected App Name, API Name, Contact Email)
   - Check **"Enable OAuth Settings"**
   - Add **Callback URLs**:
     ```
     https://perm-comparator-reloaded-ef9f76256418.herokuapp.com/login/oauth2/code/salesforce-prod
     https://perm-comparator-reloaded-ef9f76256418.herokuapp.com/login/oauth2/code/salesforce-sandbox
     ```
   - Select **OAuth Scopes**: `Full access (full)` and `Refresh token (refresh_token)`
   - Save the Connected App

2. **Get Your Connected App Credentials:**
   - Go to **App Manager** and click **"View"** on your Connected App
   - Copy the **Consumer Key** (this becomes your `SALESFORCE_CLIENT_ID`)
   - Copy the **Consumer Secret** (this becomes your `SALESFORCE_CLIENT_SECRET`)

3. **Configure Heroku Environment Variables:**
   ```bash
   heroku config:set SALESFORCE_CLIENT_ID="your_consumer_key_here" --app perm-comparator-reloaded
   heroku config:set SALESFORCE_CLIENT_SECRET="your_consumer_secret_here" --app perm-comparator-reloaded
   ```

### 2. Access the Application
1. Navigate to the application URL: https://perm-comparator-reloaded-ef9f76256418.herokuapp.com
2. Click either:
   - **"Login Production"** - For production Salesforce orgs
   - **"Login Sandbox"** - For sandbox Salesforce orgs
3. You'll be redirected to Salesforce to authenticate
4. After successful login, you'll be returned to the application with access to the API endpoints

### 3. Use Permission Comparison Features
Once authenticated, you can access:
- `/api/users` - List Salesforce users
- `/api/permissionsets` - List permission sets
- `/api/profiles` - List profiles
- `/api/compare/user` - Compare user permissions
- `/api/compare/object` - Compare object permissions
- `/api/compare/setupentity` - Compare setup entity access

## API Endpoints

### Authentication
- `POST /api/oauth/login` - Authenticate with Salesforce credentials
- `GET /api/oauth/me` - Check authentication status
- `POST /api/oauth/logout` - Logout and clear session

### Data Retrieval
- `GET /api/users?search={query}` - List Salesforce users
- `GET /api/permissionsets?search={query}` - List permission sets
- `GET /api/profiles?search={query}` - List profiles

### Comparison
- `GET /api/compare/user?id1={id}&id2={id}&id3={id}&id4={id}` - Compare user permissions
- `GET /api/compare/object?id1={id}&id2={id}&id3={id}&id4={id}` - Compare object permissions
- `GET /api/compare/setupentity?id1={id}&id2={id}&id3={id}&id4={id}` - Compare setup entity access

## Local Development

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Setup
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd perm-comparator
   ```

2. Build and run:
   ```bash
   mvn clean package
   java -jar target/perm-comparator-1.0.0.jar
   ```

3. Open your browser to `http://localhost:8080`

## Heroku Deployment

### Automatic Deployment
1. **Fork this repository**
2. **Connect to Heroku:**
   - Create a new Heroku app
   - Connect your GitHub repository
   - Enable automatic deploys

### Manual Deployment
1. **Install Heroku CLI** and login:
   ```bash
   heroku login
   ```

2. **Create Heroku app:**
   ```bash
   heroku create your-app-name
   ```

3. **Deploy:**
   ```bash
   git push heroku main
   ```

### Configuration Files
The application includes the necessary Heroku configuration:
- `Procfile` - Specifies how to run the application
- `system.properties` - Sets Java 17 runtime
- `pom.xml` - Maven configuration with Spring Boot plugin

## Architecture

### Technology Stack
- **Backend**: Spring Boot 3.2.6, Java 17
- **Security**: Spring Security (session-based authentication)
- **Build Tool**: Maven
- **Deployment**: Heroku
- **Frontend**: HTML, CSS, JavaScript (jQuery)

### Authentication Flow
1. User provides Salesforce Connected App credentials through web form
2. Application uses OAuth 2.0 Client Credentials flow to authenticate with Salesforce
3. Access tokens are stored in HTTP session
4. Subsequent API calls use session-stored tokens

### Key Components
- **Controllers**: Handle HTTP requests and responses
- **Services**: Business logic for Salesforce API interactions
- **Security Configuration**: Manages authentication and authorization
- **Static Resources**: Web interface for user interactions

## Security Considerations
- Credentials are only stored in user sessions (not persisted)
- All API endpoints require authentication
- HTTPS is enforced in production (Heroku)
- Client secrets are handled securely

## Troubleshooting

### Common Issues

1. **Login buttons don't work / nothing happens when clicking them:**
   - **Solution**: You need to set up the Salesforce Connected App and environment variables first
   - Visit `/setup.html` on your deployed app for detailed setup instructions
   - Make sure both `SALESFORCE_CLIENT_ID` and `SALESFORCE_CLIENT_SECRET` are set in Heroku

2. **"redirect_uri_mismatch" error:**
   - **Solution**: Check that your Connected App callback URLs exactly match:
     ```
     https://perm-comparator-reloaded-ef9f76256418.herokuapp.com/login/oauth2/code/salesforce-prod
     https://perm-comparator-reloaded-ef9f76256418.herokuapp.com/login/oauth2/code/salesforce-sandbox
     ```

3. **"invalid_client_id" or "invalid_client" error:**
   - **Solution**: Verify your `SALESFORCE_CLIENT_ID` and `SALESFORCE_CLIENT_SECRET` are correct
   - Check that your Connected App is saved and deployed

4. **"insufficient_scope" error:**
   - **Solution**: Ensure your Connected App has these OAuth Scopes:
     - `Full access (full)`
     - `Refresh token (refresh_token)`

### Getting Help
- Check Heroku logs: `heroku logs --tail --app perm-comparator-reloaded`
- Visit the setup page: `https://perm-comparator-reloaded-ef9f76256418.herokuapp.com/setup.html`
- Verify your Connected App settings in Salesforce Setup → App Manager

## Contributing
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## License
MIT License - see LICENSE file for details  
