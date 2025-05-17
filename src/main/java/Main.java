import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import service.WorkerService;
import service.CustomerService;
import service.TourService;
import DAO.PositionDao;
import DAO.TourDao;
import entities.Worker;
import entities.Customer;
import entities.Tour;
import entities.Booking;
import entities.Review;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static String uniq(String base) {
        return base + "_" + System.currentTimeMillis();
    }

    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpaUnit"); EntityManager em = emf.createEntityManager()) {
            WorkerService ws = new WorkerService(em);
            CustomerService cs = new CustomerService(em);
            TourService ts = new TourService(em);
            PositionDao pd = new PositionDao(em);
            TourDao td = new TourDao(em);

            Scanner in = new Scanner(System.in);

            System.out.println("=== TOUR DEMO ===");
            // 1) создать два тура
            Tour tour1 = ts.createTour(
                    "Mountain Adventure",
                    LocalDate.now().plusDays(7),
                    "Hike in the mountains",
                    20, 1500,
                    "Prague", "Main St.", 10
            );
            System.out.println("Created tour id=" + tour1.getId());
            in.nextLine();

            Tour tour2 = ts.createTour(
                    "City Exploration",
                    LocalDate.now().plusDays(3),
                    "Walking tour of the city",
                    30, 800,
                    "Prague", "Old Town", 5
            );
            System.out.println("Created tour id=" + tour2.getId());
            in.nextLine();

            // 2) обновить описание и вместимость первого тура
            ts.updateTour(
                    tour1.getId(),
                    "Extended mountain hike with guide",
                    18L,
                    null
            );
            System.out.println("Updated tour id=" + tour1.getId());
            in.nextLine();

            // 3) найти туры в Prague
            List<Tour> inPrague = ts.findByCity("Prague");
            System.out.println("Tours in Prague:");
            inPrague.forEach(t -> System.out.println("  " + t.getTourName()));
            in.nextLine();

            // 4) найти туры за ближайшие 10 дней
            List<Tour> upcoming = ts.findByDateRange(
                    LocalDate.now(), LocalDate.now().plusDays(10)
            );
            System.out.println("Upcoming tours:");
            upcoming.forEach(t -> System.out.println("  " + t.getTourName()));
            in.nextLine();

            // 5) показать доступные туры (capacity > bookings)
            List<Tour> available = ts.findAvailableTours();
            System.out.println("Available tours:");
            available.forEach(t ->
                    System.out.printf("  %s (capacity=%d)%n",
                            t.getTourName(), t.getCapacity())
            );
            in.nextLine();

            // 6) резервируем место в первом туре
            boolean reserved = ts.reserveSpot(tour1.getId());
            System.out.println("Reserve spot in tour1: " + reserved);
            in.nextLine();

            // 7) отменяем резерв
            boolean cancelled = ts.cancelReservation(tour1.getId());
            System.out.println("Cancel reservation in tour1: " + cancelled);
            in.nextLine();

            // 8) отменяем второй тур целиком
            ts.cancelTour(tour2.getId());
            System.out.println("Cancelled tour id=" + tour2.getId());
            in.nextLine();

            System.out.println("--- Tours left ---");
            td.findAll().forEach(t ->
                    System.out.printf("%d: %s (%s) cap=%d%n",
                            t.getId(), t.getTourName(),
                            t.getStartDate(), t.getCapacity())
            );
            in.nextLine();

            System.out.println("=== WORKER DEMO ===");
            Worker w = ws.hire(
                    uniq("guide"), uniq("g@mail.com"),
                    "Guide", 40_000, 5
            );
            System.out.println("Hired worker id=" + w.getId());
            in.nextLine();

            ws.addLanguage(w.getId(), "EN");
            ws.raiseSalary(3, 2_000);
            System.out.println("Worker salary now=" + w.getSalary());
            in.nextLine();

            ws.fire(w.getId());
            System.out.println("Fired worker id=" + w.getId());
            in.nextLine();

            System.out.println("=== CUSTOMER DEMO ===");
            Customer c = cs.register(
                    uniq("alice"), uniq("alice@mail.com")
            );
            System.out.println("Registered customer id=" + c.getId());
            in.nextLine();

            Booking b = cs.makeBooking(c.getId(), tour1.getId());
            System.out.println("New booking id=" + b.getId());
            in.nextLine();

            cs.confirmPayment(b.getId());
            System.out.println("Booking status now=" + b.getStatus());
            in.nextLine();

            Review r = cs.leaveReview(
                    c.getId(), tour1.getId(),
                    5, "Fantastic tour!"
            );
            System.out.println("Review id=" + r.getId());
            in.nextLine();

            List<Booking> active = cs.findActiveBookings(c.getId());
            System.out.println("Active bookings for customer: " + active.size());
            in.nextLine();

            cs.deleteCustomer(c.getId());
            System.out.println("Deleted customer id=" + c.getId());
            in.nextLine();
        }
    }
}
