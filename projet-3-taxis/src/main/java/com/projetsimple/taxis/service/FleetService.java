package com.projetsimple.taxis.service;

import com.projetsimple.taxis.model.Driver;
import com.projetsimple.taxis.model.Ride;
import com.projetsimple.taxis.model.Vehicle;
import com.projetsimple.taxis.repository.DriverRepository;
import com.projetsimple.taxis.repository.RideRepository;
import com.projetsimple.taxis.repository.VehicleRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FleetService {
    private static final double PRICE_PER_KM = 3000.0;
    private static final double WAIT_PER_MIN = 200.0;
    private static final double COMMISSION_RATE = 0.2;

    private final DriverRepository driverRepository = new DriverRepository();
    private final VehicleRepository vehicleRepository = new VehicleRepository();
    private final RideRepository rideRepository = new RideRepository();

    public void addDriver(Driver d) throws SQLException {
        d.setStatus("DISPONIBLE");
        driverRepository.create(d);
    }

    public void addVehicle(Vehicle v) throws SQLException {
        v.setStatus("DISPONIBLE");
        vehicleRepository.create(v);
    }

    public void createRide(Ride ride) throws SQLException {
        Driver d = driverRepository.findFirstAvailable();
        Vehicle v = vehicleRepository.findFirstAvailable();
        if (d == null || v == null) {
            ride.setStatus("EN_ATTENTE");
            ride.setDriverId(null);
            ride.setVehicleId(null);
        } else {
            ride.setStatus("ASSIGNEE");
            ride.setDriverId(d.getId());
            ride.setVehicleId(v.getId());
            driverRepository.updateStatus(d.getId(), "EN_COURSE");
            vehicleRepository.updateStatus(v.getId(), "EN_MISSION");
        }

        double total = (ride.getDistanceKm() * PRICE_PER_KM) + (ride.getWaitMinutes() * WAIT_PER_MIN) + ride.getExtraFees();
        ride.setTotalPrice(total);
        ride.setCompanyCommission(total * COMMISSION_RATE);
        ride.setDriverRevenue(total - (total * COMMISSION_RATE));
        rideRepository.create(ride);
    }

    public void startRide(int rideId) throws SQLException {
        Ride ride = rideRepository.findById(rideId);
        if (ride == null || !"ASSIGNEE".equals(ride.getStatus())) return;
        rideRepository.updateStatus(rideId, "EN_COURS");
    }

    public void finishRide(int rideId) throws SQLException {
        Ride ride = rideRepository.findById(rideId);
        if (ride == null || !"EN_COURS".equals(ride.getStatus())) return;
        rideRepository.updateStatus(rideId, "TERMINEE");
        if (ride.getDriverId() != null) driverRepository.updateStatus(ride.getDriverId(), "DISPONIBLE");
        if (ride.getVehicleId() != null) vehicleRepository.updateStatus(ride.getVehicleId(), "DISPONIBLE");
    }

    public List<Driver> drivers() throws SQLException { return driverRepository.findAll(); }
    public List<Vehicle> vehicles() throws SQLException { return vehicleRepository.findAll(); }
    public List<Ride> rides() throws SQLException { return rideRepository.findAll(); }

    public Map<String, Object> stats() throws SQLException {
        List<Ride> rides = rideRepository.findAll();
        double revenue = rides.stream().map(Ride::getTotalPrice).filter(v -> v != null).mapToDouble(Double::doubleValue).sum();
        double company = rides.stream().map(Ride::getCompanyCommission).filter(v -> v != null).mapToDouble(Double::doubleValue).sum();
        long completed = rides.stream().filter(r -> "TERMINEE".equals(r.getStatus())).count();
        long cancelled = rides.stream().filter(r -> "ANNULEE".equals(r.getStatus())).count();
        Map<String, Object> m = new HashMap<>();
        m.put("revenue", revenue);
        m.put("companyRevenue", company);
        m.put("completed", completed);
        m.put("cancelled", cancelled);
        return m;
    }
}
