package org.msahir.plugin;

import org.msahir.model.Seat;
import org.msahir.model.Show;

import java.util.List;

public interface SeatLockPlugin {

    void lockSeats(Show show, List<Seat> seat, String user);
    void unlockSeats(Show show, List<Seat> seat, String user);
    boolean validateLock(Show show, Seat seat, String user);

    List<Seat> getLockedSeats(Show show);
}
