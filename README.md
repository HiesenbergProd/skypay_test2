## Design Questions

### 1. Suppose we put all the functions inside the same service. Is this the recommended approach ? Please explain.

**No**, this is not the recommended approach.

Putting all responsibilities (user management, room management, booking logic, payment handling, printing) into a single service class violates the Single Responsibility Principle (SRP). Such a class would:

* Become large and difficult to maintain
* Be harder to test and reason about
* Couple unrelated business rules together
* Make future changes risky and error-prone

A better approach is to separate responsibilities into focused services (e.g., UserService, BookingService, ...) and use a Facade service to coordinate them.
This improves readability, maintainability, testability, and aligns with SOLID principles.

### 2. In this design, we chose to have a function setRoom(..) that should not impact the previous bookings. What is another way ? What is your recommendation ? Please explain and justify.

Another possible approach is to use room versioning.
Each time a room is updated (for example, price or type changes), a new version of that room is created instead of modifying the existing one. Bookings are linked to the specific room version that was active at the time of reservation.

However, while versioning preserves historical correctness, it has some drawbacks:

* It increases complexity by introducing multiple versions of the same room
* It requires additional logic to manage and query room versions
* It adds overhead when the system does not require full change history tracking

Recommended approach:

Use a snapshot strategy when creating a booking.
At reservation time, store a snapshot of the room’s data (room number, type, and price per night) directly inside the Booking entity.

This ensures:

* Historical accuracy without managing multiple room versions
* Existing bookings remain immutable
* Room updates affect only future bookings
* Simpler and more maintainable design

The snapshot approach provides the same correctness guarantees as versioning while keeping the system easier to understand and maintain, making it a better fit for this use case.

