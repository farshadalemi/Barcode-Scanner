# Architecture Documentation

## Overview

The Barcode Scanner Android app follows Clean Architecture principles with a clear separation of concerns across three main layers: Presentation, Domain, and Data.

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                       │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │   Compose   │  │ ViewModels  │  │    Navigation       │  │
│  │     UI      │  │             │  │                     │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                     DOMAIN LAYER                            │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │  Use Cases  │  │ Repository  │  │      Entities       │  │
│  │             │  │ Interfaces  │  │                     │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                      DATA LAYER                             │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │ Repository  │  │    Room     │  │      Utilities      │  │
│  │    Impl     │  │  Database   │  │  (Scanner, Excel)   │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

## Layer Details

### Presentation Layer

#### Components
- **Jetpack Compose UI**: Modern declarative UI framework
- **ViewModels**: State management and UI logic
- **Navigation**: Screen navigation using Navigation Compose

#### Responsibilities
- User interface rendering
- User input handling
- State management
- Navigation between screens

#### Key Files
- `MainActivity.kt`: Entry point
- `HomeScreen.kt`: Main navigation hub
- `ScannerScreen.kt`: Barcode scanning interface
- `ProductListScreen.kt`: Product management interface
- `*ViewModel.kt`: State management classes

### Domain Layer

#### Components
- **Use Cases**: Business logic encapsulation
- **Repository Interfaces**: Data access contracts
- **Entities**: Core business models

#### Responsibilities
- Business logic implementation
- Data access abstraction
- Core application rules

#### Key Files
- `AddProductUseCase.kt`: Product addition logic
- `GetAllProductsUseCase.kt`: Product retrieval logic
- `SearchProductsUseCase.kt`: Product search logic
- `ExportToExcelUseCase.kt`: Excel export logic
- `ProductRepository.kt`: Data access interface

### Data Layer

#### Components
- **Repository Implementation**: Data access implementation
- **Room Database**: Local SQLite database
- **Utilities**: Helper classes for scanning and export

#### Responsibilities
- Data persistence
- External service integration
- Utility functions

#### Key Files
- `ProductRepositoryImpl.kt`: Repository implementation
- `AppDatabase.kt`: Room database configuration
- `ProductDao.kt`: Data access object
- `Product.kt`: Database entity
- `BarcodeScanner.kt`: ML Kit integration
- `ExcelExporter.kt`: Apache POI integration

## Design Patterns

### Repository Pattern
- Abstracts data sources from business logic
- Provides clean API for data operations
- Enables easy testing with mock implementations

### Use Case Pattern
- Encapsulates specific business operations
- Single responsibility principle
- Reusable across different UI components

### MVVM Pattern
- Model: Data layer (Repository, Database)
- View: Compose UI components
- ViewModel: Presentation logic and state management

### Dependency Injection
- Manual DI through Application class
- Constructor injection for dependencies
- Simplified approach without external frameworks

## Data Flow

### Scanning Flow
1. User opens Scanner Screen
2. Camera preview starts via CameraX
3. ML Kit analyzes frames for barcodes
4. Detected barcode triggers dialog
5. User fills product details
6. ViewModel calls AddProductUseCase
7. Use Case checks for existing product
8. Repository saves to Room database

### Product Management Flow
1. User opens Product List Screen
2. ViewModel calls GetAllProductsUseCase
3. Use Case retrieves data via Repository
4. Repository queries Room database
5. Data flows back through layers
6. UI displays product list

### Export Flow
1. User triggers export action
2. ViewModel calls ExportToExcelUseCase
3. Use Case gets current product list
4. ExcelExporter creates Excel file
5. File is saved to external storage
6. Share intent allows file distribution

## Database Schema

### Product Table
```sql
CREATE TABLE products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    barcode TEXT NOT NULL,
    name TEXT NOT NULL,
    description TEXT DEFAULT '',
    price REAL DEFAULT 0.0,
    quantity INTEGER DEFAULT 1,
    category TEXT DEFAULT '',
    notes TEXT DEFAULT '',
    timestamp INTEGER NOT NULL
);
```

### Indexes
- Primary key on `id`
- Index on `barcode` for quick lookups
- Index on `name` for search operations

## External Dependencies

### Core Android
- **Jetpack Compose**: Modern UI toolkit
- **Room**: SQLite abstraction layer
- **CameraX**: Camera integration
- **Navigation Compose**: Navigation framework

### Third-Party Libraries
- **ML Kit**: Barcode scanning (offline)
- **Apache POI**: Excel file generation
- **Accompanist**: Compose utilities

## Testing Strategy

### Unit Tests
- Repository implementations
- Use case business logic
- Utility functions
- ViewModel logic

### Integration Tests
- Database operations
- Excel export functionality
- End-to-end workflows

### UI Tests
- Screen navigation
- User interactions
- State management

## Performance Considerations

### Memory Management
- Efficient image processing for scanning
- Proper lifecycle management
- Background thread usage for heavy operations

### Database Optimization
- Indexed columns for fast queries
- Efficient query patterns
- Pagination for large datasets

### UI Performance
- Compose recomposition optimization
- Lazy loading for lists
- Efficient state management

## Security Considerations

### Data Protection
- Local-only data storage
- No network transmission
- User-controlled data export

### Permissions
- Runtime permission requests
- Minimal permission requirements
- Clear permission explanations

## Scalability

### Adding Features
- New use cases for business logic
- Additional UI screens via navigation
- Extended database schema

### Maintenance
- Clear separation of concerns
- Testable architecture
- Documented interfaces

## Future Enhancements

### Potential Improvements
- Dependency injection framework (Hilt/Dagger)
- Network synchronization capabilities
- Advanced search and filtering
- Batch operations
- Custom export templates

### Architecture Evolution
- Modularization by feature
- Multi-module architecture
- Plugin architecture for extensions
