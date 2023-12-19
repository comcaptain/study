# Performance Testing Tool Design Summary

## Backend (Spring Boot)

### Historical Test Management
- **API Endpoints**:
  - `GET /tests`: Retrieve all past performance tests.
  - `DELETE /tests/{testId}`: Delete a specific test by its ID.
  - `PATCH /tests/{testId}`: Rename a specific test.

### Test Execution and Reporting
- **Unique Test ID Generation**: Format `[ConfigurationName]_[Timestamp]`.
- **Logging**: Include the test ID in all log entries.
- **Report Generation**: Save reports in `./[TestResultsRoot]/[TestID]/`.
- **Snapshot Saving**: Store testing program version and configuration in each test result folder.

### Real-Time Updates via WebSocket
- Update internal cache with test metrics.
- Notify frontend upon cache changes.

## Frontend (React + TypeScript)

### Historical Tests Display and Management
- **List Historical Tests**: Display all past tests with options to group by year/month.
- **Grouping Functionality**: Allow users to view tests in collapsible sections or tabs based on time.
- **Deletion and Renaming**: UI controls for deleting and renaming tests.
- **Pagination/Infinite Scrolling**: For handling large data sets.

### Test Monitoring and Configuration
- **Real-Time Test Status**: Display metrics like number of messages sent/received and message rate.
- **Configuration Management**: UI for CRUD operations on test configurations.

### WebSocket Integration
- Implement native WebSocket for real-time updates.
- Custom logic for auto-reconnection.

## Development Considerations

- **File Management**: Efficient handling of configuration and test result files.
- **Error Handling**: Robust error handling, especially for file and WebSocket operations.
- **User Feedback**: Immediate UI feedback for user actions.
- **Testing and Documentation**: Thorough testing of all components and maintaining clear documentation.
