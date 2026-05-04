# API Standards

## RESTful API Conventions

### URL Structure
```
/api/v1/<resource>              # Collection
/api/v1/<resource>/{id}         # Individual resource
/api/v1/<resource>/{id}/<sub>   # Sub-resource
```

### HTTP Methods
| Method | Usage | Idempotent |
|--------|-------|-----------|
| GET | Read resource(s) | Yes |
| POST | Create resource | No |
| PUT | Full update | Yes |
| PATCH | Partial update | Yes |
| DELETE | Remove resource | Yes |

### Response Format
```json
{
  "data": { ... },
  "meta": {
    "timestamp": "2026-01-01T00:00:00Z",
    "requestId": "uuid"
  }
}
```

### Error Format
```json
{
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Human-readable description",
    "details": [ ... ]
  },
  "meta": {
    "timestamp": "2026-01-01T00:00:00Z",
    "requestId": "uuid"
  }
}
```

### HTTP Status Codes
| Code | Usage |
|------|-------|
| 200 | Success |
| 201 | Created |
| 204 | No Content (successful delete) |
| 400 | Bad Request (validation) |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 409 | Conflict |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |

### Pagination
```
GET /api/v1/approvals?page=1&size=20&sort=createdAt,desc
```

Response includes:
```json
{
  "data": [ ... ],
  "page": { "number": 1, "size": 20, "totalElements": 100, "totalPages": 5 }
}
```

### Versioning
API version is in the URL path: `/api/v1/`, `/api/v2/`

### Authentication
All endpoints require a valid Bearer token in the `Authorization` header (except health checks).
